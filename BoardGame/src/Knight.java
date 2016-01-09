import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Knight extends GamePiece {
	
	public Knight(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 3;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/knightBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/knightWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
	}

	@Override
	// return the set of all available moves still on the board.
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
	// return the set of all possible strike points still on the board.
	public Point[] getStrikes() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (Point point: pt.getNear()) {
			if (point.onBoard(8)) {
				points.add(point);
			}
		}
		return points.toArray(new Point[0]);
	}
}
