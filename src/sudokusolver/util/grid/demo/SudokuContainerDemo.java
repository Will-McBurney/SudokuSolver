package sudokusolver.util.grid.demo;
import sudokusolver.util.grid.Cell;
import sudokusolver.util.grid.ColumnContainer;
import sudokusolver.util.SettingsSingleton;

public class SudokuContainerDemo {
	public static void main(String[] args) {
		SettingsSingleton settings = SettingsSingleton.getInstance();
		settings.setGridSize(9);
		
		ColumnContainer col = new ColumnContainer(0);
		
		Cell[] c = new Cell[9];
		c[0] = new Cell(0,0,0);
		c[1] = new Cell(1,0,0);
		c[2] = new Cell(2,0,0,7);
		c[3] = new Cell(3,0,3);
		c[4] = new Cell(4,0,3,5);
		c[5] = new Cell(5,0,3);
		c[6] = new Cell(6,0,6);
		c[7] = new Cell(7,0,6,2);
		c[8] = new Cell(8,0,6);
		
		col.addCell(c[0]);
		System.out.println(col);
		
		col.addCell(c[1]);
		col.addCell(c[2]);
		col.addCell(c[3]);
		System.out.println(col);
		
		for(int i = 4; i < 9; i++) {
			col.addCell(c[i]);
		}
		System.out.println(col);
		
		c[6].solve(3);
		System.out.println(col);
	}

}
