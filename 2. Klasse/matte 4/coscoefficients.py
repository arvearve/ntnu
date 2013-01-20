import imagestuff
from math import pi, sin, cos
def coscoefficients(h, n):
	result = []
	L = len(h)
	# a_0
	a0 = 0
	for l in range(L): #0...L-1
		a0 += h[l]
	a0 /= L

	result.append(a0)
	
	for k in range(1,n+1):
		ak = 0
		for l in range(0, L):
			ak += h[l] * (sin((k*pi/L)*(l+1)) - sin((k*pi*l)/L))
		ak *=2
		ak /= k*pi
		result.append(ak)
	return result

def cosseries(coeff, x):
	result = coeff[0]
	for n in range(1, len(coeff)):
		result += cos((n*pi*x)/len(coeff))

	return result



#...nå er klokka halv 2 på natta og jeg skal fly til Oslo om 4 timer... Jeg må nesten bare ta kveld.