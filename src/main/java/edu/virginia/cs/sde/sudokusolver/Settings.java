package edu.virginia.cs.sde.sudokusolver;

import java.util.ArrayList;
import java.util.List;

public class Settings {
	private static Settings s;
	private int gridSize;
	private List<OneStepSolver> solvers;
	
	private Settings() {		
		gridSize = -1;
		addCurrentSolvers();
	}

	public static Settings getInstance() {
		if (s == null)
			s = new Settings();
		return s;
	}
	
	public void setGridSize(int size) {
		gridSize = size;
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public List<OneStepSolver> getSolvers() {
		return solvers;
	}
	

	private void addCurrentSolvers() {
		solvers = new ArrayList<>();
		solvers.add(new NakedSingleSolver());
		solvers.add(new HiddenSingleSolver());
		solvers.add(new PointingLineSolver());
		solvers.add(new BoxLineReductionSolver());
		solvers.add(new NakedPairSolver());
		solvers.add(new HiddenPairSolver());
		solvers.add(new NakedTripleSolver());
		
	}

}
