package oving1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
 
@SuppressWarnings("serial")
public class ButtonsNText extends JPanel implements ActionListener, KeyListener {
	
	private JTextField TextLine;
	private JToggleButton UpperCaseButton;
	private JToggleButton LowerCaseButton;
	private JCheckBox ContinuousButton;
	private Boolean setTextToUppercase;
	
	// Constructor
	public ButtonsNText() {	
		LowerCaseButton = new JToggleButton("Lowercase");
		UpperCaseButton= new JToggleButton("UPPERCASE");
		ContinuousButton = new JCheckBox("Continuous");
		TextLine = new JTextField(); 
		
		// Configure elements to help test locate them
		TextLine.setColumns(20); 
		TextLine.setName("TextLine");
		UpperCaseButton.setName("UpperCaseButton");
		LowerCaseButton.setName("LowerCaseButton");
		ContinuousButton.setName("ContinuousButton");
		
		// Group buttons 
		ButtonGroup myButtonGroup = new ButtonGroup();
		myButtonGroup.add(LowerCaseButton);
		myButtonGroup.add(UpperCaseButton);
		
		// Add listeners
		LowerCaseButton.addActionListener(this);
		UpperCaseButton.addActionListener(this);
		TextLine.addKeyListener(this);
		
		// Add elements to UI
		add(TextLine);
		add(UpperCaseButton);
		add(LowerCaseButton);
		add(ContinuousButton);
		// Set default state
		setTextToUppercase = false;
		ButtonModel model = LowerCaseButton.getModel();
		myButtonGroup.setSelected(model, true);
		}
	
	// Helper
	private void updateCase(){
		int caretposition = TextLine.getCaretPosition();
		if(setTextToUppercase){
			TextLine.setText(TextLine.getText().toUpperCase());
		}
		else{
			TextLine.setText(TextLine.getText().toLowerCase());
		}
		TextLine.setCaretPosition(caretposition);
	}
	
	
 
	// Event handlers
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == LowerCaseButton ){
			setTextToUppercase = false;
			updateCase();
		}
		else if ( e.getSource() == UpperCaseButton ) { 
			setTextToUppercase = true;
			updateCase();
		}
	}
 
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if ( ContinuousButton.isSelected() ){
			updateCase();
			return;
		}
		if (key == KeyEvent.VK_ENTER) {
	    	updateCase();	    
	    }	
	}		
	
	public void keyPressed(KeyEvent e) {} // Required by interface
	
	public void keyTyped(KeyEvent e) {} // Required by interface


	public static void main(String[] args) {
		JFrame frame = new JFrame("Oving1");	
		frame.getContentPane().add(new ButtonsNText()); 
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}