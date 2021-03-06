#include <stdio.h>
#include <stdlib.h>

// A struct for tree nodes, with pointers to the parent and child nodes
// and a int to store the value at the node.
typedef struct Node{
    struct Node* left;
    struct Node* right;
    int value;
} Node;


// Returns a random number between 0 and n
// For simplicity, the random number generator is not seeded,
// hence the same random numbers will be generated on every execution
int get_random_number(int n){
    return rand() % n;
}


// Return a (dynamically allocated) array of length size,
// filled with random numbers between 0 and n
int* create_random_array(int size, int n){
    int* array = malloc(sizeof(int) * size);
    if(array == NULL){ printf("Error: Could not allocate memory.\n"); }
    for(int i = 0; i<size; i++){
        array[i] = get_random_number(n);
    }
    return array;
}


// Should print the contents of array of lenght size
void print_array(int* array, int size){
    printf("\n");
    for(int i=0; i<size; i++){
        printf("%d", array[i]);
    }
}


// Helper function, for prettier sort code
void swap(int *a, int *b){
    //swap trick
    *a ^= *b;
    *b ^= *a;
    *a ^= *b;
}


// Should sort the numbers in array in increasing order
void sort(int* array, int size){
    // Insertion sort
    for(int i=0; i<size; i++){
        int head = i;
        while ( head > 0 && array[head-1] > array[head]){
            swap(&array[head-1], &array[head]);
            head--;
        }
    }

}


// Inserts the node into the tree rooted at the node pointed to by root
void insert_node(Node** root, Node* node){
    // Early return if this is the new root.
    Node* r = *root;
    if( r == NULL){ r = node;}
    if(node->value < r->value){
        if(r->left == NULL){ 
            r->left = node;
        }else{
            insert_node(&(r->left), node);
        }
    }else{
        if(r->right == NULL){
            r->right = node;
        }else{
            insert_node(&(r->right), node);
        }
    }
}


// Searches for the number n in the tree rooted at root.
// Should return 1 if the number is present, and 0 if not.
int search(Node* root, int n){
    if(root == NULL){ return 0;}
    if(root->value == n){ return 1;}
    else if(n < root->value){
        return search(root->left, n);
    }
    else{
        return search(root->right, n);
    }
}


// Returns a dynamically allocated node, with all fields set to NULL/0
Node* create_blank_node(){
    // Alloc structure
    Node* n = malloc(sizeof(Node));
    n->left = malloc(sizeof(Node));
    n->right = malloc(sizeof(Node));
    n->left = NULL;
    n->right = NULL;
    n->value = 0;
    return n;
}


// Builds a tree of all the numbers in an array
Node* create_tree(int* array, int size){
    Node* root = create_blank_node();
    root->value = array[0];
    for(int i=1; i<size; i++){
        Node* node = create_blank_node();
        node->value = array[i];
        insert_node(&root, node);
    }
    return root;
}


// Prints all the nodes of the tree.
void print_tree(Node* node, int offset){
    if(node == NULL){return;}
    printf("\n%*d", offset+1, node->value);
    print_tree(node->left, offset+1);
    print_tree(node->right, offset+1);
}


// Computes x^2
double x_squared(double x){
    return x*x;
}


// Computes x^3
double x_cubed(double x){
    return x*x*x;
}


// Computes the definite integral of the function using the rectangle method
double integrate(double (*function)(double), double start, double end, double stepsize){
    double result = 0.0f;
    double head = start;
    
    // move right with <stepsize> steps, taking care never to go past <end>,
    // taking the funciton value at the middle of the rectangle as it's height.
    while(head<end){
        head += stepsize;
        result += (*function)(head - (stepsize/2)) * stepsize; 
    }
    return result;
}


int main(int argc, char** argv){

    // Creates an array with random values
    int* array = create_random_array(10, 10);

    // Prints the values of the array, e.g:
    // 3 6 7 5 3 5 6 2 9 1
    printf("Random array:");
    print_array(array, 10);

    // Sorts the array
    sort(array, 10);

    // Prints the sorted array, e.g:
    // 1 2 3 3 5 5 6 6 7 9
    printf("\n\nSorted array:");
    print_array(array, 10);

    // Create another random array
    int* new_array = create_random_array(10,10);

    // Print the second array
    printf("\n\nAnother random array:");
    print_array(new_array, 10);


    // Create a tree with the values in the new array
    Node* root = create_tree(new_array, 10);

    // Print the tree
    printf("\n\nPrint tree:");
    print_tree(root, 0);

    // Search for the values 3 and 11 in the tree
    // and print the results
    int found_3 = search(root, 3);
    int found_11 = search(root, 11);
    printf("\n\nFound values 3 and 11: %d, %d", found_3, found_11);

    // Integrate x^2 and x^3 from 0 to 1.
    // Should be approx 1/3 and 1/4.
    printf("\n\nIntegrate x^2: \n%f", integrate(&x_squared, 0, 1, 0.001));
    printf("\n\nIntegrate x^3: \n%f\n", integrate(&x_cubed, 0, 1, 0.001));

    
    return 0;
}




