import math
a = 5
b = 6
print "Oppgave 2a"
print "a -> %i" % (a)
print "b ->  %i" % (b)
print "a + b = %i" %(a+b)

print "Oppgave 2b"
b = 7
print "b -> 7"

print "Oppgave 2c"
c = ((2* (a**10)) + (b**8))
print "2 * a^10 + b^8 = c = %i " % (c)
print "c^0.5 = %f" % (math.sqrt(c))

print "Oppgave 2d"
a = 0
print "a -> 0"
print "loop a++ 3 times"


for i in  range(3)
	print "gg"
	a +=1
print a

print "Oppgave 2e"

laan = 1000
r = 1.03
f = laan * (1.03**100)
print "Gjeld etter 100 aar: %i" % (f) 

