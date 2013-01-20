import datetime
print 'Oppgave 2 - Datoer'
print 'c) '

date_in = raw_input('skriv inn dato (YYYY.MM.DD): ')       #get date string from user
date_in = date_in.split('.')                                #split string into array of strings
date_in = map( lambda i:int(i), date_in )                   #iterate through date_in, and cast each string element to int.
dt=datetime.date(*date_in)                              #make a date object using argument expansion of date_in.


if not(date_in[1] == 12 and date_in[2] > 24):
    christmas = datetime.date(date_in[0], 12, 24)
    christmas_prev = datetime.date(date_in[0]-1, 12, 24)
else:
    christmas = datetime.date(date_in[0]+1, 12, 24)
    christmas_prev = datetime.date(date_in[0], 12, 24)

delta = christmas - dt

if delta.days == 0:
    print "dette er julaften!"
elif delta.days > 0:
    print "det er " + str(delta.days) + " dager igjen til neste jul. "

delta_prev = dt - christmas_prev

print "Det er " + str(delta_prev.days) + " siden forrige jul. "


if delta < delta_prev:
    print "Den naermeste julaften er den som kommer. "
elif delta > delta_prev:
    print "Den naermeste julaften var den forrige. "
else:
    print "Det er like lenge siden forrige jul, som det er til den neste. "
