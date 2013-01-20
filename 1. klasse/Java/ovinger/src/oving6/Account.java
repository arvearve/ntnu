package oving6;

public interface Account {
	public int getBalance();
	public int getCredit();
	public int deposit(int amount);
	public int withdraw(int amount);	
}