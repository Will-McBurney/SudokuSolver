package sudokusolver.util.grid.demo;
import sudokusolver.util.grid.Cell;
import sudokusolver.util.SettingsSingleton;

public class SudokuCellDemo {
	public static void main(String[] args) {
		SettingsSingleton settings = SettingsSingleton.getInstance();		
		settings.setGridSize(9);
		
		Cell c0 = new Cell(0, 0, 0);
		System.out.println(c0);
		
		Cell c1 = new Cell(0, 1, 0, 1);
		System.out.println(c1);
		
		c0.removePossibility(7);
		c0.removePossibility(2);
		System.out.println(c0);
		
		c0.solve(5);
		System.out.println(c0);
	}
}
