import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Wizard extends GamePiece {
	
	private String[] strikePaths;
	
	public Wizard(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 1;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/wizardBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/wizardWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
		
		String strikes[] = {
				"E", "W", "N", "S", "EN", "ES", "WN", "WS", "EE", "WW", "NN", "SS",
				"EEE", "EEN", "ENN", "NNN", "WNN", "WWN", "WWW", "WWS", "WSS", "SSS", "ESS", "EES"
		};
		strikePaths = strikes;
	}

	@Override
	public Point[] getMoves() {
		ArrayList<Point> moves = new ArrayList<Point>();
		Point corners[] = {
				new Point(pt.getRow()-1,pt.getCol()-1),
				new Point(pt.getRow()+1,pt.getCol()-1),
				new Point(pt.getRow()+1,pt.getCol()+1),
				new Point(pt.getRow()-1,pt.getCol()+1)
		};
		for (Point corner: corners) {
			if (corner.onBoard(8) && game.getPieceAt(corner) == null) {
				moves.add(corner);
			}
		}
		return moves.toArray(new Point[0]);
	}

	@Override
	public Point[] getStrikes() {
		ArrayList<Point> strikes = new ArrayList<Point>();
		for (String path: strikePaths) {
			int row = pt.getRow();
			int col = pt.getCol();
			for (int idx = 0; idx < path.length(); idx++) {
				char dir = path.charAt(idx);
				if (dir == 'E') {
					col++;
				} else if (dir == 'W') {
					col--;
				} else if (dir == 'N') {
					row--;
				} else if (dir == 'S') {
					row++;
				}
			}
			if (new Point(row, col).onBoard(8)) {
				strikes.add(new Point(row, col));
			}
		}
		return strikes.toArray(new Point[0]);
	}

}
