#if __APPLE__
    #include <GLUT/glut.h>
#else
    #include <GL/glut.h>
#endif
#include <stdio.h>
#include "visuals.h"
//Main program
 
int main(int argc, char **argv) {
 
    glutInit(&argc, argv);
     
	/*Setting up  The Display
	/    -RGB color model + Alpha Channel = GLUT_RGBA
	/    -RGB color model + Alpha Channel = GLUT_RGBA
	*/
    glutInitDisplayMode(GLUT_RGBA|GLUT_DOUBLE);
     
    //Configure Window Postion
    glutInitWindowPosition(50, 25);
     
    //Configure Window Size
    glutInitWindowSize(800,600);

    //Create Window
    glutCreateWindow("Hello OpenGL");
    Setup();

    //Call to the drawing function
    glutDisplayFunc(Render);
    glutReshapeFunc(Resize);
	// Loop require by OpenGL
    glutMainLoop();
    return 0;
}
