#Oppgave 4:
from random import randint
from pprint import pprint
romtyper = ['T', 'B', 'F', '0'] #Trygt (T), Bunnlost hull (B), Felle (F) , Ingen dor (0)
rom=[]
num_rooms = input("Hvor mange rom bestAr labyrinten av? ")
for i in range(num_rooms):
	rom.append([])									#lag 10 rom

	for j in range(4): 								#hvert rom har 4 dorer
		rom[i].append(romtyper[randint(1,3)]) 		#Fyll alle dorer med F eller B
		
	rom[i][randint(0,3)] = 'T'						#En tilfeldig dor er trygg.
	
rom[num_rooms-1][ rom[num_rooms-1].index('T') ] = 'M'				#det siste rommet leder til mAl, ikke til et nytt rom.

pprint(rom)
selected_door = 0
current_room = 0
while rom[current_room][selected_door] != 'M':
	selected_door = input ("Du er i rom nr. " + str(current_room+1) + ". Velg en d0r: (1 - 4): ")
	selected_door -=1

	if rom[current_room][selected_door] == 'T':
		print "D0ren er trygg! Du gAr videre... "
		current_room += 1
	
	elif rom[current_room][selected_door] == 'B':
		print "Bak d0ren er et bunnl0st hull. "
	
	elif rom[current_room][selected_door] == 'F':
		print "Bak d0ren lurer en felle! "
	elif rom[current_room][selected_door] == '0':
		print "Her er det ingen d0r, kun en blank vegg."

print "Gratulerer! Du kom deg igjennom labyrinten! "