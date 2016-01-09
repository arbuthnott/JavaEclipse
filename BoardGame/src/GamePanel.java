import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	// some stock colors
	public static Color BOARD_WHITE = new Color(235,235,235);
	public static Color BOARD_GREY = new Color(215,215,215);
	public static Color PLAYER_BLUE = new Color(63,72,204);
	public static Color PLAYER_ORANGE = new Color(255,127,39);
	public static Color SELECTED_HIGHLIGHT = new Color(255,242,0, 180);
	public static Color MOVE_HIGHLIGHT = new Color(34,177,76, 80);
	public static Color ATTACK_HIGHLIGHT = new Color(163,73,164, 80);

	private int GUTTER_WIDTH;
	private Point underMouse;
	private Game game;
	
	private boolean moved;
	private Point selectedPiece;
	
	/**
	 * Create the panel.
	 */
	public GamePanel(int min_gutter, int squareSize) {
		GUTTER_WIDTH = min_gutter;
		game = new Game(squareSize);
		moved = false;
		underMouse = null;
		selectedPiece = null;
	}
	
	public String getInstruction() {
		String teamColor = "Blue";
		String action = "move";
		if (game.getCurrentTeam() == game.TOP_TEAM) {
			teamColor = "Orange";
		}
		if (moved) {
			action = "attack";
		}
		if (selectedPiece == null) {
			return teamColor + " Player: Choose a Piece for your " + action + "!";
		} else {
			if (moved) {
				return teamColor + " Player: Choose a square to attack!";
			} else {
				return teamColor + " Player: Choose a square to move to.";
			}
		}
	}
	
	public String getSkipText() {
		String teamColor = "Blue";
		String action = "move";
		if (game.getCurrentTeam() == game.TOP_TEAM) {
			teamColor = "Orange";
		}
		if (moved) {
			action = "attack";
		}
		return "Skip " + teamColor + "'s " + action;
	}
	
	public Point getSquare(int x, int y) {
		int squareSize = (Math.min(getWidth(), getHeight()) - (2*GUTTER_WIDTH)) / 8;
		int row = (int)Math.floor((y - GUTTER_WIDTH) / (0.0 + squareSize));
		int col = (int)Math.floor((x - GUTTER_WIDTH) / (0.0 + squareSize));
		if (row < 0 || col < 0 || row > 7 || col > 7) {
			return null;
		} else {
			return new Point(row, col);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// calculate square size.
		int squareSize = (Math.min(getWidth(), getHeight()) - (2*GUTTER_WIDTH)) / 8;
		
		// draw the board
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row + col) % 2 == 0) {
					g.setColor(BOARD_WHITE);
				} else {
					g.setColor(BOARD_GREY);
				}
				g.fillRect(GUTTER_WIDTH + col*squareSize, GUTTER_WIDTH + row*squareSize, squareSize, squareSize);
			}
		}
		
		// highlight selected piece and (if applicable) available moves or strikes.
		if (selectedPiece != null) {
			GamePiece piece = game.getPieceAt(selectedPiece);
			g.setColor(SELECTED_HIGHLIGHT);
			g.fillRect(GUTTER_WIDTH + selectedPiece.getCol()*squareSize,  GUTTER_WIDTH + selectedPiece.getRow()*squareSize, squareSize, squareSize);
			if (moved) {
				// highlight available strikes
				Point[] strikes = piece.getStrikes();
				for (Point strike: strikes) {
					g.setColor(ATTACK_HIGHLIGHT);
					g.fillRect(GUTTER_WIDTH + strike.getCol()*squareSize,  GUTTER_WIDTH + strike.getRow()*squareSize, squareSize, squareSize);
				}
			} else {
				// highlight available moves
				Point[] moves = piece.getMoves();
				for (Point move: moves) {
					if (game.getPieceAt(move) == null) { // square is vacant
						g.setColor(MOVE_HIGHLIGHT);
						g.fillRect(GUTTER_WIDTH + move.getCol()*squareSize,  GUTTER_WIDTH + move.getRow()*squareSize, squareSize, squareSize);
					}
				}
			}
		}
		
		// draw the game pieces
		game.draw(this, g, GUTTER_WIDTH, GUTTER_WIDTH, squareSize);
		
		// outline the hovered square
		if (underMouse != null) {
			g.setColor(new Color(0,0,0));
			g.drawRect(GUTTER_WIDTH + underMouse.getCol()*squareSize, GUTTER_WIDTH + underMouse.getRow()*squareSize, squareSize, squareSize);
		}
		
	}

	/**
	 * @return the underMouse
	 */
	public Point getUnderMouse() {
		return underMouse;
	}

	/**
	 * @param underMouse the underMouse to set
	 */
	public void setUnderMouse(Point underMouse) {
		this.underMouse = underMouse;
	}

	public void click(Point pt) {
//		if (selectedPiece != null && selectedPiece.equals(pt)) { // clicked on selected.
//			selectedPiece = null;
//			repaint();
//		} else {
//			if (game.getPieceAt(pt) != null) {
//				selectedPiece = pt;
//				repaint();
//			}
//		}
		GamePiece piece = game.getPieceAt(pt);
		
		if (selectedPiece == null) { // no piece currently selected
			// see if we've clicked on a selectable piece.
			if (piece != null && piece.getTeam() == game.getCurrentTeam()) {
				selectedPiece = pt;
				repaint();
			}
			return;
		}
		
		// A piece is currently selected.  Check if we are unselecting
		if (pt.equals(selectedPiece)) {
			selectedPiece = null;
			repaint();
			return;
		}
		
		// Check if we are changing to a new piece selection.
		if (piece != null && piece.getTeam() == game.getCurrentTeam()) {
			selectedPiece = pt;
			repaint();
			return;
		}
		
		// Check if we are trying to move the selected piece
		if (!moved && piece == null) {
			// see if we can move there, and if so, do
			Point moves[] = game.getPieceAt(selectedPiece).getMoves();
			for (Point move: moves) {
				if (move.equals(pt)) {
					// we can move there.
					game.getPieceAt(selectedPiece).moveTo(pt);
					moved = true;
					selectedPiece = null;
					repaint();
					return;
				}
			}
		}
		
		// Check if we are trying to attack with the selected piece
		if (moved && piece != null && piece.getTeam() != game.getCurrentTeam()) { //TO UNSTUB CHANGE TO (moved && piece != null && piece.getTeam() != game.getCurrentTeam())
			// see if we can attack this piece, and if so, do
			Point strikes[] = game.getPieceAt(selectedPiece).getStrikes();
			for (Point strike: strikes) {
				if (strike.equals(pt)) {
					// we can attack there.
					if (game.getPieceAt(strike).hit()) {
						// remove this piece
						game.removePieceAt(strike);
					}
					game.switchTurn();
					moved = false;
					selectedPiece = null;
					repaint();
					return;
				}
			}
		}
		
		
	}

	public void skip() {
		if (!moved) {
			moved = true;
			selectedPiece = null;
			repaint();
		} else {
			game.switchTurn();
			moved = false;
			selectedPiece = null;
			repaint();
		}
		
	}

}
