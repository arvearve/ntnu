package oving5.card;

import java.util.ArrayList;

import junit.framework.TestCase;

public class CardDeckTest extends TestCase {
	
	private final static String[] suits = {"S", "H", "D", "C"};
	
	private void testCard(int pos, Card card) {
		assertEquals(suits[pos / 13], card.getSuit());
		assertEquals(pos % 13 + 1, card.getFace());
	}
	
	private void testCardDeck(CardDeck deck, int length) {
		assertEquals(length, deck.getCardCount());
		for (int i = 0 ; i < length ; i++) {
			testCard(i, deck.getCard(i));
		}
	}

	public void testCardDeck() {
		testCardDeck(new CardDeck(), 52);
	}
	
	public void testGetCard() {
		CardDeck deck = new CardDeck();
		testCardDeck(deck, 52);
		assertNull(deck.getCard(-1));
		assertNull(deck.getCard(53));
	}
	
	private void testDealSize(CardDeck deck, int pos, int size) {
		assertEquals(deck.getCardCount(), pos + 1);
		ArrayList<Card> hand = deck.deal(size);
		if (size > pos + 1) {
			assertNull(hand);
			assertEquals(deck.getCardCount(), pos + 1);
			return;
		}
		assertEquals(deck.getCardCount(), pos + 1 - size);
		assertEquals(size, hand.size());
		for (int i = 0 ; i < hand.size() ; i++) {
			testCard(pos - i, hand.get(i));
		}
	}

	public void testDeal() {
		CardDeck deck = new CardDeck();
		// testing that the cards are in right order with different deal sizes
		testDealSize(deck, 51, 1);
		testCardDeck(deck, 51);

		deck = new CardDeck();
		testDealSize(deck, 51, 0);
		testCardDeck(deck, 52);
		
		deck = new CardDeck();
		testDealSize(deck, 51, 51);
		testCardDeck(deck, 1);
		
		deck = new CardDeck();
		testDealSize(deck, 51, 52);
		testCardDeck(deck, 0);
		
		deck = new CardDeck();
		testDealSize(deck, 51, 53);
		testCardDeck(deck, 52);
		
		// testing several deals from same deck
		deck = new CardDeck();
		testDealSize(deck, 51, 10);
		testDealSize(deck, 41, 40);
		testDealSize(deck, 1, 1);
		testDealSize(deck, 0, 1);
		testDealSize(deck, -1, 1);
	}
}
