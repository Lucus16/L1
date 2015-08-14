package l1;

import l1.types.Obj;

/**
 * Returned instead of evaluated tail calls
 */
public class Unfinished extends InternalObj {
	private Obj code;
	private Environment env;

	public Unfinished(Obj code, Environment env) {
		this.code = code;
		this.env = env;
	}

	public Obj finish() throws L1Exception {
		Object o = this;
		while (o instanceof Unfinished) {
			Unfinished u = (Unfinished)o;
			o = env.evalStep(u.code);
		}
		return (Obj)o;
	}
}
