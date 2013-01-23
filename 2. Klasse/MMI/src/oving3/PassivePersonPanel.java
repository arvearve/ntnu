package oving3;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PassivePersonPanel extends PersonPanel {
	public PassivePersonPanel() {
		nameField.setEditable(false);
		emailField.setEditable(false);
		birthdayField.setEditable(false);
		genderComboBox.setEditable(false);
		heightSlider.setEnabled(false);

	}
}
