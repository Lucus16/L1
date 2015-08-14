package l1.builtins;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;
import l1.types.Func;
import l1.types.Obj;

public class BuiltinQuote extends Func {
	@Override
	public InternalObj apply(Obj args, Environment env) throws L1Exception {
		checkArgCount(args, 1);
		return args.get(0);
	}
}
