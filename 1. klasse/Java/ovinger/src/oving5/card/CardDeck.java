package oving5.card;

import java.util.ArrayList;
import java.util.Stack;

public class CardDeck {

	private Stack<Card> cards = new Stack<Card>();

	public CardDeck() {
		String[] suits = { "S", "H", "D", "C" };
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j <= 13; j++) {
				Card c = new Card(suits[i], j);
				cards.push(c);
			}
		}
		System.out.println(this.cards);
	}

	public int getCardCount() {
		System.out.println(cards.size());
		return cards.size();
	}

	public Card getCard(int n) {
		if(n<cards.size() && n >=0){
			return cards.get(n);
		}
		return null;
	}

	public ArrayList<Card> deal(int n) {
		ArrayList<Card> result = new ArrayList<Card>();
		if(n>getCardCount()){return null;}
		for (int i = 0; i < n; i++) {
			result.add(cards.pop());
		}
		return result;
	}
}
