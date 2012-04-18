package sokoban1;

import java.lang.System;
import java.util.ArrayList;

public class SokobanEngine {
	ArrayList<ArrayList<Boolean>> targets = new ArrayList<ArrayList<Boolean>>();
	ArrayList<ArrayList<Character>> grid = new ArrayList<ArrayList<Character>>();;
	int currentLevel;

	String[][] level = {{"#####",
		   "#@$.#",
		   "#####"},
		   {"###  ",
		   "#.###",
		   "#*$ #",
		   "# @ #",
		   "#####"},
		   {"#######",
		   "#.@ # #",
		   "#$* $ #",
		   "#   $ #",
		   "# ..  #",
		   "#  *  #",
		   "#######"},
		   {"*###########*",
		   "#           #",
		   "#  ... ...  #",
		   "#  *$$ $.$  #",
		   "# $*@$. $*$$#",
		   "#  *$$ $.$  #",
		   "#  ...  .   #",
		   "#           #",
		   "*###########*"}};
	
	public SokobanEngine() {
		super();
		currentLevel = 0;
		loadMap(level[currentLevel]);
	}
	
	public void reload(){
		loadMap(level[currentLevel]);
	}
	public void nextLevel(){
		 currentLevel++;
		 reload();
	}
	public void prevLevel(){
		currentLevel--;
		reload();
	}

	
	public void loadMap(String[] input) {
		this.grid.clear();
		this.targets.clear();
		
		for (String row : input) {
			ArrayList<Character> newGridRow = new ArrayList<Character>();
			ArrayList<Boolean> newTargetsRow = new ArrayList<Boolean>();
			for (Character c : row.toCharArray()) {
				newGridRow.add(c);
				if (c == '.' || c == '+' || c == '*') {
					newTargetsRow.add(true);
				} else {
					newTargetsRow.add(false);
				}
			}
			this.grid.add(newGridRow);
			this.targets.add(newTargetsRow);

		}
	}

	public void setCell(int x, int y, char c) {
		this.grid.get(y).set(x, c);
	}

	public char getCell(int x, int y) {
		return this.grid.get(y).get(x);
	}

	public boolean isTarget(int x, int y) {
		return this.targets.get(y).get(x);
	}

	public int getPlayerY() {
		int counter = 0;
		for (ArrayList<Character> y : this.grid) {
			if (y.contains('@')) {return counter;}			
			counter++;			
		}
		return -1;
	}
	
	public int getPlayerX(){
		return this.grid.get(this.getPlayerY()).indexOf('@');
	}

	public boolean doMove(int dx, int dy) {
		int y = getPlayerY();
		int x = getPlayerX();
		if (Math.abs(dx) + Math.abs(dy) != 1) { return false;} // only allow 1-step movement in NSEW direction.
		
		if( (getCell(x+dx, y+dy) == '$' || getCell(x+dx, y+dy) == '*' ) && moveObject(x+dx, y+dy, dx, dy) == false){return false;}  // flytter boks om n¿dvendig og mulig.
		if(getCell(x+dx, y+dy) == ' ' || getCell(x+dx,y+dy) == '.'){
			if(isTarget(x,y)){
				setCell(x,y,'.');
				}
			else{
				setCell(x,y,' ');
				}
			setCell(x+dx, y+dy, '@');
			return true;
		}
		return false;	
	}
	public boolean moveObject(int originX, int originY, int dx, int dy) {
		if (Math.abs(dx) + Math.abs(dy) != 1) { return false;} // only allow 1-step movement in NSEW direction.
		int targetX = originX + dx;
		int targetY = originY + dy;
		switch (getCell(targetX, targetY)){
			case '.':
				setCell(targetX,targetY,'*');
				break;
			case ' ':
				setCell(targetX,targetY,'$');
				break;
			default:
				return false;
		}
		if(isTarget(originX,originY)){setCell(originX, originY, '.');}
		else{setCell(originX, originY,' ');}
		
		return true;
		
	}

	public String toString() {
		String result = "";
		for (ArrayList<Character> row : this.grid) {
			for (Character c : row) {
				result += c;
			}
			result += "|";
		}
		return result;
	}

}
