package oving8;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import acm.program.GraphicsProgram;
import acm.util.SwingTimer;

public class Asteroids extends GraphicsProgram implements Iterable {

	protected static final int UNIVERSE_WIDTH = 800;
	protected static final int UNIVERSE_HEIGHT = 600;

	private List<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();
	
	protected void addSpaceObject(SpaceObject sp) {
		add(sp);
		spaceObjects.add(sp);
	}

	public void run() {
		setBackground(Color.black);
		setSize(UNIVERSE_WIDTH, UNIVERSE_HEIGHT);
		addKeyListeners();
		new SwingTimer(1000 / 24, this).start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		tick();
	}

	private void tick() {
		// change speed due to gravitation
		for (SpaceObject pullingObject : spaceObjects) {
			for (SpaceObject pulledObject : spaceObjects) {
				if (pulledObject != pullingObject){
					pulledObject.applyGravitationalForce(pullingObject);
				}
			}
		}
		
		// move all objects
		for (SpaceObject spaceObject : spaceObjects) {
			spaceObject.tick();
		}
		
		// check for collision
		for (SpaceObject spaceObject : spaceObjects) {
			for (SpaceObject otherSpaceObject : spaceObjects) {
				if (spaceObject != otherSpaceObject && spaceObject.intersects(otherSpaceObject)) {
					spaceObject.setFilled(true);
					spaceObject.setFillColor(Color.RED);
				}
			}
		}
	}
}
