# coding: utf8
from pprint import pprint
from os import system
import string

class Piece:
	color='';
	def __init__(self, board , color):
		self.color = color
		self.board = board

	def pawn(self):
		return False

	def validateTake(self, target):
		if self.board.table[target[0]][target[1]] == ' ':
			return True
		if self.board.table[target[0]][target[1]].color != self.color:
			return True
		else:
			self.board.message = '\ndu kan ikke ta dine egene brikker!'
			return False 

	def getPosition(self):
		x=0
		y=0
		for row in self.board.table:
			try:
				x = row.index(self)
				return (y,x)
			except ValueError:
				y += 1
				continue
		print 'ERROR: Could not find position :( (line 22 in class Piece)'
		return False

	def forward(self):
		if self.color=='white':
			return 1
		else:
			return (-1)
		

	def getForwardCoord(self,amount):
		pos = self.getPosition() #(y,x)
		if self.color=='white':
			result = (pos[0] + amount, pos[1])
		else:
			result = (pos[0] - amount, pos[1])
		return result

class Pawn(Piece):
	hasMoved = 0
	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2659').encode('utf-8')
		
		else:
			return unicode(u'\u265f').encode('utf-8')

	def pawn(self):
		return True

	def moveMap(self):
		pos = self.getPosition()
		result = []
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if  ( y == pos[0] + self.forward() and abs( x - pos[1] ) == 1 ) or \
					( y == pos[0] and x == pos[1]):
					row.append(1)
				
				else:
					row.append(0)

			result.append(row)
		#pprint(result)	
		return result

	def validateMove(self, target): #target: (to y, to x)
		pos = self.getPosition()
		moveMap = self.moveMap()

		if moveMap[target[0]][target[1]] == 1 and self.board.table[target[0]][target[1]] != ' ': #flytter til "tagbar spot", men kun hvis det faktisk er en brikke der.
			self.hasMoved = 1
			return True
		elif ( ( pos[1] == target[1] and target[0] == pos[0] + self.forward() ) or \
			 ( pos[1] == target[1] and target[0] == pos[0] + 2*self.forward() and self.hasMoved == 0 ) ) and \
			 ( self.board.table[ target[0] ][ target[1] ] == ' ' ): 	# Rett frem. Kun ett hakk, med mindre du står i startrekken. Men ikke hvis det ville 'ta' en annen brikke.
				self.hasMoved = 1
				return True
		else:
			self.board.message = '\nKan ikke flytte dit.'
			return False

class Knight(Piece):
	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2658').encode('utf-8')
		
		else:
			return unicode(u'\u265e').encode('utf-8')

	def moveMap(self):
		pos = self.getPosition()
		result = []
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if ( abs(y - pos[0]) == 1 and abs(x - pos[1]) == 2) or (abs(y - pos[0]) == 2 and abs(x - pos[1]) == 1) or (	 y == pos[0] 	  and 	  x ==pos[1] ):
					row.append(1)
				else:
					row.append(0)
			result.append(row)

		return result
	
	def validateMove(self,target):
		moveMap = self.moveMap()
		if ( moveMap[target[0]][target[1]] == 1 ):
			return True
		else:
			self.board.message = "\nKan ikke flytte dit."
			return False

class Rook(Piece):
	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2656').encode('utf-8')
		else:
			return unicode(u'\u265c').encode('utf-8')
	
	def moveMap(self):
		result = []
		pos = self.getPosition()
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if x == pos[1] or y == pos[0]:
					row.append(1)
				else:
					row.append(0)
			result.append(row)
		return result

	
	def validateRookTraversal(self,target):
		pos=self.getPosition()
		if pos[0] != target[0] and pos[1] == target[1]:  # oppover / nedover
			for y in range(pos[0] + self.forward(), target[0], self.forward()):
				#sjekk om du krasjer på veien.
				if self.board.table[ y ][ target[1] ] != ' ':
					self.board.message = '\nKan ikke flytte igjennom en brikke. (oppover/nedover)'
					return False
			return True

		elif pos[0] == target[0] and pos[1] != target[1]:  # sidelengs
			if pos[1] >= target[1]:
				const = -1
			else:
				const =1
			for x in range(pos[1] + const, target[1], const):
				#sjekk om du krasjer på veien.
				if self.board.table[ target[0] ][x] != ' ':
					self.board.message = '\nKan ikke flytte gjennom en brikke. (sidelengs)'
					return False
			return True
		return False


	def validateMove(self,target):#move: (to y, to x)
		moveMap = self.moveMap()

		if moveMap[target[0]][target[1]] == 1 and self.validateRookTraversal(target):
			return True
						
		else:
			self.board.message = '\nKan ikke flytte på skrå med tårnet.'
			return False 
			
