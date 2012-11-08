/**
 * @author Roy Guo
 * @date 8 Nov 2012
 * @link
 * 
 * @usage The basic components of a board. 
 * 
 */
public class Square {
	/*the x coordinate on the board*/
	private int x;
	/*the y coordinate on the board*/
	private int y;
	/*whether the square has been visited*/
	private boolean visited;
	
	
	/**
	 * Constructor
	 * @param i initialize the x coordinate
	 * @param j initialize the y coordinate
	 * @param x indicate whether it is visited
	 */
	public Square(int i, int j, boolean x) {
		visited = x;
		this.x = i;
		this.y = j;
	}
	
	/*getter of visited*/
	public boolean isVisited() {
		return visited;
	}
	/*setter of visited*/
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	/*getter of X*/
	public int getX() {
		return x;
	}
	/*setter of X*/
	public void setX(int x) {
		this.x = x;
	}
	/*getter of Y*/
	public int getY() {
		return y;
	}
	/*setter of Y*/
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * 
	 * @param x x coordinator
	 * @param y y coordinator
	 * @return
	 */

	public boolean matches(int x, int y) {
		/*If the arguments equal to the coordinates of the square itself return true*/
		return (this.x == x && this.y == y);
	}

}
