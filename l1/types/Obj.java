package l1.types;

import java.util.ArrayList;
import java.util.List;

import l1.Environment;
import l1.InternalObj;
import l1.L1Exception;
import l1.Unfinished;

public abstract class Obj extends InternalObj {
	public Obj get(int index) throws L1Exception {
		throw new L1Exception("Index out of bounds.");
	}

	public Obj eval(Environment env) throws L1Exception {
		return env.eval(this);
	}

	public Unfinished evalLater(Environment env) {
		return new Unfinished(this, env);
	}

	public Cons asCons() throws L1Exception {
		throw new L1Exception("Not a cons: " + this);
	}

	public Atom asAtom() throws L1Exception {
		throw new L1Exception("Not an atom: " + this);
	}

	public Obj finish() throws L1Exception {
		return this;
	}

	public boolean toBool() throws L1Exception {
		if (Atom.t.equals(this)) {
			return true;
		} else if (Atom.f.equals(this)) {
			return false;
		} else {
			throw new L1Exception("Expected boolean.");
		}
	}

	public List<Obj> toList() throws L1Exception {
		List<Obj> list = new ArrayList<Obj>();
		Obj o = this;
		while (o instanceof Cons) {
			Cons cons = (Cons)o;
			list.add(cons.getCar());
			o = cons.getCdr();
		}
		if (!Cons.nil.equals(o)) {
			throw new L1Exception("Improper list.");
		}
		return list;
	}

	public int listLength() throws L1Exception{
		int length = 0;
		Obj o = this;
		while (o instanceof Cons) {
			Cons cons = (Cons)o;
			length++;
			o = cons.getCdr();
		}
		if (!Cons.nil.equals(o)) {
			throw new L1Exception("Improper list.");
		}
		return length;
	}
}
