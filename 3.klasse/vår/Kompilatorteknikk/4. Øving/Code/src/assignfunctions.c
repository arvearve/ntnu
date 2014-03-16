// Simplify tree:
#include "assignfunctions.h"

// Bind names:
#include "bindnames.h"
#include "typecheck.h"


/* Assign nodes functions according to their type */
/* Version with simplify tree and bind names */
void assignFunctionsToNodes ( node_t *root )
{
	if ( root == NULL )
		return;

	// Recursively assign the functions of the children of the current node
	for ( int i=0; i<root->n_children; i++ ){
		assignFunctionsToNodes ( root->children[i] );
	}
	
	
	
	
	// Here we assign the correct functions used by each node for bind names.
	switch ( root->nodetype.index )
	{
		case FUNCTION_LIST: 
			root->bind_names = bind_function_list;
			break;
		case FUNCTION: 
			root->bind_names = bind_function;
			break;
		case DECLARATION_LIST:
			root->bind_names = bind_declaration_list;
			break;
		case DECLARATION_STATEMENT:
			root->bind_names = bind_declaration;
			break;
		case CONSTANT:
			root->bind_names = bind_constant;
			break;
		case CLASS:
			root->bind_names = bind_class;
			break;
		case VARIABLE:
			root->bind_names = bind_variable;
			break;
		case EXPRESSION:
			root->bind_names = bind_expression;
			break;
		default:
			root->bind_names = bind_default;
			break;
	}

	// Here we assign the correct functions used by each node for simplify tree.
	switch ( root->nodetype.index )
	{
		
		case FUNCTION:
			root->simplify =simplify_function;
			break;
		
		
		case STATEMENT_LIST:
		case EXPRESSION_LIST: case VARIABLE_LIST: case CLASS_LIST:
			root->simplify = simplify_list;
			break;

		
		case DECLARATION_LIST: case FUNCTION_LIST:
			root->simplify = simplify_list_with_null;
			break;
		
		
		case DECLARATION_STATEMENT:
			root->simplify = simplify_declaration_statement;
			break;

		
		case STATEMENT: case PARAMETER_LIST: case ARGUMENT_LIST:
			root->simplify = simplify_single_child;
			break;
		
		
		case TYPE:
			root->simplify = simplify_types;
			break;

		case EXPRESSION:
			root->simplify = simplify_expression;
			break;

        case CLASS:
            root->simplify = simplify_class;
            break;
		
		default:
			root->simplify = simplify_default;
			break;
	}
	
	switch( root->nodetype.index )
	{
		case EXPRESSION:
			root->typecheck = typecheck_expression;
			break;
		case VARIABLE:
			root->typecheck = typecheck_variable;
			break;
		default:
			root->typecheck = typecheck_default;
			break;
	}
}


