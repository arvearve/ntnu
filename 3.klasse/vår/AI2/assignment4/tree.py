function DECISION-TREE-LEARNING(examples, attributes, parent  examples) # returns a tree
	if examples is empty
		return PLURALITY-VALUE(parent examples) 
	else if all examples have the same classification 
		return the classification 
	else if attributes is empty 
		return PLURALITY-VALUE(examples)
	else
		A ← argmax a ∈ attributes IMPORTANCE(a, examples) 
		tree ← a new decision tree with root test A
	for each value vk of A do
		exs ←{e : e∈examples and e.A = vk}
		subtree ← DECISION-TREE-LEARNING(exs, attributes − A, examples) 
		add a branch to tree with label (A = vk) and subtree subtree
	return tree


def decision_tree_learning(examples, attrs, parent_examples):
	if not examples:
		return plurality_value(parent_examples)
	elif has_same_classification(examples):
		return examples[0].classification
	else:
	

def has_same_classification(examples):
	"""
	Helper for decision_tree_learning
	"""
	

def importance(attr, examples):
	pass

def plurality_value():
	pass