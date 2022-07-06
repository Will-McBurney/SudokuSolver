package edu.virginia.cs.sde.sudokusolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class NakedPairSolver implements OneStepSolver {

	@Override
	public String solveOneStep(SudokuGrid g) {
		for (BoxContainer box : g.getBoxes()) {
			List<Cell[]> pairs = getNakedPairs(box);
			if (pairs.isEmpty()) {
				continue;
			} else {
				for (Cell[] pair : pairs) {
					if (removesPossibilities(pair, box)) {
						return "NakedPair: Cell " + pair[0].getCoordinates() + " and Cell " + pair[1].getCoordinates() + " are a naked pair for " +
								pair[0].getPossibilities() + " - removed those two values from all other Cells in Box " + pair[0].getBoxNumber();
					}
				}
			}
		}
		
		for (RowContainer row : g.getRows()) {
			List<Cell[]> pairs = getNakedPairs(row);
			if (pairs.isEmpty()) {
				continue;
			} else {
				for (Cell[] pair : pairs) {
					if (removesPossibilities(pair, row)) {
						return "NakedPair: Cell " + pair[0].getCoordinates() + " and Cell " + pair[1].getCoordinates() + " are a naked pair for " +
								pair[0].getPossibilities() + " - removed those two values from all other Cells in Row " + pair[0].getRowNumber();
					}
				}
			}
		}
		
		for (ColumnContainer col : g.getColumns()) {
			List<Cell[]> pairs = getNakedPairs(col);
			if (pairs.isEmpty()) {
				continue;
			} else {
				for (Cell[] pair : pairs) {
					if (removesPossibilities(pair, col)) {
						return "NakedPair: Cell " + pair[0].getCoordinates() + " and Cell " + pair[1].getCoordinates() + " are a naked pair for " +
								pair[0].getPossibilities() + " - removed those two values from all other Cells in Row " + pair[0].getColNumber();
					}
				}
			}
		}
		
		return null;
	}
	
	private List<Cell[]> getNakedPairs(SudokuContainer container) {
		List<Cell[]> out = new ArrayList<Cell[]>();
		for (Cell first : container.getCells()) {
			if (first.isSolved()) {
				continue;
			}
			if (first.getPossibilities().size() == 2) {
				for (Cell second : container.getCells()) {
					if (first == second) {
						continue;
					} else if (first.getPossibilities().equals(second.getPossibilities())) {
						Cell[] pair = {first, second};
						out.add(pair);
					}
				}
			}
		}
		return out;
	}
	
	private boolean removesPossibilities(Cell[] pair, SudokuContainer container) {
		boolean progress = false;
		Set<Integer> valuePair = pair[0].getPossibilities();
		Iterator<Integer> iterator = valuePair.iterator();
		int first = iterator.next();
		int second = iterator.next();
		for (Cell c : container.getCells()) {
			if (c.isSolved() || c == pair[0] || c == pair[1]) {
				continue;
			}
			if (c.getPossibilities().contains(first) || c.getPossibilities().contains(second)) {
				c.removePossibility(first);
				c.removePossibility(second);
				progress = true;
			}
		}
		
		return progress;
	}
	
}
