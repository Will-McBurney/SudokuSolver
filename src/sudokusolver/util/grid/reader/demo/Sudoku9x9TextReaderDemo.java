package sudokusolver.util.grid.reader.demo;

import sudokusolver.util.SettingsSingleton;
import sudokusolver.util.grid.SudokuGrid;
import sudokusolver.util.grid.reader.Sudoku9x9TextReader;
import sudokusolver.util.solver.BoxLineReductionSolver;
import sudokusolver.util.solver.HiddenSingleSolver;
import sudokusolver.util.solver.NakedPairSolver;
import sudokusolver.util.solver.NakedTripleSolver;
import sudokusolver.util.solver.OneStepSolver;
import sudokusolver.util.solver.OnlyPossibleCellSolver;
import sudokusolver.util.solver.PointingLineSolver;

public class Sudoku9x9TextReaderDemo {
	public static void main(String[] args) throws InterruptedException {
		SettingsSingleton settings = SettingsSingleton.getInstance();
		settings.setGridSize(9);
		Sudoku9x9TextReader s = new Sudoku9x9TextReader("test9x9hard.txt");
		SudokuGrid g = s.read();
		System.out.println(g.getGridAsString());
		System.out.println(g);
		OneStepSolver only = new OnlyPossibleCellSolver();
		OneStepSolver single = new HiddenSingleSolver();
		OneStepSolver pair = new NakedPairSolver();
		OneStepSolver pointing = new PointingLineSolver();
		OneStepSolver triplet = new NakedTripleSolver();
		OneStepSolver boxLine = new BoxLineReductionSolver();
		String st = only.solveOneStep(g);
		while(st != "") {
			System.out.println(st);
			st = single.solveOneStep(g);
			if (st == "") {
				st = only.solveOneStep(g);
			}
			if (st == "") {
				st = pair.solveOneStep(g);
			}
			if (st == "") {
				st = pointing.solveOneStep(g);
			}
			if (st == "") {
				st = boxLine.solveOneStep(g);
			}
			if (st == "") {
				st = triplet.solveOneStep(g);
			}
			
		}
		System.out.println(g);
		System.out.println(g.getGridAsString());
	}
}
