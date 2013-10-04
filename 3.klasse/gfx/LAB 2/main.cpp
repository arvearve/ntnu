#include <math.h>
#include <iostream>
#include <ctime>
#include <GL/glew.h>
#if __APPLE__
    #include <GLUT/glut.h>
#else
    #include <GL/freeglut.h>
#endif
#include "glm/glm.hpp"
#include "visuals.h"

#pragma comment(lib, "glew32.lib")
#define GLEW_STATIC
//--------- Global Variables
int g_iWindowWidth = 512;
int g_iWindowHeight = 512;
int g_iGLUTWindowHandle = 0;
int g_iErrorCode = 0;


unsigned char g_eCurrentScene = 1;

//---------- Main program
int main(int argc, char **argv) {

    glutInit(&argc, argv);

    //Setting up  The Display
    //   -RGB color model + Alpha Channel = GLUT_RGBA
    glutInitDisplayMode(GLUT_RGBA|GLUT_DOUBLE);

    // Configure Window Position
    int iScreenWidth = glutGet(GLUT_SCREEN_WIDTH);
    int iScreenHeight = glutGet(GLUT_SCREEN_HEIGHT);

    int windowWdith = 512;
    int windowHeight = 512;


    glutInitDisplayMode( GLUT_RGBA | GLUT_ALPHA | GLUT_DOUBLE | GLUT_DEPTH );

    glutInitWindowPosition( (iScreenWidth - windowHeight)/2 , (iScreenHeight - windowHeight)/2 );
    glutInitWindowSize( windowWdith, windowHeight );

    g_iGLUTWindowHandle = glutCreateWindow( "OpenGL" );

    SetupGL();

    //Call to the drawing function
    glutDisplayFunc(DisplayGL);
    glutReshapeFunc(ReshapeGL);
    // Loop require by OpenGL
    glutMainLoop();
    return 0;
}
//-----------------------------------------------------------


