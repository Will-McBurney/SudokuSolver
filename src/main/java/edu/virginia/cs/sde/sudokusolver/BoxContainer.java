package edu.virginia.cs.sde.sudokusolver;


public class BoxContainer extends SudokuContainer {
	public final int boxNumber;
	
	public BoxContainer(int boxNumber) {
		Settings s = Settings.getInstance();
		int gridSize = s.getGridSize();
		
		if (boxNumber < 0 || boxNumber >= gridSize) {
			throw new IllegalArgumentException("Error: Tried to create a row with index: " + boxNumber + " in a grid of size: " + gridSize);
		}
		
		this.boxNumber = boxNumber;
	}
	
	@Override
	public void addCell(Cell c) {
		if (c.getBoxNumber() != boxNumber) {
			throw new IllegalArgumentException("Error: Added a cell to a non matching BoxContainer.\n\tRow Number: " + boxNumber + 
					"\n\tCell: " + c);
		}		
		super.addCell(c);
	}
	
	@Override
	public String toString() {
		return "Box " + boxNumber + ": " + super.toString();
	}

	@Override
	public String getContainerType() {
		// TODO Auto-generated method stub
		return "Box";
	}

	@Override
	public int getContainerID() {
		// TODO Auto-generated method stub
		return boxNumber;
	}
}
