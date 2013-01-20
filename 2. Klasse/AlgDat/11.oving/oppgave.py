from sys import stdin

def minCoinsGreedy(coins, value):
	result = 0
	for coin in coins:
		while value >= coin:
			value -= coin
			result +=1
	return result


def count_change(amount, coins = (50, 25, 10, 5, 1)):
	memo_table = [ [-1] * (len(coins)+1) for i in range(amount+1) ]
	def count_change(amount, coins):
		return memo_table[amount][len(coins)]
	def full_count_change(amount, coins):
	# How often is this called?
	... # (calls count_change for recursive results)
	for a in range(0, amount+1):
	memo_table[a][0] = full_count_change(a, ())
    for k in range(1, len(coins) + 1):
    for a in range(1, amount+1):
	memo_table[a][k] = full_count_change(a, coins[-k:])
	return count_change(amount, coins)

def minCoinsDynamic(coins, value):
	results = [Inf] * (value + 1)
	usefulCoins = []
	for c in coins:
		if c <= value:
			results[c] = 1
			usefulCoins.append(c)
	for curVal in xrange(1, value + 1):
		if results[curVal] != Inf:
			continue
		best = Inf
		for c in usefulCoins:
			if c <= curVal:
				current = 1 + results[curVal - c]
				if current < best:
					best = current
		results[curVal] = best
	return results[value]

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
		print coinChange(coins, int(line))