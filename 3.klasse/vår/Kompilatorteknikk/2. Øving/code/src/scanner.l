%{
/* This %{ ... %} section is code that is glued into the generated scanner. */
#include "parser.h"
#define YYDEBUG 1
extern int outputStage; // This variable is located in vslc.c
    #define RETURN(t) do {                                             \
        if ( outputStage == 1 )                                        \
            fprintf ( stderr, "TOKEN ( %d,\t'%s' )\n", t, yytext );    \
        return t;                                                      \
    } while ( 0 )
    
    
%}

%option array
%option noyywrap
%option yylineno


LETTER [a-zA-Z_]
ALPHANUMERIC [a-zA-Z0-9_]
DIGIT [0-9]
WHITESPACE [\ \t\n]
%%





"class"						{ RETURN( _CLASS_ ); }
"this"						{ RETURN( THIS ); }
"has"						{ RETURN( HAS ); }
"with"						{ RETURN( WITH ); }
"new"						{ RETURN( NEW ); }

":="							{ RETURN( ASSIGN ); }
"func"						{ RETURN( FUNC ); }
"start"						{ RETURN( START ); }
"print"						{ RETURN( PRINT ); }
"return"					{ RETURN( RETURN ); }
"if"						{ RETURN( IF ); }
"then"						{ RETURN( THEN ); }
"else"						{ RETURN( ELSE ); }
"end"						{ RETURN( END ); }
"while"						{ RETURN( WHILE ); }
"do"						{ RETURN( DO ); }

"="							{ RETURN( EQUAL );}
">="						{ RETURN( GEQUAL );}
"<="						{ RETURN( LEQUAL );}
"!="						{ RETURN( NEQUAL );}
"&&"						{ RETURN( AND );}
"||"						{ RETURN( OR );}
					
"int"						{ RETURN( INT ); }
"float"						{ RETURN( FLOAT ); }
"bool"						{ RETURN( BOOL ); }
"void"						{ RETURN( VOID ); }


"TRUE"						{ RETURN( TRUE_CONST ); }
"FALSE"						{ RETURN( FALSE_CONST ); }
{DIGIT}+					{ RETURN( INT_CONST ); }
{DIGIT}+\.{DIGIT}+			{ RETURN( FLOAT_CONST ); }
"\""[^\n]*"\""				{ RETURN( STRING_CONST ); }


{LETTER}{ALPHANUMERIC}*		{ RETURN( IDENTIFIER ); }

{WHITESPACE}+				{}
%%
