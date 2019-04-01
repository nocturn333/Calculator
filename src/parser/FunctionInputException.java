package parser;


public abstract class FunctionInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FunctionInputException(String message, String s) {
		super(message+ " at: "+ s);
	}
}
