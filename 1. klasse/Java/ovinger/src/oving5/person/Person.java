package oving5.person;

import java.util.ArrayList;

public class Person {
	public enum Gender {
		MALE, FEMALE, NA // usikker om jeg bruker dette riktig?
							// vennligst kommenter.
	};

	private String name;
	private Gender gender;
	private Person mother;
	private Person father;
	private ArrayList<Person> children = new ArrayList<Person>();

	public Person(char gender) {
		// set gender.
		if (gender == 'm') {
			this.gender = Gender.MALE;
		} else if (gender == 'f') {
			this.gender = Gender.FEMALE;
		} else {
			this.gender = Gender.NA;
		}

	}

	public void setName(String name) {
		if (name.matches("[[A-Za-z]*\\s]*")) {
			this.name = name;
		}
	}

	public int getChildCount() {
		return this.children.size();
	}

	public String getName() {
		return this.name;
	}

	public boolean isMale() {
		if (gender == Gender.MALE) {
			return true;
		}
		return false;
	}

	public boolean isFemale() {
		if (gender == Gender.FEMALE) {
			return true;
		}
		return false;
	}

	public Person getMother() {
		return mother;
	}

	public Person getFather() {
		return father;
	}

	public Person getChild(int i) {
		return this.children.get(i);
	}

	public void setFather(Person parent) {
		if (parent == null) {
			if (this.father != null) {
				this.father.removeChild(this);
			}
			this.father = null;
			return;
		}
		if (this.father != null && this.father != parent) {
			this.father.removeChild(this);
		} // remove old relations
		if (parent.isMale()) {
			this.father = parent;
			parent.addChild(this);
		}
	}

	public void setMother(Person parent) {
		if (parent == null) {
			if (this.mother != null) {
				this.mother.removeChild(this);
			}
			this.mother = null;
			return;
		}
		if (this.mother != null && this.mother != parent) {
			this.mother.removeChild(this);
		} // remove old relations
		if (parent.isFemale()) {
			this.mother = parent;
			parent.addChild(this);
		}
	}

	public void addChild(Person child) {
		if (!this.children.contains(child)) {
			this.children.add(child);
		} // ensure no double entries.
		if (isMale() && child.getFather() != this) {
			child.setFather(this);
		} else if (isFemale() && child.getMother() != this) {
			child.setMother(this);
		}
	}

	public void removeChild(Person child) {
		if (this.children.contains(child)) {
			this.children.remove(child);
		}
		if (isMale()) {
			// child.setFather(null);
			child.removeFather();
		} else if (isFemale()) {
			// child.setMother(null);
			child.removeMother();
		}
	}

	private void removeFather() {
		this.father = null;
	}

	private void removeMother() {
		this.mother = null;
	}

	public int indexOfChild(Person child) {
		return this.children.indexOf(child);
	}

	public boolean containsChild(Person child) {
		return this.children.contains(child);
	}

	public boolean isAncestorOf(Person child){

        for (Person c : this.children){
            if (c.equals(child)){
                return true;

            } else if (c.isAncestorOf(child)) {
                return true;
            }
        }
        return false;
    }
}
