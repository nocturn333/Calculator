package exp;

public class Num extends Expression {

	double c;

	public Num(double c) {
		this.c = c;
	}

	@Override
	public double calculate(double x) {
		return c;
	}


	@Override
	public String toString() {
		return String.valueOf(c);
	}

}
