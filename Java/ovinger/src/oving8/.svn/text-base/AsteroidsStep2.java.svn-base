package oving8;

import java.awt.Color;

public class AsteroidsStep2 extends Asteroids {

	public void init() {
		// create sun - which is just a large asteroid
		// with some modifications.
		SpaceObject sun = new Asteroid(100, 2, 20);
		sun.setColor(Color.YELLOW);
		sun.setLocation(UNIVERSE_WIDTH / 2, UNIVERSE_HEIGHT / 2);
		sun.recenter();
		addSpaceObject(sun);

		// create nicely orbiting asteroids
		{
			SpaceObject asteroid = new Asteroid(100, 1, 10);
			asteroid.setSpeed(9, 0);
			asteroid.setLocation(UNIVERSE_WIDTH / 2.0, UNIVERSE_HEIGHT * 3.0 / 4.0);
			addSpaceObject(asteroid);
		}

		// create random asteroids
		for (int i = 0; i < 7; i++) {
			SpaceObject asteroid = new Asteroid(Math.random(), Math.random(), 10);
			asteroid.setSpeed((Math.random() - 0.5) * 3, (Math.random() - 0.5) * 3);
			asteroid.setLocation(Math.random() * UNIVERSE_WIDTH, Math.random() * UNIVERSE_HEIGHT);
			addSpaceObject(asteroid);
		}
	}
}
