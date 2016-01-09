import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Cannon extends GamePiece {
	
	public Cannon(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 2;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/cannonBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/cannonWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
	}

	@Override
	public Point[] getMoves() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (Point point: pt.getAdjacent()) {
			if (point.onBoard(8)) {
				points.add(point);
			}
		}
		return points.toArray(new Point[0]);
	}

	@Override
	public Point[] getStrikes() {
		ArrayList<Point> strikes = new ArrayList<Point>();
		String directions = "NESW";
		for (int idx = 0; idx < directions.length(); idx++) {
			boolean stillClear = true;
			Point target = pt;
			while (stillClear) {
				target = movePoint(target, directions.charAt(idx));
				if (target.onBoard(8)) {
					strikes.add(target);
					stillClear = (game.getPieceAt(target) == null);
				} else {
					stillClear = false;
				}
			}
		}
		return strikes.toArray(new Point[0]);
	}

	private Point movePoint(Point point, char dir) {
		if (dir == 'E') {
			return new Point(point.getRow(),point.getCol()+1);
		} else if (dir == 'W') {
			return new Point(point.getRow(),point.getCol()-1);
		} else if (dir == 'N') {
			return new Point(point.getRow()-1,point.getCol());
		} else if (dir == 'S') {
			return new Point(point.getRow()+1,point.getCol());
		}
		return null;
	}

}
