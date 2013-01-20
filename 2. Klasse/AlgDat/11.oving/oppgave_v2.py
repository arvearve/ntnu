from sys import stdin

def minCoinsGreedy(coins, value):
	result = 0
	for coin in coins:
		while value >= coin:
			value -= coin
			result +=1
	return result



def minCoinsDynamic(V, C):
    m, n = len(V)+1, C+1
    table = [[0] * n for x in xrange(m)]
    for j in xrange(1, n):
        table[0][j] = float('inf')
    for i in xrange(1, m):
        for j in xrange(1, n):
            aC = table[i][j - V[i-1]] if j - V[i-1] >= 0 else float('inf')
            table[i][j] = min(table[i-1][j], 1 + aC)
    return table[m-1][n-1]


def canUseGreedy(coins):
	for i in xrange(len(coins) - 1):
			if coins[i] % coins[i + 1] != 0:
				return False
	return True

Inf = 1000000000
coins = []
for c in stdin.readline().split():
	coins.append(int(c))
coins.sort()
coins.reverse()
method = stdin.readline().strip()
if method == "graadig" or (method == "velg" and canUseGreedy(coins)):
	for line in stdin:
		print minCoinsGreedy(coins, int(line))
else:
	for line in stdin:
		print minCoinsDynamic(coins, int(line))