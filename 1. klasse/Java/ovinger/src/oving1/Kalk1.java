package oving1;

import acm.program.*;

public class Kalk1 extends ConsoleProgram {

	public double performOperation(double arg1, double arg2, char operator) {
		switch (operator) {
		case '+':
			return arg1 + arg2;

		case '-':
			return arg1 - arg2;

		case '*':
			return arg1 * arg2;

		case '/':
			return arg1 / arg2;

		default:
			println("Illegal operator, defaulted to +. ");
			return arg1 + arg2;

		}
	}

	public void run() {
		double arg1 = readDouble("First operand: ");
		double arg2 = readDouble("Second Operand: ");
		char operator = readLine("Operator: ").toCharArray()[0];
		double result = performOperation(arg1, arg2, operator);

		println("The result of " + arg1 + ' ' + operator + ' ' + arg2 + " is: "
				+ result);
	}

	/* Standard Java entry point */
	/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new Kalk1().run();
	}
}