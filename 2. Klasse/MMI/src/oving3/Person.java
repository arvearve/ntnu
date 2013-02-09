package oving3;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Person {
	public final static String NAME_PROPERTY = "Name";
	public final static String DATEOFBIRTH_PROPERTY = "DateOfBirth";
	public final static String EMAIL_PROPERTY = "Email";
	public final static String GENDER_PROPERTY = "Gender";
	public final static String HEIGHT_PROPERTY = "Height";
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private String name;
	private String dateOfBirth;
	private String email;
	private Gender gender;
	private int height;
	
	public Person(){
		this("No Name");
	}
	
	public Person(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		pcs.firePropertyChange(NAME_PROPERTY, oldValue, name);
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		String oldValue = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		pcs.firePropertyChange(DATEOFBIRTH_PROPERTY, oldValue, dateOfBirth);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		Gender oldValue = this.gender;
		this.gender = gender;
		pcs.firePropertyChange(GENDER_PROPERTY, oldValue, gender);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;
		pcs.firePropertyChange(EMAIL_PROPERTY, oldValue, email);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		int oldValue = this.height;
		this.height = height;
		pcs.firePropertyChange(HEIGHT_PROPERTY, oldValue, this.height);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
	
	public String toString() {
		return name;
	};
}
