package oving6;

public class SavingsAccount implements Account{
	private int balance;
	private int creditLimit;
	
	public SavingsAccount(){
		this.balance = 0;
		this.creditLimit = 0;
	}
	
	public int getBalance(){
		return balance;
	}
	
	public int getCredit(){
		return creditLimit;
	}
	
	public int deposit(int amount){
		if(amount > 0){
			this.balance += amount;
		}
		return balance;
	}
	
	public int withdraw(int amount){
		if(amount < 0){return 0;}
		if (balance >= amount){
			balance -= amount;
			return amount;
		}
		return 0;
	}
	
}
