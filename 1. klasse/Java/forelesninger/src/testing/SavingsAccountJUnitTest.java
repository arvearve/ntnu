package testing;

import junit.framework.TestCase;

public class SavingsAccountJUnitTest extends TestCase {

	private SavingsAccount account;
	
	protected void setUp() {
		account = new SavingsAccount(10);
	}
	protected void tearDown() {
		account = null;
	}

	public void testSavingsAccount() {
		assertEquals(0, account.getBalance());
		assertEquals(10, account.getFee());
	}

	public void testDepositPositiveValue() {
		account.deposit(1000);
		assertEquals(10, account.getFee());
		assertEquals(1000, account.getBalance());
		account.deposit(100);
		assertEquals(10, account.getFee());
		assertEquals(1100, account.getBalance());
	}

	public void testDepositNegativeValue() {
		account.deposit(-750);
		assertEquals(10, account.getFee());
		assertEquals(0, account.getBalance());
	}

	public void testWithdraw() {
		account.deposit(1000);
		account.withdraw(750);
		assertEquals(10, account.getFee());
		assertEquals(240, account.getBalance());
		account.withdraw(750);
		assertEquals(10, account.getFee());
		assertEquals(240, account.getBalance());
		account.withdraw(-650);
		assertEquals(10, account.getFee());
		assertEquals(240, account.getBalance());
	}
}
