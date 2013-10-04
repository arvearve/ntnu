cmake_minimum_required(VERSION 2.8)


PROJECT(Opengl) # Project Name
SET( Executable_file       Hello_world_GL    ) # This is how you .exe will be name
#########################################################
# Include Files , you can add source .cpp and .h to the test program
#########################################################
add_executable(${Executable_file} main.cpp visuals.cpp) 
# Here only main.cpp was added, but more files
#could be added, for instance:
# add_executable(${Executable_file} main.cpp header.h function.cpp header2.h ) 


# Do not change settings below this area, unless you know what you are doing

#########################################################
# Setting up OUTPUT FOLDER as /bin
#########################################################
set(dir ${CMAKE_CURRENT_SOURCE_DIR}/bin)
set(EXECUTABLE_OUTPUT_PATH ${dir} CACHE PATH "Build directory" FORCE)
set(LIBRARY_OUTPUT_PATH ${dir} CACHE PATH "Build directory" FORCE)
IF(${MINGW})
	message("\n-- Compiler : MINGW\n")
	add_custom_command(TARGET ${Executable_file} PRE_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy "${CMAKE_CURRENT_SOURCE_DIR}/freeglut-mingw/bin/freeglut.dll"  "${CMAKE_CURRENT_SOURCE_DIR}/bin/" )
ELSEIF(${MSVC})
	message("\n-- Compiler : Visual Studio\n")
	add_custom_command(TARGET ${Executable_file} PRE_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy "${CMAKE_CURRENT_SOURCE_DIR}/freeglut-msvc/bin/freeglut.dll"  "${CMAKE_CURRENT_SOURCE_DIR}/bin/" )
ENDIF(${MINGW})

#########################################################
# FIND OPENGL
#########################################################
find_package(OpenGL REQUIRED)
include_directories(${OpenGL_INCLUDE_DIRS})
link_directories(${OpenGL_LIBRARY_DIRS})
add_definitions(${OpenGL_DEFINITIONS})
if(NOT OPENGL_FOUND)
	message(ERROR " OPENGL not found!")
endif(NOT OPENGL_FOUND)
#########################################################
# Find FreeGLUT
#########################################################

IF(${WIN32})
	IF(${MINGW})
		FIND_PATH(GLUT_INCLUDE_DIR NAMES GL/freeglut.h
		 HINTS ${CMAKE_CURRENT_SOURCE_DIR}/freeglut-mingw/include/)
		# Look for the library.
		FIND_LIBRARY(GLUT_LIBRARY NAMES freeglut
		 HINTS ${CMAKE_CURRENT_SOURCE_DIR}/freeglut-mingw/lib/ )  
	ELSEIF(${MSVC})
		FIND_PATH(GLUT_INCLUDE_DIR NAMES GL/freeglut.h
		 HINTS ${CMAKE_CURRENT_SOURCE_DIR}/freeglut-msvc/include/)
		# Look for the library.
		FIND_LIBRARY(GLUT_LIBRARY NAMES freeglut
		 HINTS ${CMAKE_CURRENT_SOURCE_DIR}/freeglut-msvc/lib/ )  
	ENDIF(${MINGW})

	# Handle the QUIETLY and REQUIRED arguments and set FREEGLUT_FOUND to TRUE if all listed variables are TRUE.
	INCLUDE(FindPackageHandleStandardArgs)
	FIND_PACKAGE_HANDLE_STANDARD_ARGS(GLUT DEFAULT_MSG GLUT_LIBRARY GLUT_INCLUDE_DIR)
	# Copy the results to the output variables.
	IF(GLUT_FOUND)
		SET(GLUT_LIBRARIES ${GLUT_LIBRARY})
		SET(GLUT_INCLUDE_DIRS ${GLUT_INCLUDE_DIR})
	ELSE(GLUT_FOUND)
		SET(GLUT_LIBRARIES)
		SET(GLUT_INCLUDE_DIRS)
	ENDIF(GLUT_FOUND)

	MARK_AS_ADVANCED(GLUT_INCLUDE_DIRS GLUT_LIBRARIES)
	include_directories(${GLUT_INCLUDE_DIRS})
	link_directories(${GLUT_LIBRARY_DIRS})
	add_definitions(${GLUT_DEFINITIONS})
ENDIF(${WIN32})

IF(${CMAKE_SYSTEM_NAME} MATCHES "Linux" OR APPLE)
# In linux Freeglut is found as glut ;)
	find_package(GLUT REQUIRED)
	include_directories(${GLUT_INCLUDE_DIRS})
	link_directories(${GLUT_LIBRARY_DIRS})
	add_definitions(${GLUT_DEFINITIONS})
ENDIF(${CMAKE_SYSTEM_NAME} MATCHES "Linux" OR APPLE)

if(NOT GLUT_FOUND)
    message(ERROR " GLUT not found!")
endif(NOT GLUT_FOUND)

########################################################
# Linking & stuff
#########################################################

 target_link_libraries( ${Executable_file} ${OPENGL_LIBRARIES} ${GLUT_LIBRARY} )


