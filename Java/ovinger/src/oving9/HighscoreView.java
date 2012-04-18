package oving9;

import java.awt.Color;
import java.util.ArrayList;

import acm.program.GraphicsProgram;
import acm.graphics.*;
import acm.util.SwingTimer;

public class HighscoreView extends GraphicsProgram implements ListListener {
	HighscoreList scores = new HighscoreList(10);
	GCanvas canvas = this.getGCanvas();
	private final int X_START = 30;
	private final int Y_START = 30;
	private final int L_HEIGHT = 40;
	
	public HighscoreView(){
		scores.addListListener(this);
		
	}
	
	@Override
	public void listChanged(ObservableList list, int lowestIndex, int highestIndex) {
		for(int i = lowestIndex; i<= highestIndex; i++){
			String str = Integer.toString(i) + ": " + list.getList().get(i).toString();
			if(canvas.getElementCount() > i){
				((GLabel)canvas.getElement(i)).setLabel(str);
			}
			else{
				GLabel label = new GLabel(str, X_START, Y_START + L_HEIGHT * canvas.getElementCount());
				label.setColor(Color.white);
				canvas.add(label);
			}
		
		}
	}

	private void addScore(){
		String name = readLine("Name: ");
		int moves = readInt("# moves: ");
		SokobanResult listing = new SokobanResult(name, moves);
		scores.addResult(listing);
	}
	
	
	public void run() {
		setBackground(Color.black);
		setSize(500,500);
		while(true){
			addScore();
		}

	}
	

}