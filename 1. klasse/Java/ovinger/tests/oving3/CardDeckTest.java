package oving3;

import junit.framework.TestCase;

public class CardDeckTest extends TestCase {
	
	private String getSuit(Card c) {
		return c.suit;
	}
	private int getFace(Card c) {
		return c.face;
	}
	
	public void testInit() {
		// Create a deck of cards
		CardDeck cardDeck = new CardDeck();
		cardDeck.init();
		
		// Make sure there's 52 cards in the deck
		assertEquals(cardDeck.cards.size(), 52);

		// Make sure the cards are organized correctly 
		String[] suits = {"S", "H", "D", "C"};
		for (int suitPos = 0 ; suitPos < suits.length; suitPos++){
			String suit = suits[suitPos];
			
			for (int facePos = 0; facePos < 13; facePos ++){
				int pos = 13*suitPos+facePos;
				
				assertEquals(suit, getSuit(cardDeck.cards.get(pos)));
				assertEquals((facePos + 1), getFace(cardDeck.cards.get(pos)));
			}
		}
	}

	public void testGetCard() {
		// Create a deck of cards
		CardDeck cardDeck = new CardDeck();
		cardDeck.init();
		
		// Make sure getCard always returns the correct card
		for (int cardPos = 0; cardPos < 52; cardPos++) {
			Card recievedCard = cardDeck.getCard(cardPos);
			Card correctCard = cardDeck.cards.get(cardPos);
			
			assertEquals(getFace(correctCard), getFace(recievedCard));
			assertEquals(getSuit(correctCard), getSuit(recievedCard));
		} 
	}

}
