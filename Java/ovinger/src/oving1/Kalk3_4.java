package oving1;

import acm.program.*;

public class Kalk3_4 extends ConsoleProgram {
	public double arg1 = 0;
	public double arg2 = 0;
	public char operator;
	public double result = 0;

	public void performOperation() {
		switch (this.operator) {
		case '+':
			this.result = this.arg1 + this.arg2;
			break;
		case '-':
			this.result = this.arg1 - this.arg2;
			break;
		case '*':
			this.result = this.arg1 * this.arg2;
			break;
		case '/':
			this.result = this.arg1 / this.arg2;
			break;
		default:
			println("Ugyldig operator, brukte +. ");
			this.result = this.arg1 + this.arg2;

		}
	}

	public void run() {
		while (result < 1337) {
			this.arg1 = readDouble("First Operand: ");
			this.arg2 = readDouble("Second Operand: ");
			this.operator = readLine("Operator: ").toCharArray()[0]; // only use
																		// first
																		// character.
			performOperation();
			println("The result of " + this.arg1 + ' ' + this.operator + ' '
					+ this.arg2 + " is: " + this.result);
		}

		println("The result was over 1337, so i stopped.");
		println("**Finished**");
	}

	/* Standard Java entry point */
	/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new Kalk3_4().run();
	}
}