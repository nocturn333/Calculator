package exp.functions;

import exp.Expression;
import exp.Num;
import exp.Var;

public abstract class Function extends Expression {

	Expression r;
	Var v;

	public Function(String varName, double r) {
		v = new Var(varName);
		this.r = new Num(r);
	}

	public Function(String varName, Expression r) {
		v = new Var(varName);
		this.r = r;
	}

	public void printValues(double x2) {
		printValues(0, x2, 1);
	}

	public void printValues(double x1, double x2) {
		printValues(x1, x2, 1);
	}

	public void printValues(double x1, double x2, double inc) {
		if (inc == 0) {
			inc = 1;
		}
		for (double i = x1; i <= x2; i = i + inc) {
			double y = calculate(i);
			System.out.println("x= " + i + " y= " + y);
		}
	}

	// public abstract Function der();
}
