package oving3;

import java.util.ArrayList;

public class Person {

	String name;
	Person mother;
	Person father;

	ArrayList<Person> children = new ArrayList<Person>();

	public Person() {
	} // Constructor to allow making of nameless persons.

	public Person(String name) { // Constructor to allow making persons with
		super(); // initial name
		this.name = name;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
		// mother.children.add(this); //Removed to pass test in Part1. Makes
		// sense to do it this way in Part 2.
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
		// father.children.add(this); //Removed to pass test in Part1. Makes
		// sense to do it this way in Part 2.
	}

	public String toString() {
		String result = "\n\n" + name + ": ";
		result += "\n------";
		if (this.father != null) {
			result += "\nFather: " + this.father.name;
		}
		if (this.mother != null) {
			result += "\nMother: " + this.mother.name;
		}
		for (Person p : children) {
			result += "\nChild: " + p.name;

		}
		result += "\n";
		return result;
	}

	public boolean isMotherOf(Person child) {
		if (this.children.contains(child) && child.getMother() == this) {
			return true;
		}
		return false;
	}

	public boolean isFatherOf(Person child) {
		if (this.children.contains(child) && child.getFather() == this) {
			return true;
		}
		return false;
	}

	public boolean isSiblingOf(Person person) {

		if (this.getFather() == person.getFather()
				&& this.getMother() == person.getMother() && this != person) {
			return true;
		}
		return false;
	}

}
