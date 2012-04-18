package oving3;

import java.util.ArrayList;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class CardDeck extends GraphicsProgram {

	ArrayList<Card> cards = new ArrayList<Card>();

	GImage createGImage(String suit, int value) {
		String name = "";
		switch (suit.charAt(0)) {
		case 'H':
			name += "hearts";
			break;
		case 'D':
			name += "diamonds";
			break;
		case 'C':
			name += "clubs";
			break;
		case 'S':
			name += "spades";
			break;
		}
		name += "-";
		switch (value) {
		case 1:
			name += "a";
			break;
		case 11:
			name += "j";
			break;
		case 12:
			name += "q";
			break;
		case 13:
			name += "k";
			break;
		default:
			name += "" + value;
		}

		name += "-150.png";
		return new GImage("oving3/img/" + name);
	}

	public Card getCard(int i) {
		return this.cards.get(i);
	}

	public void init() {
		String[] suits = { "S", "H", "D", "C" };
		for (int i = 0; i < suits.length; i++) {
			for (int j = 1; j <= 13; j++) {
				Card c = new Card();
				c.suit = suits[i];
				c.face = j;
				cards.add(c);
			}
		}
	}

	public void run() {
		int col = 0;
		int row = 0;
		int n = 0;
		for (Card c : cards) {
			GImage currentCard = createGImage(c.suit, c.face);
			currentCard.setLocation(35 * col + 25, 150 * row + 25);
			add(currentCard);
			n++;
			col++;
			if (n == 13) {
				n = 0;
				col = 0;
				row++;
			}

		}
	}
}