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

"CLASS"						{ RETURN( _CLASS_ ); }
"THIS"						{ RETURN( THIS ); }
"HAS"						{ RETURN( HAS ); }
"WITH"						{ RETURN( WITH ); }
"NEW"						{ RETURN( NEW ); }


"FUNC"						{ RETURN( FUNC ); }
"START"						{ RETURN( START ); }
"PRINT"						{ RETURN( PRINT ); }
"RETURN"					{ RETURN( RETURN ); }
"IF"						{ RETURN( IF ); }
"THEN"						{ RETURN( THEN ); }
"ELSE"						{ RETURN( ELSE ); }
"END"						{ RETURN( END ); }
"WHILE"						{ RETURN( WHILE ); }
"DO"						{ RETURN( DO ); }



"TRUE"						{ RETURN( TRUE_CONST ); }
"FALSE"						{ RETURN( FALSE_CONST ); }
{DIGIT}+					{ RETURN( INT_CONST ); }
{DIGIT}+\.{DIGIT}+			{ RETURN( FLOAT_CONST ); }
"\""(\\.|[^\"\n])*"\"" 				{ RETURN( STRING_CONST ); }


":="							{ RETURN( ASSIGN ); }
"=="							{ RETURN( EQUAL );}
">="						{ RETURN( GEQUAL );}
"<="						{ RETURN( LEQUAL );}
"!="						{ RETURN( NEQUAL );}
"&&"						{ RETURN( AND );}
"||"						{ RETURN( OR );}

"+"						{ RETURN( '+' );}
"-"						{ RETURN( '-' );}
"*"						{ RETURN( '*' );}
"/"						{ RETURN( '/' );}
"<"						{ RETURN( '<' );}
">"						{ RETURN( '>' );}
					


"."						{ RETURN( '.' );}
","						{ RETURN( ',' );}
":"						{ RETURN( ':' );}
";"						{ RETURN( ';' );}
"("						{ RETURN( '(' );}
")"						{ RETURN( ')' );}
"="						{ RETURN( '=' );}



"INT"						{ RETURN( INT ); }
"FLOAT"						{ RETURN( FLOAT ); }
"BOOL"						{ RETURN( BOOL ); }
"VOID"						{ RETURN( VOID ); }





"//"[^\n]*\n			{}

{LETTER}{ALPHANUMERIC}*		{ RETURN( IDENTIFIER ); }
{WHITESPACE}+				{}
%%
