#ifndef SYMTAB_H
#define SYMTAB_H


#include <stdio.h>
#include <stdint.h>
#include <string.h>

typedef enum  {NO_TYPE=0, INT_TYPE, FLOAT_TYPE, BOOL_TYPE, STRING_TYPE, VOID_TYPE, CLASS_TYPE, DOUBLE_TYPE, ARRAY_TYPE}
base_data_type_t;

typedef struct data_type{
	base_data_type_t base_type;
	char* class_name;
	int n_dimensions;
	int* dimensions;
	base_data_type_t array_type;
} data_type_t;

#define HASH_BUCKETS 8
typedef int hash_t;

typedef struct {
    int stack_offset, depth;
    char *label;
    data_type_t type;
} symbol_t;

typedef struct {
	char* label;
	data_type_t return_type;
	int nArguments;
	data_type_t* argument_types;
} function_symbol_t;

typedef struct {
	int size;
	hash_t* symbols;
	hash_t* functions;
} class_symbol_t;


#endif
