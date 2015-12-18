import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;

public class Circle extends Shape {
	// properties
	private int radius;
	// properties used for colorfades:
	private Color targetColor; // color to fade to.  Current color 'color' inherited from Shape.
	private int counter = 0; // no fade in progress when counter is 0.
	
	public int getRadius() {
		return radius;
	}
	
	public void fadeColor(Color col) {
		targetColor = col;
		counter = 40;
	}
	
	// CONSTRUCTORS:
	
	// get a randomized ball-style circle.
	public static Circle randomBall(int fieldWidth, int fieldHeight) {
		int rad = Shape.getRandom(8, 20);
		int cntx = Shape.getRandom(rad, fieldWidth-rad);
		int cnty = Shape.getRandom(rad, fieldHeight-rad);
		int velx = Shape.getRandom(-5, 5);
		int vely = Shape.getRandom(-5, 5);
		Color col = new Color(Shape.getRandom(0, 255), Shape.getRandom(0, 255), Shape.getRandom(0, 255));
		Circle circle = new Circle(cntx, cnty, rad, col);
		circle.setVelocity(velx, vely);
		return circle;
	}
	
	// create stationary, static circle.
	public Circle(int cntx, int cnty, int rad, Color col) {
		centerx = cntx;
		centery = cnty;
		radius = rad;
		color = col;
		velx = 0;
		vely = 0;
	}
	
	// ANIMATION METHODS: UPDATE AND DRAW
	
	// update center, velocity and counter value for color morphing.
	// handles wall bounces: dimensions of panel passed in.  Doesn't handle collisions.
	public void update(int fieldWidth, int fieldHeight) {
		if (Math.sqrt(velx*velx + vely*vely) > 1.5) {
			velx *= FRICTION;
			vely *= FRICTION;
		}
		centerx = (int)Math.round(centerx+velx);
		centery = (int)Math.round(centery+vely);
		if (centerx < radius) {
			centerx = radius;
			velx *= -1;
		} else if (centerx > (fieldWidth-radius)) {
			centerx = fieldWidth - radius;
			velx *= -1;
		}
		if (centery < radius) {
			centery = radius;
			vely *= -1;
		} else if (centery > (fieldHeight-radius)) {
			centery = fieldHeight - radius;
			vely *= -1;
		}
		// color morphing behavior using the counter:
		if (counter == 1)
			color = targetColor;
		if (counter > 0)
			counter --;
	}
	
	// draw using the passed in graphics object.
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// get parameters required by fillOval:
		int xcoord = centerx - radius;
		int ycoord = centery - radius;
		int width = 2*radius;
		int height = 2*radius;
		
		// try a gradient.
		float focusx = centerx - radius/2;
		float focusy = centery - radius/2;
		float[] fractions = {0.1f, 1.0f};
		Color clr = color;
		// set clr scaled between 'color' and 'targetColor' using counter, if counter is > 0.
		if (counter > 0) {
			double factor = (40-counter)/40.0;
			int red = (int)(color.getRed() + factor*(targetColor.getRed()-color.getRed()));
			int green = (int)(color.getGreen() + factor*(targetColor.getGreen()-color.getGreen()));
			int blue = (int)(color.getBlue() + factor*(targetColor.getBlue()-color.getBlue()));
			clr = new Color(red, green, blue);
		}
		Color[] colors = {Color.WHITE, clr};
		RadialGradientPaint grad = new RadialGradientPaint((float)centerx, (float)centery, (float)getRadius(),
				focusx, focusy, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
		
		// paint the circle.
		g2d.setPaint(grad);
		g2d.fillOval(xcoord, ycoord, width, height);
	}
}
