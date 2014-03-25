#!/bin/bash
rm -rf testOutput
mkdir testOutput

makeForARM=1
makeDifFiles=1
outExtention=""
vslProgramDir="vsl_programs"
correctDir="correct_output"

warningsAssembler=0

singleStage=12
upToStage=0



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
	
	if [ $singleStage -eq 12 ] || [ $upToStage -eq 12 ]; then
	
		./bin/vslc < $inputFile 2> testOutput/$inputFileBase.s
		
		if [ $makeForARM -eq 1 ]; then
			arm-linux-gnueabihf-gcc-4.6 -static testOutput/$inputFileBase.s -o testOutput/a.out 2> testOutput/$inputFileBase.asmError
			#arm-linux-gnueabihf-gcc-4.6 -static testOutput/$inputFileBase.s -o testOutput/a.out 2> testOutput/$inputFileBase.asmError
		else
			gcc -m32 testOutput/$inputFileBase.s -o testOutput/a.out 2> testOutput/$inputFileBase.asmError
		fi
	
		# Check if the assembly produces warnings/errors
		if [ ! -s "testOutput/$inputFileBase.asmError" ]; then
			rm testOutput/$inputFileBase.asmError
			removeAsm=1
		else
			removeAsm=0
			if [ $makeDifFiles == 1 ]; then
				diff -N $correctDir/$inputFileBase.s12 testOutput/$inputFileBase.asmError > testOutput/$inputFileBase.diff
			fi
			if [ ! -s "testOutput/$inputFileBase.diff" ]; then
				echo -e "\e[00;32mCorrect error message \e[00m"
				
				#rm testOutput/$inputFileBase.s
				rm testOutput/$inputFileBase.diff
			else
				echo -e "\e[00;31mERROR error message\e[00m"

			fi
		fi
	
		
		if [ $removeAsm -eq 1 ]; then
			if [ $makeForARM -eq 1 ]; then
				qemu-arm ./testOutput/a.out 1 2 > testOutput/$inputFileBase.s12 
			else
				./testOutput/a.out 1 2 > testOutput/$inputFileBase.s12 
			fi
			
			if [ $makeDifFiles == 1 ]; then
				diff -N $correctDir/$inputFileBase.s12 testOutput/$inputFileBase.s12 > testOutput/$inputFileBase.diff
			fi
			if [ ! -s "testOutput/$inputFileBase.diff" ]; then
				echo -e "\e[00;32mCorrect execution output\e[00m"
				#if [ $removeAsm == 1 ]; then
					#rm -f testOutput/$inputFileBase.s
				#fi
				rm testOutput/$inputFileBase.s12*
				rm testOutput/$inputFileBase.diff
			else
				echo -e "\e[00;31mERROR execution output\e[00m"

			fi
		fi
		
		rm -f testOutput/a.out
	fi
	echo			
done


