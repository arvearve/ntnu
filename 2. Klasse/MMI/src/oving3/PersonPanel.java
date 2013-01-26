package oving3;
import static java.awt.GridBagConstraints.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class PersonPanel extends JPanel implements PropertyChangeListener{

	private Person model;

	private JLabel nameLabel = new JLabel("Name: ");
	private JLabel emailLabel = new JLabel("Email: ");
	private JLabel birthdayLabel = new JLabel("Birthday: ");
	private JLabel genderLabel = new JLabel("Gender: ");
	private JLabel heightLabel = new JLabel("Height: ");

	protected JTextField nameField = new JTextField();
	protected JTextField emailField = new JTextField();
	protected JTextField birthdayField = new JTextField();
	protected JComboBox genderComboBox = new JComboBox(Gender.values());
	protected JSlider heightSlider = new JSlider(120, 220, 180);


	// Constructor
	public PersonPanel(){
		this.setLayout(new GridBagLayout());
		
		nameField.addActionListener(new myActions());
		emailField.addActionListener(new myActions());
		birthdayField.addActionListener(new myActions());
		genderComboBox.addActionListener(new myActions());
		heightSlider.addChangeListener(new myActions());
		
		nameField.setName("NamePropertyComponent");
		emailField.setName("EmailPropertyComponent");
		birthdayField.setName("DateOfBirthPropertyComponent");
		genderComboBox.setName("GenderPropertyComponent");
		heightSlider.setName("HeightPropertyComponent");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 7, 3, 7);
		c.gridx = 0;
		c.anchor = LINE_END;
			c.gridy = 0;
			add(nameLabel, c);
			
			c.gridy = 1;
			add(emailLabel, c);
			
			c.gridy = 2;
			add(birthdayLabel, c);
			
			c.gridy = 3;
			add(genderLabel, c);
			
			c.gridy = 4;
			add(heightLabel, c);		
		c.gridx=1;
		c.anchor = LINE_START;
		c.weightx = 1.0d;
		c.fill = HORIZONTAL;
			c.gridy = 0;
			add(nameField, c);
			
			c.gridy = 1;
			add(emailField, c);
			
			c.gridy = 2;
			add(birthdayField,c);
			
			c.gridy = 3;
			c.fill = NONE;
			add(genderComboBox, c);
			
			c.gridy = 4;
			c.fill=HORIZONTAL;
			c.insets = new Insets(3, 3, 3, 3);
			add(heightSlider, c);
			
			c.gridy = 5;
	}
	
		public void setModel(Person theModel) {
			model = theModel;
			model.addPropertyChangeListener(this);
			nameField.setText(model.getName());
			emailField.setText(model.getEmail());
			birthdayField.setText(model.getDateOfBirth());
			genderComboBox.setSelectedItem(model.getGender());
			heightSlider.setValue(model.getHeight());
		}

	public Person getModel(){
		return model;
	}
	
	
	// Event handlers
	
	/** Action for text field * */
	class myActions implements ActionListener, ChangeListener {
		public void actionPerformed(ActionEvent e) {
			if ( model == null ){ return;}
			
			Object src = e.getSource();
			if( src.equals(nameField)){
				model.setName(nameField.getText());
			}
			else if (src.equals(emailField)) {
				model.setEmail(emailField.getText());
			}
			else if (src.equals(birthdayField)) {
				model.setDateOfBirth(birthdayField.getText());
			}
			else if ( src.equals(genderComboBox)) {
				model.setGender((Gender)genderComboBox.getSelectedItem());
			}
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			model.setHeight(heightSlider.getValue());
			
		}
	}
	// PropertyChangeListener
	public void propertyChange(PropertyChangeEvent e) {
		if ( e.getNewValue() == e.getOldValue() ) {return;}
		
		String p = e.getPropertyName();
		if ( p.equals(Person.NAME_PROPERTY) ){
			nameField.setText(model.getName());
		} else if ( p.equals(Person.DATEOFBIRTH_PROPERTY) ){
			birthdayField.setText(model.getDateOfBirth());
		} else if ( p.equals(Person.EMAIL_PROPERTY) ){
			emailField.setText(model.getEmail());
		} else if ( p.equals(Person.GENDER_PROPERTY) ){
			genderComboBox.setSelectedItem(model.getGender());
		} else if ( p.equals(Person.HEIGHT_PROPERTY) ){
			heightSlider.setValue(model.getHeight());
		}

	}	
}
