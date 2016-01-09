import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public abstract class GamePiece {
	
	protected Game game;
	protected Point pt;
	protected boolean team;
	protected int maxHP;
	protected int currentDamage;
	protected ImageIcon icon;
	protected Image image;
	
	public Point getPoint() {
		return pt;
	}
	
	public boolean getTeam() {
		return team;
	}
	
	public void moveTo(Point point) {
		pt = point;
	}
	
	public boolean hit() {
		currentDamage += 1;
		return currentDamage >= maxHP;
	}
	
	public abstract Point[] getMoves();
	public abstract Point[] getStrikes();
	
	public void draw(ImageObserver obs, Graphics g, int boardLeft, int boardTop, int squareSize) {
		if (this.team == Game.TOP_TEAM) {
			g.setColor(GamePanel.PLAYER_ORANGE);
		} else {
			g.setColor(GamePanel.PLAYER_BLUE);
		}
		int myLeft = boardLeft + pt.getCol() * squareSize;
		int myTop = boardTop + pt.getRow() * squareSize;
		g.fillRect(myLeft + squareSize/4, myTop + squareSize/4, squareSize/2, squareSize/2);
		
		// fill in health circles
		int barY = myTop + 3*squareSize / 4;
		for (int idx = 0; idx < maxHP; idx++) {
			int barX = myLeft + (int)Math.floor(squareSize/4.0) + (int)Math.floor(idx*(squareSize-5)/6.0);
			if (idx < maxHP - currentDamage) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			g.fillOval(barX, barY, 5, 5);
			g.setColor(Color.BLACK);
			g.drawOval(barX, barY, 5, 5);
		}
		
		// draw the icon
		g.drawImage(this.image, myLeft + squareSize/3, myTop + squareSize/3, squareSize/3, squareSize/3, obs);
//		g.drawImage(this.icon.getImage(), myLeft + squareSize/3, myTop + squareSize/3, myLeft + 2*squareSize/3, myTop + 2*squareSize/3,
//				0, 0, 512, 512, null);
	}

}
