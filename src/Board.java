/**
 * @author    Roy Guo
 * @date      8 Nov 2012
 * @link        
 *  
 * @usage
 *     This class will be instantiated in the chess frame. When user trigger the start event. 
 *     Board board = new Board();
 *     board.init()
 *     board.play(Square A, Square B)
 * 
 */

import java.util.LinkedList;

public class Board {

	private Square[][] squares = new Square[8][8];

	/**
	 * Constructor
	 */
	public Board() {
		this.init();
	}
	
	/**
	 * Initialise the board, create a two dimension matrix of Squares
	 */

	public void init() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new Square(i + 1, j + 1,false);//Pass the edited x and y to Square
			}
		}
	}
	/**
	 * 
	 * @param start1 The start square of this travel
	 * @param end1 The end square of this travel
	 * @return A two dimensional LinkedList, within which are linkedlists of squares.
	 */
	

	public LinkedList<LinkedList<Square>> run(Square start1, Square end1) {

		/*get the references of our start square and end square */
		Square start = getSquare(start1.getX(), start1.getY()); 
		Square end = getSquare(end1.getX(), end1.getY());
		
		/*The linkedlist for storing our solutions */
		LinkedList<LinkedList<Square>> results = new LinkedList<LinkedList<Square>>();
		
		/*The linkedlist used as a queue */
		LinkedList<LinkedList<Square>> queue = new LinkedList<LinkedList<Square>>();
		
		boolean pathFound = false;
		
		/*   initialise a solution     */
		LinkedList<Square> firstPath = new LinkedList<Square>();
		
		/*add the start square to this solution since the start square should always be the first one in a solution list */
		firstPath.add(start); 
		
		/*add it to the queue */
		queue.add(firstPath);
		
		/*Keep looping until all tasks in the queue are finished     */
		while (!queue.isEmpty()) {
			/*Get the first task in the queue and remove it */
			LinkedList<Square> currentPath = queue.removeFirst();  
			
			/*Get the last square we approached in this task*/
			Square currentSquare = currentPath.getLast();
			
			/*If the solution is found, set the PathFound to true, then the it will keep looping but only remove unfinished tasks from the queue */
			if (currentSquare == end) {
				results.add(currentPath);
				pathFound = true;
			}
			/*Otherwise, if solution is not found, */
			if (!pathFound) {
				
				/*Loop through all possible next legal squares*/
				for (Square nextLegalSquare : this.getLegalSquares(currentSquare)) {
					
					/*If the next legal square has not been visited */
					if (nextLegalSquare.isVisited() == false) { //this new way should not be visited before
						LinkedList<Square> newPath = new LinkedList<Square>(); // a new way is found
						newPath.addAll(currentPath);    // clone the current path
						newPath.add(nextLegalSquare); // add the next step to the new path
						queue.addLast(newPath);   //a new unfinished task is added to the queue

					}
				}
			/*When all new ways of current tasks are found, we will move on to other squares. The current square won't show in latter loops */
				currentSquare.setVisited(true);
			}

		}

		return results;

	}
	
	/**
	 * 
	 * @param currentSquare The reference of current square
	 * @return list of possible destinations
	 */

	public LinkedList<Square> getLegalSquares(Square currentSquare) {
		/*A storing list*/
		LinkedList<Square> nextSquares = new LinkedList<Square>();
		/*loop through all squares on the board, this algorithm can be improved */
		for (Square[] cols : squares) {
			for (Square destination : cols) {
				int xMoveDistance = destination.getX() - currentSquare.getX(); //calculate the difference of X
				int yMoveDistance = destination.getY() - currentSquare.getY(); //calculate the difference of Y
				
		/*A legal move will always get a result of either -2 or 2 when multiplying the differences*/			
				if (Math.abs(xMoveDistance * yMoveDistance) == 2) {
					//add the legal destination to the storing list
					nextSquares.add(destination);

				}
			}
		}
		return nextSquares;
	}
	
	/**
	 * 
	 * @param x The value of x on the board
	 * @param y The value of y on the board
	 * @return the reference of the board on the matrix we initialised in the contractor
	 */

	public Square getSquare(int x, int y) {
		for (Square[] cols : squares) {
			for (Square square : cols) {
				if (square.matches(x, y))  // find the square in the matrix with the same values of x and y in the argument
					return square;
			}
		}
		return null;

	}

}
