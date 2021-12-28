package sudokusolver.util.grid.reader.demo;

import sudokusolver.util.SettingsSingleton;
import sudokusolver.util.grid.SudokuGrid;
import sudokusolver.util.grid.reader.Sudoku6x6TextReader;
import sudokusolver.util.grid.reader.Sudoku9x9TextReader;
import sudokusolver.util.solver.HiddenSingleSolver;
import sudokusolver.util.solver.OneStepSolver;
import sudokusolver.util.solver.OnlyPossibleCellSolver;

public class Sudoku6x6TextReaderDemo {
	public static void main(String[] args) {
		SettingsSingleton settings = SettingsSingleton.getInstance();
		settings.setGridSize(6);
		Sudoku6x6TextReader s = new Sudoku6x6TextReader("6x6easy.txt");
		SudokuGrid g = s.read();
		System.out.println(g.getGridAsString());
		System.out.println(g);
		OneStepSolver only = new OnlyPossibleCellSolver();
		OneStepSolver single = new HiddenSingleSolver();
		String st = only.solveOneStep(g);
		while(st != "") {
			System.out.println(st);
			st = only.solveOneStep(g);
			if (st == "") {
				st = single.solveOneStep(g);
			}
		}
		System.out.println(g);
	}
	
}
