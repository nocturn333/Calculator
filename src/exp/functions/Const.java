package exp.functions;

import exp.Num;

public class Const extends Function {

	public Const(double c) {
		super("x", new Num(c));
	}

	@Override
	public double calculate(double x) {
		return r.calculate(x);
	}

	@Override
	public String toString() {
		return r.toString();
	}
}
