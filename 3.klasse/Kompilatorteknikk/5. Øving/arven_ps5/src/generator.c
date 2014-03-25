#include "generator.h"
extern int outputStage; // This variable is located in vslc.c
static char* currentClass = NULL;
int peephole = 0;

/* Instructions */
typedef enum {
	COMMMENT, STRING, LABEL, PUSH, POP, MOVE,MOVES,MOVED, CALL, SYSCALL, LEAVE, RET,
	ADD, SUB, MUL, DIV, JUMP, JUMPZERO, JUMPNONZ, DECL, CLTD, NEG, CMPZERO, NIL,
	CMP, SETL, SETG, SETLE, SETGE, SETE, SETNE, CBW, CWDE,JUMPEQ,CVTSD, SET, LOAD, STORE, LOADS, STORES,
	FADD, FSUB, FMUL, FDIV, MOVE32
} opcode_t;

/* Registers */
// stackpointer = r13 = sp, framepointer = r11 / r7 = fp, linkregister (return address) = r14 = lr
static char
	*lr = "lr", *r0 = "r0", *r1 = "r1", *r2 = "r2", *r3 = "r3",
	*fp = "fp", *sp = "sp", *r5 = "r5", *r6 = "r6",
	*d0 = "d0", *d1="d1", *s0 = "s0", *s1 = "s1";


/* A struct to make linked lists from instructions */
typedef struct instr {
	opcode_t opcode;
	char *operands[2];
	int offsets[2];
	struct instr *next;
} instruction_t;

/* Start and last element for emitting/appending instructions */
static instruction_t *start = NULL, *last = NULL;


/* Support variables for nested while, for, if and continue.*/
node_t *continue_target;
char *continue_target_label;
int continue_target_depth;
static int while_count = 0;
static int for_count = 0;
static int if_count = 0;

// The counter used for the debug printing.
static int nodeCounter = 0;

/* Provided auxiliaries... */

static void instruction_append ( instruction_t *next )
{
	if ( start != NULL )
	{
		last->next = next;
		last = next;
	}
	else
		start = last = next;
}


static void instruction_add ( opcode_t op, char *arg1, char *arg2, int off1, int off2 )
{
	instruction_t *i = (instruction_t *) malloc ( sizeof(instruction_t) );
	*i = (instruction_t) { op, {arg1, arg2}, {off1, off2}, NULL };
	instruction_append ( i );
}


/*
 * Smart wrapper for "printf". 
 * Makes a comment in the assembly to guide.
 * Also prints a copy to the debug stream if needed.
 */
static void tracePrint( const char * string, ... )
{
	va_list args;
	char buff[1000];
	char buff2[1000];
	// 
	va_start (args, string);
	vsprintf(buff, string, args);
	va_end (args);
	
	sprintf(buff2, "%d %s", nodeCounter++, buff);
	
	if( outputStage == 10 )
    	fprintf(stderr, "%s", buff2);

	instruction_add ( COMMMENT, STRDUP( buff2 ), NULL, 0, 0 );
}


void gen_default ( node_t *root, int scopedepth)
{
	/* Everything else can just continue through the tree */
	if(root == NULL){
		return;
	}

	for ( int i=0; i<root->n_children; i++ )
		if( root->children[i] != NULL )
			root->children[i]->generate ( root->children[i], scopedepth );
}


void gen_PROGRAM ( node_t *root, int scopedepth)
{
	/* Output the data segment */
	if( outputStage == 12 )
		strings_output ( stderr );
	instruction_add ( STRING, STRDUP( ".text" ), NULL, 0, 0 );

	tracePrint("Starting PROGRAM\n");

	gen_default(root, scopedepth);//RECUR();

	TEXT_DEBUG_FUNC_ARM();
	TEXT_HEAD_ARM();
	

	/* Call to the first defined function */
	instruction_add(CALL,root->children[0]->children[0]->label, NULL, 0, 0);

	tracePrint("End PROGRAM\n");

	TEXT_TAIL_ARM();
	
	if( outputStage == 12 )
		instructions_print ( stderr );

}



void gen_FUNCTION ( node_t *root, int scopedepth ) {

    tracePrint ( "Starting FUNCTION (%s) with depth %d\n", root->label, scopedepth);
    
    instruction_add(LABEL, root->label,NULL,0,0);
	instruction_add(PUSH,  lr, NULL,0,0);
	instruction_add(PUSH,  fp, NULL,0,0);
	instruction_add(MOVE, fp ,sp ,0 ,0);
	gen_default(root->children[1], scopedepth);
	instruction_add(MOVE, sp, fp, 0,0);
	instruction_add(POP, fp,NULL,0,0);
	instruction_add(RET,NULL, NULL, 0,0);

	//Leaving the scope, decreasing depth
	tracePrint ("Leaving FUNCTION (%s) with depth %d\n", root->label, scopedepth);
    
}


