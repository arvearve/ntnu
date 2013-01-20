package sokoban1;

import java.awt.event.*;
import acm.graphics.*;
import acm.program.GraphicsProgram;

public class GUI extends GraphicsProgram {

	int gridSize = 16;
	GCompound level = new GCompound();
	GCompound objects = new GCompound();
	SokobanEngine game = new SokobanEngine();

	GImage newTile(String type) {

		return new GImage("sokoban/" + type + gridSize + "x" + gridSize
				+ ".png");
	}

	void drawObjects() {
		this.objects.removeAll();
		int row = 0;
		int col = 0;
		for (Character c : this.game.toString().toCharArray()) {
			switch (c) {
			case '@': // player
				this.objects.add(newTile("mover"), gridSize * col, gridSize
						* row);
				break;
			case '+': // player on target
				this.objects.add(newTile("mover_on_target"), gridSize * col,
						gridSize * row);
				break;
			case '$': // box
				this.objects.add(newTile("movable"), gridSize * col, gridSize
						* row);
				break;
			case '*': // box on target
				this.objects.add(newTile("movable_on_target"), gridSize * col,
						gridSize * row);
				break;
			case '|':
				row++;
				col = -1; // gets incremented to 0 at end of loop;
				break;
			}
			col++;
		}
		this.objects.sendForward();
		add(this.objects);
	}

	void drawLevel() {
		this.level.removeAll();
		int row = 0;
		int col = 0;
		for (Character c : this.game.toString().toCharArray()) {
			switch (c) {
			case '#':
				this.level.add(newTile("wall"), gridSize * col, gridSize * row);
				break;
			case '.':
				this.level.add(newTile("target"), gridSize * col, gridSize
						* row);
				break;
			case '*':
				this.level.add(newTile("target"), gridSize * col, gridSize
						* row);
				break;
			case '|':
				row++;
				col = -1; // gets incremented to 0 at end of loop
				break;
			default:
				this.level
						.add(newTile("blank"), gridSize * col, gridSize * row);
			}
			col++;
		}
		add(this.level);
	}

	public void run() {
		drawLevel();
		drawObjects();
		addKeyListeners();
		print(game.toString());

	}

	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		switch (c) {
		case 'j':
			this.game.nextLevel();
			drawLevel();
			drawObjects();
			break;
		case 'k':
			this.game.prevLevel();
			drawLevel();
			drawObjects();
			break;
			
		case 'w':
			game.doMove(0, -1);
			drawObjects();
			break;
		case 's':
			game.doMove(0, 1);
			drawObjects();
			break;
		case 'a':
			game.doMove(-1, 0);
			drawObjects();
			break;
		case 'd':
			game.doMove(1, 0);
			drawObjects();
			break;
		 case 'q':
		 System.exit(0);
		 break;
		case 'r':
			game.reload();
			drawObjects();
			break;
		}
	}

}
