#include "typecheck.h"
extern int outputStage;

data_type_t td(node_t* root);
data_type_t te(node_t* root);
data_type_t tv(node_t* root);

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

int equal_types(data_type_t a, data_type_t b)
{
	return a.base_type == b.base_type;
}

data_type_t typecheck_default(node_t* root)
{
	return td(root);
}

data_type_t typecheck_expression(node_t* root)
{
	data_type_t toReturn;

	if(outputStage == 10)
		fprintf( stderr, "Type checking expression %s\n", root->expression_type.text);

	toReturn = te(root);
	
	//Insert additional checking here

	// Function
	if(root->expression_type.index == FUNC_CALL_E){
		if(root->children[1]){
			
			// Check that number of parameters match signature
			if(!root->function_entry->nArguments == root->children[1]->n_children){ type_error(root); }
			
			// Check that function arguments match signature
			for(int i = 0; i < root->function_entry->nArguments; i++){
				node_t * child = root->children[1]->children[i];
 				if(!equal_types(child->typecheck(child), root->function_entry->argument_types[i])){type_error(root); }
			}
		}
		else{
			// Parameter count mismatch?
			if(root->function_entry->nArguments){ type_error(root); }
		}

		root->data_type = root->function_entry->return_type;
		return root->function_entry->return_type;
	}

	// Class field
	if(root->expression_type.index == CLASS_FIELD_E){
		return root->children[1]->data_type;
	}

	return toReturn;
	
}

data_type_t typecheck_variable(node_t* root){
	return tv(root);
}

data_type_t typecheck_assignment(node_t* root)
{
	node_t* left = root->children[0];
	node_t* right = root->children[1];
	if(!equal_types(left->typecheck(left), right->typecheck(right))){ type_error(root); }
	return left->typecheck(left);
}
