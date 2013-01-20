#!/usr/bin/env python
# -*- coding: utf-8 -*-
import csv
from pprint import pprint

def csv2table(filename):
	test = csv.reader(open(filename, 'rb'), delimiter=',', quotechar='"')
	result = []
	for row in test:
		tmp=[]

		if len(row) != 2: #skip malformed lines
			continue
		
		for element in row:
			tmp.append(unicode(element, 'utf-8'))
		
		result.append(tmp)
	return result
		

def countOpenStudies(table):
	count=0
	for row in table:
		if row[1].strip(' "') == u'Alle':
			count +=1		
	return count


def filterNTNU(table): 
	result=[]
	for row in table:
		if row[0].startswith('NTNU') and row[1] != 'Alle' and row[1] != ' "Alle"': #Filter only NTNU entries with score limits, and remmove a couple of rogue entries.
			result.append(row)
	return result

def gjennomsnittScore(table):
	score=0.0
	count=0
	for row in table:
		score += float(row[1])
		count +=1
	return score/count

def topScore(table):
	score=0.0
	for row in table:
		if float(row[1]) > score:
			score = float(row[1])
	return score
	
def bottomScore(table):
	score=100
	for row in table:
		if float(row[1]) < score:
			score = float(row[1])
	return score

oversikt = csv2table('poenggrenser_2011.csv')
ntnu = filterNTNU(oversikt)
#countOpenStudies(oversikt)
#pprint(oversikt)
print '3a) Antall åpne studier i Norge 2011: ', countOpenStudies(oversikt)

print '3b)  Gjennomsnittlig opptaksgrense ved NTNU: ', gjennomsnittScore(ntnu)

print '3c)  Høyeste opptaksgrense ved NTNU:, ', topScore(ntnu)
print '3c)  laveste opptaksgrense: NTNU, ', bottomScore(ntnu)