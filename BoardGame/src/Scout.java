import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Scout extends GamePiece {
	
	public Scout(Game gm, boolean tm, int row, int col, int squareSize) {
		this.game = gm;
		this.pt = new Point(row, col);
		this.team = tm;
		this.maxHP = 2;
		this.currentDamage = 0;
		if (this.team == Game.TOP_TEAM) {
			// use black image
			this.icon = new ImageIcon(getClass().getResource("/images/scoutBlack.png"));
		} else {
			// use white image
			this.icon = new ImageIcon(getClass().getResource("/images/scoutWhite.png"));
		}
		this.image = this.icon.getImage().getScaledInstance(squareSize/3, squareSize/3, Image.SCALE_SMOOTH);
	}

	@Override
	public Point[] getMoves() {
		Point[] nearPoints = pt.getNear();
		ArrayList<Point> moves = new ArrayList<Point>();
		for (Point move: nearPoints) {
			if (move.onBoard(8)) {
				moves.add(move);
			}
		}
		// now iterate over moves, look for extra moves further in the compass directions.
		ArrayList<Point> extraMoves = new ArrayList<Point>();
		for (Point move: moves) {
			if (move.getRow() == pt.getRow() - 1 && move.getCol() == pt.getCol() &&
					new Point(move.getRow() - 1, move.getCol()).onBoard(8) && game.getPieceAt(move) == null) { // North
				extraMoves.add(new Point(move.getRow() - 1, move.getCol()));
			} else if (move.getRow() == pt.getRow() + 1 && move.getCol() == pt.getCol() &&
					new Point(move.getRow() + 1, move.getCol()).onBoard(8) && game.getPieceAt(move) == null) { // South
				extraMoves.add(new Point(move.getRow()+1, move.getCol()));
			} else if (move.getRow() == pt.getRow() && move.getCol() == pt.getCol() - 1 &&
					new Point(move.getRow(), move.getCol()-1).onBoard(8) && game.getPieceAt(move) == null) { // West
				extraMoves.add(new Point(move.getRow(), move.getCol() - 1));
			} else if (move.getRow() == pt.getRow() && move.getCol() == pt.getCol() + 1 &&
					new Point(move.getRow(), move.getCol()+1).onBoard(8) && game.getPieceAt(move) == null) { // East
				extraMoves.add(new Point(move.getRow(), move.getCol()+1));
			}
		}
		moves.addAll(extraMoves);
		return moves.toArray(new Point[0]);
	}

	@Override
	public Point[] getStrikes() {
		Point[] nearPoints = pt.getNear();
		ArrayList<Point> strikes = new ArrayList<Point>();
		for (Point strike: nearPoints) {
			if (strike.onBoard(8)) {
				strikes.add(strike);
			}
		}
		// now iterate over moves, look for extra moves further in the compass directions.
		ArrayList<Point> extraStrikes = new ArrayList<Point>();
		for (Point strike: strikes) {
			if (strike.getRow() == pt.getRow() - 1 && strike.getCol() == pt.getCol() &&
					new Point(strike.getRow() - 1, strike.getCol()).onBoard(8) && game.getPieceAt(strike) == null) { // North
				extraStrikes.add(new Point(strike.getRow() - 1, strike.getCol()));
			} else if (strike.getRow() == pt.getRow() + 1 && strike.getCol() == pt.getCol() &&
					new Point(strike.getRow() + 1, strike.getCol()).onBoard(8) && game.getPieceAt(strike) == null) { // South
				extraStrikes.add(new Point(strike.getRow()+1, strike.getCol()));
			} else if (strike.getRow() == pt.getRow() && strike.getCol() == pt.getCol() - 1 &&
					new Point(strike.getRow(), strike.getCol()-1).onBoard(8) && game.getPieceAt(strike) == null) { // West
				extraStrikes.add(new Point(strike.getRow(), strike.getCol() - 1));
			} else if (strike.getRow() == pt.getRow() && strike.getCol() == pt.getCol() + 1 &&
					new Point(strike.getRow(), strike.getCol()+1).onBoard(8) && game.getPieceAt(strike) == null) { // East
				extraStrikes.add(new Point(strike.getRow(), strike.getCol()+1));
			}
		}
		strikes.addAll(extraStrikes);
		return strikes.toArray(new Point[0]);
	}

}
