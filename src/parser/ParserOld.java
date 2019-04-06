package parser;

import java.util.Stack;

import exp.*;
import exp.operators.*;

public class ParserOld {

	String openBracket = "^\\(.*";
	String closedBracket = "^\\).*";
	String number = "^\\-?[\\d]*\\.?[\\d]+$";

	String add = ".+\\+.+";
	String sub = ".+\\-.+";	
	String mul = ".+\\*.+";
	String div = ".+\\/.+";
	
	
	String varName = "";

	public Expression parse(String input) throws FunctionInputException {
		System.out.println("source: " + input);
		Expression exp = null;

		int len = input.length();
		int index = 0;

		while (index < len) {
			String reading = input.substring(index, len);
			System.out.println("currently reading: " + reading);

			// open bracket
			if (reading.matches(openBracket)) {
				int end = closeBracketIndex(reading);

				if (end < 0) {
					//throw new UnevenBracketsException("open", reading);
				}
				String inner = reading.substring(1, end);
				System.out.println("inner: " + inner);
				exp = parse(inner);

				index = index + end;
			}

			// closed bracket
			if (reading.matches(closedBracket)) {
				//throw new UnevenBracketsException("closed", reading);
			}

			if(reading.startsWith("*")) {
				
			}
			
			
			// number
			if (reading.matches(number)) {

				int end = numberEndIndex(reading);
				String d = reading.substring(0, end);
				double number = Double.parseDouble(d);

				exp = new Num(number);
				System.out.println(exp);

				index = index + end - 1;
			}

			index++;
		}

		return exp;
	}

	private Expression operation(String symbol, String input) {
		Expression exp = null;

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
					//throw new UnexpectedSymbolException(".", input);
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
