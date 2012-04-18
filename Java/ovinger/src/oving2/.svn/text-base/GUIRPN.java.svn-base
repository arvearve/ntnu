package oving2;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;

import acm.graphics.GLabel;
import acm.gui.TablePanel;
import acm.program.GraphicsProgram;

/*
 * @startuml
 * class GraphicsProgram {
 * }
 * class GUIRPN {
 * 	+void init()
 * 	+void run()
 * }
 * class RPN {
 *  int getOperandCount()
 * 	double peek(int n)
 * 	void push(double value)
 * 	double pop(double defaultValue)
 * 	void performOperation(char operator)
 * }
 * GraphicsProgram <|-- GUIRPN
 * GUIRPN --> "rpn" RPN
 * @enduml
 */

public class GUIRPN extends GraphicsProgram {

	RPN rpn;

	GLabel valueLabel;
	String input;

	public void init() {
		valueLabel = new GLabel("", 20, 20);
		input = null;
		rpn = new RPN();
		setSize(800,200);
	}

	final String[] buttonLabels = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "push", "0", ".", "/", "e", "pi", "The answer", "backspace"};

	public void run() {
		updateValueLabel();
		add(valueLabel);
		TablePanel tablePanel = new TablePanel(5, 4);
		for (String buttonLabel: buttonLabels) {
			tablePanel.add(new JButton(buttonLabel));
		}
		add(tablePanel, SOUTH);
		addActionListeners();
		addKeyListeners();
	}

	public void actionPerformed(ActionEvent event) {
		performAction(event.getActionCommand());
	}

	public void performAction(String command) {
		if (command.length() < 1) {
			return;
		}
		char c = command.charAt(0);
		if (command.length() == 1 && ((c >= '0' && c <= '9') || c == '.')) {
			if (input == null) {
				input = String.valueOf(c);
			} else {
				input = input + c;
			}
		} else if (command.equals("backspace")) {
			if (input == null || input.length() == 0) {
				input = String.valueOf(rpn.pop(0.0));
			} else {
				input = input.substring(0, input.length() - 1);
			}
		}
		else {
			// push existing input
			if (input != null) {
				rpn.push(Double.valueOf(input));
				input = null;
			}
			String numberString = parseAsNumberString(command);
			if (numberString != null) {
				input = numberString;
			} else if (! command.equals("push")) {
				rpn.performOperation(c);
			}
		}
		updateValueLabel();
	}

	void updateValueLabel() {
		String label = "";
		for (int i = 0; i < rpn.getOperandCount(); i++) {
			label = rpn.peek(i) + " " + label;
		}
		label = label + " > ";
		if (input != null) {
			label = label + input;
		}
		valueLabel.setLabel(label);
	}

	public void keyTyped(KeyEvent event) {
		char c = event.getKeyChar();
		String command = null;
		if (c == KeyEvent.VK_BACK_SPACE) {
			command = "backspace";
		} else if (c == KeyEvent.VK_ENTER) {
			command = "push";
		} else if ("0123456789.+-*/".indexOf(c) >= 0) {
			command = String.valueOf(c);
		} 
		if (command != null) {
			performAction(command);
		}
	}

	public final static double THE_ANSWER = 42.0;

	Class[] constantClasses = {Math.class, GUIRPN.class};
	
	String parseAsNumberString(String s) {
		try {
			Double.valueOf(s);
			return s;
		} catch (NumberFormatException e) {
		}
		String constantName = s.toUpperCase().replace(' ', '_');
		for (Class constantClass: constantClasses) {
			try {
				Field constantField = constantClass.getField(constantName);
				if (constantField.getType() == double.class) {
					return String.valueOf((Number)constantField.get(null));
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}
