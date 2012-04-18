import math

#3 a)
print "Gi meg Vektor 1 FFS! @1! "
input_x = input('x: ')
input_y = input('y: ')
input_z = input('z: ')

vector1 = [input_x, input_y, input_z]

print "Vektor 1: "
print vector1

#3 b)
scalar = input("SCALE, MOTHERFCUKER! ")
vectorScale=[]
for element in vector1:
    vectorScale.append( element*scalar)

print "Skalert vektor: "
print vectorScale

#3 c)
lengde1 = 0


for element in vector1:
    lengde1 = lengde1 + element**2

lengde1 = math.sqrt(lengde1)

print "Lengde på vektor før skalering: "
print lengde1

print "Lengde på vektor etter skalering"
print lengde1*scalar
lengde1=0
for element in vector1:
    lengde1 += element**2
print math.sqrt(lengde1)
#3d)

print "I need another vector ASAP!"
v2_x = input('x: ')
v2_y = input('y: ')
v2_z = input('z: ')

vector2 = [v2_x, v2_y, v2_z]
print "Vektor 2:"
print vector2

#dotproduct = vector1[0]* vector2[0] + vector1[1]* vector2[1] + vector1[2]* vector2[2]

dotprod = 0
for i in range (0, len(vector1)):
    dotprod = dotprod + ( vector1[i] * vector2[i])
print "prikkprodukt av vektor 1 og vektor 2:"
print dotprod


