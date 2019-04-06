package parser;


public abstract class FunctionInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FunctionInputException(String message, int i) {
		super(message+ " at index "+ i);
	}

}
