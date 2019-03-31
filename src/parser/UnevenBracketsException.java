package parser;

public class UnevenBracketsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnevenBracketsException(String b) {
		super("Uneven " + b + " brackets");
	}
}
