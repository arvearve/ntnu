#include <stdint.h>
#include <stdbool.h>
#include "tree.h"


//Precompiled solutions to previous assignment
void gp(node_t*,int);
void gf(node_t*,int);
void gd(node_t*,int);
void ge(node_t*,int);
void gv(node_t*,int);
void ga(node_t*,int);
void gr(node_t*,int);
void gc(node_t*,int);

/* Instructions */
typedef enum {
	COMMMENT, STRING, LABEL, PUSH, POP, MOVE,MOVES,MOVED, CALL, SYSCALL, LEAVE, RET,
	ADD, SUB, MUL, DIV, JUMP,  DECL, NEG, NIL,FCMP,
	CMP, JUMPEQ,JUMPNE, JUMPZERO, JUMPNONZ,CVTSD, SET, LOAD, STORE, LOADS, STORES,
	FADD, FSUB, FMUL, FDIV, MOVE32, MOVGE, MOVGT, MOVLE, MOVLT, MOVEQ, MOVNE
} opcode_t;

/* Registers */
// stackpointer = r13 = sp, framepointer = r11 / r7 = fp, linkregister (return address) = r14 = lr
static char
	*lr = "lr", *r0 = "r0", *r1 = "r1", *r2 = "r2", *r3 = "r3",
	*fp = "fp", *sp = "sp", *r5 = "r5", *r6 = "r6",
	*d0 = "d0", *d1="d1", *s0 = "s0", *s1 = "s1", *pc = "pc";


/* A struct to make linked lists from instructions */
typedef struct instr {
	opcode_t opcode;
	char *operands[3];
	int offsets[2];
	struct instr *next;
} instruction_t;

/*
 * These macros set implement a function to start/stop the program, with
 * the only purpose of making the call on the first defined function appear
 * exactly as all other function calls.
 */

#define TEXT_HEAD_ARM() do {\
    /*instruction_add ( STRING,       STRDUP(".thumb_func"), NULL, 0, 0 );*/\
    instruction_add ( STRING,       STRDUP("main:"), NULL, 0, 0 );      \
    instruction_add ( PUSH,         lr, NULL, 0, 0 );                   \
    instruction_add ( PUSH,         fp, NULL, 0, 0 );                   \
    instruction_add ( MOVE,         fp, sp, 0, 0 );                     \
    /*instruction_add ( MOVE,         esp, esi, 8, 0 );*/               \
    instruction_add ( MOVE,         r5, r0, 0, 0 );                     \
    instruction_add ( DECL,         r5, NULL, 0, 0 );                   \
    instruction_add ( JUMPZERO,     STRDUP("noargs"), NULL, 0, 0 );     \
    instruction_add ( MOVE,         r6, r1, 0, 0 );                     \
    instruction_add ( STRING,       STRDUP("pusharg:"), NULL, 0, 0 );   \
    instruction_add ( MOVE,         r6, r0, 4, 0 );                     \
    instruction_add3 ( ADD,          r6,r6,STRDUP("#4"));           \
    instruction_add ( MOVE,         r1, STRDUP("#0"), 0, 0 );           \
    instruction_add ( MOVE,         r2, STRDUP("#10"), 0, 0 );          \
    instruction_add ( SYSCALL,      STRDUP("strtol"), NULL, 0, 0 );     \
    /*instruction_add ( ADD,          STRDUP("#12"), esp, 0, 0 ); */    \
    instruction_add ( PUSH,         r0, NULL, 0, 0 );                   \
    instruction_add ( DECL,         r5, NULL, 0, 0 );                   \
    instruction_add ( JUMPNONZ,     STRDUP("pusharg"), NULL, 0, 0 );    \
    instruction_add ( STRING,       STRDUP("noargs:"), NULL, 0, 0 );    \
} while ( false )

#define TEXT_TAIL_ARM() do {\
    instruction_add ( MOVE,         sp, fp, 0, 0 );       \
    instruction_add ( POP,          fp, NULL, 0, 0 );      \
    instruction_add ( SYSCALL, STRDUP("exit"), NULL, 0, 0 );\
    instruction_add ( STRING,  STRDUP(".end"), NULL, 0, 0 );\
} while ( false )

