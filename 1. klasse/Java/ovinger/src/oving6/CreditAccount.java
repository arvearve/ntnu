package oving6;

public class CreditAccount implements Account {

	private int balance;
	private int creditLimit;
	private int feesTotal;

	public CreditAccount(int creditLimit) {
		this.creditLimit = creditLimit;
		balance = 0;
		feesTotal = 0;
	}

	public int getFees() {
		return feesTotal;
	}

	public int getBalance() {
		return balance;
	}

	public int getCredit() {
		return creditLimit;
	}

	public int deposit(int amount) {
		if (amount > 0) {
			this.balance += amount;
		}
		return balance;
	}

	public int withdraw(int amount) {
		int fee = 0;
		if (amount < 0) {return 0;}
		if (balance - amount < 0) {fee = 50;} 
		
		if( (balance - (amount+fee)) >= (0-creditLimit) ){
			balance -= (amount+fee);
			feesTotal += fee;
			return amount;
		}
		return 0;
		
	}

}
