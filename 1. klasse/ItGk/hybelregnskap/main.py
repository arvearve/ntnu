#hovedmodulen

#importerer moduler somskal brukes i main
import brukerïmport fil
import hjelp

#init db
db={}
dbid=0

bruker.velkommen()

#Vis en valgmeny til bruker
valg = bruker.valgmeny()

while valg <> 'avslutt':
    if valg == 'utgift' or valg == 'inntekt':
        dbid +=1
        db = bruker.registrer(db,dbid,valg)
    elif valg == 'vis':
        bruker.vis(db)
    elif valg == 'fjern':
        db = bruker.fjern(db)
    else:
        
