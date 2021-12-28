package sudokusolver.util.solver;

import java.util.ArrayList;
import java.util.List;

import sudokusolver.util.grid.BoxContainer;
import sudokusolver.util.grid.Cell;
import sudokusolver.util.grid.SudokuGrid;

public class PointingLineSolver implements OneStepSolver{

	@Override
	public String solveOneStep(SudokuGrid g) {
		for (BoxContainer b : g.getBoxes()) {
			for (int target : b.getMissing()) {
				int boxNumber = b.boxNumber;
				List<Cell> cellsWithTarget = getCellsWith(target, b);			
				
				int rowNumber = cellsWithTarget.get(0).getRowNumber();
				int colNumber = cellsWithTarget.get(0).getColNumber();
				if (cellsSameRow(cellsWithTarget) && removeFromRowExcludingBox(g, rowNumber, boxNumber, target)) {
					return "PointingLine: Found pointing items in Box " + boxNumber + " for value " + target +
							"; Removed all other " + target + "s in Row " + rowNumber;
				}
				if (cellsSameColumn(cellsWithTarget) && removeFromColumnExcludingBox(g, colNumber, boxNumber, target)) {
					return "PointingLine: Found pointing items in Box " + boxNumber + " for value " + target +
							"; Removed all other " + target + "s in Column " + colNumber;
				}
			}
		}
		return "";
	}

	private List<Cell> getCellsWith(int target, BoxContainer b) {
		List<Cell> out = new ArrayList<Cell>();
		for (Cell cell : b.getCells()) {
			if (!cell.isSolved() && cell.isPossible(target)) {
				out.add(cell);
			}
		}
		return out;
	}
	
	private boolean cellsSameRow(List<Cell> cells) {
		if (cells.isEmpty()) {
			throw new IllegalArgumentException("Error: cellsSameRow requires a non-empty input list.");
		}
		int targetRow = cells.get(0).getRowNumber();
		for (Cell cell : cells) {
			if (cell.getRowNumber() != targetRow) {
				return false;
			}
		}
		return true;
	}
	
	private boolean cellsSameColumn(List<Cell> cells) {
		if (cells.isEmpty()) {
			throw new IllegalArgumentException("Error: cellsSameCol requires a non-empty input list.");
		}
		int targetCol = cells.get(0).getColNumber();
		for (Cell cell : cells) {
			if (cell.getColNumber() != targetCol) {
				return false;
			}
		}
		return true;
	}
	
	private boolean removeFromRowExcludingBox(SudokuGrid grid, int rowNumber, int boxNumber, int target) {
		boolean progress = false;
		for (Cell cell : grid.getRows().get(rowNumber).getCells()) {
			if (!cell.isSolved() && cell.getBoxNumber() != boxNumber && cell.isPossible(target)) {
				progress = true;
				cell.removePossibility(target);
			}
		}
		return progress;
	}
	
	private boolean removeFromColumnExcludingBox(SudokuGrid grid, int colNumber, int boxNumber, int target) {
		boolean progress = false;
		for (Cell cell : grid.getColumns().get(colNumber).getCells()) {
			if (!cell.isSolved() && cell.getBoxNumber() != boxNumber && cell.isPossible(target)) {
				progress = true;
				cell.removePossibility(target);
			}
		}
		return progress;
	}

}
