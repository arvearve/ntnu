/* A Bison parser, made by GNU Bison 2.5.  */

/* Bison interface for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2011 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     INT_CONST = 258,
     FLOAT_CONST = 259,
     TRUE_CONST = 260,
     FALSE_CONST = 261,
     STRING_CONST = 262,
     INT = 263,
     FLOAT = 264,
     BOOL = 265,
     VOID = 266,
     IDENTIFIER = 267,
     ASSIGN = 268,
     FUNC = 269,
     START = 270,
     PRINT = 271,
     RETURN = 272,
     IF = 273,
     THEN = 274,
     ELSE = 275,
     END = 276,
     WHILE = 277,
     DO = 278,
     EQUAL = 279,
     GEQUAL = 280,
     LEQUAL = 281,
     NEQUAL = 282,
     AND = 283,
     OR = 284,
     _CLASS_ = 285,
     THIS = 286,
     HAS = 287,
     WITH = 288,
     NEW = 289,
     UMINUS = 290
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


