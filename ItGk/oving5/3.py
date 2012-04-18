

# a)
from random import randint

again="yes"
while again == "yes":

	solution = randint(0,1000)
	tries_left=10
	print "Gjett et tall mellom 0 og 1000. Du har 10 forsok. : "
	
	while(tries_left > 0):
		guess=int(raw_input("Hvilket tall gjetter du?: "))
		if guess == solution:
			print "You win! "
			exit()
		
		elif guess < solution:
			print "For lavt. "
		
		elif guess > solution:
			print "For hoyt. "
		
		tries_left -= 1
		print tries_left, " forsok gjenstAr. "

	print "Game Over. You were eaten by a Grue. "
	print "Riktig svar var: ", solution
	again = raw_input("Play again? (yes/no): ")