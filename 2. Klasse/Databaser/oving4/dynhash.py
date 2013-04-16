data = [2369, 3760, 4692, 4871, 5659, 1821, 1074, 7115, 1620, 2428, 3943, 4750, 6975, 4981, 2908]

print 'K		h(K) 	base2'
print '---------------------'
for i in data:
	print print i,', ', i%128, ',', bin(i%128)[2:]



def extendibleHash(input, globalDepth, blockSize):
	