package oving6;
import junit.framework.TestCase;

/**
 * @author Eivind Sorteberg (sorteber)
 * @author Rune Vistnes (runevi)
 */
public class SavingsAccountTest extends TestCase {
	
    public void testConstructor() {
        isInitialisedToZeroTester();
    }
    
    public void testGetBalance() {
        positiveBalanceTester();
    }
    
    public void testGetCredit() {
        isCreditZeroTester();
    }
    
    public void testWithdraw() {
        withdrawUsingPositiveValuesTester();
        withdrawUsingNegativeValuesTester();
        balanceIsNegativeTester();
        balanceIsZeroTester();
    }
    
    public void testDeposit() {
        depositUsingPositiveValuesTester();
        depositUsingNegativeValuesTester();
    }
    
    private void balanceIsNegativeTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    account.deposit(100);
	    
	    assertEquals(60, account.withdraw(60));
	    assertEquals(40, account.getBalance());
	    
	    assertEquals(0, account.withdraw(41));
	    assertEquals(40, account.getBalance());
	    
	    assertEquals(30, account.withdraw(30));
	    assertEquals(10, account.getBalance());
	}

    private void balanceIsZeroTester() {
    	SavingsAccount account = new SavingsAccount();
    	
    	account.deposit(100);
    	
    	assertEquals(100, account.withdraw(100));
    	assertEquals(0, account.getBalance());
    	assertEquals(0, account.getBalance());
    }

	private void withdrawUsingNegativeValuesTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    account.deposit(10000);
	    
	    assertEquals(0, account.withdraw(-100));
	    assertEquals(10000, account.getBalance());
	    
	    assertEquals(8000, account.withdraw(8000));
	    assertEquals(2000, account.getBalance());
	    
	    assertEquals(0, account.withdraw(-2000));
	    assertEquals(2000, account.getBalance());
	}

	private void withdrawUsingPositiveValuesTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    account.deposit(10000);
	    
	    assertEquals(100, account.withdraw(100));
	    assertEquals(9900, account.getBalance());
	    
	    assertEquals(8000, account.withdraw(8000));
	    assertEquals(1900, account.getBalance());
	}

	private void depositUsingNegativeValuesTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    assertEquals(0, account.deposit(-20));
	    assertEquals(10, account.deposit(10));
	    assertEquals(10, account.deposit(-90));
	}

	private void depositUsingPositiveValuesTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    assertEquals(10, account.deposit(10));
	    assertEquals(100, account.deposit(90));
	}

	private void isCreditZeroTester() {
	    SavingsAccount account = new SavingsAccount();
	    assertEquals(0, account.getCredit());
	    
	    account.withdraw(100);
	    assertEquals(0, account.getCredit());
	}

	private void positiveBalanceTester() {
	    SavingsAccount account = new SavingsAccount();
	    
	    account.deposit(100);
	    assertEquals(100, account.getBalance());
	    
	    account.deposit(101);
	    assertEquals(201, account.getBalance());
	}

	private void isInitialisedToZeroTester() {
	    SavingsAccount account = new SavingsAccount();
	    assertEquals(0, account.getBalance());
	}
}
