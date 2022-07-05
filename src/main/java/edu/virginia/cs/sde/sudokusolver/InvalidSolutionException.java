package edu.virginia.cs.sde.sudokusolver;

public class InvalidSolutionException extends IllegalStateException {

	/**
	 * Exception caused by trying to make a cell be a value it cannot be
	 */
	private static final long serialVersionUID = -7889730267385877234L;

	public InvalidSolutionException(String message) {
		super(message);
	}
}
