package l1.types;

import java.util.ArrayList;
import java.util.List;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;

public class Lambda extends Func {
	private Environment defEnv;
	private Obj argNames;
	private Obj body;

	public Lambda(Environment defEnv, Obj argNames, Obj body) {
		this.defEnv = defEnv;
		this.argNames = argNames;
		this.body = body;
	}

	@Override
	public InternalObj apply(Obj args, Environment argEnv) throws L1Exception {
		Environment inEnv = new Environment(defEnv);
		if (argNames instanceof Atom) {
			List<Obj> argList = args.toList();
			List<Obj> evaluatedArgList = new ArrayList<Obj>();
			for (Obj o : argList) {
				evaluatedArgList.add(o.eval(argEnv));
			}
			Obj evaluatedArgs = Cons.nil;
			inEnv.put(((Atom)argNames), evaluatedArgs);
		}
		return body.evalLater(inEnv);
	}
}
