from collections import defaultdict
# function DECISION-TREE-LEARNING(examples, attributes, parent  examples) # returns a tree
# 	if examples is empty
# 		return PLURALITY-VALUE(parent examples)
# 	else if all examples have the same classification
# 		return the classification
# 	else if attributes is empty
# 		return PLURALITY-VALUE(examples)
# 	else
# 		A ← argmax a ∈ attributes IMPORTANCE(a, examples)
# 		tree ← a new decision tree with root test A
# 	for each value vk of A do
# 		exs ←{e : e∈examples and e.A = vk}
# 		subtree ← DECISION-TREE-LEARNING(exs, attributes − A, examples)
# 		add a branch to tree with label (A = vk) and subtree subtree
# 	return tree
# example input:
# 1 1 2 2 1 1 1 1
# a a a a a a a c

class Example:
	cl = 0
	attrs = []
	def __init_(self, input_data):
		self.cl = input_data[-1]
		self.attrs = input_data[0:-1]

class Node:
	parent = None
	children = {}
	def __init__(self, parent, children):
		self.parent = parent
		self.children = children

	def add_child(label, child):
		self.children[label] = child


def decision_tree_learning(examples, attrs, parent_examples):
	if not examples:
		# No examples left. Use most common case from parent's examples
		return plurality_value(parent_examples)
	elif has_same_classification(examples):
		# All examples have the same class. Return the class.
		return examples[0].classification
	else:
		A = 0

		# find the most important attribute
		for attr in attrs:
			arg = importance(attr, examples)
			if arg > A:
				A = arg
		tree = Node()


def has_same_classification(examples):
	"""
	Helper for decision_tree_learning
	returns true if all examples have the same classification
	"""
	# Get the class of the first example
	classification = examples[0][-1]
	for example in examples:
		if example[-1] != classification
			return False
	return True


def importance(attr, examples):
	"""
	Return attribute with highest importance.
	"""
	pass

def plurality_value(examples):
	"""
	Find most common class
	"""

	# Dictionary with default value = 0
	counts = defaultdict(int)
	for example in examples:
		cls = example[-1]
		counts[cls] += 1

	# return key with the largest value
	return max(counts.iterkeys(), key=lambda k: counts[k])
