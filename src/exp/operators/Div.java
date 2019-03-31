package exp.operators;

import exp.Expression;

public class Div extends Operator {

	public Div(Expression e1, Expression e2) {
		super(e1, e2);
		this.sign = "/";
	}

	@Override
	public double calculate(double x) {
		double l = e2.calculate(x);
		if(l==0) {
			System.out.println("Division by 0");
		}
		return e1.calculate(x) / l;
	}


}
