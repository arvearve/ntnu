/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

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
/* Tokens.  */
#define INT_CONST 258
#define FLOAT_CONST 259
#define TRUE_CONST 260
#define FALSE_CONST 261
#define STRING_CONST 262
#define INT 263
#define FLOAT 264
#define BOOL 265
#define VOID 266
#define IDENTIFIER 267
#define ASSIGN 268
#define FUNC 269
#define START 270
#define PRINT 271
#define RETURN 272
#define IF 273
#define THEN 274
#define ELSE 275
#define END 276
#define WHILE 277
#define DO 278
#define EQUAL 279
#define GEQUAL 280
#define LEQUAL 281
#define NEQUAL 282
#define AND 283
#define OR 284
#define _CLASS_ 285
#define THIS 286
#define HAS 287
#define WITH 288
#define NEW 289
#define UMINUS 290




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

