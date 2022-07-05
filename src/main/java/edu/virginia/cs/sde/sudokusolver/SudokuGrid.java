package edu.virginia.cs.sde.sudokusolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;



public class SudokuGrid implements Observer {
	private List<RowContainer> rows;
	private List<ColumnContainer> columns;
	private List<BoxContainer> boxes;
	private int unsolved;
	private int size;
	
	/**
	 * Creates an empty Sudoku Grid of the size stored in the Singleton grid size field
	 */
	public SudokuGrid() {
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		size = gridSize;
		
		rows = new ArrayList<RowContainer>();
		columns = new ArrayList<ColumnContainer>();
		boxes = new ArrayList<BoxContainer>();
		
		for (int i = 0; i < gridSize; i++) {
			rows.add(new RowContainer(i));
			columns.add(new ColumnContainer(i));
			boxes.add(new BoxContainer(i));
		}
		
		unsolved = gridSize * gridSize;
	}
	
	
	public Cell getCell(int row, int col) {
		RowContainer r = rows.get(row);
		for (Cell c : r.getCells()) {
			if (c.getColNumber() == col) {
				return c;
			}
		}
		return null;
	}
	
	public int getGridSize() {
		return size;
	}
	
	public List<RowContainer> getRows() {
		return rows;
	}


	public List<ColumnContainer> getColumns() {
		return columns;
	}


	public List<BoxContainer> getBoxes() {
		return boxes;
	}


	public void addCell(Cell cell) {
		int r = cell.getRowNumber();
		int c = cell.getColNumber();
		int b = cell.getBoxNumber();
		
		rows.get(r).addCell(cell);
		columns.get(c).addCell(cell);
		boxes.get(b).addCell(cell);
		
		if(cell.isSolved()) {
			unsolved -= 1;
		}
	}
	
	public int unsolvedCellsRemaining() {
		return unsolved;
	}
	
	public boolean isSolved() {
		return unsolved == 0;
	}
	
	public void update(Observable subject, Object arg) {
		unsolved -= 1;
	}
	
	public String getGridAsString() {
		StringBuilder sb = new StringBuilder();
		for (RowContainer row : rows) {
			for (Cell c : row.getCells()) {
				if (c.isSolved()) {
					sb.append(c.getSolution());
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (RowContainer row : rows) {
			sb.append("Row: " + row.rowNumber + "\n");
			for (Cell c : row.getCells()) {
				sb.append("\t" + c.toString() + "\n");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
