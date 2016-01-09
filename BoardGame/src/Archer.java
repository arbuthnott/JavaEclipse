import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;

public class Archer extends GamePiece {
	
	private String[] strikePaths;
	
	public Archer(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 1;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/archerBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/archerWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
		
		String strikes[] = {
			"EEE", "EEN", "ENE", "NEE", "ENN", "NEN", "NNE",
			"NNN", "WNN", "NWN", "NNW", "WWN", "WNW", "NWW",
			"WWW", "WWS", "WSW", "SWW", "WSS", "SWS", "SSW",
			"SSS", "ESS", "SES", "SSE", "EES", "ESE", "SEE"
		};
		strikePaths = strikes;
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
		HashSet<Point> strikeSet = new HashSet<Point>();
		for (String path: strikePaths) {
			int row = pt.getRow();
			int col = pt.getCol();
			int idx = 0;
			while (idx < path.length()) {
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
				if (idx > 0 && new Point(row, col).onBoard(8)) {
					strikeSet.add(new Point(row, col));
				}
				if (!(new Point(row, col).onBoard(8)) || game.getPieceAt(new Point(row, col)) != null) {
					break; // this path is blocked, look no further.
				}
				idx++;
			}
		}
		return strikeSet.toArray(new Point[0]);
	}

}
