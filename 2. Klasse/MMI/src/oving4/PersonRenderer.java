package oving4;


import javax.swing.*;

import java.awt.*;
import java.lang.management.ManagementFactory;

import javax.swing.DefaultListCellRenderer;
import oving3.*;

import com.sun.tools.javac.resources.javac;


@SuppressWarnings("serial")
public class PersonRenderer extends DefaultListCellRenderer {
	@Override
	public java.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Person p = (Person) value;
		label.setText(p.getName() + ": " + p.getEmail());
		ImageIcon man = new ImageIcon("man.png");
		ImageIcon woman = new ImageIcon("woman.png");
		ImageIcon unknown = new ImageIcon("unknown.png");
		
		if ( p.getGender() == Gender.male ) {
			label.setIcon(man);
		}else if (p.getGender() == Gender.female) {
			label.setIcon(woman);
		}else{
			label.setIcon(unknown);
		}
		
		return label;
	}
	
	
}