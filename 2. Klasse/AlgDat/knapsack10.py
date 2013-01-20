from sys import stdin

Inf = float(1E300)

def avstand(s1, s2):
    # START IKKE-UTDELT KODE
    # pad'er strenger foran for aa forenkle indeksering
    s1 = ' ' + s1
    s2 = ' ' + s2
    avstand = [None] * len(s1)
    for i in range(len(s1)):
        avstand[i] = [0] * len(s2)
        avstand[i][0] = i
    for j in range(len(s2)):
        avstand[0][j] = j
    for i in range(1, len(s1)):
        for j in range(1, len(s2)):
            fjern = avstand[i - 1][j] + 1
            leggtil = avstand[i][j - 1] + 1
            bytt = avstand[i - 1][j - 1]
            if s1[i] != s2[j]:
                bytt += 1
            avstand[i][j] = min(fjern, leggtil, bytt)
    return avstand[-1][-1]
    # SLUTT IKKE-UTDELT KODE

def minste_avstand(strenger):
    # START IKKE-UTDELT KODE
    min_avstand = Inf
    for x in range(len(strenger)):
        for y in range(x):
            min_avstand = min(min_avstand, avstand(strenger[x], strenger[y]))
    return min_avstand
    # SLUTT IKKE-UTDELT KODE

linjer = []
for linje in stdin:
    linjer.append(linje.strip())
print minste_avstand(linjer)