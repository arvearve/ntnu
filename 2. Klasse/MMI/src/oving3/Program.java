package oving3;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Program {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Oving3");
		
		JPanel content = new JPanel();		
		Person model = new Person("Stian");
		PersonPanel active = new PersonPanel();
		PassivePersonPanel passive = new PassivePersonPanel();
		
		active.setModel(model);
		passive.setModel(model);
		
		content.add(active);
		content.add(passive);
		
		frame.getContentPane().add(content);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
