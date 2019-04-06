package parser;

public class UnexpectedSymbolException extends FunctionInputException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnexpectedSymbolException(String symbol, int i) {
		super("Unexpected symbol "+symbol, i);
	}
}
