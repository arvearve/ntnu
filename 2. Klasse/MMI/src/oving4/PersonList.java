package oving4;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import oving3.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

public class PersonList extends JPanel implements PropertyChangeListener{
	
	private DefaultListModel listModel = new DefaultListModel();
	private JList personList = new JList(listModel);
	JScrollPane scroller = new JScrollPane(personList);
	
	public PersonList(){
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		personList.setName("PersonList");
		personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(scroller);
	}
	
	
	public void addListSelectionListener(ListSelectionListener listener){
		personList.addListSelectionListener(listener);
	}
	
	public DefaultListModel getListModel() {
		return listModel;
	}
	
	public void setListModel(DefaultListModel model){
		personList.setModel(model);
		listModel = model;
	}
	
	public void addPerson(Person p){
		p.addPropertyChangeListener(this);
		listModel.addElement(p);
		personList.clearSelection();
		personList.setSelectedValue(p, true);
		System.out.println("AddPerson: " + listModel);
	}
	
	public Person getSelectedValue(){
		return (Person)personList.getSelectedValue();
	}
	
	public void removePerson(Person p){
		p.removePropertyChangeListener(this);
		listModel.removeElement(p);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if ( e.getNewValue() == e.getOldValue() ) {return;}
		
		personList.repaint();
		System.out.println("Property changed, repainting list.");
		
	}

	

}
