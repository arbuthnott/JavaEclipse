import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;


public class Star extends Polygon {
	
	// CONSTRUCTORS:
	// returns a randomized, moving, dynamic Star.
	public static Star randomStar(int fieldWidth, int fieldHeight) {
		// create base star with random values:
		int rad = Shape.getRandom(10, 30);
		int cntx = Shape.getRandom(rad, fieldWidth-rad);
		int cnty = Shape.getRandom(rad, fieldHeight-rad);
		int velx = Shape.getRandom(-5, 5);
		int vely = Shape.getRandom(-5, 5);
		// note: color had random transparency:
		Color col = new Color(Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(70, 200));
		Star star = new Star(cntx,cnty,rad,col);
		
		// set movement and dynamic behavior:
		star.setVelocity(velx, vely);
		double rotvel = Shape.getRandom(-5, 5)*2*Math.PI/360;
		star.setRotationVel(rotvel);
		star.setRipple(true);
		star.randomizeControlPoints();
		return star;
	}
	
	// plain, stationary equilateral star:
	public Star(int cntx, int cnty, int rad, Color col) {
		centerx = cntx;
		centery = cnty;
		rotation = 0;
		// three vertices, so three values in each array:
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
	
	// Override the draw function from polynomial.  One change only: Star uses the
	// controlPoints to draw lines (not quadratics) from vertex to vertex.  It does this whether
	// dynamic behavior is turned on or off.
	@Override
	public void draw(Graphics g) {
		Path2D.Double path = new Path2D.Double();
		path.moveTo(getXVal(0),getYVal(0));
		for (int idx=1; idx<pointRadii.length; idx++) {
			int[] controlPoint = getControlPoint(idx);
			path.lineTo(controlPoint[0], controlPoint[1]); // line to control point
			path.lineTo(getXVal(idx), getYVal(idx)); // line to next vertex.
		}
		path.lineTo(getControlPoint(0)[0], getControlPoint(0)[1]);
		path.lineTo(getXVal(0), getYVal(0));
		
		// draw the shape.
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.fill(path);
	}

}
