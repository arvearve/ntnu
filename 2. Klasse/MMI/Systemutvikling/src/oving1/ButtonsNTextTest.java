package oving1;

import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.KeyEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class ButtonsNTextTest extends JFCTestCase{	
    private JPanel testPanel;

    private NamedComponentFinder finder;

    protected void setUp() throws Exception {
        super.setUp();

        setHelper(new JFCTestHelper());
        finder = new NamedComponentFinder(JComponent.class, null);

        JFrame frame = new JFrame();
        testPanel = new ButtonsNText();
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

    private JComponent findComponent(Class c, String name) {
        finder.setComponentClass(c);
        finder.setName(name);
        JComponent comp = (JComponent) finder.find(testPanel, 0);
        assertNotNull("Could not find the " + c.getName() + " named " + name,
                comp);
        return comp;
    }

    private JTextField findTextLine() {
        return (JTextField) findComponent(JTextField.class, "TextLine");
    }

    private AbstractButton findButton(String name) {
        return (AbstractButton) findComponent(AbstractButton.class, name);
    }

    public void testComponentPresence() {
        findTextLine();
        findButton("UpperCaseButton");
        findButton("LowerCaseButton");
        findButton("ContinuousButton");
    }

    public void testUpperCaseButton() {
        JTextField textLine = findTextLine();
        AbstractButton button = findButton("UpperCaseButton");
        String sampleText = "Sample text";
        textLine.setText("");
        getHelper().sendString(new StringEventData(this, textLine, sampleText));
        getHelper().enterClickAndLeave(new MouseEventData(this, button));
        assertEquals(sampleText.toUpperCase(), textLine.getText());
    }

    public void testLowerCaseButton() {
        JTextField textLine = findTextLine();
        AbstractButton button = findButton("LowerCaseButton");
        String sampleText = "Sample text";
        textLine.setText("");
        getHelper().sendString(new StringEventData(this, textLine, sampleText));
        getHelper().enterClickAndLeave(new MouseEventData(this, button));
        assertEquals(sampleText.toLowerCase(), textLine.getText());
    }

    public void testEnter() {
        JTextField textLine = findTextLine();
        AbstractButton upperButton = findButton("UpperCaseButton");
        AbstractButton lowerButton = findButton("LowerCaseButton");
        String sampleText = "Sample text";
        textLine.setText(sampleText);
        getHelper().enterClickAndLeave(new MouseEventData(this, upperButton));
        assertEquals(sampleText.toUpperCase(), textLine.getText());

        textLine.setText(sampleText);
        getHelper().sendKeyAction(
                new KeyEventData(this, textLine, KeyEvent.VK_ENTER));
        assertEquals(sampleText.toUpperCase(), textLine.getText());

        getHelper().enterClickAndLeave(new MouseEventData(this, lowerButton));
        assertEquals(sampleText.toLowerCase(), textLine.getText());

        textLine.setText(sampleText);
        getHelper().sendKeyAction(
                new KeyEventData(this, textLine, KeyEvent.VK_ENTER));
        assertEquals(sampleText.toLowerCase(), textLine.getText());
    }

    public void testContinuous() {
        JTextField textLine = findTextLine();
        AbstractButton button = findButton("ContinuousButton");
        AbstractButton upperButton = findButton("UpperCaseButton");
        String sampleText = "Sample text", typedText = " typed here";
        textLine.setText(sampleText);
        getHelper().enterClickAndLeave(new MouseEventData(this, upperButton));
        assertEquals(sampleText.toUpperCase(), textLine.getText());

        getHelper().enterClickAndLeave(new MouseEventData(this, button));

        textLine.setCaretPosition(textLine.getText().length());
        getHelper().sendString(new StringEventData(this, textLine, typedText));
        assertEquals(sampleText.toUpperCase() + typedText.toUpperCase(),
                textLine.getText());
    }

    public void testContinuousAndCaret() {
        JTextField textLine = findTextLine();
        AbstractButton button = findButton("ContinuousButton");
        AbstractButton lowerButton = findButton("UpperCaseButton");
        String sampleText1 = "Sample ", sampleText2 = " typed here";
        String typedText = "text";
        textLine.setText(sampleText1 + sampleText2);
        getHelper().enterClickAndLeave(new MouseEventData(this, lowerButton));
        assertEquals(sampleText1.toUpperCase() + sampleText2.toUpperCase(),
                textLine.getText());

        getHelper().enterClickAndLeave(new MouseEventData(this, button));

        textLine.setCaretPosition(sampleText1.length());
        getHelper().sendString(new StringEventData(this, textLine, typedText));
        assertEquals(sampleText1.toUpperCase() + typedText.toUpperCase()
                + sampleText2.toUpperCase(), textLine.getText());
    }
}