#ifndef SYMTAB_H
#define SYMTAB_H


#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include "ght_hash_table.h"

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
typedef ght_hash_table_t hash_t;

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

char* symbol_to_string(symbol_t* symbol);
char* type_to_string(data_type_t type);
char* function_symbol_to_string(function_symbol_t* fun_symbol);
void function_add(char* key, function_symbol_t* value);
function_symbol_t* function_get(char* key);
void class_add(char *key, class_symbol_t* value);
class_symbol_t* class_get(char* key);
void class_insert_field(char* class_name, char* key, symbol_t* value);
void class_insert_method(char* class_name, char* key, function_symbol_t* value);
function_symbol_t* class_get_method(char* class_name, char* key);
symbol_t* class_get_symbol(char* class_name, char* key);
void symtab_init ( void );
void symtab_finalize ( void );

int strings_add ( char *str );
void strings_output ( FILE *stream );

void scope_add ( void );
void scope_remove ( void );

void symbol_insert ( char *key, symbol_t *value );
symbol_t *symbol_get ( char *key );


#endif
