package oving6;
import junit.framework.TestCase;

/**
 * @author Eivind Sorteberg (sorteber)
 * @author Rune Vistnes (runevi)
 */
public class CreditAccountTest extends TestCase {
    public void testConstructor() {
        balanceIsInitialisedToZeroTester();
        creditIsInitialisedTester();
    }
    
    public void testGetBalance() {
        negativeBalanceTester();
        positiveBalanceTester();
    }
    
    public void testGetCredit() {
        creditIsUnchangedTester();
    }
    
    public void testWithdraw() {
        withdrawUsingPositiveValuesTester();
        withdrawUsingNegativeValuesTester();
        balanceIsNegativeTester();
        creditLimitIsReachedTester();
        creditLimitIsExeededTester();
    }
    
    public void testDeposit() {
        depositUsingPositiveValuesTester();
        depositUsingNegativeValuesTester();
    }
    
    public void testGetFees() {
        havingPositiveBalanceTester();
        havingNegativeBalanceTester();
    }
    
    private void creditIsInitialisedTester() {
	    CreditAccount account = new CreditAccount(0);
	    assertEquals(0, account.getCredit());
	    
    	account = new CreditAccount(100);
	    assertEquals(100, account.getCredit());
	}

	private void balanceIsInitialisedToZeroTester() {
	    CreditAccount account = new CreditAccount(100);
	    assertEquals(0, account.getBalance());    
	}

	private void creditLimitIsExeededTester() {
	    CreditAccount account = new CreditAccount(150);
	    
	    assertEquals(90, account.withdraw(90));
	    assertEquals(-140, account.getBalance());
	    
	    assertEquals(0, account.withdraw(100));
	    assertEquals(-140, account.getBalance());
	}

	private void havingNegativeBalanceTester() {
	    CreditAccount account = new CreditAccount(200);
	                
	    account.withdraw(40);
	    assertEquals(50, account.getFees());
	    
	    account.withdraw(10);
	    assertEquals(100, account.getFees());
	    
	    account.withdraw(51);
	    assertEquals(100, account.getFees());
	    
	    account.deposit(100);
	    assertEquals(100, account.getFees());
	    
	    account.withdraw(90);
	    assertEquals(150, account.getFees());
	    
	    account.withdraw(1);
	    assertEquals(150, account.getFees());
	}

	private void havingPositiveBalanceTester() {
	    CreditAccount account = new CreditAccount(150);
	    
	    assertEquals(0, account.getFees());
	    
	    account.deposit(200);
	    assertEquals(0, account.getFees());
	    
	    account.withdraw(90);
	    assertEquals(0, account.getFees());
	    
	    account.withdraw(100);
	    assertEquals(0, account.getFees());
	    
	    account.withdraw(10);
	    assertEquals(0, account.getFees());
	}

	private void creditLimitIsReachedTester() {
	    CreditAccount account = new CreditAccount(150);
	    
	    assertEquals(41, account.withdraw(41));
	    assertEquals(-91, account.getBalance());
	    
	    assertEquals(0, account.withdraw(50));
	    assertEquals(-91, account.getBalance());
	    
	    assertEquals(9, account.withdraw(9));
	    assertEquals(-150, account.getBalance());
	}

	private void balanceIsNegativeTester() {
	    CreditAccount account = new CreditAccount(200);
	    
	    account.deposit(100);
	    
	    assertEquals(60, account.withdraw(60));
	    assertEquals(40, account.getBalance());
	    
	    assertEquals(41, account.withdraw(41));
	    assertEquals(-51, account.getBalance());
	    
	    assertEquals(50, account.withdraw(50));
	    assertEquals(-151, account.getBalance());
	}

	private void withdrawUsingNegativeValuesTester() {
	    CreditAccount account = new CreditAccount(100);
	    
	    account.deposit(100);
	    
	    assertEquals(0, account.withdraw(-100));
	    assertEquals(100, account.getBalance());
	}

	private void withdrawUsingPositiveValuesTester() {
	    CreditAccount account = new CreditAccount(100);
	    
	    account.deposit(1000);
	    
	    assertEquals(100, account.withdraw(100));
	    assertEquals(900, account.getBalance());
	    
	    assertEquals(80, account.withdraw(80));
	    assertEquals(820, account.getBalance());
	}

	private void depositUsingNegativeValuesTester() {
	    CreditAccount account = new CreditAccount(100);
	    
	    assertEquals(0, account.deposit(-20));
	    assertEquals(10, account.deposit(10));
	    assertEquals(10, account.deposit(-90));
	}

	private void depositUsingPositiveValuesTester() {
	    CreditAccount account = new CreditAccount(100);
	    
	    assertEquals(10, account.deposit(10));
	    assertEquals(100, account.deposit(90));
	}

	private void creditIsUnchangedTester() { 
	    CreditAccount account = new CreditAccount(100);
	    
	    assertEquals(100, account.getCredit());
	    
	    account.deposit(100);
	    assertEquals(100, account.getCredit());
	    
	    account.withdraw(200);
	    assertEquals(100, account.getCredit());
	}

	private void negativeBalanceTester() {
	    CreditAccount account = new CreditAccount(100);
	    
	    account.deposit(100);
	    assertEquals(100, account.getBalance());
	    
	    account.withdraw(101);
	    assertEquals(-51, account.getBalance());
	    
	    account.deposit(100);
	    assertEquals(49, account.getBalance());
	}

	private void positiveBalanceTester() {
	    CreditAccount account = new CreditAccount(0);
	    
	    account.deposit(100);
	    assertEquals(100, account.getBalance());
	    
	    account.deposit(101);
	    assertEquals(201, account.getBalance());
	}
}
