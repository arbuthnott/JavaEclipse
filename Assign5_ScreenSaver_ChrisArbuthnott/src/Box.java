import java.awt.Color;


public class Box extends Polygon {
	
	// CONSTRUCTORS:
	// returns a randomized, moving, dynamic box.
	public static Box randomBox(int fieldWidth, int fieldHeight) {
		// create basic box with random values:
		int rad = Shape.getRandom(10, 30);
		int cntx = Shape.getRandom(rad, fieldWidth-rad);
		int cnty = Shape.getRandom(rad, fieldHeight-rad);
		int velx = Shape.getRandom(-5, 5);
		int vely = Shape.getRandom(-5, 5);
		Color col = new Color(Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(70, 200));
		Box box = new Box(cntx,cnty,rad,col);
		
		// set movement and dynamic behavior
		box.setVelocity(velx, vely);
		double rotvel = Shape.getRandom(-5, 5)*2*Math.PI/360;
		box.setRotationVel(rotvel);
		box.randomizeControlPoints();
		return box;
	}
	
	// plain, stationary equilateral box:
	public Box(int cntx, int cnty, int rad, Color col) {
		centerx = cntx;
		centery = cnty;
		rotation = 0;
		pointRadii = new int[4];
		pointAngles = new double[4];
		controlPointCounters = new int[4];
		controlFreqMultipliers = new int[4];
		for (int idx=0; idx<4; idx++) {
			pointRadii[idx] = rad;
			pointAngles[idx] = 2*idx*Math.PI/4;
			controlPointCounters[idx] = 0;
			controlFreqMultipliers[idx] = 1;
		}
		color = col;
		velx = 0;
		vely = 0;
		rotationVel = 0;
		isRippling = false;
	}

	// Box overrides the methods to get x and y values of the vertices, modifying them so the
	// controlPointCounters and controlFreqMultipliers cyclically change the distance (but not angle)
	// of each vertex from the shape center.  Mid side control points are not used.  This effect will
	// always be on, whether isRippling is true or not.
	@Override
	protected int[] getXPoints() {
		int[] xvals = new int[pointRadii.length];
		double effectiveRad;
		for (int idx=0; idx<xvals.length; idx++) {
			effectiveRad = pointRadii[idx]*(1 + (0.5)*Math.sin(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360.0));
			xvals[idx] = centerx + (int)Math.round(effectiveRad * Math.cos(pointAngles[idx]+rotation));
		}
		return xvals;
	}
	@Override
	protected int getXVal(int idx) {
		double effectiveRad = pointRadii[idx]*(1 + (0.5)*Math.sin(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360.0));
		return centerx + (int)Math.round(effectiveRad * Math.cos(pointAngles[idx]+rotation));
	}
	@Override
	protected int[] getYPoints() {
		int[] yvals = new int[pointRadii.length];
		double effectiveRad;
		for (int idx=0; idx<yvals.length; idx++) {
			effectiveRad = pointRadii[idx]*(1 + (0.5)*Math.sin(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360));
			yvals[idx] = centerx + (int)Math.round(effectiveRad * Math.sin(pointAngles[idx]+rotation));
		}
		return yvals;		
	}
	@Override
	protected int getYVal(int idx) {
		double effectiveRad = pointRadii[idx]*(1 + (0.5)*Math.sin(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360.0));
		return centery + (int)Math.round(effectiveRad * Math.sin(pointAngles[idx]+rotation));
	}
}
