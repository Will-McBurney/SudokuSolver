package edu.virginia.cs.sde.sudokusolver;

import java.util.ArrayList;
import java.util.List;

public class SettingsSingleton {
	private static SettingsSingleton s;
	private int gridSize;
	private List<OneStepSolver> solvers;
	
	private SettingsSingleton() {		
		gridSize = -1;
		addCurrentSolvers();
	}

	public static SettingsSingleton getInstance() {
		if (s == null)
			s = new SettingsSingleton();
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
		solvers.add(new HiddenSingleSolver());
		solvers.add(new OnlyPossibleCellSolver());
		solvers.add(new PointingLineSolver());
		solvers.add(new BoxLineReductionSolver());
		solvers.add(new NakedPairSolver());
		solvers.add(new HiddenPairSolver());
		solvers.add(new NakedTripleSolver());
		
	}

}
