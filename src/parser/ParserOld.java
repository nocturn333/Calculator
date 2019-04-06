package parser;

import java.util.Stack;

import exp.*;
import exp.operators.*;

public class Parser {

	String openBracket = "^\\(.*";
	String closedBracket = "^\\).*";
	String number = "^\\d+.*";

	String varName = "";

	public Expression parse(String input) throws FunctionInputException {
		System.out.println("source: " + input);
		Expression exp = null;
		Operator op = null;

		boolean expectExp = true;
		//boolean expectVar = true;
		boolean expectOp = false;
		boolean expectExp2 = false;

		boolean neg = false;

		int len = input.length();
		int index = 0;

		while (index < len) {
			String reading = input.substring(index, len);
			System.out.println("currently reading: " + reading);

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

			// negative
			if (reading.startsWith("-")) {
				neg = true;
				if (expectOp) {
					op = new Sub();
					op.setLeft(exp);
					expectOp = false;
					expectExp = true;
					//expectVar = true;
					expectExp2 = true;
					neg=false;
					System.out.println("set op -");
				}
			}

			// number
			if (reading.matches(number)) {
				if (expectExp) {
					int end = numberEndIndex(reading);
					String d = reading.substring(0, end);
					double number = Double.parseDouble(d);
					if (neg) {
						number = number * -1;
					}
					exp = new Num(number);
					System.out.println(exp);

					index = index + end-1;

					neg = false;
					expectExp = false;
					expectOp = true;
				} else {
					throw new UnexpectedSymbolException(input.substring(index, index + 1), input);
				}
			}

			if (expectExp2&&!expectExp) {
				op.setRight(exp);
				exp = op;
				System.out.println("final:"+exp);
				expectExp2 = false;
			}

			index++;
		}

		return exp;
	}

	private int numberEndIndex(String input) throws UnexpectedSymbolException {
		int len = input.length();
		int index = 0;

//		if (input.startsWith("-")) {
//			index = index + 1;
//		}

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
