#include "vslc.h"

static char *outfile = NULL;
int outputStage = -1;
int arch = 1; //ARM = 1, x86 = 0

static void
options ( int argc, char **argv )
{
    int opt = 0;
    while ( opt != -1 )
    {
        opt = getopt ( argc, argv, "f:o:s:p" );
        switch ( opt )
        {
            case -1:    /* No more options */
                break;

            case 'p':
                peephole = 1;
                break;
                
            case 's': /* Select what to print */
                outputStage = (int) strtol( optarg, NULL, 0);
                break;

            case 'f':   /* Redirect input stream from file */{
                if ( freopen ( optarg, "r", stdin ) == NULL )
                {
                    fprintf (
                        stderr, "Could not open input file '%s'\n", optarg
                    );
                    exit ( EXIT_FAILURE );
                }
                                                             }
                break;

            case 'o':   /* Save filename, redirect stdout when src is ok */{
                outfile = ( STRDUP ( optarg ));
                                                                           }
                break;

            default:    /* Got some option we don't recognize */
                fprintf ( stderr,
                    "Usage: %s [-p] [-v #] [-f infile] [-o] outfile [-t num]\n", argv[0]
                );
                exit ( EXIT_FAILURE );
        }

    }
}


int
main ( int argc, char **argv )
{
	outputStage = 12;

    options ( argc, argv );

    symtab_init ();
    
    /* In order to only scan the tokens we call yylex() directly */
    if ( outputStage == 1 ) {
    	do { } while ( yylex() ); // "Token files"
        exit(0);
    }
    
    /* The parser calls yylex(), match the rules and builds the abstract syntax tree */
    // "BuildTree files"
    yyparse();
    if ( outputStage == 2 ) { 
        exit(0); // Exit if we are only printing this stages debug information. "BuildTree files"
    }
    
    /* Print the abstract syntax tree */
    if ( outputStage == 3 ) {
        node_print ( stderr, root, 0 ); // "Tree files"
        exit(0);
    }

    //simplify_tree ( root );
    /* Assign nodes functions according to their type; Handout first time only? */
    assignFunctionsToNodes( root );
    /* Simplify the abstract syntax tree */
    root->simplify( root, 0 );
    
    if ( outputStage == 4 ) { 
        exit(0); // Exit if we are only printing this stages debug information. "Build Simple Tree files"
    }

    /* Print the abstract syntax tree after simplification "Final Simple Tree files" */
    if ( outputStage == 5 ) {
        node_print ( stderr, root, 0 );
        exit(0);
    }

    //bind_names ( root );
    root->bind_names( root, 0);
    if ( outputStage == 6 || outputStage == 7) {
        exit(0); // Exit if we are only printing this stages debug information. "Scopes&String files" and "Symbol table files"
    }
    
    /* Print the .data (strings) segment of the assembly file */
    if ( outputStage == 8) {
        strings_output(stderr);
        exit(0);
    }
    
    /* Print the entries and string indexes in the node tree "Entries files" */
    if ( outputStage == 9) {
        node_print_entries ( stderr, root, 0 );
        exit(0);
    }
    
    root->typecheck(root);
    if (outputStage == 10) {
    	exit(0);
    }


    /* Parsing and semantics are ok, redirect stdout to file (if requested) */
    if ( outfile != NULL )
    {
        if ( freopen ( outfile, "w", stdout ) == NULL )
        {
            fprintf ( stderr, "Could not open output file '%s'\n", outfile );
            exit ( EXIT_FAILURE );
        }
        free ( outfile );
    }

	root->generate(root, 1);
	/*
	if(outputStage > 10 )
    	generate ( NULL, NULL, root ); // Output nothing, for later debugging stages.
    else if(outputStage == 10 )
    	generate ( NULL, stderr, root ); // Output only the traversal process.
    else if(outputStage == -1 )
    	generate ( stderr, NULL, root ); // Output the asm as no debug text is made.
	*/
	
    //destroy_subtree ( stderr, root );
    
    if ( outputStage == 11 ) {
    	destroy_subtree ( stderr, root );
        exit(0);
    } else
    	destroy_subtree ( NULL, root );
    
    symtab_finalize();
    
    yylex_destroy(); // Free internal data structures of the scanner.

    exit ( EXIT_SUCCESS );
}
