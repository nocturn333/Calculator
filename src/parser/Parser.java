package parser;

import java.util.Stack;

public class Parser {

	public void parse(String input) throws UnevenBracketsException {
		//System.out.println(input);
		int len = input.length();
		int index = 0;

		while (index < len) {
			String s = input.substring(index, index + 1);
			System.out.println(index + " " + s);
			switch (s) {
			case "(":
				int end = bracket(input, index);
				if(end<0) {
					throw new UnevenBracketsException("open");
				}
				String inner = input.substring(index + 1, end);
				System.out.println("inner" + inner);
				parse(inner);
				index = end;
				break;
			case ")":
				throw new UnevenBracketsException("closed");			
			default:
				break;
			}
			index++;
		}
	}

	private int bracket(String input, int index) {
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
