package oving6;

import junit.framework.TestCase;

/**
 * @author Eivind Sorteberg (sorteber)
 * @author Rune Vistnes (runevi)
 */
public class CardTest extends TestCase {

	public void testConstructor() {
        GetFaceTests.testAllFaces();
        GetSuitTests.testAllSuits();
    }
    
    public void testGetFace() {
        GetFaceTests.testAllFaces();
    }
    
    public void testGetSuit() {
        GetSuitTests.testAllSuits();
    }
    
    public void testCompareTo() {
        CompareToTests.testUsingDifferentFaces();
        CompareToTests.testUsingSimilarFacesAndDifferentSuits();
    }

    private static String[] suitsInOrder = {"C", "D", "H", "S"};
    
    private static class GetFaceTests {
        private static void testAllFaces() {
            for (int suit = 0; suit < suitsInOrder.length; suit++) {
                for (int face = 1; face <= 13; face++) {
                    Card card = new Card(suitsInOrder[suit], face);
                    assertEquals(face, card.getFace());
                }
            }
        }
    }
    
    private static class GetSuitTests {
        private static void testAllSuits() {
            for (int suit = 0; suit < suitsInOrder.length; suit++) {
                for (int face = 1; face <= 13; face++) {
                    Card card = new Card(suitsInOrder[suit], face);
                    assertEquals(suitsInOrder[suit], card.getSuit());
                }
            }
        }
    }
        
    private static class CompareToTests {
    	
        private static void testUsingDifferentFaces() {
            for (int i = 0; i < 20; i++) {
                int value1 = (int)(Math.random() * 13) + 1;
                int value2 = (int)(Math.random() * 13) + 1;
                
                int suitVal1 = (int)(Math.random() * 4);
                int suitVal2 = (int)(Math.random() * 4);
                
                while (value2 == value1) {
                    value2 = (int)(Math.random() * 13) + 1;
                }
                
                Card card1 = new Card(suitsInOrder[suitVal1], value1);
                Card card2 = new Card(suitsInOrder[suitVal2], value2);
                
                if (value1 == 1) value1 = 14; 
                if (value2 == 1) value2 = 14;  
                
                assertEquals(value1 + " < " + value2, value1 < value2, card1.compareTo(card2) < 0);
            }
        }
        
        private static void testUsingSimilarFacesAndDifferentSuits() {
            for (int i = 0; i < 20; i++) {
                int value = (int)(Math.random() * 13) + 1;
                
                int suitVal1 = (int)(Math.random() * 4);
                int suitVal2 = (int)(Math.random() * 4); 
                
                while (suitVal2 == suitVal1) {
                    suitVal2 = (int)(Math.random() * 4);
                }
                
                Card card1 = new Card(suitsInOrder[suitVal1], value);
                Card card2 = new Card(suitsInOrder[suitVal2], value);
                
                assertEquals(suitsInOrder[suitVal1] + " < " + suitsInOrder[suitVal2], suitVal1 < suitVal2, card1.compareTo(card2) < 0);
            }
        }
    }
}