#define TEXT_DEBUG_FUNC_ARM() do {\
	instruction_add ( STRING,       STRDUP("debugprint:"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tpush {r0-r11, lr}"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovw	r0, #:lower16:.DEBUG"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovt	r0, #:upper16:.DEBUG"), NULL, 0, 0 );      \
	instruction_add ( SYSCALL, STRDUP("printf"), NULL, 0, 0 );\
	instruction_add ( STRING,       STRDUP("\tpop {r0-r11, pc}"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("debugprint_r0:"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tpush {r0-r11, lr}"), NULL, 0, 0 );      \
	instruction_add ( MOVE,         r1, r0, 0, 0 );                     \
	instruction_add ( STRING,       STRDUP("\tmovw	r0, #:lower16:.DEBUGINT"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovt	r0, #:upper16:.DEBUGINT"), NULL, 0, 0 );      \
	instruction_add ( SYSCALL, STRDUP("printf"), NULL, 0, 0 );\
	instruction_add ( STRING,       STRDUP("\tpop {r0-r11, pc}"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("_malloc:"), NULL, 0, 0 );      \
	instruction_add ( PUSH,			lr, NULL, 0,0); \
	instruction_add ( PUSH,			fp, NULL, 0,0); \
	instruction_add ( LOAD,          r0, sp, 0, 8 );                     \
	instruction_add ( SYSCALL, STRDUP("malloc"), NULL, 0, 0 );\
	instruction_add ( POP, fp, NULL, 0,0); \
	instruction_add ( STRING,       STRDUP("\tpop {pc}"), NULL, 0, 0 );      \
} while ( false )

/*
#define TEXT_HEAD() do {\
    instruction_add ( STRING,       STRDUP("main:"), NULL, 0, 0 );      \
    instruction_add ( PUSH,         ebp, NULL, 0, 0 );                  \
    instruction_add ( MOVE,         esp, ebp, 0, 0 );                   \
    instruction_add ( MOVE,         esp, esi, 8, 0 );                   \
    instruction_add ( DECL,         esi, NULL, 0, 0 );                  \
    instruction_add ( JUMPZERO,     STRDUP("noargs"), NULL, 0, 0 );     \
    instruction_add ( MOVE,         ebp, ebx, 12, 0 );                  \
    instruction_add ( STRING,       STRDUP("pusharg:"), NULL, 0, 0 );   \
    instruction_add ( ADD,          STRDUP("$4"), ebx, 0, 0 );          \
    instruction_add ( PUSH,         STRDUP("$10"), NULL, 0, 0 );        \
    instruction_add ( PUSH,         STRDUP("$0"), NULL, 0, 0 );         \
    instruction_add ( PUSH,         STRDUP("(%ebx)"), NULL, 0, 0 );     \
    instruction_add ( SYSCALL,      STRDUP("strtol"), NULL, 0, 0 );     \
    instruction_add ( ADD,          STRDUP("$12"), esp, 0, 0 );         \
    instruction_add ( PUSH,         eax, NULL, 0, 0 );                  \
    instruction_add ( DECL,         esi, NULL, 0, 0 );                  \
    instruction_add ( JUMPNONZ,     STRDUP("pusharg"), NULL, 0, 0 );    \
    instruction_add ( STRING,       STRDUP("noargs:"), NULL, 0, 0 );    \
} while ( false )

#define TEXT_TAIL() do {\
    instruction_add ( LEAVE, NULL, NULL, 0, 0 );            \
    instruction_add ( PUSH, eax, NULL, 0, 0 );              \
    instruction_add ( SYSCALL, STRDUP("exit"), NULL, 0, 0 );\
} while ( false )
*/



/* Prototypes for auxiliaries (implemented at the end of this file) */
static void instructions_print ( FILE *stream );
static void instructions_finalize ( void );
 void instruction_add ( opcode_t op, char *arg1, char *arg2, int off1, int off2 );
 void instruction_add3 ( opcode_t op, char* arg1, char* arg2, char* arg3);
void tracePrint( const char * string, ... );

void gen_default ( node_t *root, int scopedepth );
void gen_PROGRAM ( node_t *root, int scopedepth );
void gen_CLASS (node_t *root, int scopedepth);
void gen_FUNCTION ( node_t *root, int scopedepth );
void gen_BLOCK ( node_t *root, int scopedepth );
void gen_DECLARATION_STATEMENT (node_t *root, int scopedepth);
void gen_PRINT_LIST ( node_t *root, int scopedepth );
void gen_PRINT_ITEM ( node_t *root, int scopedepth );
void gen_PRINT_STATEMENT (node_t* root, int scopedepth);
void gen_EXPRESSION ( node_t *root, int scopedepth );
void gen_VARIABLE ( node_t *root, int scopedepth );
void gen_INTEGER ( node_t *root, int scopedepth );
void gen_CONSTANT (node_t *root, int scopedepth);
void gen_ASSIGNMENT_STATEMENT ( node_t *root, int scopedepth );
void gen_RETURN_STATEMENT ( node_t *root, int scopedepth );
void gen_int_expression(node_t* root, int scopedepth);
void gen_float_expression(node_t* root, int scopedepth);
void gen_string_expression(node_t* root, int scopedepth);
void gen_bool_expression(node_t* root, int scopedepth);


// ps6 :
void gen_WHILE_STATEMENT ( node_t *root, int scopedepth );
void gen_FOR_STATEMENT ( node_t *root, int scopedepth );
void gen_IF_STATEMENT ( node_t *root, int scopedepth );
void gen_CONTINUE_STATEMENT ( node_t *root, int scopedepth );
void gen_BREAK_STATEMENT ( node_t *root, int scopedepth );

int peephole;
