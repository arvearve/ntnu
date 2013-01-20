#!/usr/bin/env python
# -*- coding: utf-8 -*-
import csv

def csv2table(filename):
	test = csv.reader(open(filename, 'rb'), delimiter=',')
	result = []
	for row in test:
		tmp=[]
		for element in row:
			tmp.append(element)
		result.append(tmp)
	return result

def printSchedule(table):
	days=[
		'Mandag',
		'Tirsdag',
		'Onsdag',
		'Torsdag',
		'Fredag'
		]
	
	hours=[
		'08:00:',
		'09:00:',
		'10:00:',
		'11:00:',
		'12:00:',
		'13:00:',
		'14:00:',
		'15:00:'
	]
	
	line1= 'Tid \t'
	
	for d in days:
		line1 += d + '\t'
	print '\n' + line1
	
	for time in range(len(hours)):
		line = hours[time] + '\t'
		for day in range(len(days)):
			line += table[day][time] + '\t'
		print line	
	print '\n\n'
	return True

def setTidspunkt(table, day, hour, course):
	day-=1
	hour-=1
	table[day][hour]=course
	return table

def removeTidspunkt(table, day, hour):
	day-=1
	hour-=1
	table[day][hour]=''
	return table

def moveTidspunkt(table, dayFrom, hourFrom, dayTo, hourTo):
	course= table[dayFrom-1][hourFrom-1]
	print course
	table = setTidspunkt(table, dayTo, hourTo, course)
	print table
	table = removeTidspunkt(table, dayFrom, hourFrom)
	print table
	return table

def save(table, filnavn):
	f=open(filnavn, 'w')
	buf=''
	for day in table:
		line=''
		for hour in day:
			line += hour + ','
		line=line[:-1]	
		buf += line + '\n'
	f.write(buf) 
	f.close()
	return buf



def printMenu():
	print "0) Avslutt"
	print "1) Åpne timeplan fra fil"
	print "2) Lagre timeplan til fil"
	print "3) Vis timeplan"
	print "4) Legg til time til timeplan"
	print "5) Fjern time fra timeplan"
	print "6) Endre tidspunkt for time i timeplan	"
	return True


while (True):
	printMenu()

	action = input('Velg et alternativ: ') 
	if action == 0:
		print "Good Bye! \n"
		exit();
	
	elif action == 1:
		open_file = str(raw_input('Skriv inn filnavn: (blank for standard): '))
		if open_file == '':					#default filename
			open_file = 'timeplan.csv'
		timeplan = csv2table(open_file)
	
	elif action == 2:
		if(timeplan):
			save_file = raw_input('Skriv inn filnavn: (blank for standard): ')
			if save_file == '':					#default filename
				save_file= 'saveplan.csv'
			save(timeplan, save_file)
	
	elif action == 3:
		if(timeplan):
			printSchedule(timeplan)
		else:
			print('Velg en timeplan først!')
			
	elif action == 4:
		if(timeplan):
			timeplan = setTidspunkt(timeplan, input('Dag: '), input('Time: '), raw_input('Fag: '))
		else:
			print('Velg en timeplan først!')	
	
	elif action == 5:
		if(timeplan):
			timeplan = removeTidspunkt(timeplan, input('Dag: '), input('Time: '))
		else:
			print('Velg en timeplan først!')	

	elif action ==6:
		timeplan = moveTidspunkt(timeplan, input('Fra Dag: '), input('Fra Time: '), input('Til Dag: '), input('Til Time: '))
