package edu.virginia.cs.sde.sudokusolver;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SudokuReaderFactory {
	
	public SudokuReader getSudokuReader(String filename) throws FileNotFoundException, IOException {
		Stream<String> stream = Files.lines(Paths.get(filename));
		int firstLineLength = stream.findFirst().get().length();
		stream.close();
		if (firstLineLength == 6) {
			return new Sudoku6x6TextReader(filename);
		} else if  (firstLineLength == 9) {
			return new Sudoku9x9TextReader(filename);
		} else {
			throw new IllegalArgumentException("Error: File format is not valid - first line is not a valid length.");
		}
	}

}
