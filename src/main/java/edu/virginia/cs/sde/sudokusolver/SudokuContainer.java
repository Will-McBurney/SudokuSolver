package edu.virginia.cs.sde.sudokusolver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


/**
 * This class models an abstract Sudoku Container.
 * 
 * See RowContainer, ColumnContainer, and GridContainer
 * @author pm8fc
 *
 */

@SuppressWarnings("deprecation")
public abstract class SudokuContainer implements Observer {
	protected List<Cell> cells;
	protected Set<Integer> missing;
	
	/**
	 * Constructor - creates an empty CellContainer
	 */
	public SudokuContainer() {
		cells = new ArrayList<Cell>();
		
		missing = new HashSet<Integer>();
		
		//getGridSize
		Settings s = Settings.getInstance();
		int gridSize = s.getGridSize();
		
		for(int i = 1; i <= gridSize; i++) {
			missing.add(i);
		}
	}
	
	/**
	 * Gets the cells in the container as a List
	 * @return
	 */
	public List<Cell> getCells() {
		return cells;
	}

	/**
	 * Returns whether the container is a Column, Row, or Box
	 * @return
	 */
	public abstract String getContainerType();
	
	/**
	 * Returns the integer id of the container (such as the Row#)
	 * @return
	 */
	public abstract int getContainerID();

	/**
	 * Returns a safe copy of the list of missing values from the container
	 * Missing is empty when ever cell in the container is solved.
	 * @return
	 */
	public Set<Integer> getMissing() {
		Set<Integer> out = new HashSet<>();
		out.addAll(missing);
		return out;
	}


	/**
	 * Returns if the container is solved
	 */
	public boolean isSolved() {
		/**
		 * Returns if the container is solved
		 */
		return missing.size() == 0;
	}


	/**
	 * Adds a new cell to the Container.
	 * 
	 * If the cell is solved, this results in the possibilities being removed from other cells
	 * @param c
	 */
	public void addCell(Cell c) {
		Settings s = Settings.getInstance();
		int gridSize = s.getGridSize();
		
		// Is our container full?
		if (cells.size() + 1 > gridSize) {
			throw new IllegalStateException("Tried to add to a full Sudoku Container. Cell to be added: " + c + "\nContainer: "  + this);
		}
		
		// Check if a cell with that address already exists
		for (Cell cell : cells) {
			if (cell.getRowNumber() == c.getRowNumber() &&
				cell.getColNumber() == c.getColNumber() &&
				cell.getBoxNumber() == c.getBoxNumber()) {
				throw new IllegalArgumentException("Error, attempted to add a Cell to a container with another cell at the same address: "+
						"\n\tNew Cell: " + c + "\n\tExisting Cell: " + cell + "\n\tContainer:" + this);
			}
		}
		
		
		if (c.isSolved()) { // Remove possibility from other Cells in this container
			if (!missing.contains(c.getSolution())) { //If value has already been found
				throw new InvalidSolutionException("Error, tried to add a cell to a container that already has that solution: +\n" +
						"Cell - " + c + "\nCell Container: " + this);
			}
			
			missing.remove(c.getSolution()); // value has been found in this cell			
			
			for (Cell cell : cells) { // remove all possibilities of the solution from other cells already in the container
				cell.removePossibility(c.getSolution());
			}
		} else { // Is not solved
			for (Cell cell : cells) {
				if (cell.isSolved()) { // remove all possibilities of solved cells in this container
					c.removePossibility(cell.getSolution());
				}
			}
		}
		cells.add(c);
		c.addObserver(this); //monitor c for solutions
	}
	
	
	@Override
	public void update(Observable obj, Object arg) {
		int i = (Integer) arg;
		if (!missing.contains(i)) { //If value has already been found
			throw new InvalidSolutionException("Error, cell was solved in a container that already has that solution: +\n" +
					"Cell - " + obj + "\nCell Container: " + this);
		}
		missing.remove(i);
		for (Cell cell : cells) { // remove all possibilities of the solution from other cells already in the container
			if (!cell.isSolved()) {
				cell.removePossibility(i);
			}
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (missing.isEmpty()) {
			sb.append("Solved: ");
		} else {
			sb.append("Missing:\t" + missing);
		}
		sb.append("\nCells:");
		for (Cell cell : cells) {
			sb.append("\n\t" + cell);
		}	
		
		return sb.toString();
	}
}