void gen_DECLARATION_STATEMENT (node_t *root, int scopedepth) {
	tracePrint("Starting DECLARATION: adding space on stack\n");

	instruction_add(PUSH, r0, NULL, 0,0);

	tracePrint("Ending DECLARATION\n");
}


void gen_PRINT_STATEMENT(node_t* root, int scopedepth)
{
	tracePrint("Starting PRINT_STATEMENT\n");

	for(int i = 0; i < root->children[0]->n_children; i++){

		root->children[0]->children[i]->generate(root->children[0]->children[i], scopedepth);

		//Pushing the .INTEGER constant, which will be the second argument to printf,
		//and cause the first argument, which is the result of the expression, and is
		//allready on the stack to be printed as an integer
		base_data_type_t t = root->children[0]->children[i]->data_type.base_type;
		switch(t)
		{
		case INT_TYPE:
			instruction_add(STRING, STRDUP("\tmovw	r0, #:lower16:.INTEGER"), NULL, 0,0);
			instruction_add(STRING, STRDUP("\tmovt	r0, #:upper16:.INTEGER"), NULL, 0,0);
			instruction_add(POP, r1, NULL, 0,0);
			break;

		case FLOAT_TYPE:
			instruction_add(LOADS, sp, s0, 0,0);
			instruction_add(CVTSD, s0, d0, 0,0);
			instruction_add(STRING, STRDUP("\tfmrrd	r2, r3, d0"), NULL, 0,0);
			instruction_add(STRING, STRDUP("\tmovw	r0, #:lower16:.FLOAT"), NULL, 0,0);
			instruction_add(STRING, STRDUP("\tmovt	r0, #:upper16:.FLOAT"), NULL, 0,0);
			
			// And now the tricky part... 8-byte stack alignment :(
			// We have at least 4-byte alignment always.
			// Check if its only 4-byte aligned right now by anding that bit in the stack-pointer.
			// Store the answer in r5, and set the zero flag.
			instruction_add(STRING, STRDUP("\tandS	r5, sp, #4"), NULL, 0,0);
			// Now use the zero flag as a condition to optionally change the stack-pointer
			instruction_add(STRING, STRDUP("\tpushNE	{r5}"), NULL, 0,0);
			break;
			
		case BOOL_TYPE:
			instruction_add(STRING, STRDUP("\tmovw	r0, #:lower16:.INTEGER"), NULL, 0,0);
			instruction_add(STRING, STRDUP("\tmovt	r0, #:upper16:.INTEGER"), NULL, 0,0);
			instruction_add(POP, r1, NULL, 0,0);
			break;

		case STRING_TYPE:
			instruction_add(POP, r0, NULL, 0,0);
			break;

		default:
			instruction_add(PUSH, STRDUP("$.INTEGER"), NULL, 0,0);
			fprintf(stderr, "WARNING: attempting to print something not int, float or bool\n");
			break;
		}
		
		instruction_add(SYSCALL, STRDUP("printf"), NULL,0,0);

		// Undo stack alignment.
		if(t == FLOAT_TYPE) {
			// Redo the zero flag test on r5, as it will give the same answer as the first test on sp.
			instruction_add(STRING, STRDUP("\tandS	r5, #4"), NULL, 0,0);
			// Conditionally remove the alignment. 
			instruction_add(STRING, STRDUP("\tpopNE	{r5}"), NULL, 0,0);
		}
	}

	instruction_add(SET, r0, NULL, 0x0A, 0);
	instruction_add(SYSCALL, STRDUP("putchar"), NULL, 0,0);

	tracePrint("Ending PRINT_STATEMENT\n");
}


void gen_EXPRESSION ( node_t *root, int scopedepth ) {
	
	tracePrint ( "Starting EXPRESSION of type %s\n", (char*) root->expression_type.text);

	switch(root->expression_type.index){
		case FUNC_CALL_E:
			instruction_add(STRING, STRDUP("\tpush\t{r1-r7}"), NULL, 0,0);
			if(root->children[1]){
				root->children[1]->generate(root->children[1], scopedepth);
			}

			instruction_add(CALL, root->children[0]->label, NULL, 0,0 );

			if(root->children[1]){
				for(int i = 0; i < root->children[1]->n_children; i++){
					instruction_add(POP,r1, NULL, 0,0);
				}
			}
			instruction_add(STRING, STRDUP("\tpop \t{r1-r7}"), NULL, 0,0);
			instruction_add(PUSH, r0, NULL, 0, 0);
		default:
			break;
	}

	tracePrint ( "Ending EXPRESSION of type %s\n", (char*) root->expression_type.text);
}


