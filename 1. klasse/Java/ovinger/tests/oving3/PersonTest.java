package oving3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class PersonTest extends TestCase {
	
	public void testParents(Person father, Person mother, int childCount) {
		List<Person> children = null; 
		if (father != null) {
			children = father.children;
		} else if (mother != null) {
			children = mother.children;
		}
		assertTrue(children != null && children.size() == childCount);
		for (Person child : children) {
			if (mother != null) {
				assertEquals(child.mother, mother);
			} else {
				mother = child.mother;
			}
			if (father != null) {
				assertEquals(child.father, father);
			} else {
				father = child.father;
			}
		}
	}

	public void testParent(Person parent, int childCount) {
		List<Person> children = parent.children;
		assertTrue(children != null && children.size() == childCount);
		if (children.get(0).mother == parent) {
			testParents(null, parent, childCount);
		} else if (children.get(0).father == parent) {
			testParents(parent, null, childCount);
		}
	}

	private Person createPerson(String name) {
		Person p = new Person();
		p.name = name;
		return p;
	}
	
	public void testIsMotherOf() {
		Person father = createPerson("father");
		Person mother = createPerson("mother");
		Person child = createPerson("child");
		child.mother = mother;		
		child.father = father;

		assertFalse(mother.isMotherOf(child));

		mother.children = new ArrayList<Person>();
		mother.children.add(child);
		father.children = new ArrayList<Person>();
		father.children.add(child);
		
		assertTrue(mother.isMotherOf(child));
		assertFalse(mother.isMotherOf(mother));
		assertFalse(father.isMotherOf(child));
		assertFalse(child.isMotherOf(child));
	}

	public void testIsFatherOf() {
		Person father = createPerson("father");
		Person mother = createPerson("mother");
		Person child = createPerson("child");
		child.mother = mother;		
		child.father = father;

		assertFalse(father.isFatherOf(child));

		mother.children = new ArrayList<Person>();
		mother.children.add(child);
		father.children = new ArrayList<Person>();
		father.children.add(child);

		assertTrue(father.isFatherOf(child));
		assertFalse(father.isFatherOf(father));
		assertFalse(mother.isFatherOf(child));
		assertFalse(child.isFatherOf(child));
	}
	
	public void testIsSiblingOf() {
		Person father1 = createPerson("father1");
		Person father2 = createPerson("father2");
		Person mother = createPerson("mother");
		Person child1 = createPerson("child1");
		Person child2 = createPerson("child2");
		Person child3 = createPerson("child3");
		
		child1.mother = mother;		
		child2.mother = mother;
		child3.mother = mother;
		mother.children = new ArrayList<Person>(Arrays.asList(child1, child2, child3));
		
		child1.father = father1;
		child2.father = father1;
		father1.children = new ArrayList<Person>(Arrays.asList(child1, child2)); 		
		
		child3.father = father2;
		father2.children = new ArrayList<Person>(Arrays.asList(child3));
		
		assertTrue(child1.isSiblingOf(child2));
		assertTrue(child2.isSiblingOf(child1));
		assertFalse(child1.isSiblingOf(child1));
		assertFalse(child1.isSiblingOf(child3));
		assertFalse(child2.isSiblingOf(child3));
		assertFalse(child3.isSiblingOf(child1));
		assertFalse(child3.isSiblingOf(child2));
	}
	
}
