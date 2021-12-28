package sudokusolver.util.grid;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import sudokusolver.util.SettingsSingleton;
import sudokusolver.util.exceptions.IllegalCellValueException;
import sudokusolver.util.exceptions.InvalidSolutionException;
import sudokusolver.util.exceptions.UnsolvedCellException;

public class Cell extends Observable{	
	private int row; //which row the cell is in
	private int col; //which column the cell is in
	private int box; //which box the cell is in
	private boolean isSolved; // whether or not the box is solved yet
	private Set<Integer> possibilities; //a solved cell has a null possibilities value
	private int solution; // -1 when unsolved
	
	/**
	 * Constructor for an unsolved cell
	 * @param r - row number
	 * @param c - col number
	 * @param b - box number
	 */
	public Cell(int r, int c, int b) {
		//getGridSize
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		
		if (r < 0 || r >= gridSize) {
			throw new IllegalArgumentException("Error: Invalid row number in Cell constructor: " + r + " - gridSize: " + gridSize);
		}
		
		if (c < 0 || c >= gridSize) {
			throw new IllegalArgumentException("Error: Invalid row number in Cell constructor: " + r + " - gridSize: " + gridSize);
		}
		
		if (b < 0 || b >= gridSize) {
			throw new IllegalArgumentException("Error: Invalid row number in Cell constructor: " + r + " - gridSize: " + gridSize);
		}
		
		row = r;
		col = c;
		box = b;
		possibilities = new HashSet<Integer>();
		
		
		
		for(int i = 1; i <= gridSize; i++) {
			possibilities.add(i);
		}
		isSolved = false;
		solution = -1;
	}
	
	/**
	 * Constructor for a solved (i.e. - initially given) box
	 * @param r - row number
	 * @param c - col number
	 * @param b - box number
	 * @param solution - the boxes initial solved value
	 */
	public Cell(int r, int c, int b, int solution) {
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		
		if (solution < 1 || solution > gridSize) {
			throw new IllegalCellValueException("Error: Illegal Cell Value Used when constructing Cell. Should be 1 <= value <= " + gridSize + "; Your value was " + solution);
		}
		
		row = r;
		col = c;
		box = b;
		possibilities = null;
		isSolved = true;
		this.solution = solution;
	}
	
	//Getters 
	
	public int getRowNumber() {
		return row;
	}
	
	public int getColNumber() {
		return col;
	}
	
	public int getBoxNumber() {
		return box;
	}
	
	public boolean isSolved() {
		return isSolved;
	}
		
	public Set<Integer> getPossibilities() {
		return possibilities;
	}
	
	
	/**
	 * Check whether it's possible for a cell to be set to a given number
	 * @param num - the number to check
	 * @return True if it is possible, false if not.
	 */
	public boolean isPossible(int num) {
		if (isSolved && num == solution) {
			return true;
		} else if (isSolved) {
			return false;
		}
		return possibilities.contains(num);
	}
	
	/**
	 * Removes a particular possibility from the cell
	 * @param num
	 */
	public void removePossibility(int num) {
		if (isSolved && num == solution) {
			throw new InvalidSolutionException("Attempted to remove a solution from a solved cell's possibilities: " + this);
		} else if (!isSolved) {
			possibilities.remove(num);
			if (possibilities.isEmpty()) { // If this leaves the cell unfillable
				throw new InvalidSolutionException("Error! A cell has no possibilities remaining, but is still unsolved: " + this);
			}
		}
	}
	
	/**
	 * Assign the cells value to a specific solution - Note that this
	 * @param solution
	 */
	public void solve(int solution) {
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		
		if (solution < 1 || solution > gridSize) {
			throw new IllegalCellValueException("Error: Illegal Cell Value Used when solving Cell. Should be 1 <= value <= " + gridSize + "; Your value was " + solution);
		}
		
		if (isSolved) {
			throw new InvalidSolutionException("Error: Tried to solve an already solved cell: " + this);
		}
		
		if (!possibilities.contains(solution)) {
			throw new InvalidSolutionException("Error: Tried to solve a cell with an invalid solution (" + solution + ": " + this);
		}
		possibilities = null;
		isSolved = true;
		this.solution = solution;
		setChanged();
		this.notifyObservers(solution);
	}
	
	/**
	 * Gets solution if one exists, but throws an UnsolvedCellException if the cell hasn't been solved yet
	 * (Safety check this function with isSolved() boolean)
	 * @return the solution if the cell is solved.
	 */
	public int getSolution() {
		if (!isSolved) {
			throw new UnsolvedCellException("Error: Tried to get the solution from an unsolved cell:\n" + this);
		}
		
		return solution;
	}
	
	public String getCoordinates() {
		return "[" + row + ", " +  col + "]";
	}
	
	public String toString() {
		if (isSolved) {
			return "SOLVED:   Cell - row: " + row + "\tcol: " + col + "\tbox: " + box + "\tSolution: " + solution;
		} else {
			return "UNSOLVED: Cell - row: " + row + "\tcol: " + col + "\tbox: " + box + "\tPossible: " + possibilities;
		}
	}
}
