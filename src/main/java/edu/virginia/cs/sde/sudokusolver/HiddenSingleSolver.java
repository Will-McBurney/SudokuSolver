package edu.virginia.cs.sde.sudokusolver;


public class HiddenSingleSolver implements OneStepSolver {

	@Override
	public String solveOneStep(SudokuGrid g) {	
		for (BoxContainer box : g.getBoxes()) {
			for (int i = 1; i <= g.getGridSize(); i++) {
				if (!box.getMissing().contains(i)) {
					continue;
				}
				Cell c = getOnlyCellWithValue(i, box);
				if (c != null) {
					c.solve(i);
					return "OnlyPossible: Cell [" + c.getRowNumber() + ", " + c.getColNumber() + "] was only possible " + i + " in Box " + c.getBoxNumber();
				}
			}
		}
		
		for (RowContainer row : g.getRows()) {
			for (int i = 1; i <= g.getGridSize(); i++) {
				if (!row.getMissing().contains(i)) {
					continue;
				}
				Cell c = getOnlyCellWithValue(i, row);
				if (c != null) {
					c.solve(i);
					return "OnlyPossible: Cell [" + c.getRowNumber() + ", " + c.getColNumber() + "] was only possible " + i + " in Row " + c.getRowNumber();
				}
			}
		}
		
		for (ColumnContainer col : g.getColumns()) {
			for (int i = 1; i <= g.getGridSize(); i++) {
				if (!col.getMissing().contains(i)) {
					continue;
				}
				Cell c = getOnlyCellWithValue(i, col);
				if (c != null) {
					c.solve(i);
					return "OnlyPossible: Cell [" + c.getRowNumber() + ", " + c.getColNumber() + "] was only possible " + i + " in Column " + c.getColNumber();
				}
			}
		}
		
		return null;
	}
	
	private Cell getOnlyCellWithValue(int value, SudokuContainer container) {
		int count = 0;
		Cell candidate = null;
		for (Cell cell : container.getCells()) {
			if (cell.isSolved()) {
				continue;
			}
			if (cell.getPossibilities().contains(value)) {
				count += 1;
				if (count > 1) { //found more than 1
					return null;
				}
				candidate = cell;
			}
		}
		
		return candidate;
	}
	
}
