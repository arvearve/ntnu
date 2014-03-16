#include <stdint.h>
#include <stdbool.h>
#include "tree.h"


/*
 * These macros set implement a function to start/stop the program, with
 * the only purpose of making the call on the first defined function appear
 * exactly as all other function calls.
 */

#define TEXT_HEAD_ARM() do {\
    /*instruction_add ( STRING,       STRDUP(".thumb_func"), NULL, 0, 0 );*/\
    instruction_add ( STRING,       STRDUP("main:"), NULL, 0, 0 );      \
    instruction_add ( PUSH,         lr, NULL, 0, 0 );                   \
    instruction_add ( PUSH,         fp, NULL, 0, 0 );                   \
    instruction_add ( MOVE,         sp, fp, 0, 0 );                     \
    /*instruction_add ( MOVE,         esp, esi, 8, 0 );*/               \
    instruction_add ( MOVE,         r0, r5, 0, 0 );                     \
    instruction_add ( DECL,         r5, NULL, 0, 0 );                   \
    instruction_add ( JUMPZERO,     STRDUP("noargs"), NULL, 0, 0 );     \
    instruction_add ( MOVE,         r1, r6, 0, 0 );                     \
    instruction_add ( STRING,       STRDUP("pusharg:"), NULL, 0, 0 );   \
    instruction_add ( MOVE,         r6, r0, 4, 0 );                     \
    instruction_add ( ADD,          STRDUP("#4"), r6, 0, 0 );           \
    instruction_add ( MOVE,         STRDUP("#0"), r1, 0, 0 );           \
    instruction_add ( MOVE,         STRDUP("#10"), r2, 0, 0 );          \
    instruction_add ( SYSCALL,      STRDUP("strtol"), NULL, 0, 0 );     \
    /*instruction_add ( ADD,          STRDUP("#12"), esp, 0, 0 ); */    \
    instruction_add ( PUSH,         r0, NULL, 0, 0 );                   \
    instruction_add ( DECL,         r5, NULL, 0, 0 );                   \
    instruction_add ( JUMPNONZ,     STRDUP("pusharg"), NULL, 0, 0 );    \
    instruction_add ( STRING,       STRDUP("noargs:"), NULL, 0, 0 );    \
} while ( false )

#define TEXT_TAIL_ARM() do {\
    instruction_add ( MOVE,         ebp, esp, 0, 0 );       \
    instruction_add ( POP,          ebp, NULL, 0, 0 );      \
    instruction_add ( SYSCALL, STRDUP("exit"), NULL, 0, 0 );\
    instruction_add ( STRING,  STRDUP(".end"), NULL, 0, 0 );\
} while ( false )

#define TEXT_DEBUG_FUNC_ARM() do {\
	instruction_add ( STRING,       STRDUP("debugprint:"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tpush {r0-r11, lr}"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovw	r0, #:lower16:.DEBUG"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovt	r0, #:upper16:.DEBUG"), NULL, 0, 0 );      \
	instruction_add ( SYSCALL, STRDUP("printf"), NULL, 0, 0 );\
	instruction_add ( STRING,       STRDUP("\tpop {r0-r11, pc}"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("debugprint_r0:"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tpush {r0-r11, lr}"), NULL, 0, 0 );      \
	instruction_add ( MOVE,         r0, r1, 0, 0 );                     \
	instruction_add ( STRING,       STRDUP("\tmovw	r0, #:lower16:.DEBUGINT"), NULL, 0, 0 );      \
	instruction_add ( STRING,       STRDUP("\tmovt	r0, #:upper16:.DEBUGINT"), NULL, 0, 0 );      \
	instruction_add ( SYSCALL, STRDUP("printf"), NULL, 0, 0 );\
	instruction_add ( STRING,       STRDUP("\tpop {r0-r11, pc}"), NULL, 0, 0 );      \
} while ( false )

#define TEXT_HEAD() do {\
    instruction_add ( STRING,       STRDUP("main:"), NULL, 0, 0 );      \
    instruction_add ( PUSH,         ebp, NULL, 0, 0 );                  \
    instruction_add ( MOVE,         esp, ebp, 0, 0 );                   \
    instruction_add ( MOVE,         esp, esi, 8, 0 );                   \
    instruction_add ( DECL,         esi, NULL, 0, 0 );                  \
    instruction_add ( JUMPZERO,     STRDUP("noargs"), NULL, 0, 0 );     \
    instruction_add ( MOVE,         ebp, ebx, 12, 0 );                  \
    instruction_add ( STRING,       STRDUP("pusharg:"), NULL, 0, 0 );   \
    instruction_add ( ADD,          STRDUP("$4"), ebx, 0, 0 );          \
    instruction_add ( PUSH,         STRDUP("$10"), NULL, 0, 0 );        \
    instruction_add ( PUSH,         STRDUP("$0"), NULL, 0, 0 );         \
    instruction_add ( PUSH,         STRDUP("(%ebx)"), NULL, 0, 0 );     \
    instruction_add ( SYSCALL,      STRDUP("strtol"), NULL, 0, 0 );     \
    instruction_add ( ADD,          STRDUP("$12"), esp, 0, 0 );         \
    instruction_add ( PUSH,         eax, NULL, 0, 0 );                  \
    instruction_add ( DECL,         esi, NULL, 0, 0 );                  \
    instruction_add ( JUMPNONZ,     STRDUP("pusharg"), NULL, 0, 0 );    \
    instruction_add ( STRING,       STRDUP("noargs:"), NULL, 0, 0 );    \
} while ( false )

#define TEXT_TAIL() do {\
    instruction_add ( LEAVE, NULL, NULL, 0, 0 );            \
    instruction_add ( PUSH, eax, NULL, 0, 0 );              \
    instruction_add ( SYSCALL, STRDUP("exit"), NULL, 0, 0 );\
} while ( false )



/* Prototypes for auxiliaries (implemented at the end of this file) */
static void instructions_print ( FILE *stream );
static void instructions_finalize ( void );

void gen_default ( node_t *root, int scopedepth );
void gen_PROGRAM ( node_t *root, int scopedepth );
void gen_CLASS (node_t *root, int scopedepth);
void gen_FUNCTION ( node_t *root, int scopedepth );
void gen_BLOCK ( node_t *root, int scopedepth );
void gen_DECLARATION_STATEMENT (node_t *root, int scopedepth);
void gen_PRINT_LIST ( node_t *root, int scopedepth );
void gen_PRINT_ITEM ( node_t *root, int scopedepth );
void gen_PRINT_STATEMENT (node_t* root, int scopedepth);
void gen_EXPRESSION ( node_t *root, int scopedepth );
void gen_VARIABLE ( node_t *root, int scopedepth );
void gen_INTEGER ( node_t *root, int scopedepth );
void gen_CONSTANT (node_t *root, int scopedepth);
void gen_ASSIGNMENT_STATEMENT ( node_t *root, int scopedepth );
void gen_RETURN_STATEMENT ( node_t *root, int scopedepth );
void gen_int_expression(node_t* root, int scopedepth);
void gen_float_expression(node_t* root, int scopedepth);
void gen_string_expression(node_t* root, int scopedepth);
void gen_bool_expression(node_t* root, int scopedepth);


// ps6 :
void gen_WHILE_STATEMENT ( node_t *root, int scopedepth );
void gen_FOR_STATEMENT ( node_t *root, int scopedepth );
void gen_IF_STATEMENT ( node_t *root, int scopedepth );
void gen_CONTINUE_STATEMENT ( node_t *root, int scopedepth );
void gen_BREAK_STATEMENT ( node_t *root, int scopedepth );

int peephole;
