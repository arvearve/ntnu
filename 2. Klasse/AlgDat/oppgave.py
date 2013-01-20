from sys import stdin, stderr

# kapasiteter er den opprinnelige kapasitetsmatrisen, som inneholder n x n elementer (hvor n er antall noder).
# startrom er en liste med indeksene til nodene som tilsvarer startrommene.
# utganger er en liste med indeksene til nodene som tilsvarer utgangene.

def antallIsolerteStier(kapasiteter, startrom, utganger):
    C = utvid(kapasiteter, startrom, utganger)
    n = len(C) # antall noder
    F= [] # flyt
    for i in range(n):
        F.append([0] * n)
    antallStier = 0
    superkilde = 0
    supersluk = n - 1
    while True:
        # Finn en sti det kan sendes flyt langs
        sti = finnFlytsti(superkilde, supersluk, F, C)
        if not sti:
            return antallStier
        # Oppdaterer flyten langs stien
        for i in range(len(sti)-1):
            # finnFlytsti() har garantert bare funnet en flyt paa 1
            F[ sti[i]   ][ sti[i+1] ] += 1
            F[ sti[i+1] ][ sti[i]   ] -= 1
        antallStier += 1


def utvid(C, startrom, utganger):
    n = len(C)
    # Vi skal splitte alle noder og legge paa en superkilde og et supersluk 
    # -> 2*tidligere lengde + 2
    ny_n = 2 * n + 2
    #Lager en liste av lister med den utregnede dimensjonen
    ny_C = []
    for i in range(ny_n):
        ny_C.append([0] * ny_n)
    
    #foerst fyller vi inn hvilke superkilden skal ha kapasiteter til:
    for i in startrom:
        #siden vi splitter noder skal denne til inn-noden for et gitt rom
        ny_C[0][i*2+1] = 1
    
    # saa tar vi for oss de tidligere kapasitetene
    for i in range(n):
        for j in range(n):
            ny_C[2*i+2][2*j+1] = C[i][j]
    
    # saa fyller vi inn slik at vi har 1 i kapasitet mellom 
    # de to splittede nodene som representerer et rom
    for i in range(n):
        ny_C[2*i+1][2*i+2]=1
    
    # tilslutt setter vi inn hvilke rom som er utganger
    for i in utganger:
        ny_C[2*i+2][ny_n-1] = 1
    
    return ny_C

def sjekk(nm):
    for i in xrange(len(nm)):
        if nm[i][i] != 0:
            print 'nm[%d][%d] != 0' % (i, i)
        for j in xrange(len(nm)):
            if nm[i][j] != nm[j][i]:
                print 'nm[%d][%d] != nm[%d][%d]' % (i, j, j, i)

def finnFlytsti(kilde, sluk, F, C):
    n = len(F)
    oppdaget = [False] * n
    forelder = [None] * len(F)
    koe = [kilde]
    while koe:
        node = koe.pop(0)
        if node == sluk:
            # Har funnet sluken, lager en array med passerte noder
            sti = []
            i = node
            while True:
                sti.append(i)
                if i == kilde:
                    break
                i = forelder[i]
            sti.reverse()
            return sti;
        for nabo in range(n):
            if not oppdaget[nabo] and F[node][nabo] < C[node][nabo]:
                koe.append(nabo);
                oppdaget[nabo] = True;
                forelder[nabo] = node;
    return None

noder, _, _ = [int(x) for x in stdin.readline().split()]
startrom = [int(x) for x in stdin.readline().split()]
utganger = [int(x) for x in stdin.readline().split()]
nabomatrise = []
for linje in stdin:
    naborad = [int(nabo) for nabo in linje.split()]
    nabomatrise.append(naborad)
print antallIsolerteStier(nabomatrise, startrom, utganger)