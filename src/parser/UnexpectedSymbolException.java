package parser;

public class UnexpectedSymbolException extends FunctionInputException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnexpectedSymbolException(String symbol, String s) {
		super("Unexpected symbol "+symbol, s);
	}
}
