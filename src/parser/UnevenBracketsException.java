package parser;

public class UnevenBracketsException extends FunctionInputException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnevenBracketsException(String b, String s) {
		super("Uneven " + b + " brackets", s);
	}
}
