print "\nOppgave 2a) "
a = 24
b = 42
c = 64
print "a = " + str(a)
print "b = " + str(b)
print "c = " + str(c)

if (a > b) and (a > c):
    print "Det storste tallet er (a): " + str(a)
    mindre = str(b) + ", " + str(c)
elif (b > a) and (b > c):
    print "Det storste tallet er (b): " + str(b)
    mindre = str(a) + ", " + str(c)
elif (c > a) and (c > b):
    print "Det storste tallet er (c): " + str(c)
    mindre = str(a) + ", " + str(b)

print "\n Oppgave 2b)"
print "Og disse er mindre: "+ mindre

print "\nOppgave 2c) "
import random
f = int(random.random()*100)
print "f = " + str(f)
if f != 0:
    print "b / f: " + str(b/f)
    print "float(b) / float(f): " + str(float(b)/float(f))
else:
    print "f = 0"

print "\nOppgave 2d)"
d = 2
e = 0

print "a / d: " + str(a/d)
#print "a / e: " + str(a/e)

print "\nOppgave 2e)"
print "f->d"
f = d
print "f er naa lik: " + str(f)

if f != 0:
    print "b / f: " + str(b/f)
    print "float(b) / float(f): " + str(float(b)/float(f))
else:
    print "f = 0"

