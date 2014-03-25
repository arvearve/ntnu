#!/bin/bash
rm -rf testOutput
mkdir testOutput

makeDifFiles=1
outExtention=""
vslProgramDir="vsl_programs"
correctDir="correct_output"

warningsAssembler=0

singleStage=0
upToStage=10



for inputFile in `ls $vslProgramDir/*.vsl`; do
	echo "Testing $inputFile ..."
	inputFileBase=`basename $inputFile .vsl`
	
	for i in {1..11}; do
		if [ $i -eq $singleStage ] || [ $i -le $upToStage ] ; then
			
			./bin/vslc -s $i < $inputFile 2> testOutput/$inputFileBase.s$i$outExtention
			if [ $makeDifFiles == 1 ]; then
				diff -N $correctDir/$inputFileBase.s$i testOutput/$inputFileBase.s$i$outExtention > testOutput/$inputFileBase.s$i.diff
			fi
			if [ ! -s "testOutput/$inputFileBase.s$i.diff" ]; then
				echo -e "\e[00;32mCorrect Stage $i \e[00m"
				rm testOutput/$inputFileBase.s$i*
			else
				echo -e "\e[00;31mERROR Stage $i \e[00m"

			fi
		fi
	done
	
	
	echo		
done


