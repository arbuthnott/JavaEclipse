import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public abstract class Polygon extends Shape {
	// PROPERTIES:
	// vertices defined by radius and angle from center.
	protected int[] pointRadii; // distances of points from center
	protected double[] pointAngles; // angles of points from center
	// support for rotation:
	protected double rotation;
	protected double rotationVel;
	// two arrays control dynamic behavior:
	int[] controlPointCounters; // a cyclic counter representing argument of sin function in degrees.
	int[] controlFreqMultipliers; // a multiplier of the counter to alter frequency.
	boolean isRippling = false; // when true, dynamic behavior is turned on.

	// getRadius returns the max of the point radii, for use in wall bounces or collisions.
	public int getRadius() {
		int highest = pointRadii[0];
		for (int idx=1; idx<pointRadii.length; idx++)
			if (pointRadii[idx] > highest)
				highest = pointRadii[idx];
		return highest;
	}
	
	// VERTEX CALCULATION METHODS:
	// returns an array of the vertices' x-values, calculated using radii and angle properties.
	protected int[] getXPoints() {
		int[] xvals = new int[pointRadii.length];
		for (int idx=0; idx<xvals.length; idx++)
			xvals[idx] = centerx + (int)Math.round(pointRadii[idx] * Math.cos(pointAngles[idx]+rotation));
		return xvals;
	}
	// returns an array of the vertices' y-values, calculated using radii and angle properties.
	protected int[] getYPoints() {
		int[] yvals = new int[pointRadii.length];
		for (int idx=0; idx<yvals.length; idx++)
			yvals[idx] = centery + (int)Math.round(pointRadii[idx] * Math.sin(pointAngles[idx]+rotation));
		return yvals;
	}
	// returns the x-value of the vertex with index idx.
	protected int getXVal(int idx) {
		return centerx + (int)Math.round(pointRadii[idx] * Math.cos(pointAngles[idx]+rotation));
	}
	// returns the y-value of the vertex with index idx.
	protected int getYVal(int idx) {
		return centery + (int)Math.round(pointRadii[idx] * Math.sin(pointAngles[idx]+rotation));
	}

	//ROTATION PROPERTY METHODS:
	public void setRotation(double rot) {
		rotation = rot;
	}
	public double getRotation() {
		return rotation;
	}
	public void setRotationVel(double rotVel) {
		rotationVel = rotVel;
	}
	public double getRotationVel() {
		return rotationVel;
	}
	
	// support for variable control point on each side of shape.
	// convention: ctrl point n is between points (n-1) and n.
	
	// DYNAMIC BEHAVIOR METHODS:
	public void toggleRipple() {
		isRippling = !isRippling;
	}
	public void setRipple(boolean bool) {
		isRippling = bool;
	}
	public void randomizeControlPoints() {
		for (int idx=0; idx<controlPointCounters.length; idx++) {
			controlPointCounters[idx] = Shape.getRandom(0,359);
			controlFreqMultipliers[idx] = Shape.getRandom(1,4);
		}
	}
	// utility method to find midpoint of two input points.
	protected int[] getMidPoint(int xone, int yone, int xtwo, int ytwo) {
		int xmid = (xone+xtwo)/2;
		int ymid = (yone+ytwo)/2;
		int[] midpoint = {xmid, ymid};
		return midpoint;
	}
	
	// returns the control point that goes between points idx-1 and idx.  Starts as the midpoint,
	// then is modified by controlFreqMultiplier and controlPointCounter variables.
	protected int[] getControlPoint(int idx) {
		// get point vars for the start, end, midpoint and shape center:
		int[] pointb = {getXVal(idx), getYVal(idx)};
		int idx2 = idx-1;
		if (idx2 < 0)
			idx2 = pointRadii.length - 1;
		int[] pointa = {getXVal(idx2),getYVal(idx2)};
		int[] midpoint = getMidPoint(pointa[0],pointa[1],pointb[0],pointb[1]);
		int[] center = {centerx, centery};
		
		double maxOffset = (pointRadii[idx]+pointRadii[idx2])/3.0; // less than avg radius of endpoints.
		// get magnitude and angle of line from polygon centre to line midpoint.
		double connectMag = getDistance(midpoint, center);
		double connectAngle = Math.acos((midpoint[0]-centerx)/connectMag);
		if (midpoint[1]-centery < 0)
			connectAngle = 2*Math.PI - connectAngle;
		// get component maximum offsets.
		double maxOffsetX = maxOffset*Math.cos(connectAngle);
		double maxOffsetY = maxOffset*Math.sin(connectAngle);
		// use to calculate cyclic offsets:
		double offsetX = maxOffsetX*Math.cos(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360);
		double offsetY = maxOffsetY*Math.sin(controlFreqMultipliers[idx]*controlPointCounters[idx]*2*Math.PI/360);
		// return the control point.
		int[] ctrlpt = {(int)Math.round(midpoint[0]+offsetX), (int)Math.round(midpoint[1]+offsetY)};
		return ctrlpt;
	}
	
	// If collisions are on, this override allows rotations to be affected by collisions:
	@Override
	protected double[] getRicochetValues(Shape shape) {
		// adjust the rotations to this shape.
		setRotationVel((-1)*getRotationVel());
		return super.getRicochetValues(shape);
	}
	
	// ANIMATIN METHODS
	// updates position, velocity and rotation, and iterates controlPointCounters.
	public void update(int fieldWidth, int fieldHeight) {
		// apply friction, only is speed is above a threshold.
		if (Math.sqrt(velx*velx+vely*vely) > 5) {
			velx *= Shape.FRICTION;
			vely *= Shape.FRICTION;
		}
		// update position and rotation.
		centerx = (int)Math.round(centerx+velx);
		centery = (int)Math.round(centery+vely);
		rotation += rotationVel;
		// check for wall bounces:
		int radius = getRadius();
		if (centerx < radius) {
			centerx = radius;
			velx *= -1;
			rotationVel *= -1;
		} else if (centerx > (fieldWidth-radius)) {
			centerx = fieldWidth - radius;
			velx *= -1;
			rotationVel *= -1;
		}
		if (centery < radius) {
			centery = radius;
			vely *= -1;
			rotationVel *= -1;
		} else if (centery > (fieldHeight-radius)) {
			centery = fieldHeight - radius;
			vely *= -1;
			rotationVel *= -1;
		}
		// cyclically update controlPointCounters:
		for (int idx=0; idx<controlPointCounters.length; idx++) {
			controlPointCounters[idx] = (controlPointCounters[idx] + 4) % 360;
		}
	}
	
	// build the shape as a general path, using vertices.
	public void draw(Graphics g) {
		Path2D.Double path = new Path2D.Double();
		path.moveTo(getXVal(0),getYVal(0));
		for (int idx=1; idx<pointRadii.length; idx++) {
			if (isRippling) {
				// draw a quadratic to next vertex through the control point.
				int[] controlPoint = getControlPoint(idx);
				path.quadTo(controlPoint[0], controlPoint[1], getXVal(idx), getYVal(idx));
			} else {
				// draw a line to next vertex.
				path.lineTo(getXVal(idx),getYVal(idx));
			}
		}
		// draw final connector:
		if (isRippling)
			path.quadTo(getControlPoint(0)[0],getControlPoint(0)[1], getXVal(0),getYVal(0));
		else
			path.lineTo(getXVal(0), getYVal(0));
		
		// draw the shape:
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.fill(path);
	}
	
}
