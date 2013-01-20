package oving5;

import oving5.card.CardDeckTest;
import oving5.card.CardTest;
import oving5.person.PersonTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for oving5");
        //$JUnit-BEGIN$
        suite.addTestSuite(PersonTest.class);
        suite.addTestSuite(CardTest.class);
        suite.addTestSuite(CardDeckTest.class);
        //$JUnit-END$
        return suite;
    }
}
