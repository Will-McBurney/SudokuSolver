package edu.virginia.cs.sde.sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class NakedTripleSolver implements OneStepSolver{

	@Override
	public String solveOneStep(SudokuGrid g) {
		for (BoxContainer box : g.getBoxes()) {
			List<Cell[]> triplets = getNakedTriplets(box);
			if (triplets.isEmpty()) {
				continue;
			} else {
				for (Cell[] triplet : triplets) {
					if (removesPossibilities(triplet, box)) {
						Set<Integer> valueTriplet = new HashSet<Integer>();
						for (int value : triplet[0].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[1].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[2].getPossibilities()) {
							valueTriplet.add(value);
						}
						return "NakedTriplet: Cell " + triplet[0].getCoordinates() + ", " + triplet[1].getCoordinates() + ", and " + 
								triplet[2].getCoordinates() + " are a naked triplet for " +	valueTriplet + 
								" - removed those two values from all other Cells in Box " + triplet[0].getBoxNumber();
					}
				}
			}
		}
		
		for (RowContainer row : g.getRows()) {
			List<Cell[]> triplets = getNakedTriplets(row);
			if (triplets.isEmpty()) {
				continue;
			} else {
				for (Cell[] triplet : triplets) {
					if (removesPossibilities(triplet, row)) {
						Set<Integer> valueTriplet = new HashSet<Integer>();
						for (int value : triplet[0].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[1].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[2].getPossibilities()) {
							valueTriplet.add(value);
						}
						return "NakedTriplet: Cell " + triplet[0].getCoordinates() + ", " + triplet[1].getCoordinates() + ", and " + 
								triplet[2].getCoordinates() + " are a naked triplet for " +	valueTriplet + 
								" - removed those two values from all other Cells in Row " + triplet[0].getBoxNumber();
					}
				}
			}
		}
		
		for (ColumnContainer col : g.getColumns()) {
			List<Cell[]> triplets = getNakedTriplets(col);
			if (triplets.isEmpty()) {
				continue;
			} else {
				for (Cell[] triplet : triplets) {
					if (removesPossibilities(triplet, col)) {
						Set<Integer> valueTriplet = new HashSet<Integer>();
						for (int value : triplet[0].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[1].getPossibilities()) {
							valueTriplet.add(value);
						}
						for (int value : triplet[2].getPossibilities()) {
							valueTriplet.add(value);
						}
						return "NakedTriplet: Cell " + triplet[0].getCoordinates() + ", " + triplet[1].getCoordinates() + ", and " + 
								triplet[2].getCoordinates() + " are a naked triplet for " +	valueTriplet + 
								" - removed those two values from all other Cells in Col " + triplet[0].getBoxNumber();
					}
				}
			}
		}
		
		return "";
	}
	
	private List<Cell[]> getNakedTriplets(SudokuContainer container) {
		List<Cell[]> out = new ArrayList<Cell[]>();
		for (Cell first : container.getCells()) {
			if (first.isSolved()) {
				continue;
			}
			if (first.getPossibilities().size() == 3) {
				Cell[] triplet = new Cell[3];
				int count = 1;
				triplet[0] = first;
				for (Cell second : container.getCells()) {
					if (second == first) {
						continue;
					}
					if (matchesPossibilities(first.getPossibilities(), second)) {
						if (count == 1) {
							triplet[1] = second;
							count++;
						} else if (count == 2) {
							triplet[2] = second;
							count++;
						} else {
							throw new IllegalStateException("Error: Found unsolvable grid while checking for Naked Triplets");
						}
					}
				}
				if (count == 3) {
					out.add(triplet);
				}
			} else if (first.getPossibilities().size() == 2) {
				for (int missingValue : container.getMissing()) {
					Cell[] triplet = new Cell[3];
					int count = 1;
					triplet[0] = first;
					if (first.getPossibilities().contains(missingValue)) {
						continue;
					}
					Set<Integer> newPossibilities = new HashSet<Integer>();
					for (int value : first.getPossibilities()) {
						newPossibilities.add(value);
					}
					newPossibilities.add(missingValue);
					for (Cell second : container.getCells()) {
						if (second == first) {
							continue;
						}
						if (matchesPossibilities(newPossibilities, second)) {
							if (count == 1) {
								triplet[1] = second;
								count++;
							} else if (count == 2) {
								triplet[2] = second;
								count++;
							} else {
								throw new IllegalStateException("Error: Found unsolvable grid while checking for Naked Triplets");
							}
						}
					}
					if (count == 3) {
						out.add(triplet);
					}
				}
			}
		}
		return out;
	}
	
	private boolean matchesPossibilities(Set<Integer> possibilities, Cell cell) {
		if (cell.isSolved()) {
			return false;
		}
		SettingsSingleton settings = SettingsSingleton.getInstance();
		int size = settings.getGridSize();
		
		for (int i = 1; i <= size; i++) {
			if (!possibilities.contains(i) && cell.getPossibilities().contains(i)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean removesPossibilities(Cell[] triplet, SudokuContainer container) {
		boolean progress = false;
		Set<Integer> valueTriplet = new HashSet<Integer>();
		for (int value : triplet[0].getPossibilities()) {
			valueTriplet.add(value);
		}
		for (int value : triplet[1].getPossibilities()) {
			valueTriplet.add(value);
		}
		for (int value : triplet[2].getPossibilities()) {
			valueTriplet.add(value);
		}
		Iterator<Integer> iterator = valueTriplet.iterator();
		int first = iterator.next();
		int second = iterator.next();
		int third = iterator.next();
		for (Cell c : container.getCells()) {
			if (c.isSolved() || c == triplet[0] || c == triplet[1] || c == triplet[2]) {
				continue;
			}
			if (c.getPossibilities().contains(first) || c.getPossibilities().contains(second) || c.getPossibilities().contains(third)) {
				c.removePossibility(first);
				c.removePossibility(second);
				c.removePossibility(third);
				progress = true;
			}
		}
		
		return progress;
	}

}
