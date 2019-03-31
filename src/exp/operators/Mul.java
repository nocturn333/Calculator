package exp.operators;

import exp.Expression;

public class Mul extends Operator {

	public Mul(Expression e1, Expression e2) {
		super(e1, e2);
		this.sign = "*";
	}

	@Override
	public double calculate(double x) {
		return e1.calculate(x) * e2.calculate(x);
	}


}
