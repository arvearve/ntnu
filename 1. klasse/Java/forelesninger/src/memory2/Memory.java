package memory2;

import java.util.ArrayList;

import acm.util.RandomGenerator;

public class Memory {

	RandomGenerator randomGenerator;

	ArrayList<Integer> numberSequence;
	
	public Memory() {
		randomGenerator = new RandomGenerator();
		numberSequence = new ArrayList<Integer>();
	}

	int addNewNumber() {
		int number = randomGenerator.nextInt(1, 9);
		numberSequence.add(number);
		return number;
	}
	
	int getNumberCount() {
		return numberSequence.size();
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
}
