package memory2;

import java.util.ArrayList;

import acm.program.ConsoleProgram;

/*
 * @startuml
 * class MemoryProgram {
 * 	+ void run()
 * 	+ void init()
 * 	ArrayList<Integer> readInput()
 * 	void clearConsole()
 * }
 * class Memory {
 * 	int addNewNumber()
 * 	int getNumberCount()
 * 	int compareNumbers(ArrayList<Integer>)
 * }
 * class RandomGenerator {
 * 	int nextInt(int, int)
 * }
 * MemoryProgram "memory" --> Memory
 * Memory "randomGenerator" --> RandomGenerator
 * @enduml
 */
public class MemoryProgram extends ConsoleProgram {

	Memory memory;
	
	public void init() {
		memory = new Memory();
	}
	
	public void run() {
		while (true) {
			clearConsole();
			int newNumber = memory.addNewNumber();
			println("New number is " + newNumber);
			ArrayList<Integer> input = readInput();
			int pos = memory.compareNumbers(input);
			if (pos >= 0) {
				println("The number at position " + (pos + 1) + " was wrong!");
				break;
			}
		}
	}

	ArrayList<Integer> readInput() {
		ArrayList<Integer> input = new ArrayList<Integer>();
		while (input.size() < memory.getNumberCount()) {
			int number = readInt("Skriv inn tall nr. " + (input.size() + 1) + ": ");
			input.add(number);
		}
		return input;
	}
	
	void clearConsole() {
		removeAll();
		setConsole(createConsole());
		add(getConsole(), CENTER);
		validate();
	}
}