void gen_VARIABLE ( node_t *root, int scopedepth ) {
	
	tracePrint ( "Starting VARIABLE\n");

	instruction_add(LOAD,r0,fp,0, root->entry->stack_offset);
	instruction_add(PUSH,r0, NULL, 0,0);

	tracePrint ( "End VARIABLE %s, stack offset: %d\n", root->label, root->entry->stack_offset);
}


void gen_CONSTANT (node_t * root, int scopedepth) {
	tracePrint("Starting CONSTANT\n");
	
	char buf[30];
	switch (root->data_type.base_type) {
		case INT_TYPE:
			sprintf(buf, "#%d", root->int_const);
			instruction_add(MOVE32, STRDUP(buf), r0, 0, 0);
			break;

		case STRING_TYPE:
			sprintf(buf, "=.STRING%d", root->string_index);
			instruction_add(MOVE32, STRDUP(buf), r0, 0,0);
			break;

		case BOOL_TYPE:
			sprintf(buf, "#%d", root->bool_const);
			instruction_add(MOVE, r0, STRDUP(buf), 0, 0);

		default:
			break;
	}
	instruction_add(PUSH,r0,NULL,0,0);

	tracePrint("End CONSTANT\n");
}


void gen_ASSIGNMENT_STATEMENT ( node_t *root, int scopedepth ) {
	
	tracePrint ( "Starting ASSIGNMENT_STATEMENT\n");

	root->children[1]->generate(root->children[1], scopedepth);
	instruction_add(POP, r0, NULL, 0, 0);
	instruction_add(STORE,r0,fp, 0, root->children[0]->entry->stack_offset);	 

	tracePrint ( "End ASSIGNMENT_STATEMENT\n");
}


void gen_RETURN_STATEMENT ( node_t *root, int scopedepth ) {
	
	tracePrint ( "Starting RETURN_STATEMENT\n");

	gen_default(root, scopedepth);
	instruction_add(POP, r0, NULL, 0, 0);
	
	tracePrint ( "End RETURN_STATEMENT\n");
}


