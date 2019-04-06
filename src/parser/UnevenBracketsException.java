package parser;

public class UnevenBracketsException extends FunctionInputException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnevenBracketsException(String b, int i) {
		super("Uneven " + b + " brackets", i);
	}
}
