import math
#Oppgave 4 a)
m1=[[0,0],[0,0]] #init empty matrix
m2=[[0,0],[0,0]] #init empty matrix
c=[[0,0],[0,0]] #init empty matrix
print "Matrise 1:"
print "Vennligst skriv inn tall; posisjon representert av ? i matrisen:"
m1[0][0] = input("|? x|\n|x x|\n: ")
m1[0][1] = input("|x ?|\n|x x|\n: ")
m1[1][0] = input("|x x|\n|? x|\n: ")
m1[1][1] = input("|x x|\n|x ?|\n: ")


print m1


print "Matrise 2:"
print "Vennligst skriv inn tall; posisjon representert av ? i matrisen:"
m2[0][0] = input("|? x|\n|x x|\n: ")
m2[0][1] = input("|x ?|\n|x x|\n: ")
m2[1][0] = input("|x x|\n|? x|\n: ")
m2[1][1] = input("|x x|\n|x ?|\n: ")
        
print m2


print "m1*m2: "

def matrixMult(m1, m2):
    if len(m1) != len(m2[0]):
        return "Matrisene kan ikke multipliseres"
    for i in range(len(m1)):
        for j in range(len(m1[i])):
            tmp = 0
            for count in range(len(m1)):
                tmp = tmp + m1[i][count] * m2[count][j]
            c[i][j] = tmp
    return c
print matrixMult(m1,m2)
