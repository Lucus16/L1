package l1.builtins;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;
import l1.types.Atom;
import l1.types.Func;
import l1.types.Obj;

public class BuiltinEq extends Func {
	@Override
	public InternalObj apply(Obj args, Environment env) throws L1Exception {
		checkArgCount(args, 2);
		return Atom.fromBool(args.get(0).eval(env).asAtom()
				.equals(args.get(1).eval(env).asAtom()));
	}
}
