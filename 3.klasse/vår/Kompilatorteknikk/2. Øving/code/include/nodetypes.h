#ifndef NODETYPES_H
#define NODETYPES_H

//typedef struct node_t Node_t;
/*
 * Enumerated type for describing the various types of tree nodes.
 * Each node is labelled with one of these, to identify its nature
 * when traversing the syntax tree.
 */
typedef enum {
    PROGRAM, FUNCTION,
    FUNCTION_LIST, STATEMENT_LIST, VARIABLE_LIST, 
    EXPRESSION_LIST, PARAMETER_LIST, ARGUMENT_LIST,
    
    STATEMENT, ASSIGNMENT_STATEMENT, RETURN_STATEMENT,
    PRINT_STATEMENT, IF_STATEMENT, WHILE_STATEMENT, DECLARATION_STATEMENT, 
    
    PRINT_ITEM,
    EXPRESSION, VARIABLE, CONSTANT, TYPE,
    
    CLASS_LIST, CLASS, DECLARATION_LIST, // Extention 1
    
} nt_number;


/*
 * Structure for pairing integer and string encodings of node types.
 * Integers make readable code, strings make readable trees - this way, we
 * can pun freely back and forth between the two.
 */
typedef struct {
    nt_number index;
    char *text;
    //Node_t *(*simplify)( Node_t* );
} nodetype_t;


/* Root node: program */
extern const nodetype_t program_n;

/* Node types for the lists */
extern const nodetype_t
    function_list_n, statement_list_n, print_list_n, expression_list_n,
    variable_list_n, argument_list_n, parameter_list_n, 
    declaration_list_n, class_list_n
    ;

/* Function declarations */
extern const nodetype_t function_n;

/* Class declarations */
extern const nodetype_t class_n;


/* Statements and blocks */
extern const nodetype_t
    statement_n, assignment_statement_n, return_statement_n,
    print_statement_n, if_statement_n, while_statement_n, declaration_statement_n ;

/* Expressions and terminals */
extern const nodetype_t
    print_item_n, expression_n, declaration_n, variable_n, constant_n, type_n
    ;

/* Enum to label the different expression nodes */
typedef enum{
	ADD_E, SUB_E, MUL_E, DIV_E, UMINUS_E,
	LEQUAL_E, GEQUAL_E, EQUAL_E, NEQUAL_E, LESS_E, GREATER_E, AND_E, OR_E, NOT_E,
	FUNC_CALL_E, VARIABLE_E, CONSTANT_E,
	THIS_E, METH_CALL_E, CLASS_FIELD_E, NEW_E,
	DEFAULT_E

} et_number;

typedef struct{
	et_number index;
	char* text;
} expression_type_t;

extern const expression_type_t add_e, sub_e, mul_e, div_e, uminus_e,
	lequal_e, gequal_e, equal_e, nequal_e, less_e, greater_e, not_e,
	and_e, or_e,
	func_call_e, variable_e, constant_e,
	new_e,
	this_e, meth_call_e, class_field_e,
	array_index_e,
	read_e,
	toint_e, tofloat_e, tobool_e, todouble_e,
	default_e;


#endif
