#Pseudokode for vasking av tøy:


foreach (skitne_klær):
    if plagg == hvit:
        putt i maskin1
    else:
        putt i maskin 2

maskin1.temperatur = 60
maskin2.temperatur = 40

maskin1.put_vaskemiddel(hvit)
maskin2.put_vaskemiddel(farget)

start(maskin_1, maskin2)

while ( maskin1.running() or maskin2.running() ):
    wait

flytt klær fra maskin1 til tørketrommel1
flytt klær fra maskin2 til tørketrommel2
while tørketrommel1.running or tørketrommel2.running
    wait

flytt klær fra tørketrommel1 og tørketrommel2 til skapet.
