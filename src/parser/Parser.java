package parser;

import java.util.Stack;

import exp.*;

public class Parser {

	String openBracket = "^\\(.*";
	String closedBracket = "^\\).*";
	String number = "^-?\\d+.*";

	public Expression parse(String input) throws FunctionInputException {
		System.out.println("source: " + input);
		Expression e = null;
		boolean expectOp = false;

		int len = input.length();
		int index = 0;

		while (index < len) {
			String reading = input.substring(index, len);
			System.out.println(reading);

			// open bracket
			if (reading.matches(openBracket)) {
				int end = closeBracketIndex(reading);

				if (end < 0) {
					throw new UnevenBracketsException("open", reading);
				}
				String inner = reading.substring(1, end);
				System.out.println("inner: " + inner);
				parse(inner);
				index = index + end;
			}

			// closed bracket
			if (reading.matches(closedBracket)) {
				throw new UnevenBracketsException("closed", reading);
			}

			// number
			if (reading.matches(number)) {
				if (expectOp == false) {
					int end = numberEndIndex(reading);
					System.out.println(end);
					String d = reading.substring(0, end);
					double number = Double.parseDouble(d);
					System.out.println(number);
					index = index + end;
					expectOp = true;
				}
				else {
					throw new UnexpectedSymbolException(input.substring(index, index+1), input);
				}
			}

			index++;
		}

		return e;
	}

	private int numberEndIndex(String input) throws UnexpectedSymbolException {
		int len = input.length();
		int index = 0;

		boolean dec = false;

		while (index < len) {
			String s = input.substring(index, index + 1);

			if (s.matches("\\.")) {
				if (dec == false) {
					dec = true;
					index++;
				} else {
					throw new UnexpectedSymbolException(".", input);
				}
			} else if (s.matches("\\d")) {
				index++;
			} else {
				break;
			}

		}
		return index;
	}

	private int closeBracketIndex(String input) {
		int len = input.length();
		int index = 0;

		Stack<Integer> b = new Stack<Integer>();

		while (index < len) {

			String s = input.substring(index, index + 1);
			if (s.equals("(")) {
				b.push(index);
			}
			if (s.equals(")")) {
				b.pop();
				if (b.empty()) {
					return index;
				}
			}

			index++;
		}

		return -1;
	}
}

/*



















*/
