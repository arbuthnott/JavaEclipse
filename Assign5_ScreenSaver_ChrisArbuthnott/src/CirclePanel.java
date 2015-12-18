import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CirclePanel extends JPanel {
	
	// arraylist of circles:
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private boolean isShowing = true;
	// property methods for 'circles':
	public void addShape(Circle circ) {
		circles.add(circ);
	}
	public void setShapes(ArrayList<Circle> circs) {
		circles = circs;
	}
	
	// colorchange methods:
	public void changeColors(Color col) {
		for (int idx=0; idx<circles.size(); idx++)
			circles.get(idx).fadeColor(col);
	}
	public void randomColors() {
		Color col;
		for (int idx=0; idx<circles.size(); idx++) {
			col = new Color(Shape.getRandom(0, 255),Shape.getRandom(0, 255),Shape.getRandom(0, 255));
			circles.get(idx).fadeColor(col);
		}
	}
	
	// timer:
	private final int ANIMATION_DELAY = 20;
	Timer timer = new Timer(ANIMATION_DELAY, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tick();
		}
	});	
	private void tick() {
		repaint();
	}
	
	// Panel Management methods:
	public void toFront() {
		isShowing = true;
		setVisible(true);
		timer.start();
	}
	public void toBack() {
		isShowing = false;
		setVisible(false);
		timer.stop();
	}
	public void togglePause() {
		if (isShowing) {
			if (timer.isRunning())
				timer.stop();
			else
				timer.start();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//check for ricochets
		Circle circa, circb;
		for (int idxa=0; idxa<circles.size(); idxa++) {
			circa = circles.get(idxa);
			for (int idxb=idxa+1; idxb<circles.size(); idxb++) {
				circb = circles.get(idxb);
				if (circa.collides(circb)) {
					circa.ricochet(circb);
				}
			}
		}
		// move and draw the shapes
		for (int idx=0; idx<circles.size(); idx++) {
			circles.get(idx).update(this.getWidth(), this.getHeight());
			circles.get(idx).draw(g);
		}
	}

	// method to scatter the circles away from a mouseclick.
	public void explode(int posx, int posy) {
		// find maximum distance from click to edge.
		int[] here = {posx, posy};
		int[] topleft = {0,0};
		int[] bottomleft = {0, this.getHeight()};
		int[] topright = {this.getWidth(), 0};
		int[] bottomright = {this.getWidth(), this.getHeight()};
		double max1 = Math.max(Shape.getDistance(here, topleft), Shape.getDistance(here, bottomright));
		double max2 = Math.max(Shape.getDistance(here,  topright), Shape.getDistance(here, bottomleft));
		double maxDist = Math.max(max1, max2);
		
		Circle circ;
		for (int idx=0; idx<circles.size(); idx++) {
			circ = circles.get(idx);
			// reset velocity: direct away from click with spd inv proportional to distance
			// from the click (max of 20, min of 0)
			double spd = 20*(maxDist - Shape.getDistance(here, circ.getCenter()))/maxDist;
			// get magnitude and angle of line from here to shape.
			double connectMag = Shape.getDistance(here, circ.getCenter());
			double connectAngle = Math.acos((circ.getCenter()[0]-posx)/connectMag);
			if (circ.getCenter()[1]-posy < 0)
				connectAngle = 2*Math.PI - connectAngle;
			// reset the velocity:
			circ.setVelocity(spd*Math.cos(connectAngle), spd*Math.sin(connectAngle));
		}
	}
	
	/**
	 * Create the panel.
	 */
	public CirclePanel() {
		setBackground(Color.BLACK);
		// start wih one circle:
		circles.add(Circle.randomBall(300,300));
		timer.start();
	}

}
