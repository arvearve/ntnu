import datetime
print 'Oppgave 2 - Datoer'
print 'a) '

daynames = [        #Liste passer fint her. Enkleste losning. Bonus at indexen fra datetime.weekday() starter pa null.
    'Mandag',
    'Tirsdag',
    'Onsdag',
    'Torsdag',
    'Fredag',
    'Lordag',
    'Sondag'
    ]

monthnames = [      #Her bruker vi en dictionary, som vi kan invertere senere
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

date_in = raw_input('skriv inn dato (YYYY.MM.DD): ')       #get date string from user
date_in = date_in.split('.')                                #split string into array of strings
date_in = map( lambda i:int(i), date_in )                   #iterate through date_in, and cast each string element to int.
dt=datetime.date(*date_in)                              #make a date object using argument expansion of date_in.

print daynames[dt.weekday()] + ' ' + str(dt.day) + '. ' + monthnames[dt.month-1] + ' ' + str(dt.year) #Losning iflg oppgaven. dt.month-1 fordi monthnames' index starter paa 0, mens dt.month starter paa 1

#print dt.strftime('%A %d. %B %y') Mer elegant losning, bruker strftime, men er avhengig av spraaket paa maskinen som kjorer.
