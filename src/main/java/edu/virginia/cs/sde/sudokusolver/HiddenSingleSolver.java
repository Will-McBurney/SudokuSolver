package edu.virginia.cs.sde.sudokusolver;


public class HiddenSingleSolver implements OneStepSolver {

	@Override
	public String solveOneStep(SudokuGrid g) {
		int gridSize = g.getGridSize();
		for (int x = 0; x < gridSize; x++) { //row index
			for (int y = 0; y < gridSize; y++) { //col index
				Cell c = g.getCell(x, y);
				if (c.isSolved()) {
					continue;
				} else if (c.getPossibilities().size() == 1) {
					int solution = c.getPossibilities().iterator().next();
					c.solve(solution);
					return "Hidden Single Found: Set cell [" + x + ", " + y + "] to " + solution; 
				}
			}
		}	
		
		return "";
	}

}
