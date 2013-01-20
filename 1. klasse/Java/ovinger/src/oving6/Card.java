package oving6;
public class Card implements Comparable<Card>{
	private final int face;
	private final Character suit;

	public Card(String suitString, int face) {
		this.face = (0 < face && face < 14) ? face : -1;
		char suit = suitString.charAt(0);
		switch (suit) {
		case 'S':
			this.suit = suit;
			break;
		case 'H':
			this.suit = suit;
			break;
		case 'D':
			this.suit = suit;
			break;
		case 'C':
			this.suit = suit;
			break;
		default:
			this.suit = null;
		}
	}

	public String getSuit() {
		return (suit == null) ? null : suit.toString();
	}

	public int getFace() {
		return this.face;
	}

	public String toString() {
		return getSuit() + getFace();
	}
	
	public int compareTo(Card other){
		if (this.getFace() == other.getFace()){
			String alphabet = "SHDC";
			return this.getSuit().compareTo(other.getSuit());
		}
		if(this.getFace()== 1 ){
			return 1;
		}
		if(other.getFace() == 1){
			return -1;
		}
		return this.getFace()-other.getFace();
			
		}
	}
