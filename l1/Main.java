package l1;

import l1.types.Obj;

public class Main {
	public static void main(String[] args) {
		String source = "(car '(blah))";
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
