package oving4;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.beans.PropertyChangeListener;
import oving3.*;
@SuppressWarnings("serial")
public class PersonListPanel extends JPanel{
	
	
	private PersonPanel personPanel = new PersonPanel();
	private PersonList personList = new PersonList();
	private JButton addPersonButton = new JButton("Add Person");
	private JButton deletePersonButton = new JButton("Delete Person");
	
	public PersonListPanel(){
		
		
		personPanel.setName("PersonPanel");
		addPersonButton.setName("NewPersonButton");
		deletePersonButton.setName("DeletePersonButton");
		
		personList.addListSelectionListener(new listSelectionAction());
		addPersonButton.addActionListener(new newPersonAction());
		deletePersonButton.addActionListener(new removePersonAction());
		
		// Layout
		JPanel buttons = new JPanel();
		buttons.add(addPersonButton);
		buttons.add(deletePersonButton);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		
		add(personPanel);
		add(personList);
		add(buttons);
		
	}


	
	public static void main(String[] args) {
		JFrame frame = new JFrame("¯ving 4");
		PersonListPanel lp = new PersonListPanel();
		
		frame.getContentPane().add(lp);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Person p1 = new Person("Hans");
		Person p2 = new Person("Elin");
		Person p3 = new Person("Roger");
		lp.personList.addPerson(p1);
		lp.personList.addPerson(p2);
		lp.personList.addPerson(p3);
		
		
		frame.setSize(700, 300);
	}
	class listSelectionAction implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting() && personList.getSelectedValue() != null){
				
				Person selectedPerson = personList.getSelectedValue();
				personPanel.setModel(selectedPerson);
			}
		}

	}
	
	class removePersonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource().equals(deletePersonButton) && personList.getSelectedValue() != null){
				personList.removePerson(personList.getSelectedValue());
			}
		}
	}
	
	
	class newPersonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addPersonButton)){
				Person newPerson = new Person();
				personList.addPerson(newPerson);
			}
			
		}
		
	}
	
}
