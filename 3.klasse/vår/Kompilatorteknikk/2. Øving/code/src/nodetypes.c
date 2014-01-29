#include <nodetypes.h>

/* Root node: program */
const nodetype_t
    program_n = { PROGRAM, "PROGRAM" };

/* Node types for the lists */
const nodetype_t
    function_list_n    = { .index=FUNCTION_LIST,    .text="FUNCTION_LIST" },
    statement_list_n   = { .index=STATEMENT_LIST,   .text="STATEMENT_LIST" },
    expression_list_n  = { .index=EXPRESSION_LIST,  .text="EXPRESSION_LIST" },
    variable_list_n    = { .index=VARIABLE_LIST,    .text="VARIABLE_LIST" },
    argument_list_n    = { .index=ARGUMENT_LIST,    .text="ARGUMENT_LIST" },
    parameter_list_n   = { .index=PARAMETER_LIST,   .text="PARAMETER_LIST" },
    declaration_list_n = { .index=DECLARATION_LIST, .text="DECLARATION_LIST"}, 
    class_list_n       = { .index=CLASS_LIST,       .text="CLASS_LIST"};

/* Function declarations */
const nodetype_t function_n = { .index=FUNCTION, .text="FUNCTION" };

/* Class declarations */
const nodetype_t class_n    = { .index=CLASS, .text="CLASS" };

/* Statements and blocks */
const nodetype_t
    assignment_statement_n = { .index=ASSIGNMENT_STATEMENT, .text="ASSIGNMENT_STATEMENT" },
    statement_n        = { .index=STATEMENT,        .text="STATEMENT" },
    return_statement_n = { .index=RETURN_STATEMENT, .text="RETURN_STATEMENT" },
    print_statement_n  = { .index=PRINT_STATEMENT,  .text="PRINT_STATEMENT" },
    if_statement_n     = { .index=IF_STATEMENT,     .text="IF_STATEMENT" },
    while_statement_n  = { .index=WHILE_STATEMENT,  .text="WHILE_STATEMENT" },
    declaration_statement_n  = { .index=DECLARATION_STATEMENT,  .text="DECLARATION_STATEMENT" };

/* Expressions and terminals */
const nodetype_t
    print_item_n  = { .index=PRINT_ITEM,  .text="PRINT_ITEM" },
    expression_n  = { .index=EXPRESSION,  .text="EXPRESSION" },
    variable_n    = { .index=VARIABLE,    .text="VARIABLE" },
    constant_n    = { .index=CONSTANT,     .text="CONSTANT" }, 
    type_n        = { .index=TYPE,     .text="TYPE" };
    ;

const expression_type_t
	add_e = 	{ .index=ADD_E, 	.text="+" },
	sub_e = 	{ .index=SUB_E, 	.text="-" },
	mul_e = 	{ .index=MUL_E, 	.text="*" },
	div_e = 	{ .index=DIV_E, 	.text="/" },
	lequal_e = 	{ .index=LEQUAL_E, 	.text="<=" },
	gequal_e = 	{ .index=GEQUAL_E, 	.text=">=" },
	equal_e = 	{ .index=EQUAL_E, 	.text="==" },
	nequal_e = 	{ .index=NEQUAL_E, 	.text="!=" },
	less_e = 	{ .index=LESS_E, 	.text="<" },
	greater_e = { .index=GREATER_E, .text=">" },
	and_e = 	{ .index=AND_E, 	.text="&&" },
	or_e = 		{ .index=OR_E, 		.text="||" },
	not_e = 	{ .index=NOT_E,		.text="!" },
	uminus_e = 	{ .index=UMINUS_E,	.text="UMINUS" },
	func_call_e = 	{ .index=FUNC_CALL_E, 	.text="FUNC_CALL" },
	variable_e = 	{ .index=VARIABLE_E,	.text="VARIABLE" },
	new_e = 	{ .index=NEW_E, 	.text="NEW" },
	this_e = 	{ .index=THIS_E, 	.text="THIS" },
	meth_call_e = 	{ .index=METH_CALL_E, 	.text="METH_CALL" },
	class_field_e = { .index=CLASS_FIELD_E, .text="CLASS_FIELD" },
	constant_e = { .index=CONSTANT_E, .text="CONSTANT" },
	default_e = { .index = DEFAULT_E, .text=""};
