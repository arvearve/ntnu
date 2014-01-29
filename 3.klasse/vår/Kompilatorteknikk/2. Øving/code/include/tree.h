#ifndef TREE_H
#define TREE_H

#include <stdbool.h>
#include <stdarg.h>
#include <stdlib.h>
#include "symtab.h"
#include "nodetypes.h"

/*
 * Macro for creating a heap-allocated duplicate of a string.
 * This macro mirrors the function 'strdup' (which is itself a pretty
 * common standard extension by GCC and many others). The function is not
 * part of the C99 standard because it allocates heap memory as a
 * side-effect, so it is reimplemented here in terms of std. calls.
 */
#define STRDUP(s) strncpy ( (char*)malloc ( strlen(s)+1 ), s, strlen(s)+1 )




/*
 * Basic data structure for syntax tree nodes.
 */
typedef struct node_t {
    nodetype_t nodetype;        			// Type of this node
    char *label;           					// Data label for variables
    expression_type_t expression_type;		// If this node is an expression, the kind of expression
    data_type_t data_type;					// The data type, only used for declarations,
    										// the data type for variables is in the symbol table entry.
    int string_index;						// Index of string in string table

    symbol_t *entry;        				// Pointer to symbol table entry
    class_symbol_t* class_entry;			// Pointer to class symbol table entry
    function_symbol_t* function_entry;		// Pointer to function symbol table entry


    int n_children;    		// Number of children
    struct node_t **children;    	// Array of pointers to child nodes
    
    struct node_t *(*simplify)( struct node_t*, int );			// This nodes simplify function
    int (*bind_names)( struct node_t*, int, nt_number );	// This nodes bind_names function
    data_type_t (*typecheck)( struct node_t*);						// This nodes typecheck function
    void (*generate)( struct node_t*, int);						// This nodes generate function
    


    // The value of constant nodes
    union {
    	double double_const;
    	int int_const;
    	float float_const;
    	char char_const;
    	char* string_const;
    	bool bool_const;
    };
} node_t;


/*
 *  Function prototypes: implementations are found in tree.c
 */
node_t * node_init ( nodetype_t type,
		char* label,
		base_data_type_t base_type,
		expression_type_t expression_type,
		int n_children,
		va_list child_list );
void node_print ( FILE *output, node_t *root, int nesting );
void node_print_entries ( FILE *output, node_t *root, int nesting );
void node_finalize ( node_t *discard );

void destroy_subtree ( FILE *output, node_t *discard );
node_t *simplify_tree ( node_t *root );
void bind_names ( node_t *root );

#endif
