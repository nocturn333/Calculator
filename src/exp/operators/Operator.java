package exp.operators;

import exp.Expression;

public abstract class Operator extends Expression {

	Expression e1; // left expression
	Expression e2; // right expression
	String sign;
	
	public Operator() {
		
	}
	
	public Operator(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public void setLeft(Expression e) {
		this.e1 = e;
	}
	
	public void setRight(Expression e) {
		this.e2 = e;
	}
	
	public Expression getLeft() {
		return e1;
	}
	
	public Expression getRight() {
		return e2;
	}
	
	public String toString() {
		return "(" + e1.toString() + ")" + sign + "(" + e2.toString() + ")";
	}

}
