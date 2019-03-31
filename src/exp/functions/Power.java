package exp.functions;

import exp.*;

public class Power extends Function {

	public Power(double r) {
		super("x", new Num(r));
	}

	public Power(String varName, double r) {
		super(varName, new Num(r));
	}

	public Power(Expression r) {
		super("x", r);
	}

	public Power(String varName, Expression r) {
		super(varName, r);
	}

	@Override
	public double calculate(double x) {
		return Math.pow(v.calculate(x), r.calculate(x));
	}

	// x^r
	@Override
	public String toString() {
		return "(" + v.toString() + ")^(" + r.toString() + ")";
	}

}
