package sudokusolver.util.solver;

import java.util.ArrayList;
import java.util.List;

import sudokusolver.util.SettingsSingleton;
import sudokusolver.util.grid.BoxContainer;
import sudokusolver.util.grid.Cell;
import sudokusolver.util.grid.ColumnContainer;
import sudokusolver.util.grid.RowContainer;
import sudokusolver.util.grid.SudokuContainer;
import sudokusolver.util.grid.SudokuGrid;

public class BoxLineReductionSolver implements OneStepSolver {

	@Override
	public String solveOneStep(SudokuGrid g) {
		for (RowContainer row : g.getRows()) {
			for (int missingValue : row.getMissing()) {
				List<Cell> cells = getCellsWithPossibility(row, missingValue);
				if (inSameBox(cells)) {
					int boxNumber = cells.get(0).getBoxNumber();
					if (removePossibilitiesExcludingRow(missingValue, row.rowNumber, g.getBoxes().get(boxNumber))) {
						return "BoxLineReduction: " + missingValue + " in Box " + boxNumber + " must be in Row " + row.rowNumber + "; removed " +
								missingValue + " from all other cells in the Box.";
					}
				}
			}
		}
		
		for (ColumnContainer col : g.getColumns()) {
			for (int missingValue : col.getMissing()) {
				List<Cell> cells = getCellsWithPossibility(col, missingValue);
				if (inSameBox(cells)) {
					int boxNumber = cells.get(0).getBoxNumber();
					if (removePossibilitiesExcludingColumn(missingValue, col.colNumber, g.getBoxes().get(boxNumber))) {
						return "BoxLineReduction: " + missingValue + " in Box " + boxNumber + " must be in Column " + col.colNumber + "; removed " +
								missingValue + " from all other cells in the Box.";
					}
				}
			}
		}
		return "";
	}
	
	private List<Cell> getCellsWithPossibility(SudokuContainer container, int possibility) {
		List<Cell> cells = new ArrayList<Cell>();
		for (Cell cell : container.getCells()) {
			if (!cell.isSolved() && cell.getPossibilities().contains(possibility)) {
				cells.add(cell);
			}
		}
		return cells;
	}
	
	private boolean inSameBox(List<Cell> cells) {
		if (cells.isEmpty()) {
			return false;
		}
		int boxNumber = cells.get(0).getBoxNumber();
		for (Cell cell : cells) {
			if (boxNumber != cell.getBoxNumber()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean removePossibilitiesExcludingRow(int value, int rowNumber, BoxContainer container) {
		boolean progress = false;
		for (Cell cell : container.getCells()) {
			if (!cell.isSolved() && cell.getRowNumber() != rowNumber && cell.getPossibilities().contains(value)) {
				progress = true;
				cell.removePossibility(value);
			}		
		}
		return progress;
	}
	
	private boolean removePossibilitiesExcludingColumn(int value, int colNumber, BoxContainer container) {
		boolean progress = false;
		for (Cell cell : container.getCells()) {
			if (!cell.isSolved() && cell.getColNumber() != colNumber && cell.getPossibilities().contains(value)) {
				progress = true;
				cell.removePossibility(value);
			}
		}
		return progress;
	}

}
