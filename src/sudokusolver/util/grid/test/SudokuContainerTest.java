package sudokusolver.util.grid.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;

import sudokusolver.util.grid.Cell;
import sudokusolver.util.grid.RowContainer;
import sudokusolver.util.grid.SudokuContainer;
import sudokusolver.util.SettingsSingleton;

class SudokuContainerTest {

	SettingsSingleton singleton;
	SudokuContainer r;
	Cell c0, c1, c2, c3;
	
	@BeforeEach
	void setup() {
		singleton = SettingsSingleton.getInstance();
		singleton.setGridSize(9);
		
		r = new RowContainer(0);
		c0 = new Cell(0, 0, 0);
		c1 = new Cell(0, 1, 0);
		c2 = new Cell(0, 2, 0, 1);
		c3 = new Cell(0, 3, 1, 4);
	}
	
	@Test
	void testConstructorGetCells() {
		List<Cell> cells = r.getCells();
		assertEquals(0, cells.size());
	}
	
	@Test
	void testConstructorGetMissing() {
		Set<Integer> missing = r.getMissing();
		assertEquals(9, missing.size());
		for (int i = 1; i <= singleton.getGridSize(); i++) {
			assertTrue(missing.contains(i));
		}
	}

	@Test
	void testConstructorSolved() {
		assertFalse(r.isSolved());
	}
	
	@Test
	void testAddUnsolvedCellGetCells() {
		r.addCell(c0);
		List<Cell> cells = r.getCells();
		assertEquals(1, cells.size());
		assertTrue(cells.contains(c0));
	}
	
	@Test
	void testAddUnsolvedCellSolve() {
		r.addCell(c0);
		assertFalse(r.isSolved());
	}
	