class Bishop(Piece):
	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2657').encode('utf-8')
		
		else:
			return unicode(u'\u265d').encode('utf-8')
		
	def moveMap(self):
		result = []
		pos = self.getPosition()  #(offsety, offsetx)
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if abs(pos[1] - x) == abs(pos[0] - y):
					row.append(1)
				else:
					row.append(0)
			result.append(row)
		return result

	def validateBishopTraversal(self,target):
		pos = self.getPosition()
		if pos[0] > target[0]:
			y = -1
		else:
			y = 1
		if pos[1] > target[1]:
			x = -1
		else:
			x = 1
		stepCount = 0
		for step in range(pos[0]+y , target[0], y):
			stepCount += 1
			if self.board.table[pos[0]+(y*stepCount)][pos[1]+(x*stepCount)] != ' ':
				self.board.message = '\nKan ikke flytte igjennom en brikke. (skrå)'
				return False
		return True

	def validateMove(self,target):
		moveMap = self.moveMap()
		if moveMap[ target[0] ][ target[1] ] == 1 and self.validateBishopTraversal(target):
			return True
		else:
			self.board.message = "\nKan ikke flytte dit."
			return False

class Queen(Piece, Bishop, Rook):
	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2655').encode('utf-8')
		
		else:
			return unicode(u'\u265b').encode('utf-8')
	
	def moveMap(self):
		pos = self.getPosition()
		result = []
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if ( y == pos[0] or x == pos[1] ):
					row.append(1)
				elif abs(pos[1] - x) == abs(pos[0] - y):
					row.append(1)
				else:
					row.append(0)
			result.append(row)
		return result


	def validateMove(self, target):
		pos = self.getPosition()
		moveMap = self.moveMap()
		if moveMap[target[0]][target[1]] == 1: #kan flytte dit, sjekk om det er noen brikker i veien
			if abs(pos[0]-target[0]) == abs(pos[1]-target[1]): #flytter på skrå, bruk Bishop sin validering
				return self.validateBishopTraversal(target)
			else: #flytter som et tårn, bruk Rook sin validering.
				return self.validateRookTraversal(target)
		else:
			self.board.message = '\nKan ikke flytte dit.'
			return False

class King(Piece):

	def __init__(self, board , color):
		self.color = color
		self.board = board		

	def __repr__(self):
		if self.color=='white':
			return unicode(u'\u2654').encode('utf-8')
		
		else:
			return unicode(u'\u265a').encode('utf-8')

	def moveMap(self):
		pos = self.getPosition()
		result = []
		for y in range(0,8):
			row = []
			for x in range(0,8):
				if abs(y-pos[0]) <= 1 and abs(x-pos[1]) <= 1:
					row.append(1)
				else:
					row.append(0)
			result.append(row)

		return result
	
	def validateMove(self,target):
		moveMap = self.moveMap()

		if moveMap[target[0]][target[1]] == 1:
			self.board.kingPos[self.color] = (target[0],target[1])
			return True

		elif self.board.dangerSpot(self.color, target):
			self.board.message ='\nKan ikke sette deg selv i sjakk!'
			return False
		else:
			self.board.message = '\nKan ikke flytte dit.'
			return False

	

