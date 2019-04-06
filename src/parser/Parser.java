package parser;

import java.util.Stack;
import exp.*;
import exp.operators.*;

public class Parser {

	char[] tokens;
	int i;
	String varName = "";

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public Expression evaluate(String expression) throws FunctionInputException {
		tokens = expression.toCharArray();

		// Stack for numbers: 'values'
		Stack<Expression> values = new Stack<Expression>();

		// Stack for Operators: 'ops'
		Stack<Character> ops = new Stack<Character>();

		boolean expectingOp = false;
		System.out.println(expression);
		for (i = 0; i < tokens.length; i++) {
			char token = tokens[i];

			System.out.println("values: " + values);
			System.out.println("ops: " + ops);
			System.out.println(i);

			// Current token is a whitespace, skip it
			if (token == ' ')
				continue;

			// Current token is a number, push it to stack for numbers
			if (token >= '0' && token <= '9' || token == '.' || (token == '-' && !expectingOp)) {
				Expression n = parseNum();
				values.push(n);
				System.out.println("parsed num, jump to " + (i + 1));
				expectingOp = true;
			}
			
			else if (tokens[i] >= 'a' && tokens[i] <= 'z') {
				if(varName.isEmpty()) {
					varName = String.valueOf(token);
				}			
				Expression v = new Var(varName);
				values.push(v);
				expectingOp = true;
			}

			// Current token is an opening brace, push it to 'ops'
			else if (token == '(')
				ops.push(token);

			// Closing brace encountered, solve entire brace
			else if (token == ')') {
				while (ops.peek() != '(')
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				ops.pop();
			}

			// Current token is an operator.
			else if (token == '+' || (token == '-' && expectingOp) || token == '*' || token == '/') {
				// While top of 'ops' has same or greater precedence to current
				// token, which is an operator. Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && hasPrecedence(token, ops.peek()))
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));

				// Push current token to 'ops'.
				ops.push(token);
				expectingOp = false;
			}

		}

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!ops.empty())
			values.push(applyOp(ops.pop(), values.pop(), values.pop()));

		// Top of 'values' contains result, return it
		return values.pop();
	}

	private Expression parseNum() throws UnexpectedSymbolException {
		StringBuffer sbuf = new StringBuffer();
		boolean dec = false;
		// There may be more than one digits in number
		if (tokens[i] == '-') {
			sbuf.append(tokens[i]);
			i++;
		}
		while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) {
			if (tokens[i] == '.') {
				if (dec) {
					throw new UnexpectedSymbolException(".", i);
				} else {
					dec = true;
				}
			}
			sbuf.append(tokens[i++]);
		}

		double d = Double.parseDouble(sbuf.toString());
		Expression n = new Num(d);

		if (tokens[i] >= 'a' && tokens[i] <= 'z') {
			if(varName.isEmpty()) {
				varName = String.valueOf(tokens[i]);
			}				
			Expression v = new Var(varName);
			return new Mul(n, v);
		}
		i--;

		return n;

	}

	// Returns true if 'op2' has higher or same precedence as 'op1',
	// otherwise returns false.
	private boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		else
			return true;
	}

	// A utility method to apply an operator 'op' on operands 'a'
	// and 'b'. Return the result.
	private Expression applyOp(char op, Expression b, Expression a) {
		System.out.println(op + " " + a + " " + b);

		switch (op) {
		case '+':
			return new Add(a, b);
		case '-':
			return new Sub(a, b);
		case '*':
			return new Mul(a, b);
		case '/':
			return new Div(a, b);
		}
		return null;
	}

}
