import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Defenseman extends GamePiece {
	
	public Defenseman(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 4;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/defenseBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/defenseWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
	}

	@Override
	public Point[] getMoves() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (Point point: pt.getNear()) {
			if (point.onBoard(8)) {
				points.add(point);
			}
		}
		return points.toArray(new Point[0]);
	}

	@Override
	public Point[] getStrikes() {
		// cannot attack.
		return new Point[0];
	}

}
