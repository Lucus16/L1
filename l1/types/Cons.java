package l1.types;

import java.util.List;

import l1.L1Exception;

public class Cons extends Obj {
	public static final Atom nil = new Atom("nil");
	private Obj car;
	private Obj cdr;

	public Cons(Obj car, Obj cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	public Obj getCar() { return car; }
	public Obj getCdr() { return cdr; }

	public Obj get(int index) throws L1Exception {
		assert index >= 0;
		Cons cons = this;
		while (index > 0) {
			index -= 1;
			Obj list = cons.cdr;
			if (!(list instanceof Cons)) {
				throw new IndexOutOfBoundsException();
			}
			cons = (Cons)list;
		}
		return cons.car;
	}

	@Override
	public Cons asCons() throws L1Exception {
		return this;
	}

	public static Obj fromList(List<Obj> list) {
		Obj prev = nil;
		for (int i = list.size() - 1; i >= 0; i--) {
			prev = new Cons(list.get(i), prev);
		}
		return prev;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(car);
		Obj next = cdr;
		while (next instanceof Cons) {
			sb.append(' ');
			sb.append(((Cons)next).getCar());
			next = ((Cons)next).getCdr();
		}
		if (!nil.equals(next)) {
			sb.append(" . ");
			sb.append(next);
		}
		sb.append(')');
		return sb.toString();
	}
}
