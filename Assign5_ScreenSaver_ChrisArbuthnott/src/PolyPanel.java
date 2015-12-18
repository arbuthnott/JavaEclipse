import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;

import javax.swing.Timer;

import java.awt.Color;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PolyPanel extends JPanel {
	// arraylist of polygons:
	private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	private boolean isShowing = false;
	// property methods for polygons:
	public void addShape(Polygon poly) {
		polygons.add(poly);
	}
	public void setShapes(ArrayList<Polygon> polys) {
		polygons = polys;
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
	
	// panel management methods:
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
		
		//UNCOMMENT TO ENABLE COLLISIONS:
		//check for ricochets
//		Polygon polya, polyb;
//		for (int idxa=0; idxa<polygons.size(); idxa++) {
//			polya = polygons.get(idxa);
//			for (int idxb=idxa+1; idxb<polygons.size(); idxb++) {
//				polyb = polygons.get(idxb);
//				if (polya.collides(polyb)) {
//					polya.ricochet(polyb);
//				}
//			}
//		}
		
		// move and draw the shapes
		for (int idx=0; idx<polygons.size(); idx++) {
			polygons.get(idx).update(this.getWidth(), this.getHeight());
			polygons.get(idx).draw(g);
		}
	}
	
	// method to scatter the shapes from the mouseclick.
	public void explode(int posx, int posy) {
		// find max distance from click on the panel:
		int[] here = {posx, posy};
		int[] topleft = {0,0};
		int[] bottomleft = {0, this.getHeight()};
		int[] topright = {this.getWidth(), 0};
		int[] bottomright = {this.getWidth(), this.getHeight()};
		double max1 = Math.max(Shape.getDistance(here, topleft), Shape.getDistance(here, bottomright));
		double max2 = Math.max(Shape.getDistance(here,  topright), Shape.getDistance(here, bottomleft));
		double maxDist = Math.max(max1, max2);
		
		Polygon poly;
		for (int idx=0; idx<polygons.size(); idx++) {
			poly = polygons.get(idx);
			// set velocity direction away from the mouse click, with spd inversely proportional
			// to distance from the click (max of 20, min of 0).
			double spd = 20*(maxDist - Shape.getDistance(here, poly.getCenter()))/maxDist;
			// get magnitude and angle of line from here to shape.
			double connectMag = Shape.getDistance(here, poly.getCenter());
			double connectAngle = Math.acos((poly.getCenter()[0]-posx)/connectMag);
			if (poly.getCenter()[1]-posy < 0)
				connectAngle = 2*Math.PI - connectAngle;
			// set the velocity:
			poly.setVelocity(spd*Math.cos(connectAngle), spd*Math.sin(connectAngle));
		}
	}

	/**
	 * Create the panel.
	 */
	public PolyPanel() {
		setBackground(Color.BLACK);
		polygons.add((Polygon)Triangle.randomTriangle(300,300));
		timer.start();
	}

}
