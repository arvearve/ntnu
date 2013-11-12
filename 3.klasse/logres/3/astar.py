class AStar:
	numPiecesPerTeam = 0;
	open = []
	closed = []
	solutionFound = False

	def __init(self, nppt):
		self.numPiecesPerTeam = nppt
	

	def attach_and_eval(child, parent):
		child.parent = parent
		child.g = parent.g + 1

	def propagatePathImprovements(parent):
		for child in parent.kids:
			if (parent.g + 1) < child.g:
				child.parent = parent
				child.g = parent.g + 1
				propagatePathImprovements(child)

	def bestFirstSearch(self):
		initial = Node()
		initial.g = 0
		open.append(intitial)

		while solutionFound == False:
			print len(open)
			if len(open) == 0:
				print "No solution found :("
				return false




a = AStar()
a.bestFirstSearch()