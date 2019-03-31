package exp.functions;

import exp.*;

public class Exponential extends Function {

	public Exponential(double r) {
		super("x", new Num(r));
	}

	public Exponential(String varName, double r) {
		super(varName, new Num(r));
	}

	public Exponential(Expression r) {
		super("x", r);
	}

	public Exponential(String varName, Expression r) {
		super(varName, r);
	}

	@Override
	public double calculate(double x) {
		return Math.pow(v.calculate(x), r.calculate(x));
	}

	// r^x
	@Override
	public String toString() {
		return "(" + r.toString() + ")^(" + v.toString() + ")";
	}

}
