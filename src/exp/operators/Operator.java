package exp.operators;

import exp.Expression;

public abstract class Operator extends Expression {

	Expression e1; // left expression
	Expression e2; // right expression
	String sign;
	
	public Operator(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public String toString() {
		return "(" + e1.toString() + ")" + sign + "(" + e2.toString() + ")";
	}

}
