package oving5.card;

public class Card {
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
		 
		return (suit == null)?null:suit.toString();
	}

	public int getFace() {
		return this.face;
	}

	public String toString() {
		return getSuit() + getFace();
	}

}
