package oving2;
import static java.awt.GridBagConstraints.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class PersonPanel extends JPanel implements ActionListener{
	
	private Person model;
	
	private Label nameLabel = new Label("Name: ");
	private Label emailLabel = new Label("Email: ");
	private Label birthdayLabel = new Label("Birthday: ");
	private Label genderLabel = new Label("Gender: ");
	private Label heightLabel = new Label("Height: ");
	
	private TextField nameField = new TextField();
	private TextField emailField = new TextField();
	private TextField birthdayField = new TextField();
	private JComboBox genderComboBox = new JComboBox(Gender.values());
	private JSlider heightSlider = new JSlider(120, 220, 180);
	
	private PersonListener listener = new PersonListener();
	
	
	// Constructor
	public PersonPanel(){
		this.setLayout(new GridBagLayout());
		
		nameField.addKeyListener(listener);
		emailField.addKeyListener(listener);
		birthdayField.addKeyListener(listener);
		genderComboBox.addActionListener(listener);
		heightSlider.addChangeListener(listener);
		
		
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
			
			//JButton printModelButton = new JButton("Print (create) model info to console");
			//add(printModelButton, c);
			//printModelButton.addActionListener(this);
					
	}
	
	public void setModel(Person p){
		model = p;
		nameField.setText(model.getName());
		emailField.setText(model.getEmail());
		genderComboBox.setSelectedItem(model.getGender());
		birthdayField.setText(model.getDateOfBirth());
		heightSlider.setValue(model.getHeight());
	}
	
	public Person getModel(){
		return model;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Oving2");
		frame.getContentPane().add(new PersonPanel());
		frame.pack();
		frame.setVisible(true);
	}

	
	// my own button event handler
	@Override
	public void actionPerformed(ActionEvent e) {
		if(model == null){
			model = new Person();
		}
		// TODO Auto-generated method stub
		System.out.println(model.toString());
		
	}

	
	// Event handlers
	class PersonListener implements KeyListener, ActionListener, ChangeListener{

		private void saveModel(){
				if(model == null){return;};
				model.setName(nameField.getText());
				model.setEmail(emailField.getText());
				model.setDateOfBirth(birthdayField.getText());
				model.setGender((Gender)genderComboBox.getSelectedItem());
				model.setHeight(heightSlider.getValue());
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveModel();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			saveModel();
			
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			saveModel();
			
		}
		
		
	}


	
}
