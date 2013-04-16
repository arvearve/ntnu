package oving4;


import javax.swing.*;

import java.awt.*;
import java.awt.geom.Path2D;
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
		String path = (p.getGender() != null)?p.getGender().toString():"unknown";
		label.setIcon(mkIcon(path + ".png"));
		return label;
	}
	
	protected ImageIcon mkIcon (String path) {
		  java.net.URL imgURL = getClass().getResource(path);
		  if (imgURL != null) {
			  return new ImageIcon(imgURL);
		  } else {
			  System.err.println("Couldn't find file: " + path);
			  return null;
		  }
	  }
	
}