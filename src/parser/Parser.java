package parser;

import java.util.Stack;

public class Parser {

	String openBracket = "\\(.*";
	String closedBracket = "\\).*";
	
	public void parse(String input) throws FunctionInputException {
		System.out.println("source: " + input);
		int len = input.length();
		int index = 0;

		while (index < len) {
			String s = input.substring(index, index + 1);
			System.out.println(index + ": " + s);

			switch (s) {
			case "(":
				int end = closeBracketIndex(input, index);
				if (end < 0) {
					throw new UnevenBracketsException("open");
				}
				String inner = input.substring(index + 1, end);
				System.out.println("inner" + inner);
				parse(inner);
				index = end;
				System.out.println(index + ": )");
				break;

			case ")":
				throw new UnevenBracketsException("closed");
				
			default:
				break;
			}
			index++;
		}
	}

	private int closeBracketIndex(String input, int index) {
		int len = input.length();

		Stack<Integer> b = new Stack<Integer>();
		b.push(index);
		index++;

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
