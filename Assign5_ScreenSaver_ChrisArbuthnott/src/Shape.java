import java.awt.Graphics;
import java.awt.Color;

public abstract class Shape {

	// STATIC PROPERTIES AND METHODS: FRICTION, RANDOM #S AND DISTANCE CALCULATION:
	protected static final double FRICTION = 0.995;
	public static double getDistance(int[] pointA, int[] pointB) {
		return Math.sqrt(Math.pow(pointA[0]-pointB[0], 2) + Math.pow(pointA[1]-pointB[1], 2));
	}
	public static double getDistance(Shape shapeA, Shape shapeB) {
		return getDistance(shapeA.getCenter(), shapeB.getCenter());
	}
	public static int getRandom(int min, int max) {
		return (int)Math.floor(min + Math.random()*(max+1-min));
	}
	
	// properties common to all shapes: color, velocity, center.
	protected Color color;
	protected double velx, vely;
	protected int centerx, centery;
	// radius retrievable from all subtypes, used for collisions.
	public abstract int getRadius();
	
	// property methods:
	public void setVelocity(double vx, double vy) {
		velx = vx;
		vely = vy;
	}
	public double getVelx() {
		return velx;
	}
	public double getVely() {
		return vely;
	}
	public int[] getCenter() {
		int[] center = {centerx, centery};
		return center;
	}
	public void move(int dx, int dy) {
		centerx += dx;
		centery += dy;
	}
	
	// COLLISION DETECTION AND RICOCHET METHODS:
	public boolean contains(double xval, double yval) {
		int[] center = getCenter();
		int[] point = new int[2];
		point[0] = (int)xval;
		point[1] = (int)yval;
		return getDistance(center, point) < getRadius();
	}
	public boolean collides(Shape shape) {
		return getDistance(this, shape) <= (getRadius()+shape.getRadius());
	}
	// Get ricochet values for collision with input shape: new Velocity angle,
	// velocity magnitude, and displacement needed to clear overlap.
	protected double[] getRicochetValues(Shape shape) {
		// get magnitude and angle of current vel vector.
		double mag = Math.sqrt(velx*velx + vely*vely);
		double angle = Math.acos(velx/mag);
		if (vely < 0)
			angle = 2*Math.PI - angle;
		// get magnitude and angle of line from other center to this center.
		double connectMag = getDistance(this, shape);
		double connectAngle = Math.acos((getCenter()[0]-shape.getCenter()[0])/connectMag);
		if (getCenter()[1]-shape.getCenter()[1] < 0)
			connectAngle = 2*Math.PI - connectAngle;
		// compute new angle.
		double newAngle = Math.PI + connectAngle + (connectAngle-angle);
		newAngle = newAngle % (2*Math.PI);
		double margin = (getRadius()+shape.getRadius()+1) - getDistance(this, shape);	
		double movex = Math.ceil(margin*Math.cos(connectAngle));
		double movey = Math.ceil(margin*Math.sin(connectAngle));
		// package the values for return.
		double[] values = {newAngle, mag, movex, movey};
		return values;
	}
	
	// implement ricochet for collision between this and input shape.  Velocities of
	// both shapes are updated!!
	public void ricochet(Shape shape) {
		// get ricochet values for this shape from getRicochetValues.
		double[] valuesThis = getRicochetValues(shape);
		double newAngle = valuesThis[0];
		double mag = valuesThis[1];
		double movex = valuesThis[2];
		double movey = valuesThis[3];
		// get ricochet values for the input shape using getRicochetValues.
		double[] valuesOther = shape.getRicochetValues(this);
		double newAngleOther = valuesOther[0];
		double magOther = valuesOther[1];
		// calculate the new magnitudes and new component velocities.
		double newMag = (mag+magOther)/2;
		double newVelx = Math.cos(newAngle)*newMag;
		double newVely = Math.sin(newAngle)*newMag;
		double newVelxOther = Math.cos(newAngleOther)*newMag;
		double newVelyOther = Math.sin(newAngleOther)*newMag;
		// update the velocities and positions of both shapes.
		setVelocity(newVelx, newVely);
		shape.setVelocity(newVelxOther, newVelyOther);
		move((int)Math.ceil(movex/2), (int)Math.ceil(movey/2));
		shape.move((int)Math.ceil(-movex/2), (int)Math.ceil(-movey/2));
	}
	
	// abstract methods draw, update, to be implemented by subclasses.
	public abstract void update(int fieldWidth, int fieldHeight);
	public abstract void draw(Graphics g);
	
}
