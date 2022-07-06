# SudokuSolver

To build, simply clone and run ./gradlew build

To run, first build.

In build/libs/ you'll find SudokuSolver.jar

This is run from command line with:

java -jar SudokuSolver.jar [filename]

Where [filename] is the path to a sudoku text file.

You can find examples of sudoku input files in the root directory:

/example_input_files/

Note that this program supports solving:

9x9 Sudoku  
6x6 Sudoku

It currently utilizes the following strategies:  
-Hidden Single  
-Naked Single  
-Pointing Pair  
-Box Line Reduction  
-Naked Pair  
-Naked Triplet  

Planned to be implemented:  
-Hidden Pair  
-Hidden Triplet  

You can read more about these strategies at:
https://www.sudokuwiki.org/sudoku.htm
