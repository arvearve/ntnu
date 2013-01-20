package testing;

import junit.framework.TestCase;

public class PersonTest extends TestCase {

	private Person p1, p2, p3, p4;
	
	protected void setUp() throws Exception {
		super.setUp();
		p1 = new Person();
		p2 = new Person();
		p3 = new Person();
		p4 = new Person();
	}

	public void testPerson() {
		assertNull(p1.getPartner());
		assertNull(p2.getPartner());
		assertNull(p3.getPartner());
		assertNull(p4.getPartner());
	}

	public void testSetPartner1() {
		p1.setPartner(p2);
		assertEquals(p2, p1.getPartner());
		assertEquals(p1, p2.getPartner());
	}
	
	public void testSetPartner2() {
		p1.setPartner(p2);
		p1.setPartner(null);
		assertNull(p1.getPartner());
		assertNull(p2.getPartner());
	}
	
	public void testSetPartner3() {
		p1.setPartner(p2);
		p3.setPartner(p4);
		p1.setPartner(p4);
		assertEquals(p4, p1.getPartner());
		assertEquals(p1, p4.getPartner());
		assertNull(p2.getPartner());
		assertNull(p3.getPartner());
	}
}
