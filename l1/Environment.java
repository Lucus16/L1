package l1;

import java.util.HashMap;

import l1.builtins.*;
import l1.types.Atom;
import l1.types.Cons;
import l1.types.Func;
import l1.types.Obj;

/**
 * To evaluate a (f a b c) we only need to evaluate f and then
 * call f with (a b c)
 */
public class Environment {
	private Environment parent;
	private HashMap<Atom, Obj> map;

	public Environment() {
		parent = null;
		map = new HashMap<Atom, Obj>();
		put("car", new BuiltinCar());
		put("cdr", new BuiltinCdr());
		put("cons", new BuiltinCons());
		put("eq", new BuiltinEq());
		put("eval", new BuiltinEval());
		put("if", new BuiltinIf());
		put("isatom", new BuiltinIsAtom());
		put("iscons", new BuiltinIsCons());
		put("isfunc", new BuiltinIsFunc());
		put("quote", new BuiltinQuote());
		put("let", new BuiltinLet());
		put(Atom.t, Atom.t);
		put(Atom.f, Atom.f);
		put(Cons.nil, Cons.nil);
	}

	public Environment(Environment parent) {
		this.parent = parent;
		map = new HashMap<Atom, Obj>();
	}

	public Obj get(Atom key) throws L1Exception {
		Obj value = map.get(key);
		if ((value == null) && (parent != null)) {
			value = parent.get(key);
		}
		if (value == null) {
			throw new L1Exception("Undefined: " + key);
		}
		return value;
	}

	public void put(String key, Obj value) {
		map.put(new Atom(key), value);
	}

	public void put(Atom key, Obj value) {
		map.put(key, value);
	}

	public Obj eval(Obj expr) throws L1Exception {
		return evalStep(expr).finish();
	}

	public InternalObj evalStep(Obj expr) throws L1Exception {
		if (expr instanceof Atom) {
			return get((Atom)expr);
		} else if (expr instanceof Cons) {
			Cons cons = (Cons)expr;
			Obj fnObj = eval(cons.getCar());
			Obj args = cons.getCdr();
			if (!(fnObj instanceof Func)) {
				throw new L1Exception("Not callable: " + fnObj);
			}
			Func fn = (Func)fnObj;
			return fn.apply(args, this);
		} else {
			throw new L1Exception("Unexpected type: " + expr.getClass());
		}
	}
}
