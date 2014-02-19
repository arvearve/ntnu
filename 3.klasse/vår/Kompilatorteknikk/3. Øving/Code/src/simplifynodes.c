#include "simplifynodes.h"

extern int outputStage; // This variable is located in vslc.c


// Recursively call simplify on each child.
Node_t* simplify_default ( Node_t *root, int depth ) {
  for(int i = 0; i < root->n_children; i++) {
    
    Node_t * child = root->children[i];
    
    if(child){
      root->children[i] = child->simplify(child, depth+1);
    }

  }

  return root;
}

Node_t *simplify_types ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }

  // Recurse
  simplify_default(root, depth);


  
  if(root->data_type.base_type == CLASS_TYPE) {
    // Lift label
    root->data_type.class_name = STRDUP(root->children[0]->label);
    
    // Prune unnecessary child.
    node_finalize(root->children[0]);
    root->n_children = 0;   
  }
  
  return root;
}


Node_t *simplify_function ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }

  // Recurse
  simplify_default(root, depth);

  // Lift datatype and label
  root->data_type = root->children[0]->data_type;
  root->label = STRDUP(root->children[1]->label);
  root->n_children = 2;

  // Recreate children list with datatype and label nodes removed.
  Node_t **new_children_list = malloc(sizeof(Node_t*) * root->n_children);
  
  for( int i=0; i < root->n_children; i++ ){
    new_children_list[i] = root->children[i+2];
    node_finalize(root->children[i]);
  }

  free(root->children);
  root->children = new_children_list; 
  return root;
}


Node_t *simplify_class( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }

  // Recurse
  simplify_default(root, depth);

  // lift label
  Node_t *label_node = root->children[0];
  root->label = STRDUP(label_node->label);

  // kill label node
  node_finalize(label_node);

  // Fixup child list
  root->n_children = 2;
  root->children[0] = root->children[1];
  root->children[1] = root->children[2];


  // we might have left an empty slot in our array here. Maybe realloc or dropping the whole list and assigning a new one is better?
  return root;
}


Node_t *simplify_declaration_statement ( Node_t *root, int depth ) {
  
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }

  // Recurse
  simplify_default(root, depth);


  // Lift datatype and label
  root->data_type = root->children[0]->data_type;
  root->label = STRDUP(root->children[1]->label);
  node_finalize(root->children[0]);
  node_finalize(root->children[1]);
  root->n_children = 0;
  free(root->children);
  return root;
}


Node_t *simplify_single_child ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }

  // Recurse
  simplify_default(root, depth);

  Node_t * child = root->children[0];
  node_finalize(root);
  
  return child;
}

Node_t *simplify_list_with_null ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }
  
  // Recurse
  simplify_default(root, depth);

  if(root->children[0]) {
    Node_t * child = root->children[0];
    lift_nodes(root, child);
  }
  else{
    Node_t **new_child_list = malloc(sizeof(Node_t*));
    new_child_list[0] = root->children[1];
    free(root->children);
    root->children = new_child_list;
    root->n_children = 1;
  }

  return root;
}


Node_t *simplify_list ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s \n", depth, ' ', root->nodetype.text );
  }
  
 // Recurse
  simplify_default(root, depth);

  Node_t * child = root->children[0];
  lift_nodes(root, child);

  return root;
}

Node_t *simplify_expression ( Node_t *root, int depth ) {
  if(outputStage == 4){
    fprintf ( stderr, "%*cSimplify %s (%s) \n", depth, ' ', root->nodetype.text, root->expression_type.text );
  }
    
  // Recurse
  simplify_default(root, depth);


  if(root->n_children == 1){
    if(!(root->expression_type.index == new_e.index || root->expression_type.index == uminus_e.index || root->expression_type.index == not_e.index)){
      Node_t *child = root->children[0];
      node_finalize(root);
      return child;
    }
    
  }
  return root;
}


void lift_nodes(Node_t *root, Node_t *child) {
  if(root->n_children == 2 
  && child->n_children > 0 
  && child->nodetype.index == root->nodetype.index ) {
    Node_t **new_child_list = malloc(sizeof(Node_t*)*(root->n_children + child->n_children-1));
    for(int i = 0; i < child->n_children; i++){
      new_child_list[i] = child->children[i];
    }
  
    new_child_list[child->n_children] = root->children[root->n_children-1];
    free(root->children);
    root->children = new_child_list;
    root->n_children = child->n_children + root->n_children -1;
    node_finalize(child);
  }
}