package oving5.person;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * 
 * @author Alexander H�st, Ind�k, Undass TDT4100/TDT4102, Spring 2009
 * 
 */

public class PersonTest extends TestCase {

	private ArrayList<Person> testPersonReturningPersons() {
		Person person1 = new Person('m');
		Person person2 = new Person('f');
		Person person3 = new Person('a');
		assertTrue(person1.isMale());
		assertFalse(person1.isFemale());
		assertTrue(person2.isFemale());
		assertFalse(person2.isMale());
		assertFalse(person3.isMale());
		assertFalse(person3.isFemale());
		return new ArrayList<Person>(Arrays.asList(person1, person2, person3));
	}

	public void testPerson() {
		testPersonReturningPersons();
	}
	
	public void testGetSetName() {
		getNameTester();
		setNameTester();
	}

	private void getNameTester() {
		Person person1 = new Person('m');
		person1.setName("John Doe");

		Person person2 = new Person('f');
		person2.setName("Jane Doe");

		assertEquals("John Doe", person1.getName());
		assertEquals("Jane Doe", person2.getName());
	}

	private void setNameTester() {
		Person person1 = new Person('m');
		Person person2 = new Person('m');

		person1.setName("John Doe");
		assertEquals("John Doe", person1.getName());

		person2.setName("Jane Doe");
		assertEquals("Jane Doe", person2.getName());
		usingNameWithLettersOnlyTester();
		usingNameWithLettersAndWhitespacesTester();
		usingNameWithIllegalCharactersTester();
	}
	
	private void usingNameWithLettersOnlyTester() {
		Person p = new Person('m');
		p.setName("Sylvester");
		assertTrue("Sylvester".equals(p.getName()));
		p.setName("MacGyver");
		assertTrue("MacGyver".equals(p.getName()));
	}

	private void usingNameWithLettersAndWhitespacesTester() {
		Person p = new Person('m');
		p.setName("Sylvester Cat");
		assertTrue("Sylvester Cat".equals(p.getName()));
		p.setName("Angus MacGyver");
		assertTrue("Angus MacGyver".equals(p.getName()));
	}

	private void usingNameWithIllegalCharactersTester() {
		Person p = new Person('m');
		p.setName("John$ Doe");
		assertTrue(p.getName() == null);
		p.setName("$Jane Doe$");
		assertTrue(p.getName()== null);
	}

	public void testGender() {
		isMaleTester();
		isFemaleTester();
	}

	private void isMaleTester() {
		Person p = new Person('m');
		assertTrue("Was not male", p.isMale());
		p = new Person('f');
		assertFalse("Was male", p.isMale());
	}

	private void isFemaleTester() {
		Person p = new Person('m');
		assertFalse("Was female", p.isFemale());
		p = new Person('f');
		assertTrue("Was not female", p.isFemale());
	}

	//
	
	public void testPersonWithChildCount() {
		ArrayList<Person> persons = testPersonReturningPersons();
		for (int i = 0; i < persons.size(); i++) {
			assertEquals(persons.get(i).getChildCount(), 0);
		}
	}

	private void addChildToFatherMotherTester() {
		Person father = new Person('m');
		Person mother = new Person('f');
		Person child1 = new Person('m');
		Person child2 = new Person('m');
		Person child3 = new Person('f');

		// add once
		father.addChild(child1);
		mother.addChild(child1);
		assertEquals(1, father.getChildCount());
		assertEquals(1, mother.getChildCount());
		assertEquals(father, child1.getFather());
		assertEquals(mother, child1.getMother());
		assertEquals(child1, father.getChild(0));
		assertEquals(child1, mother.getChild(0));

		// add same child should not affect the children
		father.addChild(child1);
		mother.addChild(child1);
		assertEquals(1, father.getChildCount());
		assertEquals(1, mother.getChildCount());
		
		// adding multiple children
		father.addChild(child2);
		father.addChild(child3);
		assertEquals(3, father.getChildCount());
		assertEquals(father, child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());
		assertEquals(child1, father.getChild(0));
		assertEquals(child2, father.getChild(1));
		assertEquals(child3, father.getChild(2));
	}
	
