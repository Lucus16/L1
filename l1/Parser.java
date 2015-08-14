package l1;

import java.util.ArrayList;
import java.util.List;

import l1.types.Atom;
import l1.types.Cons;
import l1.types.Obj;

public class Parser {
	private String code;
	private int pos;

	public Parser(String code) {
		this.code = code;
		pos = 0;
	}

	private Obj getString() throws L1Exception {
		List<Obj> elems = new ArrayList<Obj>();
		char c;
		while (true) {
			c = next();
			if (c == '"') {
				break;
			} else if (c == '(') {
				elems.add(new Atom("cpopen"));
			} else if (c == ')') {
				elems.add(new Atom("cpclose"));
			} else if (c == ' ') {
				elems.add(new Atom("cspace"));
			} else if (c == '\\') {
				c = next();
				if (c == 'n') {
					elems.add(new Atom("cnewline"));
				} else if (c == 't') {
					elems.add(new Atom("ctab"));
				} else if (c == '"') {
					elems.add(new Atom("cdquote"));
				} else if (c == '\'') {
					elems.add(new Atom("csquote"));
				} else {
					throw new L1Exception("Unknown escape sequence: \\" + c);
				}
			} else {
				elems.add(new Atom("c" + c));
			}
		}
		return Cons.fromList(elems);
	}

	private Obj getList() throws L1Exception {
		List<Obj> elems = new ArrayList<Obj>();
		while (true) {
			try {
				elems.add(getElem());
			} catch (L1Exception e) {
				if (e.getMessage() == "End of list.") {
					next();
					break;
				}
				throw e;
			}
		}
		return Cons.fromList(elems);
	}

	private Obj getElem() throws L1Exception {
		while (isWhitespace(peek())) {
			next();
		}
		if (peek() == ')') {
			throw new L1Exception("End of list.");
		} else if (peek() == '(') {
			next();
			return getList();
		} else if (peek() == '"') {
			next();
			return getString();
		} else if (peek() == '\'') {
			next();
			Obj o = getElem();
			return new Cons(new Atom("quote"), new Cons(o, Cons.nil));
		} else {
			return getAtom();
		}
	}

	private Atom getAtom() throws L1Exception {
		int start = pos;
		while (hasNext()) {
			if (isWhitespace(peek()) || peek() == '(' || peek() == ')') {
				break;
			}
			next();
		}
		return new Atom(code.substring(start, pos));
	}

	private static boolean isWhitespace(char c) {
		return c == ' ' || c == '\n' || c == '\t';
	}

	private char peek() throws L1Exception {
		try {
			return code.charAt(pos);
		} catch (IndexOutOfBoundsException e) {
			throw new L1Exception("Unexpected end of file.");
		}
	}

	private boolean hasNext() throws L1Exception {
		return pos < code.length();
	}

	private char next() throws L1Exception {
		char c = peek();
		pos++;
		return c;
	}

	public Obj parse() throws L1Exception {
		return getElem();
	}

	public static void main(String[] args) {
		try {
			System.out.println(new Parser("(test)").parse());
		} catch (L1Exception e) {
			System.out.println("Parsing failed: " + e.getMessage());
		}
	}
}
