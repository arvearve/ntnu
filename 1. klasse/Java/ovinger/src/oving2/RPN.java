package oving2;

import java.util.ArrayList;

/*
 * class RPN {
 *  int getOperandCount()
 * 	double peek(int n)
 * 	void push(double value)
 * 	double pop(double defaultValue)
 * 	void performOperation(char operator)
 * }
 */

public class RPN {

	private ArrayList<Double> stack = new ArrayList();

	public int getOperandCount() {
		return this.stack.size();

	}

	public double peek(int n) {
		if (this.stack.size() == 0) {
			return 0;
		}
		return (Double) this.stack.toArray()[this.stack.size() - n - 1];
	}

	void push(double value) {
		this.stack.add(value);
	}

	double pop(double defaultValue) {
		if (this.stack.size() == 0) {
			return defaultValue;
		}
		double result = (Double) this.stack.toArray()[this.stack.size() - 1];
		this.stack.remove(this.stack.size() - 1);
		return result;
	}

	void performOperation(char operator) {
		double operand1 = this.pop(0);
		double operand2 = this.pop(0);

		switch (operator) {
		case '+':

			this.push(operand2 + operand1);
			break;

		case '-':
			this.push((operand2 - operand1));
			break;

		case '*':
			this.push(operand2 * operand1);
			break;

		case '/':
			this.push(operand2 / operand1);
			break;

		}
	}

}
