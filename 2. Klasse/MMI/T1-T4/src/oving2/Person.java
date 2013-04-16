package oving2;
public class Person {
	private String name;
	private String dateOfBirth;
	private Gender gender;
	private String email;
	private int height;
	
	public Person(){}
	public Person(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		System.out.println(name);
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		System.out.println(dateOfBirth);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
		System.out.println(gender);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		System.out.println(email);
	}

	public int getHeight() {
		return height;
		
	}

	public void setHeight(int height) {
		this.height = height;
		System.out.println(height);
	}

	public String toString() {
		return "Name: " + name + "\nBirthday: " + dateOfBirth + "\nGender: " + gender + "\n Email: " + email + "\nHeight: " + height;
	};
}