static void instructions_print ( FILE *stream )
{
    instruction_t *this = start;

		while ( this != NULL )
		{
		    switch ( this->opcode ) // ARM
		    {
		        case PUSH:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tpush\t{%s}\n", this->operands[0] );
		            else
		                fprintf ( stream, "\tpushl TODO\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case POP:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tpop\t{%s}\n", this->operands[0] );
		            else
		                fprintf ( stream, "\tpopl TODO\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;

		        case MOVE32:
		        	if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		        		/*
		        		snprintf(buff2, 100, "\tmovw	r0, #:lower16:%s", buff);
		        		instruction_add(STRING, STRDUP(buff2), NULL, 0,0);
		        		snprintf(buff2, 100, "\tmovt	r0, #:upper16:%s", buff);
		        		instruction_add(STRING, STRDUP(buff2), NULL, 0,0);
		        		*/
		        		fprintf ( stream, "\tmovw\t%s, #:lower16:%s\n",
		        		          this->operands[1], this->operands[0]+1
		        		          );
		        		fprintf ( stream, "\tmovt\t%s, #:upper16:%s\n",
		        			      this->operands[1], this->operands[0]+1
		        		          );
		        	break;

		        case MOVE:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tmov\t%s, %s\n",
		                        this->operands[0], this->operands[1]
		                        );
		            //Should not be used, for legacy support only
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tldr\t%s, [%s, #%d]\n", 
		                        this->operands[1], this->operands[0], this->offsets[0]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\tstr\t%s, [%s, #%d]\n",
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            break;
		        
		        
		        case LOAD:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tldr\t%s, [%s]\n",
		                        this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\tldr\t%s, [%s, #%d]\n", 
		                        //this->offsets[0], this->operands[0], this->operands[1] "\tmovl\t%d(%s),%s\n",
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "ERROR, LOAD format not correct\n",
		                        //this->operands[0], this->offsets[1], this->operands[1] "\tmovl\t%s,%d(%s)\n"
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            break;
		        
		        case LOADS:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tflds\t%s, [%s]\n",
		                        this->operands[1], this->operands[0]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tflds\t%s, [%s, #%d]\n", 
		                        //this->offsets[0], this->operands[0], this->operands[1] "\tmovl\t%d(%s),%s\n",
		                        this->operands[1], this->operands[0], this->offsets[0]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "ERROR, LOAD format not correct\n",
		                        //this->operands[0], this->offsets[1], this->operands[1] "\tmovl\t%s,%d(%s)\n"
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            break;
		        
		        
		        case STORE:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tstr\t%s, [%s]\n",
		                        this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[1] != 0 && this->offsets[0] == 0 )
		                fprintf ( stream, "\tstr\t%s, [%s, #%d]\n", 
		                        //this->offsets[0], this->operands[0], this->operands[1] "\tmovl\t%d(%s),%s\n",
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "ERROR, STORE format not correct\n",
		                        //this->operands[0], this->offsets[1], this->operands[1] "\tmovl\t%s,%d(%s)\n"
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            break;
		        
		        case STORES:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tfsts\t%s, [%s]\n",
		                        this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tfsts\t%s, [%s, #%d]\n", 
		                        //this->offsets[0], this->operands[0], this->operands[1] "\tmovl\t%d(%s),%s\n",
		                        this->operands[0], this->operands[1], this->offsets[0]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "ERROR, STORE format not correct\n",
		                        //this->operands[0], this->offsets[1], this->operands[1] "\tmovl\t%s,%d(%s)\n"
		                        this->operands[0], this->operands[1], this->offsets[1]
		                        );
		            break;
		        
		        case SET:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "SET ERROR: set\t%s, %s\n",
		                        this->operands[1], this->operands[0]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tmov\t%s, #%d\n", 
		                        //this->offsets[0], this->operands[0], this->operands[1] "\tmovl\t%d(%s),%s\n",
		                        this->operands[0], this->offsets[0]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\tmovl\t%s,%d(%s)\n",
		                        this->operands[0], this->offsets[1], this->operands[1]
		                        );
		            break;

		        case MOVES:
					if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tmfcpys\t%s, %s\n",
								this->operands[1], this->operands[0]
								);
					else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tError: NOT possible for ARM, use load/store\t%d(%s),%s\n",
								this->offsets[0], this->operands[0], this->operands[1]
								);
					else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
						fprintf ( stream, "\tError: NOT possible for ARM, use load/store\t%s,%d(%s)\n",
								this->operands[0], this->offsets[1], this->operands[1]
								);
					break;

		        case MOVED:
					if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tmfcpyd TODO\t%s,%s\n",
								this->operands[1], this->operands[0]
								);
					else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tError: NOT possible for ARM, use load/store\t%s, [%s,#%d]\n",  
								//this->offsets[0], this->operands[0], this->operands[1]   "\ldr\t%d(%s),%s\n",
								this->operands[1], this->operands[0], this->offsets[0]
								);
					else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
						fprintf ( stream, "\tError: NOT possible for ARM, use load/store\t%s,%d(%s)\n",
								this->operands[0], this->offsets[1], this->operands[1]
								);
					break;

		        case CVTSD:
					if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tfcvtds\t%s,%s\n",
								this->operands[1], this->operands[0]
								);
					else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
						fprintf ( stream, "\tfcvtds TODO\t%d(%s),%s\n",
								this->offsets[0], this->operands[0], this->operands[1]
								);
					else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
						fprintf ( stream, "\tfcvtds TODO\t%s,%d(%s)\n",
								this->operands[0], this->offsets[1], this->operands[1]
								);
					break;



		        case ADD:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tadd\t%s, %s\n",
		                        this->operands[1], this->operands[0]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\taddl\t%d(%s),%s\n",
		                        this->offsets[0], this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\taddl\t%s,%d(%s)\n",
		                        this->operands[0], this->offsets[1], this->operands[1]
		                        );
		            break;
		        
		        case FADD:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tfadds\t%s, %s, %s\n",
		                 		this->operands[1], this->operands[1], this->operands[0]
		                	   );
		            break;
		        
		        case SUB:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tsub\t%s, %s\n",
		                        this->operands[1], this->operands[0]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tsubl\t%d(%s),%s\n",
		                        this->offsets[0], this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\tsubl\t%s,%d(%s)\n",
		                        this->operands[0], this->offsets[1], this->operands[1]
		                        );
		            break;
		        
		         case FSUB:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tfsubs\t%s, %s, %s\n",
		                        this->operands[1], this->operands[1], this->operands[0]
		                        );
		            else
		            	fprintf ( stream, "Not supported...\tfsub\t%s, %s\n",
		            			this->operands[1], this->operands[0]
		                        );
		        	break;
		        
		        case MUL:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmul\t r0, %s\n", this->operands[0] ); 
		            else
		                fprintf ( stream, "Not supported...\tmul\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		         case FMUL:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tfmuls\t %s, %s, %s\n", this->operands[1], this->operands[1], this->operands[0] ); 
		            else
		                fprintf ( stream, "Not supported...\tfmul\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case DIV:
		            if ( this->offsets[0] == 0 &&  this->operands[1] == NULL)
		                fprintf ( stream, "\tsdiv\tr0, r0, %s\n", this->operands[0] );
		            else  if ( this->offsets[0] == 0)
		                fprintf ( stream, "\tsdiv\t%s, %s, %s\n", this->operands[1], this->operands[1], this->operands[0] );
		            else
		                fprintf ( stream, "\tidivl TODO\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case FDIV:
		            if ( this->offsets[0] == 0 &&  this->operands[1] == NULL)
		                fprintf ( stream, "\tfdivs\ts0, s0, %s\n", this->operands[0] );
		            else  if ( this->offsets[0] == 0)
		                fprintf ( stream, "\tfdivs\t%s, %s, %s\n", this->operands[1], this->operands[1], this->operands[0] );
		            else
		                fprintf ( stream, "\tidivl TODO\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case NEG:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tnegl\t%s\n", this->operands[0] );
		            else
		                fprintf ( stream, "\tnegl\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;

		        case DECL:
		            fprintf ( stream, "\tsubs\t%s, #1\n", this->operands[0]); // The s turn on flag updates
		            break;
		        case CLTD:
		            fprintf ( stream, "\t#cltd TODO\n" );
		            break;
		        case CBW:
		            fprintf ( stream, "#\tcbw# Useless on ARM\n" );
		            break;
		        case CWDE:
		            fprintf ( stream, "#\tcwde # Useless on ARM\n" );
		            break;
		        case CMPZERO:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tteq\t%s, $0\n", this->operands[0] ); // Test equal
		            else
		                fprintf ( stream, "\tcmpl\t$0,%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case CMP:
		            if ( this->offsets[0] == 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tcmp\t%s,%s\n",
		                        this->operands[1], this->operands[0]
		                        );
		            else if ( this->offsets[0] != 0 && this->offsets[1] == 0 )
		                fprintf ( stream, "\tcmpl\t%d(%s),%s\n",
		                        this->offsets[0], this->operands[0], this->operands[1]
		                        );
		            else if ( this->offsets[0] == 0 && this->offsets[1] != 0 )
		                fprintf ( stream, "\tcmpl\t%s,%d(%s)\n",
		                        this->operands[0], this->offsets[1], this->operands[1]
		                        );
		            break;
		        case SETL:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmovlt\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsetl\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case SETG:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmovgt\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsetg\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case SETLE:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmovle\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsetle\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case SETGE:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmovge\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsetge\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case SETE:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmoveq\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsete\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;
		        case SETNE:
		            if ( this->offsets[0] == 0 )
		                fprintf ( stream, "\tmov\t %s, #0\t\n\tmovne\t %s, #1\n", this->operands[0], this->operands[0] );
		            else
		                fprintf ( stream, "ERROR: TODO\tsetnq\t%d(%s)\n",
		                        this->offsets[0], this->operands[0]
		                        );
		            break;

		        case CALL: case SYSCALL:
		            fprintf ( stream, "\tbl\t" );
		            if ( this->opcode == CALL )
		                fputc ( '_', stream );
		            fprintf ( stream, "%s\n", this->operands[0] );
		            break;
		        case LABEL: 
		            fprintf ( stream, "_%s:\n", this->operands[0] );
		            break;

		        case JUMP:
		            fprintf ( stream, "\tb\t%s\n", this->operands[0] );
		            break;
		        case JUMPZERO:
		            fprintf ( stream, "\tbeq\t%s\n", this->operands[0] ); // Same as equal on ARM  (?)
		            break;
		        case JUMPEQ:
		            fprintf ( stream, "\tbeq\t%s\n", this->operands[0] );
		            break;
		        case JUMPNONZ:
		            fprintf ( stream, "\tbne\t%s\n", this->operands[0] );// Same as !zero on ARM  (?)
		            break;

		        case LEAVE: 
		        	// Same as "leave"
		        	fprintf ( stream, "\tmov\tsp, fp\n");
		        	fprintf ( stream, "\tpop\t{fp}\n");
		        	break;
		        case RET:   
		        	fprintf ( stream, "\tpop\t{pc}\n");
		        	break;

		        case STRING:
		                    fprintf ( stream, "%s\n", this->operands[0] );
		                    break;
		        
		        case COMMMENT:
		                    fprintf ( stream, "#%s", this->operands[0] );
		                    break;

		        case NIL:
		                    break;

		        default:
		                    fprintf ( stderr, "Error in instruction stream\n" );
		                    break;
		    }
		    this = this->next;

    }
}

