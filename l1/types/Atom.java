package l1.types;

import l1.L1Exception;

public class Atom extends Obj {
	public static final Atom t = new Atom("true");
	public static final Atom f = new Atom("false");

	private String name;

	public Atom(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Atom)) {
			return false;
		}
		return name.equals(((Atom)obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public static Atom fromBool(boolean b) {
		return b ? t : f;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Atom asAtom() throws L1Exception {
		return this;
	}
}
