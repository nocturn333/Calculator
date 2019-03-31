package main;

import java.util.regex.Matcher;

import exp.functions.*;
import exp.operators.*;
import parser.FunctionInputException;
import parser.Parser;

public class Main {

	public static void main(String[] args) throws FunctionInputException {
		String input = ")(1+1)";
		
		System.out.println(input.matches("\\).*"));
		
//		Parser p = new Parser();
//		p.parse(input);
		
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
