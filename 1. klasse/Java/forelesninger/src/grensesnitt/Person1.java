package grensesnitt;

public class Person1 implements Person, Comparable<Person1> {

	private String fornavn, etternavn;
	private int age;

	@Override
	public String toString() {
		return String.format("Person1 [fornavn=%s, etternavn=%s, age=%s]",
				fornavn, etternavn, age);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person1(String fulltNavn, int age) {
		setFulltNavn(fulltNavn);
		setAge(age);
	}

	public String getFornavn() {
		return fornavn;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public String getFulltNavn() {
		return fornavn + " " + etternavn;
	}

	public void setFulltNavn(String s) {
		int pos = s.indexOf(' ');
		if (pos < 0) {
			setFornavn(s.substring(pos + 1));
		} else {
			setFornavn(s.substring(0, pos));
			setEtternavn(s.substring(pos + 1));
		}
	}

	public int compareTo(Person1 other) {
<<<<<<< .mine
		return this.getEtternavn().compareTo(other.getEtternavn());
=======
		// Hva sŒ med fornavn, nŒr etternavnet er likt?
		// pseudo: hvis etternavnene er ulike, gj¿r som nŒ, ellers bruk fornavn
		int compareTo = this.getEtternavn().compareTo(other.getEtternavn());
		if (compareTo != 0) {
			return compareTo;
		} else {
			return this.getFornavn().compareTo(other.getFornavn());
		}
>>>>>>> .r519
	}
}
