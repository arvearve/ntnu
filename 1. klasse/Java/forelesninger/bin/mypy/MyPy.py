'''
Created on Jan 2, 2012

@author: hal
'''

def findSmallest(buckets, volume):
    smallest = -1
    for i in range(0, len(buckets)):
        if buckets[i] > volume and (smallest < 0 or buckets[i] < buckets[smallest]):
            smallest = i
    return smallest

buckets = []

def readBuckets():
    while True:
        bucket = raw_input("Input next bucket, or a newline to exit: ")
        if len(bucket) <= 1:
            return buckets
        buckets.append(int(bucket))

def run():
    readBuckets()
    volume = int(raw_input("Input volume, for which to find a bucket: "))
    smallest = findSmallest(buckets, volume)
    print("The smallest bucket larger than " + str(volume) + " is #" + str(smallest) + ", with volume " + str(buckets[smallest]))

run()
