package oving8;

import javax.vecmath.Vector2d;
import java.awt.Polygon;
import java.util.Vector;

import acm.graphics.GPoint;
import acm.graphics.GPolygon;
public class SpaceObject extends GPolygon {

	
	
	public final double G =1.0;
//	private Vector2d pos; 
	private Vector2d velocity = new Vector2d(0,0); 
	private Vector2d accel = new Vector2d(0,0); 
	
	
	public double getvelocityX() {
		return this.velocity.x;
	}

	public double getvelocityY() {
		return this.velocity.y;
	}

	public void setSpeed(double dx, double dy) {
		this.velocity.x = dx;
		this.velocity.y = dy;
	}

	public void accelerate(double ax, double ay) {
		this.velocity.x += ax;
		this.velocity.y += ay;
	}

	public double getMass() {
		return 5;
	}

	public void applyGravitationalForce(SpaceObject other) {
		
		Vector2d distance = this.getDistance(other);
		double accelAmount = ( G*other.getMass() ) / ( Math.pow(distance.length(), 2) );
		Vector2d accelVector = distance;
		accelVector.normalize(); // only interested in direction, make unit vector
		accelVector.scale(accelAmount);
		this.accelerate(accelVector.x, accelVector.y);
	}

	public boolean intersects(SpaceObject other) {
		Polygon thisPoly = this.getPolygon();
        Polygon otherPoly = other.getPolygon();
        
        for(int i = 0; i<thisPoly.xpoints.length;i++) {
                if(otherPoly.contains(thisPoly.xpoints[i], thisPoly.ypoints[i])) {
                        return true;
                }
        }
        
        for(int i = 0; i<otherPoly.xpoints.length;i++) {
                if(thisPoly.contains(otherPoly.xpoints[i], otherPoly.ypoints[i])) {
                        return true;
                }
        }              
        return false;
}

	public Vector2d getDistance(SpaceObject other){
		return new Vector2d(other.getX()-this.getX(), other.getY()- this.getY());
	}

	public void tick() {
		this.move(this.velocity.x, this.velocity.y);
	}
}
