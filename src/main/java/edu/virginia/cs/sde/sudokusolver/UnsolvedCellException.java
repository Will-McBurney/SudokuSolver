package edu.virginia.cs.sde.sudokusolver;

public class UnsolvedCellException extends IllegalStateException {

	/**
	 * Exception caused by trying to access a solution from an unsolved cell
	 */
	private static final long serialVersionUID = -8065064310974466674L;

	public UnsolvedCellException(String message) {
		super(message);
	}

}
