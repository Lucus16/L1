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

	public Lambda(Environment defEnv, Obj argNames, Obj body)
			throws L1Exception {
		if (!((argNames instanceof Atom) || (argNames instanceof Cons))) {
			throw new L1Exception("Invalid lambda syntax");
		}
		this.defEnv = defEnv;
		this.argNames = argNames;
		this.body = body;
	}

	@Override
	public InternalObj apply(Obj args, Environment argEnv) throws L1Exception {
		Environment inEnv = new Environment(defEnv);
		List<Obj> argList = args.toList();
		List<Obj> evaluatedArgList = new ArrayList<Obj>();
		for (Obj o : argList) {
			evaluatedArgList.add(o.eval(argEnv));
		}
		Obj evaluatedArgs = Cons.fromList(evaluatedArgList);
		Obj argNames = this.argNames;
		if (argNames instanceof Atom) {
			inEnv.put(((Atom)argNames), evaluatedArgs);
		} else {
			int argCount = 0;
			while (argNames instanceof Cons) {
				if (!(evaluatedArgs instanceof Cons)) {
					throw new L1Exception("Too few arguments, expected " +
							argNames.listLength() + ", got " +
							argCount + ".");
				}
				argCount++;
				Cons argCons = (Cons)evaluatedArgs;
				Cons nameCons = (Cons)argNames;
				inEnv.put(nameCons.getCar().asAtom(), argCons.getCar());
				evaluatedArgs = argCons.getCdr();
				argNames = nameCons.getCdr();
			}
			if (evaluatedArgs instanceof Cons) {
				throw new L1Exception("Too many arguments, expected " +
						argNames.listLength() + ", got " +
						argList.size());
			}
		}
		return body.evalLater(inEnv);
	}
}
