package l1.builtins;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;
import l1.types.Cons;
import l1.types.Func;
import l1.types.Obj;

public class BuiltinLet extends Func {
	@Override
	public InternalObj apply(Obj args, Environment env) throws L1Exception {
		checkArgCount(args, 2);
		Obj lets = args.get(0);
		Obj body = args.get(1);
		Environment newEnv = new Environment(env);
		while (lets instanceof Cons) {
			Cons cons = (Cons)lets;
			Obj let = cons.getCar();
			checkArgCount(let, 2);
			newEnv.put(let.get(0).asAtom(), let.get(1).eval(newEnv));
			lets = cons.getCdr();
		}
		return body.evalLater(env);
	}
}
