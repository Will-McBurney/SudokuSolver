package edu.virginia.cs.sde.sudokusolver;


public class RowContainer extends SudokuContainer {
	public final int rowNumber;
	
	public RowContainer(int rowNumber) {
		SettingsSingleton s = SettingsSingleton.getInstance();
		int gridSize = s.getGridSize();
		
		if (rowNumber < 0 || rowNumber >= gridSize) {
			throw new IllegalArgumentException("Error: Tried to create a row with index: " + rowNumber + " in a grid of size: " + gridSize);
		}
		
		this.rowNumber = rowNumber;
	}
	
	@Override
	public void addCell(Cell c) {
		if (c.getRowNumber() != rowNumber) {
			throw new IllegalArgumentException("Error: Added a cell to a non matching RowContainer.\n\tRow Number: " + rowNumber + 
					"\n\tCell: " + c);
		}		
		super.addCell(c);
	}
	
	@Override
	public String toString() {
		return "Row " + rowNumber + ": " + super.toString();
	}

	@Override
	public String getContainerType() {
		// TODO Auto-generated method stub
		return "Row";
	}

	@Override
	public int getContainerID() {
		// TODO Auto-generated method stub
		return rowNumber;
	}
}
