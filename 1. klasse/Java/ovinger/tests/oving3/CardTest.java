package oving3;

import junit.framework.TestCase;

public class CardTest extends TestCase {
	
	public void testToString() {
		String[] suits = {"S", "H", "D", "C"};
		for (int i = 0 ; i < suits.length; i++){
			for (int j = 1; j <= 13; j ++){
				Card c = new Card();
				c.suit = suits[i];
				c.face = j;
				assertTrue((suits[i] + j).equals(c.toString()));
			}
		}
	}
}
