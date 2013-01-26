package oving4;

import oving3.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JListMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PersonListPanelTest extends JFCTestCase {

    private PersonListPanel testPanel;

    private NamedComponentFinder finder;

    private Person person1, person2, person3, person4;

    public static Person createPerson(String name, String dateOfBirth, Gender gender,
            String email, int height) {
        Person person = new Person(name);
        person.setDateOfBirth(dateOfBirth);
        person.setGender(gender);
        person.setEmail(email);
        person.setHeight(height);
        return person;
    }

    protected void setUp() throws Exception {
        super.setUp();

        setHelper(new JFCTestHelper());
        finder = new NamedComponentFinder(JComponent.class, null);

        JFrame frame = new JFrame();
        testPanel = new PersonListPanel();
        person1 = createPerson("Hallvard Tr¾tteberg", "16.nov.1966", Gender.male,
                "hal@idi.ntnu.no", 179);
        person2 = createPerson("Marit Reitan", "17.apr.1964", Gender.female,
                "marit.reitan@idi.ntnu.no", 176);
        person3 = createPerson("Jens Reitan Tr¾tteberg", "14.jan.1997",
        		Gender.male, null, 145);
        person4 = createPerson("Anne Tr¾tteberg Reitan", "13.jun.1998",
        		Gender.female, null, 135);
        frame.setTitle(testPanel.getClass().getName());
        frame.setContentPane(testPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void initializePersonList() {
        JList personList = 
            (JList) findComponent(JList.class, "PersonList");
        DefaultListModel listModel = (DefaultListModel) personList.getModel();
        listModel.addElement(person1);
        listModel.addElement(person2);
        listModel.addElement(person3);
        listModel.addElement(person4);
        personList.setSelectedValue(person1, false);
    }

    protected void tearDown() throws Exception {
        testPanel = null;
        finder = null;
        TestHelper.cleanUp(this);
        super.tearDown();
    }

	protected JComponent findComponent(Class c, String name,
            boolean shouldAssert) {
        finder.setComponentClass(c);
        finder.setName(name);
        JComponent comp = (JComponent) finder.find(testPanel, 0);
        if (shouldAssert) {
            assertNotNull("Could not find the " + c.getName() + " named "
                    + name, comp);
        }
        return comp;
    }

	protected JComponent findComponent(Class c, String name) {
        return findComponent(c, name, true);
    }

	public void testNewPerson() {
        initializePersonList();
        JList personList = (JList) findComponent(JList.class, "PersonList");
        DefaultListModel personListModel = (DefaultListModel) personList.getModel();
        Person[] oldPersons = new Person[personListModel.getSize()];
        personListModel.copyInto(oldPersons);
        List oldPersonList = new ArrayList(Arrays.asList(oldPersons));
       
        AbstractButton button = (AbstractButton) findComponent(JButton.class, "NewPersonButton");
        button.doClick();
        // Problems concerning asyncronous exection in Swing thread
        // getHelper().enterClickAndLeave(new MouseEventData(this, button));
        Person[] newPersons = new Person[personListModel.getSize()];
        personListModel.copyInto(newPersons);        
        assertEquals(oldPersons.length + 1, newPersons.length);
        List newPersonList = new ArrayList(Arrays.asList(newPersons));
        assertTrue(newPersonList.containsAll(oldPersonList));
        newPersonList.removeAll(oldPersonList);
        Person newPerson = (Person)newPersonList.get(0);
        PersonPanel personPanel = (PersonPanel) findComponent(PersonPanel.class, "PersonPanel");
        assertEquals(newPerson, personPanel.getModel());
        Object newSelectedValue = personList.getSelectedValue();
        assertEquals(newPerson, newSelectedValue);
    }
    
    public void testRemovePerson() {
        initializePersonList();
        JList personList = 
            (JList) findComponent(JList.class, "PersonList");
        assertNotNull("JList named \"PersonList\" not found", personList);
        DefaultListModel personListModel = (DefaultListModel) personList.getModel();
        Person[] oldPersons = new Person[personListModel.getSize()];
        personListModel.copyInto(oldPersons);
        List oldPersonList = new ArrayList(Arrays.asList(oldPersons));
        getHelper().enterClickAndLeave(new JListMouseEventData(this, 
                personList, personListModel.getSize() - 1, 1));
        Object selectedPerson = personList.getSelectedValue();
        assertNotNull("Selected person is null", selectedPerson);
        AbstractButton button = 
            (AbstractButton) findComponent(JButton.class, "DeletePersonButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, button));
        Person[] newPersons = new Person[personListModel.getSize()];
        personListModel.copyInto(newPersons);        
        List newPersonList = new ArrayList(Arrays.asList(newPersons));
        assertTrue(oldPersonList.containsAll(newPersonList));
        oldPersonList.removeAll(newPersonList);
        Person deletedPerson = (Person)oldPersonList.get(0);
        assertEquals(selectedPerson, deletedPerson);
    }
    
    public void testListSelection() throws InterruptedException {
        initializePersonList();
        JList personList = 
            (JList) findComponent(JList.class, "PersonList");
        PersonPanel personPanel = 
            (PersonPanel) findComponent(JPanel.class, "PersonPanel");
        assertNotNull("JList named \"PersonList\" not found", personList);
        for (int i = 0; i < personList.getModel().getSize(); i++) {
            getHelper().enterClickAndLeave(
                    new JListMouseEventData(this, personList, i, 1));
            Person selectedPerson = (Person) personList.getSelectedValue();
            assertNotNull("The selected person was \"null\"!", selectedPerson);
            assertEquals("Selected person (" + selectedPerson 
                    + ") is not equal to the person model of the PersonPanel",
                    selectedPerson, personPanel.getModel());
        }
    }
}