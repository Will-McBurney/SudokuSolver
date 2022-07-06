package edu.virginia.cs.sde.sudokusolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Sudoku6x6TextReader extends SudokuReader {
	public String filename;
	
	public Sudoku6x6TextReader(String filename) {
		SettingsSingleton settings = SettingsSingleton.getInstance();
		settings.setGridSize(6);
		this.filename = filename;
	}
	
	@Override
	public SudokuGrid read() {
		SudokuGrid grid = new SudokuGrid();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line = br.readLine();
			int row = 0;
			
			while(line != null) {
				if (line.length() != 6) {
					br.close();
					throw new IllegalStateException("Error: Formatted file has invalid length at line " + row + " - " + line + " - length: " + line.length());
				}
				for (int col = 0; col < 6; col++) {
					int box = 2 * (row / 2) + (col / 3);
					char value = line.charAt(col);
					
					if (value == ' ') {
						grid.addCell(new Cell(row, col, box));
					} else {
						int v = Character.getNumericValue(value);
						grid.addCell(new Cell(row, col, box, v));
					}
				}
				
				row++;
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Error: File specified for Sudoku9x9TextReader could not be found: " + filename);
		} catch (IOException e) {
			throw new IllegalStateException("Error: Something when wrong while reading from " + filename + " to build a 9x9 grid.");
		}
		
		return grid;
	}
}
