

def velkommen():
    print 'Velkommen til hybelregnskapsprogram v 3.0'
    print '========================================='


def valgmeny():
    print '\n Velg kommando [utgift|inntekt|load|save|fjern|vis|avslutt]'
    valg = raw_input('Kommando:> ')
    return valg

def registrer(db,dbid,valg):
    print 'Registrering av', valg
    dato = raw_input('Dato [yyyy-mm-dd]: '
    belop = input('Belop [kr: ]')
    beskrivelse = raw_input('Beskrivelse [tekst]: ')

    if valg == 'utgift': 
        belop=belop * -1
    db[dbid] = [dato,belop,beskrivelse] #legges inn i dictionary

    melding('Data ble registrert. ')
    return db #returnerer databasen.


def melding(tekst):
    print '>>>', melding

def vis(db):
    balanse = 0
    print 'ID   Dato            Beskrivelse'
    print '================================'
    for x in db:
        liste = db[x] #henter ut liste med [dato,beløp,beskrivelse].
        s = str(x).rjust(4)+liste[0].rjust(11) #ID og dato
        s=s+ ' '+liste[2].ljust(30) #beskrivelse venstrejustert
        s=s+str(liste[1].rjust(8) #belop, hoyrejustert
        print s
        balanse += liste[1]
    print 'Balanse: ', balanse

def fjern(db):
    print 'ID\'er i databasen: ', db.keys()
    dbid = input('ID paa innslag som skal fjernes: ')
    if db.has_key(dbid):
        del db[dbid] #fjerner innslag fra dictionary
        melding('Innslag med ID ' + str(dbid) + 'ble fjernet.'
    else:
        melding('ID finnes ' + str(dbid) + ' finnes ikke i databasen. '
    return db

def velgfilnavn():
    filnavn = raw_input('Velg filnavn: ')
    return filnavn 

