package sudokusolver.util.solver;

import sudokusolver.util.grid.SudokuGrid;

public interface OneStepSolver {
	public String solveOneStep(SudokuGrid g);
}
