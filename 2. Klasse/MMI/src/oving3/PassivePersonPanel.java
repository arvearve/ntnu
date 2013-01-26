package oving3;

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
