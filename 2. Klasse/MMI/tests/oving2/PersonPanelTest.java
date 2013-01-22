package oving2;

import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.KeyEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class PersonPanelTest extends JFCTestCase {

    private PersonPanel testPanel;

    private NamedComponentFinder finder;

    private Person person1, person2;

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
        testPanel = new PersonPanel();
        person1 = createPerson("Ola Nordmann", "1.nov.1970", Gender.male,
                "ola@nordmann.com", 191);
        person2 = createPerson("Kari Nordmann", "7.jul.1971", Gender.female,
                "kari@nordmann.com", 170);
        testPanel.setModel(person1);
        frame.setTitle(testPanel.getClass().getName());
        frame.setContentPane(testPanel);
        frame.pack();
        frame.setVisible(true);
    }

    protected void tearDown() throws Exception {
        testPanel = null;
        finder = null;
        TestHelper.cleanUp(this);
        super.tearDown();
    }

    private JComponent findComponent(Class c, String name, boolean shouldAssert) {
        finder.setComponentClass(c);
        finder.setName(name);
        JComponent comp = (JComponent) finder.find(testPanel, 0);
        if (shouldAssert) {
            assertNotNull("Could not find the " + c.getName() + " named "
                    + name, comp);
        }
        return comp;
    }

    private JComponent findComponent(Class c, String name) {
        return findComponent(c, name, true);
    }

    public void testComponentPresence() {
    	System.out.println("looking for nameproperty..");
        findComponent(JTextField.class, "NamePropertyComponent");
        
        System.out.println("looking for dateofbirthproperty..");
        findComponent(JTextField.class, "DateOfBirthPropertyComponent");
        findComponent(JComboBox.class, "GenderPropertyComponent");
        findComponent(JTextField.class, "EmailPropertyComponent");
        JComponent comp = findComponent(JSlider.class,
                "HeightPropertyComponent", false);
        if (comp == null) {
            comp = findComponent(JSpinner.class, "HeightPropertyComponent", false);
        }
        assertNotNull("Could not find a JSlider or JSpinner named HeightPropertyComponent", comp);
    }

    public void testNamePropertyComponent() {
        JTextField nameTextField = (JTextField) findComponent(JTextField.class,
                "NamePropertyComponent");
        nameTextField.setText("");
        getHelper().sendString(
                new StringEventData(this, nameTextField, person2.getName()));
        getHelper().sendKeyAction(
                new KeyEventData(this, nameTextField, KeyEvent.VK_ENTER));
        assertEquals(person2.getName(), person1.getName());
    }

    public void testDateOfBirthPropertyComponent() throws ParseException {
        JTextField dateOfBirthTextField = (JTextField) findComponent(
                JTextField.class, "DateOfBirthPropertyComponent");
        dateOfBirthTextField.setText("");
        getHelper().sendString(
                new StringEventData(this, dateOfBirthTextField, person2.getDateOfBirth()));
        getHelper().sendKeyAction(
                new KeyEventData(this, dateOfBirthTextField, KeyEvent.VK_ENTER));        
        assertEquals(person2.getDateOfBirth(), person1.getDateOfBirth());
    }

    public void testGenderPropertyComponent() {
        JComboBox genderCombo = (JComboBox) findComponent(JComboBox.class,
                "GenderPropertyComponent");
        genderCombo.setSelectedItem(person2.getGender());
        assertEquals(person2.getGender(), person1.getGender());
    }

    public void testEmailPropertyComponent() throws ParseException {
        JTextField emailTextField = (JTextField) findComponent(
                JTextField.class, "EmailPropertyComponent");
        emailTextField.setText("");
        getHelper().sendString(
                new StringEventData(this, emailTextField, person2.getEmail()));
        getHelper().sendKeyAction(
                new KeyEventData(this, emailTextField, KeyEvent.VK_ENTER));        
        assertEquals(person2.getEmail(), person1.getEmail());
        assertEquals(person2.getEmail(), person1.getEmail());
    }

    public void testHeightPropertyComponent() {
        JComponent comp = findComponent(JSlider.class,
                "HeightPropertyComponent", false);
        if (comp == null) {
            comp = findComponent(JSpinner.class, "HeightPropertyComponent", false);
        }
        assertNotNull("Could not find a JSlider or JSpinner named HeightPropertyComponent", comp);
        if (comp instanceof JSlider) {
            ((JSlider) comp).setValue(person2.getHeight());
        } else if (comp instanceof JSpinner) {
            ((JSpinner) comp).setValue(person2.getHeight());
        }
        assertEquals(person2.getHeight(), person1.getHeight());
    }
}