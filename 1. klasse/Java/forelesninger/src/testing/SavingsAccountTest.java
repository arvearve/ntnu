package testing;

import acm.program.ConsoleProgram;

public class SavingsAccountTest extends ConsoleProgram {

	private SavingsAccount account;
	
	public void init() {
		account = new SavingsAccount(10);
	}

	private int reqCount = 0;
	
	private void testValue(int expectedValue, int realValue) {
		reqCount++;
		if (! (expectedValue == realValue)) {
			println("Krav " + reqCount + " IKKE tilfredsstilt");
		} else {
			println("Krav " + reqCount + " tilfredsstilt");
		}
	}
	
	public void run() {
		testValue(10, account.getFee());
		account.deposit(1000);
		testValue(10, account.getFee());
		testValue(1000, account.getBalance());
		account.withdraw(750);
		testValue(10, account.getFee());
		testValue(240, account.getBalance());
		account.withdraw(750);
		testValue(10, account.getFee());
		testValue(240, account.getBalance());
		account.deposit(-750);
		testValue(10, account.getFee());
		testValue(240, account.getBalance());
		account.withdraw(-650);
		testValue(10, account.getFee());
		testValue(240, account.getBalance());
	}
}
