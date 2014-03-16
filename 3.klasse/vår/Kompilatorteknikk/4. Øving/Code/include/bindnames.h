#include <stdint.h>
#include "tree.h"

int bind_names_smart ( node_t *root, int stackOffset);

int bind_default ( node_t *root, int stackOffset);
int bind_block ( node_t *root, int stackOffset);
int bind_function ( node_t *root, int stackOffset);
int bind_declaration_list ( node_t *root, int stackOffset);
int bind_function_list ( node_t *root, int stackOffset);
int bind_constant ( node_t *root, int stackOffset);
int bind_declaration ( node_t *root, int stackOffset);
int bind_class(node_t* root, int stackOffset);
int bind_variable ( node_t *root, int stackOffset);
int bind_expression( node_t* root, int stackOffset);

function_symbol_t* create_function_symbol(node_t* function_node);
symbol_t* create_symbol(node_t* declaration_node, int stackOffset);
