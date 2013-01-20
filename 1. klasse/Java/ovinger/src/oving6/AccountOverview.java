package oving6;
import java.util.ArrayList;
public class AccountOverview{
	
	private ArrayList<Account> accounts; 

	public AccountOverview(){
		accounts = new ArrayList<Account>();
	}
	
	public int getAccountCount(){
		return accounts.size();
	}
	
	public Account getAccount(int n){
		return accounts.get(n);
	}
	
	public void addAccount(Account account){
		if(accounts.contains(account)){return;}
		accounts.add(account);
	}
	
	public int getTotalBalance(){
		int result = 0;
		for(Account a:accounts){
			result += a.getBalance();
		}
		return result;
	}
	
	public int getTotalCredit(){
		int result = 0;
		for(Account a:accounts){
			result += a.getCredit();
		}
		return result;
	}
	public int getTotalFees(){
		int result = 0;
		for( Account a:accounts){
			if(a instanceof CreditAccount){
			result += ((CreditAccount)a).getFees();
			}
		}
		return result;
	}
	
}
