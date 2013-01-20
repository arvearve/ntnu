package oving3;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for oving3");
		//$JUnit-BEGIN$
		suite.addTestSuite(PersonTest.class);
		suite.addTestSuite(CardDeckTest.class);
		suite.addTestSuite(CardTest.class);
		//$JUnit-END$
		return suite;
	}
}