	@Test
	void testAddUnsolvedCellMissing() {
		r.addCell(c0);
		Set<Integer> missing = r.getMissing();
		assertEquals(9, missing.size());
		for (int i = 1; i <= singleton.getGridSize(); i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test
	void testAddSolvedCellGetCells() {
		r.addCell(c2);
		List<Cell> cells = r.getCells();
		assertEquals(1, cells.size());
		assertTrue(cells.contains(c2));
	}
	
	@Test
	void testAddSolvedCellSolve() {
		r.addCell(c2);
		assertFalse(r.isSolved());
	}
	
	@Test
	void testAddTooManyCells() {
		List<Cell> cells = r.getCells();
		for(int i = 0; i <= 8; i++) {
			cells.add(new Cell(0, i, ((i / 3) * 3)));
		}
		assertThrows(IllegalStateException.class, () ->
			r.addCell(new Cell(0, 8, 0, 4)));
	}
	
	@Test
	void testAddCellAtSameCoordinate() {
		List<Cell> cells = r.getCells();
		cells.add(new Cell(0,0,0));
		cells.add(new Cell(0,1,0));
		
		assertThrows(IllegalArgumentException.class, () ->
			r.addCell(new Cell(0, 1, 0, 2)));
	}
	
	@Test
	void testAddNonMatchingCellAddress() {
		assertThrows(IllegalArgumentException.class, () ->
			r.addCell(new Cell(1,0,0))
		);
	}
	
	@Test
	void testAddSolvedCellMissing() {
		r.addCell(c2);
		Set<Integer> missing = r.getMissing();
		assertEquals(8, missing.size());
		assertFalse(missing.contains(1));
		for (int i = 2; i <= singleton.getGridSize(); i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test
	void testAddUnsolvedCellToUnsolvedCellMissing() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		
		r.addCell(c1);
		Set<Integer> missing = r.getMissing();
		assertEquals(9, missing.size());
		for (int i = 1; i <= singleton.getGridSize(); i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test
	void testAddUnsolvedCellToUnsolvedCells() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		
		r.addCell(c1);
		assertEquals(2, cells.size());
		assertTrue(cells.contains(c0));
		assertTrue(cells.contains(c1));
	}
	
	@Test
	void testAddUnsolvedCellToUnsolvedIsSolved() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		
		r.addCell(c1);
		assertFalse(r.isSolved());
	}
	
	@Test
	void testAddSolvedCellToUnsolvedCellsSize() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		cells.add(c1);
		
		r.addCell(c2);
		assertEquals(3, cells.size());
		assertTrue(cells.contains(c0));
		assertTrue(cells.contains(c1));
		assertTrue(cells.contains(c2));
	}
	
	@Test
	void testAddSolvedCellToUnsolvedCellsMissing() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		cells.add(c1);
		
		r.addCell(c2);
		Set<Integer> missing = r.getMissing();
		assertEquals(8, missing.size());
		assertFalse(missing.contains(1));
		for (int i = 2; i <= singleton.getGridSize(); i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	
	@Test
	void testAddSolvedCellToUnsolvedCellsIsSolved() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		cells.add(c1);
		
		r.addCell(c2);
		assertFalse(r.isSolved());
	}
	
	@Test
	void testAddSolvedCellToUnsolvedOtherCellsAffected() {
		List<Cell> cells = r.getCells();
		cells.add(c0);
		cells.add(c1);
		
		r.addCell(c2);
		assertEquals(8, c0.getPossibilities().size());
		assertEquals(8, c1.getPossibilities().size());
		assertFalse(c0.isPossible(1));
		assertFalse(c1.isPossible(1));
		for (int i = 2; i <= singleton.getGridSize(); i++) {
			assertTrue(c0.isPossible(i));
			assertTrue(c1.isPossible(i));
		}
	}
	
	@Test
	void testSolveOnUnsolvedOnlyContainerMissing() {
		r.addCell(c0);
		r.addCell(c1);
		c1.solve(9);
		Set<Integer> missing = r.getMissing();
		assertEquals(8, missing.size());
		assertFalse(missing.contains(9));
		for (int i = 1; i <= 8; i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test
	void testSolveHybridContainerMissing() {
		r.addCell(c0);
		r.addCell(c1);
		r.addCell(c2);
		c1.solve(9);
		Set<Integer> missing = r.getMissing();
		assertEquals(7, missing.size());
		assertFalse(missing.contains(1));
		assertFalse(missing.contains(9));
		for (int i = 2; i <= 8; i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test
	void testSolveHybridContainerMissingWhenSolved() {
		singleton.setGridSize(2);
		r = new RowContainer(0);
		r.addCell(c1);
		r.addCell(c2);
		c1.solve(2);
		Set<Integer> missing = r.getMissing();
		assertEquals(0, missing.size());
	}
	
	@Test
	void testSolveHybridContainerIsSolvedWhenSolved() {
		singleton.setGridSize(2);
		r = new RowContainer(0);
		r.addCell(c1);
		r.addCell(c2);
		c1.solve(2);
		assertTrue(r.isSolved());
	}
	
	@Test 
	void testAddCellTwoSolvedSize() {
		
		//Bad testing style to include the next 3 lines.
//		r.addCell(c0);
//		r.addCell(c1);
//		r.addCell(c2);
		
		//add the requisite cells
		List<Cell> cells = r.getCells();
		Cell c0 = new Cell(0, 0, 0);
		Cell c1 = new Cell(0, 1, 0);
		Cell c2 = new Cell(0, 3, 1, 9);
		Cell c3 = new Cell(0, 2, 0, 1);
		cells.add(c0); //unsolved
		cells.add(c1); //unsolved
		cells.add(c2); //unsolved
		
		Set<Integer> missing = r.getMissing();
		missing.remove(9);
		c0.removePossibility(9);
		c1.removePossibility(9);	
		
		
		r.addCell(c3);
		assertEquals(4, r.getCells().size());
		assertTrue(r.getCells().contains(c0));
		assertTrue(r.getCells().contains(c1));
		assertTrue(r.getCells().contains(c2));
		assertTrue(r.getCells().contains(c3));
	}
	
	@Test 
	void testAddCellTwoSolvedMissing() {		
		//add the requisite cells
		List<Cell> cells = r.getCells();
		Cell c0 = new Cell(0, 0, 0);
		Cell c1 = new Cell(0, 1, 0);
		Cell c2 = new Cell(0, 3, 1, 9);
		Cell c3 = new Cell(0, 2, 0, 1);
		cells.add(c0); //unsolved
		cells.add(c1); //unsolved
		cells.add(c2); //unsolved
		
		Set<Integer> missing = r.getMissing();
		missing.remove(9);
		c0.removePossibility(9);
		c1.removePossibility(9);	
		
		
		r.addCell(c3);
		assertEquals(7, missing.size());
		assertFalse(missing.contains(1));
		assertFalse(missing.contains(9));
		for (int i = 2; i <= 8; i++) {
			assertTrue(missing.contains(i));
		}
	}
	
	@Test 
	void testAddCellTwoSolvedIsSolved() {
		
		//add the requisite cells
		List<Cell> cells = r.getCells();
		Cell c0 = new Cell(0, 0, 0);
		Cell c1 = new Cell(0, 1, 0);
		Cell c2 = new Cell(0, 3, 1, 9);
		Cell c3 = new Cell(0, 2, 0, 1);
		cells.add(c0); //unsolved
		cells.add(c1); //unsolved
		cells.add(c2); //unsolved
		
		Set<Integer> missing = r.getMissing();
		missing.remove(9);
		c0.removePossibility(9);
		c1.removePossibility(9);	
		
		
		r.addCell(c3);
		assertFalse(r.isSolved());
	}
	
	@Test 
	void testAddCellTwoSolvedCheckOtherCells() {
		
		//add the requisite cells
		List<Cell> cells = r.getCells();
		Cell c0 = new Cell(0, 0, 0);
		Cell c1 = new Cell(0, 1, 0);
		Cell c2 = new Cell(0, 3, 1, 9);
		Cell c3 = new Cell(0, 2, 0, 1);
		cells.add(c0); //unsolved
		cells.add(c1); //unsolved
		cells.add(c2); //unsolved
		
		Set<Integer> missing = r.getMissing();
		missing.remove(9);
		c0.removePossibility(9);
		c1.removePossibility(9);	
		
		r.addCell(c3);
		
		assertFalse(c0.isPossible(9));
		assertFalse(c0.isPossible(1));
		assertFalse(c1.isPossible(9));
		assertFalse(c1.isPossible(1));
		for (int i = 2; i <= 8; i++) {
			assertTrue(c0.isPossible(i));
			assertTrue(c1.isPossible(i));
		}
	}
	
}
