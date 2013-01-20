package memory1;

import java.util.ArrayList;

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

/*
 * @startuml
 * class MemoryProgram {
 * 	+ void run()
 * 	+ void init()
 * 	ArrayList<Integer> readInput()
 * 	int addNewNumber()
 * 	int getNumberCount()
 * 	int compareNumbers(ArrayList<Integer>)
 * 	void clearConsole()
 * }
 * class RandomGenerator {
 * 	int nextInt(int, int)
 * }
 * MemoryProgram "randomGenerator" --> RandomGenerator
 * @enduml
 */
public class MemoryProgram extends ConsoleProgram {

	RandomGenerator randomGenerator;
	
	ArrayList<Integer> numberSequence;
	
	public void init() {
		randomGenerator = new RandomGenerator();
		numberSequence = new ArrayList<Integer>();
	}
	
	public void run() {
		while (true) {
			int newNumber = addNewNumber();
			println("New number is " + newNumber);
			ArrayList<Integer> input = readInput();
			int pos = compareNumbers(input);
			if (pos >= 0) {
				println("The number at position " + (pos + 1) + " was wrong!");
				break;
			}
		}
	}

	int addNewNumber() {
		int number = randomGenerator.nextInt(1, 9);
		numberSequence.add(number);
		return number;
	}
	
	ArrayList<Integer> readInput() {
		ArrayList<Integer> input = new ArrayList<Integer>();
		while (input.size() < numberSequence.size()) {
			int number = readInt("Skriv inn tall nr. " + (input.size() + 1) + ": ");
			input.add(number);
		}
		return input;
	}
	
	int compareNumbers(ArrayList<Integer> input) {
		for (int i = 0; i < numberSequence.size(); i++) {
			int number1 = numberSequence.get(i);
			int number2 = input.get(i);
			if (number1 != number2) {
				return i;
			}
		}
		return -1;
	}
	
	void clearConsole() {
		removeAll();
		setConsole(createConsole());
		add(getConsole(), CENTER);
		validate();
	}
}
