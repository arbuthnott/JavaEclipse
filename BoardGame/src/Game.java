import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Game {

	// these represent the two sides.
	public static boolean TOP_TEAM = true;
	public static boolean BOTTOM_TEAM = false;
	
	private boolean currentTeam = BOTTOM_TEAM;
	private ArrayList<GamePiece> topPieces;
	private ArrayList<GamePiece> bottomPieces;
	
	public Game(int squareSize) {
		// stub - put on some knights.
		topPieces = new ArrayList<GamePiece>();
		bottomPieces = new ArrayList<GamePiece>();
		
		topPieces.add(new Defenseman(this, TOP_TEAM, 1, 1, squareSize));
		topPieces.add(new Defenseman(this, TOP_TEAM, 1, 3, squareSize));
		topPieces.add(new Defenseman(this, TOP_TEAM, 1, 4, squareSize));
		topPieces.add(new Defenseman(this, TOP_TEAM, 1, 6, squareSize));
		topPieces.add(new Knight(this, TOP_TEAM, 1,0, squareSize));
		topPieces.add(new Knight(this, TOP_TEAM, 1,2, squareSize));
		topPieces.add(new Knight(this, TOP_TEAM, 1,5, squareSize));
		topPieces.add(new Knight(this, TOP_TEAM, 1,7, squareSize));
		topPieces.add(new Scout(this, TOP_TEAM, 0,2, squareSize));
		topPieces.add(new Scout(this, TOP_TEAM, 0,5, squareSize));
		topPieces.add(new Wizard(this, TOP_TEAM, 0,3, squareSize));
		topPieces.add(new Wizard(this, TOP_TEAM, 0,4, squareSize));
		topPieces.add(new Archer(this, TOP_TEAM, 0,1, squareSize));
		topPieces.add(new Archer(this, TOP_TEAM, 0,6, squareSize));
		topPieces.add(new Cannon(this, TOP_TEAM, 0,0, squareSize));
		topPieces.add(new Cannon(this, TOP_TEAM, 0,7, squareSize));
		
		bottomPieces.add(new Defenseman(this, BOTTOM_TEAM, 6, 1, squareSize));
		bottomPieces.add(new Defenseman(this, BOTTOM_TEAM, 6, 3, squareSize));
		bottomPieces.add(new Defenseman(this, BOTTOM_TEAM, 6, 4, squareSize));
		bottomPieces.add(new Defenseman(this, BOTTOM_TEAM, 6, 6, squareSize));
		bottomPieces.add(new Knight(this, BOTTOM_TEAM, 6,0, squareSize));
		bottomPieces.add(new Knight(this, BOTTOM_TEAM, 6,2, squareSize));
		bottomPieces.add(new Knight(this, BOTTOM_TEAM, 6,5, squareSize));
		bottomPieces.add(new Knight(this, BOTTOM_TEAM, 6,7, squareSize));
		bottomPieces.add(new Scout(this, BOTTOM_TEAM, 7,2, squareSize));
		bottomPieces.add(new Scout(this, BOTTOM_TEAM, 7,5, squareSize));
		bottomPieces.add(new Wizard(this, BOTTOM_TEAM, 7,3, squareSize));
		bottomPieces.add(new Wizard(this, BOTTOM_TEAM, 7,4, squareSize));
		bottomPieces.add(new Archer(this, BOTTOM_TEAM, 7,1, squareSize));
		bottomPieces.add(new Archer(this, BOTTOM_TEAM, 7,6, squareSize));
		bottomPieces.add(new Cannon(this, BOTTOM_TEAM, 7,0, squareSize));
		bottomPieces.add(new Cannon(this, BOTTOM_TEAM, 7,7, squareSize));
	}
	
	public void switchTurn() {
		currentTeam = !currentTeam;
	}
	
	public boolean getCurrentTeam() {
		return currentTeam;
	}
	
	public void draw(ImageObserver obs, Graphics g, int left, int top, int squareSize) {
		for (GamePiece piece: topPieces) {
			piece.draw(obs, g, left, top, squareSize);
		}
		for (GamePiece piece: bottomPieces) {
			piece.draw(obs, g, left, top, squareSize);
		}
	}

	public GamePiece getPieceAt(Point point) {
		for (GamePiece piece: topPieces) {
			if (piece.getPoint().equals(point)) {
				return piece;
			}
		}
		for (GamePiece piece: bottomPieces) {
			if (piece.getPoint().equals(point)) {
				return piece;
			}
		}
		return null;
	}

	public void removePieceAt(Point strike) {
		// TODO Auto-generated method stub
		GamePiece deadPiece = null;
		for (GamePiece piece: topPieces) {
			if (piece.getPoint().equals(strike)) {
				deadPiece = piece;
			}
		}
		if (deadPiece != null) {
			topPieces.remove(deadPiece);
			return;
		}
		for (GamePiece piece: bottomPieces) {
			if (piece.getPoint().equals(strike)) {
				deadPiece = piece;
			}
		}
		if (deadPiece != null) {
			bottomPieces.remove(deadPiece);
			return;
		}
	}
}
