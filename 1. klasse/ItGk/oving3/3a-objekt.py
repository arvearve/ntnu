import math

class Vec3:
    def __init__(self, x, y, z):
            self.x = float(x)
            self.y = float(y)
            self.z = float(z)
            return None
    
    def __str__(self): 
        return "(" + str(self.x) + ", " + str(self.y) + ", " + str(self.z) + ")"

    def scale(self,scalar):
        self.x = self.x*scale
        self.y = self.y*scale
        self.z = self.z*scale
        
    def length(self):
        return float(sqrt(x**2 + y**2 + z**2))

    def 


input_x = input('x: ')
input_y = input('y: ')
input_z = input('z: ')

vector = Vec3(input_x, input_y, input_z)
print vector
print "lengde: " + str(vector.length)

scalar = input("Skalar: ")

print "Skalert vektor: " vector
print "lengde: " + str(vector.length)



v2_x = input('x: ')
v2_y = input('y: ')
v2_z = input('z: ')
vector2 = Vec3(v2_x, v2_y, v2_z)

print vector2
print "lengde: " + str(vector2.length)


