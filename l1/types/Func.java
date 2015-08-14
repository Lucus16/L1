package l1.types;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;

/**
 * A function receives a list of unevaluated arguments
 */
public abstract class Func extends Obj {
	public abstract InternalObj apply(Obj args, Environment env) throws L1Exception;

	public static void checkArgCount(Obj arg, int count)
			throws L1Exception {
		int total = count;
		while (count > 0) {
			if (!(arg instanceof Cons)) {
				throw new L1Exception("Too few arguments, expected " + total +
						", got " + (total - count) + ".");
			}
			arg = ((Cons)arg).getCdr();
			count -= 1;
		}
		if (arg instanceof Cons) {
			throw new L1Exception("Too many arguments, expected " + total +
					", got " + total + arg.listLength() + ".");
		}
	}
}
