package sudokusolver.util.grid.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import sudokusolver.util.SettingsSingleton;
import sudokusolver.util.exceptions.IllegalCellValueException;
import sudokusolver.util.exceptions.InvalidSolutionException;
import sudokusolver.util.exceptions.UnsolvedCellException;
import sudokusolver.util.grid.Cell;

class CellTest {
	
	Cell unsolved, solved, c, d;
	final static int DEFAULT_TEST_GRID_SIZE = 9;
	
	@BeforeEach
	void setup() {
		SettingsSingleton s = SettingsSingleton.getInstance();
		s.setGridSize(DEFAULT_TEST_GRID_SIZE);
		
		unsolved = new Cell(0, 2, 0);
		solved = new Cell(4, 7, 6, 5);
	}

	@Test
	void testConstructorIsSolvedFalse() {
		assertFalse(unsolved.isSolved());
	}
	
	@Test
	void testConstructorIsSolvedTrue() {
		assertTrue(solved.isSolved());
	}
	
	@Test
	void testConstructorRowUnsolved() {
		assertEquals(0, unsolved.getRowNumber());
	}
	
	@Test
	void testConstructorRowSolved() {
		assertEquals(4, solved.getRowNumber());
	}
	
	@Test
	void testConstructorColUnsolved() {
		assertEquals(2, unsolved.getColNumber());
	}
	
	@Test
	void testConstructorColSolved() {
		assertEquals(7, solved.getColNumber());
	}

	@Test
	void testConstructorBoxUnsolved() {
		assertEquals(0, unsolved.getBoxNumber());
	}
	
	@Test
	void testConstructorBoxSolved() {
		assertEquals(6, solved.getBoxNumber());
	}
	
	@Test
	void testConstructorPossibilitiesUnsolved() {
		Set<Integer> possibilities = unsolved.getPossibilities();
		assertEquals(DEFAULT_TEST_GRID_SIZE, possibilities.size());
		for (int i = 1; i <= DEFAULT_TEST_GRID_SIZE; i++) {
			assertTrue(possibilities.contains(i), "Possibilities of unsolved cell didn't include " + i);
		}
	}
	
	@Test
	void testConstructorPossibilitiesSolved() {
		assertNull(solved.getPossibilities());
	}
	
	@Test
	void testConstructorGetSolutionUnsolved() {
		assertThrows(UnsolvedCellException.class, () ->
				unsolved.getSolution());
	}
	
	@Test
	void testConstructorGetSolutionSolved() {
		assertEquals(5, solved.getSolution());
	}
	
	@Test
	void testRemovePossibilitiesSolvedError() {
		assertThrows(InvalidSolutionException.class, () ->
				solved.removePossibility(5)); // since cell is 5, this should be an error
	}
	
	@Test
	void testRemovePossibilitiesSolvedNonError() {
		solved.removePossibility(6); // should not throw an error
	}
	
	@Test
	void testRemovePossibilitiesUnsolvedWasPossible() {
		unsolved.removePossibility(6); // should not throw an error
		Set<Integer> possibilities = unsolved.getPossibilities();
		assertEquals(DEFAULT_TEST_GRID_SIZE - 1, possibilities.size());
		for (int i = 1; i <= DEFAULT_TEST_GRID_SIZE; i++) {
			if (i == 6) {
				assertFalse(possibilities.contains(i), "Possibilities of unsolved cell included " + 
							i + " after it wave removed.");
			} else {
				assertTrue(possibilities.contains(i), "Possibilities of unsolved cell didn't include " + i);
			}
		}
	}
	
	@Test
	void testRemovePossibilitiesUnsolvedWasNotPossible() {
		Set<Integer> possibilities = unsolved.getPossibilities();
		possibilities.clear();
		possibilities.add(1);
		possibilities.add(2);
		possibilities.add(3);
		
		unsolved.removePossibility(6); // should not throw an error
		possibilities = unsolved.getPossibilities();
		assertEquals(3, possibilities.size());
		for (int i = 1; i <= 3; i++) {
			assertTrue(possibilities.contains(i), "Possibilities of unsolved cell didn't include " + i);
		}
		for (int i = 4; i <= 9; i++) {
			assertFalse(possibilities.contains(i), "Possibilities of unsolved cell included " + 
					i + " after it wave removed.");
		}
	}
	
