import operator
# 2a)
def countLines(file):
    lineCount=0
    for line in open(file):
        lineCount += 1
    return lineCount    

def getNumbers(file):
    result = []
    for line in open(file):
        line=line.strip() #removes whitespace before and after each line,
        line=line.split('.0000000e+00') #remove noise / convert to int
        for element in line:
            element = element.strip() #removes whitespace from each element
            result += element
    return result

def histogram(array):
    result={}
    for element in array:
        if element in result:
            result[element] += 1
        else:
            result[element] = 1
    return result

def sortByCount(dict):
    return sorted(dict.iteritems(),key=operator.itemgetter(1),reverse=1)

def sortByNumber(dict):
    return sorted(dict.iteritems())

def formatPairs(list):
    result=''
    for pair in list:
        result += str(pair[0]) + ': ' + str(pair[1]) + '\n'
    return result

print '2a) Antall linjer i tekstfilen: ', countLines('exercise2.txt')
    
print '2b) Top 9 forekomster:\n', formatPairs(sortByCount(histogram(getNumbers('exercise2.txt'))))

numbers_in = sortByNumber(histogram(getNumbers('exercise2.txt')))
file_out = formatPairs(numbers_in)

handler=open('output.txt', 'a')
handler.write(file_out)
handler.close