class Board:
	table=[]
	message=''
	kingPos={'white':(0,4), 'black':(7,3)}
	def __init__(self):
		self.table=[
		[Rook(self, 'white'),Knight(self, 'white'), Bishop(self, 'white'),Queen(self, 'white'),King(self, 'white'), Bishop(self, 'white'), Knight(self, 'white'),Rook(self, 'white')],
		[Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white'), Pawn(self, 'white') ],
		[' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ],
		[' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ],
		[' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ],
		[' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ],
		[Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black'), Pawn(self, 'black')],
		[Rook(self, 'black'),Knight(self, 'black'), Bishop(self, 'black'),King(self, 'black'),Queen(self, 'black'), Bishop(self, 'black'), Knight(self, 'black'),Rook(self, 'black')],
		]
	
	def __repr__(self):
		system('cls')
		result = self.letters()
		for row in range(0,8):
			result += self.generateLines()
			result += '| '+ str(row+1) +' | '
			for square in self.table[row]:
				result += str(square) + ' | '
			result += str(row+1) +' | '
		result += self.letters()
		result += self.generateLines()
		return result

	def parseMove(self,input):
		try:
			input = input.split('-')
			start = input[0]
			finish = input[1]
			string ='abcdefgh'
			self.message=''
			result = ( ( int(start[1])-1, string.index(start[0]) ) ,  ( int(finish[1])-1, string.index(finish[0]) ) ) #convert a5-b4 to tuple ((5,1),(4,2))
			if result[0] != result[1]:
				return result
			else:  #ikke tillat å flytte til samme spot
				raise
		except:
			self.message = 'Ugyldig flytt, prøv igjen. '
			return False
		
	def performMove(self,origin, target):
		if self.table[origin[0]][origin[1]].pawn() and ( target[0] == 0 or target[0]==7 ):
			self.table[origin[0]][origin[1]] == Queen(self, self.table[origin[0]][origin[1]].color)
		self.table[ target[0] ][ target[1]] = self.table[ origin[0] ][origin[1] ]
		self.table[ origin[0] ][origin[1] ] = ' '


	def getPiece(self,origin): #returns piece in move origin
		test = self.table[origin[0]][origin[1]]
		if test == ' ':
			self.message = "That's an empty spot."
			return False
		else:
			return test

	def tryMove(self,player,movetext):
		move = self.parseMove(movetext)
		if not move:
			return False
		


		origin = move[0]
		target = move[1]
		piece = self.getPiece(origin)

		if not piece:
			return False

		if piece.color != player:
			board.message = '\nCannot move other player\'s pieces. '
			return False
		
		if piece.validateMove(target):
			if piece.validateTake(target):
				self.performMove(origin, target)
				return True
			
		return False
	
	def dangerSpot(self, color, target):
		if color == 'black':
			hostileColor = 'white'
		else:
			hostileColor = 'black'

		for y in range(0,8):
			for x in range(0,8):
				piece = self.table[y][x]
				if piece != ' ' and piece.color == hostileColor:
					if piece.validateMove(target) and piece.validateTake(target):
						print 'offending piece ' + str(piece) + piece.color
						return True
		return False

#	def checkmate(self, color):
#		king = self.getPiece(self.kingPos[color])
#		kMap = king.moveMap()
#		print king
#		print 'kMap: '
#		pprint(kMap)
#		count =0
#		for y in range(0,8):
#			for x in range(0,8):
#				if kMap[y][x] == 1: #alle spot'ene kongen kan flytte til
#					if self.dangerSpot(color,(y,x)):
#						count += 1
#		if count == 8:
#			return True
#		else:
#			return False

	def check(self, color):
		if color == 'black':
			hostileColor = 'white'
		else:
			hostileColor = 'black'

		for y in range(0,8):
			for x in range(0,8):
				piece = self.table[y][x]
				if piece != ' ' and piece.color == hostileColor:
					if piece.validateMove(self.kingPos[color]) and piece.validateTake(self.kingPos[color]):
						print 'offending piece ' + str(piece) + piece.color
						return True
		return False

	def generateLines(self):
		result = ' '
		for i in range(39):
			result += '-'
		return '\n' + result + '\n'

	def letters(self):
		result = self.generateLines()
		result += '| # | '
		for i in range (0,8):
			result += string.ascii_uppercase[i] + ' | '

		result += '# | '
		return result
	
nextGame = 'y'
while(nextGame != 'n'):
	
	board = Board()
	turn = 0
	gameOn = True
	
	while(gameOn == True):

		print board
		
		if board.message != '':
			print '>>> ' + board.message

		if board.check('black'):
			print '\n\n Black king is in check!'
		if board.check('white'):
			print '\n\n White king is in check!'
		
		if turn % 2 == 1:
			currentPlayer='black'
		else:
		 	currentPlayer='white'

		next_move = raw_input( currentPlayer +' has next move [a1-b2]: ')
		if board.tryMove(currentPlayer, next_move):
			turn += 1
		
		#if board.checkmate(currentPlayer):
		#	print currentPlayer + ' vant!'
		#	gameOn = False
	
	exit == input('ønsker dere å spille en runde til? [y/n]')
	
	
	
	 

