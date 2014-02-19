#include <stdint.h>
#include "tree.h"
//#include "nodetypes.h"

typedef struct node_t Node_t;

Node_t *simplify_default ( Node_t *root, int depth );
Node_t *simplify_function ( Node_t *root, int depth );
Node_t *simplify_types ( Node_t *root, int depth );
Node_t *simplify_single_child ( Node_t *root, int depth );
Node_t *simplify_declaration_statement ( Node_t *root, int depth );
Node_t *simplify_list_with_null ( Node_t *root, int depth ); // Extension 1
Node_t *simplify_list ( Node_t *root, int depth );
Node_t *simplify_expression ( Node_t *root, int depth );
Node_t *simplify_class( Node_t *root, int depth );


