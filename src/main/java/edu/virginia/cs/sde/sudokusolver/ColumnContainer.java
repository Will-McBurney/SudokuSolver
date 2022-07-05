package edu.virginia.cs.sde.sudokusolver;

public class ColumnContainer extends SudokuContainer {
	public final int colNumber;
	
	public ColumnContainer(int colNumber) {
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		
		if (colNumber < 0 || colNumber >= gridSize) {
			throw new IllegalArgumentException("Error: Tried to create a row with index: " + colNumber + " in a grid of size: " + gridSize);
		}
		
		this.colNumber = colNumber;
	}
	
	@Override
	public void addCell(Cell c) {
		if (c.getColNumber() != colNumber) {
			throw new IllegalArgumentException("Error: Added a cell to a non matching ColContainer.\n\tCol Number: " + colNumber + 
					"\n\tCell: " + c);
		}		
		super.addCell(c);
	}
	
	@Override
	public String toString() {
		return "Col " + colNumber + ": " + super.toString();
	}

	@Override
	public String getContainerType() {
		// TODO Auto-generated method stub
		return "Column";
	}

	@Override
	public int getContainerID() {
		// TODO Auto-generated method stub
		return colNumber;
	}
}
