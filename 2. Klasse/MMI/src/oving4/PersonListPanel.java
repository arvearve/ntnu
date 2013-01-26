package oving4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import oving3.*;
@SuppressWarnings("serial")
public class PersonListPanel extends JPanel{
	
	private JList personList = new JList();
	private PersonPanel personPanel = new PersonPanel();
	private DefaultListModel listModel = new DefaultListModel();
	
	private JButton addPersonButton = new JButton("Add Person");
	private JButton deletePersonButton = new JButton("Delete Person");
	
	public PersonListPanel(){
		
		personPanel.setName("PersonPanel");
		addPersonButton.setName("NewPersonButton");
		addPersonButton.addActionListener(new newPersonAction());
		deletePersonButton.setName("DeletePersonButton");
		deletePersonButton.addActionListener(new removePersonAction());
		
		personList.setName("PersonList");
		personList.setModel(listModel);
		personList.addListSelectionListener(new listSelectionAction());
		personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroller = new JScrollPane(personList); 
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		
		
		// TODO fix layout!
		add(personPanel);
		add(scroller);
		add(addPersonButton);
		add(deletePersonButton);
	}


	public DefaultListModel getListModel() {
		return listModel;
	}


	public void setListModel(DefaultListModel listModel) {
		this.listModel = listModel;
		personList.setModel(this.listModel);
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("test");
		PersonListPanel lp = new PersonListPanel();
		DefaultListModel listModel = new DefaultListModel();
		
		
		frame.getContentPane().add(lp);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Person p1 = new Person("Fesk");
		Person p2 = new Person("Ost");
		Person p3 = new Person("Roger");
		
		listModel.addElement(p1);
		listModel.addElement(p2);
		listModel.addElement(p3);
		lp.setListModel(listModel);
		frame.setSize(700, 300);
	}
	
	class listSelectionAction implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting() && personList.getSelectedValue() != null){
				
				Person selectedPerson = (Person)personList.getSelectedValue();
				personPanel.setModel(selectedPerson);
			}
		}

	}
	
	class removePersonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource().equals(deletePersonButton) && personList.getSelectedValue() != null){
				listModel.removeElement(personList.getSelectedValue());
			}
		}
	}
	
	
	class newPersonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addPersonButton)){
				Person newPerson = new Person();
				listModel.addElement(newPerson);
				personList.clearSelection();
				personList.setSelectedValue(newPerson, true);
			}
			
		}
		
	}
	
}
