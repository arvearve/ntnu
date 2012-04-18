package oving1;

import acm.program.*;

public class Kalk2 extends ConsoleProgram {
	public double arg1 = 0;
	public double arg2 = 0;

	public double performOperation(char operator) {
		switch (operator) {
		case '+':
			return this.arg1 + this.arg2;

		case '-':
			return this.arg1 - this.arg2;

		case '*':
			return this.arg1 * this.arg2;

		case '/':
			return this.arg1 / this.arg2;

		default:
			println("Ugyldig operator, brukte +. ");
			return this.arg1 + this.arg2;

		}
	}

	public void run() {
		this.arg1 = readDouble("First Operand: ");
		this.arg2 = readDouble("Second Operand: ");

		char op = readLine("Operator: ").toCharArray()[0]; // only use first
															// character.
		double result = performOperation(op);
		println("The result of " + this.arg1 + ' ' + op + ' ' + this.arg2
				+ " is: " + result);
	}

	/* Standard Java entry point */
	public static void main(String[] args) {
		new Kalk2().run();
	}
}