	private void addChildToOtherFatherMotherTester() {
		Person father = new Person('m');
		Person mother = new Person('f');
		Person child1 = new Person('m');
		Person otherFather = new Person('m');
		Person otherMother = new Person('f');
		
		// add once
		father.addChild(child1);
		mother.addChild(child1);
		assertEquals(1, father.getChildCount());
		assertEquals(1, mother.getChildCount());
		assertEquals(father, child1.getFather());
		assertEquals(mother, child1.getMother());
		assertEquals(child1, father.getChild(0));
		assertEquals(child1, mother.getChild(0));
		
		// add same child to other father and mother
		otherFather.addChild(child1);
		otherMother.addChild(child1);		
		assertEquals(1, otherFather.getChildCount());
		assertEquals(1, otherMother.getChildCount());
		assertEquals(otherFather, child1.getFather());
		assertEquals(otherMother, child1.getMother());
		assertEquals(child1, otherFather.getChild(0));
		assertEquals(child1, otherMother.getChild(0));

		assertEquals(0, father.getChildCount());		
		assertEquals(0, mother.getChildCount());		
	}

	public void testAddChild() {
		addChildToFatherMotherTester();
		addChildToOtherFatherMotherTester();
	}

	public void testSetAsAddChild() {
		Person father = new Person('m');
		Person mother = new Person('f');
		Person child1 = new Person('m');
		Person child2 = new Person('m');
		Person child3 = new Person('f');
		
		// add once
		child1.setFather(father);
		child1.setMother(mother);
		assertEquals(1, father.getChildCount());
		assertEquals(1, mother.getChildCount());
		assertEquals(father, child1.getFather());
		assertEquals(mother, child1.getMother());
		assertEquals(child1, father.getChild(0));
		assertEquals(child1, mother.getChild(0));
		
		// add same child should not affect the children
		child1.setFather(father);
		child1.setMother(mother);
		assertEquals(1, father.getChildCount());
		assertEquals(1, mother.getChildCount());
		
		// adding multiple children
		child2.setFather(father);
		child3.setFather(father);
		assertEquals(3, father.getChildCount());
		assertEquals(father, child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());
		assertEquals(child1, father.getChild(0));
		assertEquals(child2, father.getChild(1));
		assertEquals(child3, father.getChild(2));
	}
	
	public void testRemoveChild() {
		Person father = new Person('m');
		Person child1 = new Person('m');
		Person child2 = new Person('m');
		Person child3 = new Person('f');

		// adding/removing multiple children
		father.addChild(child1);
		father.addChild(child2);
		father.addChild(child3);
		
		father.removeChild(child1);
		assertEquals(2, father.getChildCount());
		assertNotSame(father, child1.getFather());
		assertNull(child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());

		father.removeChild(child1);
		assertEquals(2, father.getChildCount());
		assertNotSame(father, child1.getFather());
		assertNull(child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());

		father.removeChild(child3);
		assertEquals(1, father.getChildCount());
		assertNotSame(father, child3.getFather());
		assertNull(child3.getFather());
		assertEquals(father, child2.getFather());	

		father.removeChild(child2);
		assertEquals(0, father.getChildCount());
		assertNotSame(father, child2.getFather());
		assertNull(child2.getFather());
	}
	
	public void testSetAsRemoveChild() {
		Person father = new Person('m');
		Person child1 = new Person('m');
		Person child2 = new Person('m');
		Person child3 = new Person('f');
		
		// adding/removing multiple children
		father.addChild(child1);
		father.addChild(child2);
		father.addChild(child3);
		
		child1.setFather(null);
		assertEquals(2, father.getChildCount());
		assertNotSame(father, child1.getFather());
		assertNull(child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());
		
		child1.setFather(null);
		assertEquals(2, father.getChildCount());
		assertNotSame(father, child1.getFather());
		assertNull(child1.getFather());
		assertEquals(father, child2.getFather());	
		assertEquals(father, child3.getFather());
		
		child3.setFather(null);
		assertEquals(1, father.getChildCount());
		assertNotSame(father, child3.getFather());
		assertNull(child3.getFather());
		assertEquals(father, child2.getFather());	
		
		child2.setFather(null);
		assertEquals(0, father.getChildCount());
		assertNotSame(father, child2.getFather());
		assertNull(child2.getFather());
	}
	
	public void testGetSetParents(){
		testGetParent();
		testSetParent();
	}
	
	public void testGetParent(){
		Person son = new Person('m');
		Person father = new Person('m');
		Person mother = new Person('f');
		son.setMother(mother);		
		son.setFather(father);
		assertEquals(father, son.getFather());
		assertEquals(mother, son.getMother());
	}
	
	private void testInternalContainsChild(Person parent, Person child, boolean logic) {
		boolean found = false;
		int size = parent.getChildCount();
		for (int i = 0; i < size; i++) {
			found |= (parent.getChild(i) == child);
		}
		assertEquals(logic, found);
	}
	
