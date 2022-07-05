package edu.virginia.cs.sde.sudokusolver;

public class IllegalCellValueException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6136268241964284687L;
	
	public IllegalCellValueException(String message) {
		super(message);
	}

}
