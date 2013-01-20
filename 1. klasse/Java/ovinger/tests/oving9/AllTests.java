package oving9;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for oving9");
		//$JUnit-BEGIN$
		suite.addTestSuite(HighscoreListTest.class);
		suite.addTestSuite(ObservableListTest.class);
		//$JUnit-END$
		return suite;
	}

}
