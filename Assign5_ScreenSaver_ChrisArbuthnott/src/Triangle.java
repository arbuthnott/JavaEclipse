import java.awt.Color;

public class Triangle extends Polygon {

	// CONSTRUCTORS:
	// returns a randomized, moving, dynamic triangle.
	public static Triangle randomTriangle(int fieldWidth, int fieldHeight) {
		// create the base triangle with random values.
		int rad = Shape.getRandom(10, 30);
		int cntx = Shape.getRandom(rad, fieldWidth-rad);
		int cnty = Shape.getRandom(rad, fieldHeight-rad);
		int velx = Shape.getRandom(-5, 5);
		int vely = Shape.getRandom(-5, 5);
		// note: the color includes random transparency:
		Color col = new Color(Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(70, 200));
		Triangle tri = new Triangle(cntx,cnty,rad,col);
		
		// set movement and dynamic behavior:
		tri.setVelocity(velx, vely);
		double rotvel = Shape.getRandom(-5, 5)*2*Math.PI/360;
		tri.setRotationVel(rotvel);
		tri.setRipple(true);
		tri.randomizeControlPoints();
		return tri;
	}
	
	// plain, stationary equilateral triangle:
	public Triangle(int cntx, int cnty, int rad, Color col) {
		centerx = cntx;
		centery = cnty;
		rotation = 0;
		// three vertices, so three points in each array.
		pointRadii = new int[3];
		pointAngles = new double[3];
		controlPointCounters = new int[3];
		controlFreqMultipliers = new int[3];
		for (int idx=0; idx<3; idx++) {
			pointRadii[idx] = rad;
			pointAngles[idx] = 2*idx*Math.PI/3; // index * 1/3 full circle.
			controlPointCounters[idx] = 0;
			controlFreqMultipliers[idx] = 1;
		}
		color = col;
		velx = 0;
		vely = 0;
		rotationVel = 0;
		isRippling = false;
	}
}
