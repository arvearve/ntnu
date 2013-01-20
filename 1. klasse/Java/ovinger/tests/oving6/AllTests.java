package oving6;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for oving6");
        //$JUnit-BEGIN$
        suite.addTestSuite(AccountOverviewTest.class);
        suite.addTestSuite(CardTest.class);
        suite.addTestSuite(CreditAccountTest.class);
        suite.addTestSuite(SavingsAccountTest.class);
        //$JUnit-END$
        return suite;
    }
}
