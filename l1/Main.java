package l1;

import l1.types.Obj;

public class Main {
	public static void main(String[] args) {
		String source = "(let ("
				+ "(twice (lambda (f) (lambda (x) (f (f x)))))"
				+ "(many (((twice (twice (twice (twice twice)))) (lambda (x) (cons true x))) nil))"
				+ "(and (lambda (x) (if (isatom x) true (if (eq (car x) true) (and (cdr x)) false)))))"
				+ "(and many))";
		Obj parsed;
		try {
			parsed = new Parser(source).parse();
		} catch (L1Exception e) {
			System.out.println("Parsing failed: " + e.getMessage());
			return;
		}
		Environment env = new Environment();
		Obj result;
		try {
			result = env.eval(parsed);
		} catch (L1Exception e) {
			System.out.println("Evaluation failed: " + e.getMessage());
			return;
		}
		System.out.println("Result: " + result);
	}
}