	public void testSetParent() {
		Person son = new Person('m');
		Person father = new Person('m');
		Person mother = new Person('f');
		Person otherfather = new Person('m');
		Person othermother = new Person('f');
		son.setMother(father);		
		son.setFather(mother);
		assertNull("Tried to set a female as a person's father.", son.getMother());
		assertNull("Tried to set a male as a person's mother.", son.getFather());
		testInternalContainsChild(father, son, false);
		testInternalContainsChild(mother, son, false);
		son.setMother(mother);
		son.setFather(father);
		assertEquals("Was not the right parent!", mother, son.getMother());
		assertEquals("Was not the right parent!", father, son.getFather());
		testInternalContainsChild(father, son, true);
		testInternalContainsChild(mother, son, true);
		son.setMother(othermother);
		son.setFather(otherfather);
		testInternalContainsChild(father, son, false);
		testInternalContainsChild(mother, son, false);
		testInternalContainsChild(otherfather, son, true);
		testInternalContainsChild(othermother, son, true);
		assertTrue(son.getMother() == othermother);
		assertTrue(son.getFather() == otherfather);	
	}
	
	public void testIndexOfChild() {
		Person parent = new Person('m');
		Person child1 = new Person('f');
		Person child2 = new Person('f');		
		parent.addChild(child1);
		parent.addChild(child2);
		assertEquals(0, parent.indexOfChild(child1));
		assertEquals(1, parent.indexOfChild(child2));
		assertEquals(-1, parent.indexOfChild(new Person('f')));
	}
	
	public void testContainsChild(){
		Person parent = new Person('m');
		Person child1 = new Person('f');
		Person child2 = new Person('f');		
		parent.addChild(child1);
		assertTrue(parent.containsChild(child1));
		assertFalse(parent.containsChild(child2));
	}

	private void testIsAncestorOf(Person parent, Person child) {
		assertTrue(parent.isAncestorOf(child));
		assertFalse(child.isAncestorOf(parent));
		assertFalse(parent.isAncestorOf(parent));
		assertFalse(child.isAncestorOf(child));
	}
	public void testIsAncestorOf() {
		Person mormor = new Person('f');
		Person farmor = new Person('f');
		Person morfar = new Person('m');
		Person farfar = new Person('m');
		Person mor = new Person('f');
		Person far = new Person('m');
		Person child1 = new Person('m');
		Person child2 = new Person('f');

		mor.setMother(mormor);
		mor.setFather(morfar);
		far.setMother(farmor);
		far.setFather(farfar);
		
		child1.setMother(mor);
		child2.setMother(mor);
		child1.setFather(far);
		child2.setFather(far);

		testIsAncestorOf(mormor, mor);
		testIsAncestorOf(morfar, mor);
		testIsAncestorOf(mormor, child1);
		testIsAncestorOf(morfar, child1);
		testIsAncestorOf(mormor, child2);
		testIsAncestorOf(morfar, child2);

		testIsAncestorOf(farmor, far);
		testIsAncestorOf(farfar, far);
		testIsAncestorOf(farmor, child1);
		testIsAncestorOf(farfar, child1);
		testIsAncestorOf(farmor, child2);
		testIsAncestorOf(farfar, child2);

		testIsAncestorOf(mor, child1);
		testIsAncestorOf(far, child1);
		testIsAncestorOf(mor, child2);
		testIsAncestorOf(far, child2);

		assertFalse(mormor.isAncestorOf(mormor));
		assertFalse(mormor.isAncestorOf(morfar));
		assertFalse(mormor.isAncestorOf(farmor));
		assertFalse(mormor.isAncestorOf(farfar));

		assertFalse(mor.isAncestorOf(mormor));
		assertFalse(mor.isAncestorOf(morfar));
		assertFalse(mor.isAncestorOf(farmor));
		assertFalse(mor.isAncestorOf(farfar));
		
		assertFalse(child1.isAncestorOf(mor));
		assertFalse(child1.isAncestorOf(far));
		assertFalse(child1.isAncestorOf(child2));

		assertFalse(child2.isAncestorOf(mor));
		assertFalse(child2.isAncestorOf(far));
		assertFalse(child2.isAncestorOf(child1));
		
		ArrayList<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 10; i++) {
			persons.add(new Person('m'));
			if (i > 0) {
				persons.get(i).setFather(persons.get(i - 1));
			}
		}
		for (int i = 1; i < persons.size(); i++) {
			testIsAncestorOf(persons.get(0), persons.get(i));
		}
	}
}
