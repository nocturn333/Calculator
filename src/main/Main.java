package main;

import exp.functions.*;
import exp.operators.*;
import parser.Parser;
import parser.UnevenBracketsException;

public class Main {

	public static void main(String[] args) throws UnevenBracketsException {
		String input = "(((1+1)+(0))";
		Parser p = new Parser();
		p.parse(input);
	}
}

//		Function pow = new Power(2);
//		System.out.println(pow);
//		
//		Function con = new Const(3);
//		System.out.println(con);
//		
//		Function pow2 = new Power(con);
//		System.out.println(pow2);
//		pow2.printValues(10);
//		
//		Operator add = new Add(pow, con);
//		System.out.println(add);
//		System.out.println(add.calculate(10));