	@Test
	void testRemovePossibilitiesUnsolvedWasPossibleSmaller() {
		Set<Integer> possibilities = unsolved.getPossibilities();
		possibilities.clear();
		possibilities.add(1);
		possibilities.add(2);
		possibilities.add(3);
		
		unsolved.removePossibility(1); // should not throw an error
		possibilities = unsolved.getPossibilities();
		assertEquals(2, possibilities.size());
		assertFalse(possibilities.contains(1), "Possibilities of unsolved cell included 1 after it wave removed.");
		for (int i = 2; i <= 3; i++) {
			assertTrue(possibilities.contains(i), "Possibilities of unsolved cell didn't include " + i);
		}
		for (int i = 4; i <= 9; i++) {
			assertFalse(possibilities.contains(i), "Possibilities of unsolved cell included " + 
					i + " after it wave removed.");
		}
	}
	
	@Test
	void testLastPossibilityRemoved() {
		Set<Integer> possibilities = unsolved.getPossibilities();
		possibilities.clear();
		possibilities.add(5);
		
		assertThrows(InvalidSolutionException.class, () ->
				unsolved.removePossibility(5));
	}
	
	@Test
	void testSolvedIsSolved() {
		unsolved.solve(5);
		assertEquals(true, unsolved.isSolved());
	}
	
	@Test
	void testSolvedPossibilities() {
		unsolved.solve(5);
		assertEquals(null, unsolved.getPossibilities());
	}
	
	@Test
	void testSolvedGetSolution() {
		unsolved.solve(5);
		assertEquals(5, unsolved.getSolution());
	}
	
	@Test
	void testIsPossibleFullyUnsolved() {
		for (int i = 1; i <= 9; i++) {
			assertTrue(unsolved.isPossible(i));
		}
	}
	
	@Test
	void testIsPossibleFullyPartiallySolved() {
		for (int i = 2; i <= 9; i += 2) {  // remove every even value
			unsolved.removePossibility(i);
		}
		for (int i = 1; i <= 9; i++) {
			if (i % 2 == 0) { // if even
				assertFalse(unsolved.isPossible(i));
			} else {
				assertTrue(unsolved.isPossible(i));
			}
		}
	}
	
	@Test
	void testSolvedErrorNotPossible() {
		unsolved.removePossibility(5);
		assertThrows(InvalidSolutionException.class, () -> unsolved.solve(5));
	}
	
	@Test
	void testSolvedErrorAlreadySolvedDifferentValue() {
		assertThrows(InvalidSolutionException.class, () -> solved.solve(1));
	}
	
	@Test
	void testSolvedErrorAlreadySolvedSameValue() {
		assertThrows(InvalidSolutionException.class, () -> solved.solve(5));
	}
	
	@Test
	void testIsPossibleSolvedTrue() {
		assertTrue(solved.isPossible(5)); 
	}
	
	@Test
	void testIsPossibleSolvedFalse() {
		assertFalse(solved.isPossible(6)); 
	}
	
	@Test
	void testIllegalValueConstructor() {
		assertThrows(IllegalCellValueException.class,
				() -> new Cell(0, 0, 0, 0));
		assertThrows(IllegalCellValueException.class,
				() -> new Cell(0, 0, 0, -1));
		assertThrows(IllegalCellValueException.class,
				() -> new Cell(0, 0, 0, 10));
	}
	
	@Test
	void testIllegalValueSolveTestSolved() {
		assertThrows(IllegalCellValueException.class,
				() -> unsolved.solve(0));
		assertThrows(IllegalCellValueException.class,
				() -> unsolved.solve(-1));
		assertThrows(IllegalCellValueException.class,
				() -> unsolved.solve(10));
	}
}
