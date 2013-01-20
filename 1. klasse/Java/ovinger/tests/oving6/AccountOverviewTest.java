package oving6;

import junit.framework.TestCase;

/**
 * @author Eivind Sorteberg (sorteber)
 * @author Rune Vistnes (runevi)
 */
public class AccountOverviewTest extends TestCase {
    
	public void testAddAccount() {
        addingRandomAccountsTester();
    }

    public void testGetTotalBalance() {
        totalBalanceIsInitialisedToZeroTester();
        totalBalanceUsingRandomGeneratedAccountsTester();
    }
    
    public void testGetTotalCredit() {
        totalCreditIsInitialisedToZeroTester();
        totalCreditUsingRandomGeneratedAccountsTester();
    }
    
    public void testGetTotalFees() {
        totalFeesIsInitialisedToZeroTester();
        totalFeesUsingDifferentAccountsTester();
    }

    private Object[] generateRandomAccountsWithRandomCredit(int nofAccounts) {
	    AccountOverview overview = new AccountOverview();
	    int balance = 0;
	    int credit = 0;
	    for (int i = 0; i < nofAccounts; i++) {
	        Account account;
	        if (Math.random() < 0.5) {
	            account = new SavingsAccount();
	        }
	        else {
	            int randomCredit = (int)(Math.random() * 100);
	            account = new CreditAccount(randomCredit);
	            credit += randomCredit;
	        }
	        int randomDeposit = (int)(Math.random() * 100);
	        balance += randomDeposit;
	        account.deposit(randomDeposit);
	        overview.addAccount(account);
	    }
	    return new Object[]{overview, balance, credit};
	}

	private void totalFeesUsingDifferentAccountsTester() {
	    AccountOverview overview = new AccountOverview();
	    Account[] accounts = new Account[6];
	    for (int i = 0; i < accounts.length - 1; i+=2) {
	        accounts[i] = new SavingsAccount();
	        accounts[i+1] = new CreditAccount(100);
	    }
	    int fees = 0;
	    accounts[1].deposit(50);
	    accounts[1].withdraw(50);
	    accounts[1].withdraw(40);
	    fees += 50;
	    accounts[1].withdraw(50);
	
	    accounts[3].withdraw(10);
	    fees += 50;
	
	    accounts[5].deposit(100);
	    accounts[5].withdraw(50);
	    accounts[5].withdraw(50);
	    accounts[5].withdraw(50);
	    fees += 50;
	    accounts[5].withdraw(1);
	
	    for (Account account : accounts) {
	        overview.addAccount(account);
	    }
	
	    assertEquals(fees, overview.getTotalFees());
	}

	private void totalFeesIsInitialisedToZeroTester() {
	    AccountOverview overview = new AccountOverview();
	    assertEquals(0, overview.getTotalFees());
	}

	private void totalCreditUsingRandomGeneratedAccountsTester() {
	    Object[] generatedOverview = generateRandomAccountsWithRandomCredit(100);
	    AccountOverview overview = (AccountOverview)generatedOverview[0];
	    int totalCredit = (Integer)generatedOverview[2];
	    assertEquals(totalCredit, overview.getTotalCredit());
	}

	private void totalBalanceUsingRandomGeneratedAccountsTester() {
	    Object[] generatedOverview = generateRandomAccountsWithRandomCredit(10);
	    AccountOverview overview = (AccountOverview) generatedOverview[0];
	    int balance = (Integer)generatedOverview[1];
	    assertEquals(balance, overview.getTotalBalance());
	}

	private void totalBalanceIsInitialisedToZeroTester() {
	    AccountOverview overview = new AccountOverview();
	    assertEquals(0, overview.getTotalBalance());
	}

	private void totalCreditIsInitialisedToZeroTester() {
		AccountOverview overview = new AccountOverview();
		assertEquals(0, overview.getTotalCredit());
	}
	
	private void addingRandomAccountsTester() {
	    AccountOverview overview = new AccountOverview();
	    overview.addAccount(new SavingsAccount());
	    assertEquals(1, overview.getAccountCount());
	    for (int i = 2; i <= 100; i++) {
            overview.addAccount(Math.random() < 0.5 ? new SavingsAccount() : new CreditAccount((int)(Math.random() * 100)));
	        assertEquals(i, overview.getAccountCount());
	    }
	}
}
