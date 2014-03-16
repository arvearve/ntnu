#include "typecheck.h"
extern int outputStage;

void type_error(node_t* root){
	fprintf(stderr, "Type error at:\n");
	if(root != NULL){
		fprintf(stderr,"%s", root->nodetype.text);
		if(root->nodetype.index == EXPRESSION){
			fprintf(stderr," (%s)", root->expression_type.text);
		}
		fprintf(stderr,"\n");
	}
	fprintf(stderr,"Exiting\n");
	exit(-1);
}



data_type_t typecheck_default(node_t* root)
{

}

data_type_t typecheck_expression(node_t* root)
{

	if(outputStage == 10)
		fprintf( stderr, "Type checking expression %s\n", root->expression_type.text);

	
}

data_type_t typecheck_variable(node_t* root)
{

}
