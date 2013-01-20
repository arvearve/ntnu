import datetime
print "Oppgave 2b)"

daynames = [       
            'Mandag',
            'Tirsdag',
            'Onsdag',
            'Torsdag',
            'Fredag',
            'Lordag',
            'Sondag'
            ]

monthnames = [      
              'Januar',    
              'Februar',    
              'Mars',    
              'April',    
              'Mai',    
              'Juni',    
              'Juli',    
              'August',    
              'September',    
              'Oktober',    
              'November',    
              'Desember',    
              ]


string_in = raw_input('Skriv inn dato ("Lordag 31. Desember 2011"): ')
string_in = string_in.split(' ')

#map( strip('. '), string_in )     #Hvorfor funker ikke dette? :(

string_in = map( lambda i:i.strip('.,- '), string_in) #remove some punctuation
string_in = map( lambda i:i.capitalize(), string_in) #capitalize entries.

#check for malformed input
if not(string_in[0] in daynames):
    print "Ugyldig dag." 
    exit()

elif not(1 <= int(string_in[1]) <= 31):
    print "Ugyldig dato. (maa vaere mellom 1 og 31)"  #initial filtering of dayofmonth range
    exit()

elif not(string_in[2] in monthnames):
    print "Ugyldig maaned." 
    exit()
elif not( 0 <= int( string_in[3] ) ):
    print "Ugyldig aarstall."
    exit()
#input er syntaktisk OK. Naa ser vi om datoen som helhet er gyldig

else:
    try:
        dato = datetime.date( int(string_in[3]), monthnames.index(string_in[2]) + 1 , int(string_in[1]) )
        print 'Gyldig dato! ' + dato.isoformat()
    except ValueError:
        print "Det er ikke " + string_in[1] + " dager i " + string_in[2] + ". "
        exit()

if not daynames[dato.weekday()] == string_in[0]:
    print "...men det var ikke en " + string_in[0] + '. :( '
    print "(det var en " + daynames[dato.weekday()] + ".) "


