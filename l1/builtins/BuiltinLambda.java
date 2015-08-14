package l1.builtins;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;
import l1.types.Func;
import l1.types.Lambda;
import l1.types.Obj;

public class BuiltinLambda extends Func {
	@Override
	public InternalObj apply(Obj args, Environment env) throws L1Exception {
		checkArgCount(args, 2);
		return new Lambda(env, args.get(0), args.get(1));
	}
}
