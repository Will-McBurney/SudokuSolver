package edu.virginia.cs.sde.sudokusolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
	
	private static boolean printAll = false;
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Invalid Arguments: you didn't include any Sudoku file to solve.");	
		}
		
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-print")) {
				printAll = true;
			}
		}
		
		SudokuReaderFactory srf = new SudokuReaderFactory();
		try {
			readAndSolve( srf.getSudokuReader(args[0]) );
		} catch (FileNotFoundException e) {
			System.out.println("File not found! - " + args[0]);
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error when trying to open - " + args[0]);
			System.exit(0);
		}
	}

	private static void readAndSolve(SudokuReader reader) {
		List<OneStepSolver> solvers = SettingsSingleton.getInstance().getSolvers();
		SudokuGrid grid = reader.read();
		
		
		System.out.println(grid.getGridAsString() + "\n");
		
		int solverIndex = 0;
		while(!grid.isSolved() && solverIndex < solvers.size()) {
			String result = solvers.get(solverIndex).solveOneStep(grid);
			if (result == null) {
				solverIndex++;
			} else {
				System.out.println(result);
				if (printAll) {
					System.out.println(grid.getGridAsString() + "\n");
				}
				solverIndex = 0;
			}
			
		}
		System.out.println("Final Result:");
		System.out.println("\n" + grid.getGridAsString() + "\n");
	}

}
