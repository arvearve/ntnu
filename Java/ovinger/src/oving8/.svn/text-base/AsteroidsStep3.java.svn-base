package oving8;

import java.awt.event.KeyEvent;

public class AsteroidsStep3 extends AsteroidsStep2 {

	private Spaceship spaceship;

	public void init() {
		
		// create spaceship
		spaceship = new Spaceship();
		spaceship.setLocation(100, 100);
		spaceship.setDirection(0);
		addSpaceObject(spaceship);

		super.init();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			spaceship.incrementDirection(5);
			break;	
		case KeyEvent.VK_RIGHT:
			spaceship.incrementDirection(-5);
			break;
		case KeyEvent.VK_UP:
			spaceship.thrust();
			break;
		}
	}
}
