package exp;

public class Var extends Expression {

	String name;

	public Var() {
		this.name = "x";
	}

	public Var(String name) {
		this.name = name;
	}

	@Override
	public double calculate(double x) {
		return x;
	}

	@Override
	public String toString() {
		return "<" + name + ">";
	}

}
