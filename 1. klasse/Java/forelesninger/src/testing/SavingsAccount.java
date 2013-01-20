package testing;

public class SavingsAccount {

	private int balance, fee;
	
	public SavingsAccount(int fee) {
		balance = 0;
		this.fee = fee;
	}
	
	public int getFee() {
		return fee;
	}
	public int getBalance() {
		return balance;
	}
	
	public void deposit(int amount) {
		if (amount < 0) {
			return;
		}
		balance += amount;
	}
	
	public void withdraw(int amount) {
		if (amount < 0) {
			return;
		}
		int newBalance = balance - amount - fee;
		if (newBalance >= 0) {
			balance = newBalance;
		}
	}
}
