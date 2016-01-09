
public class Point {
	
	private int row;
	private int col;
	
	public Point() {
		row = 0;
		col = 0;
	}
	
	public Point(int r, int c) {
		row = r;
		col = c;
	}
	
	public boolean equals(Point otherPoint) {
		return otherPoint.getRow() == this.row && otherPoint.getCol() == this.col;
	}
	
	public boolean onBoard(int size) {
		return row >= 0 && col >=0 && row < size && col < size; 
	}
	
	public void move(int dr, int dc) {
		row += dr;
		col += dc;
	}
	
	public void moveTo(int newr, int newc) {
		row = newr;
		col = newc;
	}
	
	// returns an array of points next to this point including diagonals
	public Point[] getNear() {
		Point nearPoints[] = new Point[8];
		nearPoints[0] = new Point(row, col+1);
		nearPoints[1] = new Point(row-1, col+1);
		nearPoints[2] = new Point(row-1, col);
		nearPoints[3] = new Point(row-1, col-1);
		nearPoints[4] = new Point(row, col-1);
		nearPoints[5] = new Point(row+1, col-1);
		nearPoints[6] = new Point(row+1, col);
		nearPoints[7] = new Point(row+1, col+1);
		return nearPoints;
	}
	
	// returns an array of points adjacent to this point - no diagonals.
	public Point[] getAdjacent() {
		Point adjPoints[] = new Point[4];
		adjPoints[0] = new Point(row, col+1);
		adjPoints[1] = new Point(row-1, col);
		adjPoints[2] = new Point(row, col-1);
		adjPoints[3] = new Point(row+1, col);
		return adjPoints;
	}
	
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}
	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}

}
