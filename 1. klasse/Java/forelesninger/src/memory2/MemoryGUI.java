package memory2;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import acm.program.GraphicsProgram;

/*
 * @startuml
 * class MemoryGUI {
 * 	+ void run()
 * 	+ void init()
 * 	+ actionPerformed()
 * handleNextNumber(int)
 * }
 * class Memory {
 * 	int addNewNumber()
 * 	int getNumberCount()
 * 	int compareNumbers(ArrayList<Integer>)
 * }
 * class RandomGenerator {
 * 	int nextInt(int, int)
 * }
 * MemoryGUI "memory" --> Memory
 * Memory "randomGenerator" --> RandomGenerator
 * @enduml
 */
public class MemoryGUI extends GraphicsProgram {
	
	Memory memory = new Memory();
	
	JLabel message;
	
	public void init() {
		memory = new Memory();

		message = new JLabel("<here you'll se messages during the game>");
		add(message, NORTH);
		for (int i = 1; i < 10; i++) {
			add(new JButton(String.valueOf(i)), SOUTH);
		}
		addActionListeners();
		setSize(800,200);
	}
	
	public void run() {
		int newNumber = memory.addNewNumber();
		message.setText("Number " + memory.getNumberCount() + " is " + newNumber + ". Please hit the numbers in sequence.");
	}
	
	ArrayList<Integer> input = new ArrayList<Integer>();
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		handleNextNumber(Integer.valueOf(ae.getActionCommand()));
	}

	private void handleNextNumber(int number) {
		input.add(number);
		int diff = memory.getNumberCount() - input.size();
		if (diff > 0) {
			message.setText("Numbers so far: " + input + ", " + diff + " remaining.");
		} else {
			int mistake = memory.compareNumbers(input);
			if (mistake < 0) {
				input.clear();
				int newNumber = memory.addNewNumber();
				message.setText("Number " + memory.getNumberCount() + " is " + newNumber + ". Please hit the numbers in sequence.");
			} else {
				message.setText("Number " + mistake + " was wrong!");
				exit();
			}
		}
	}
}
