package pset4;

//Pair class was already defined and made by professor. This was left untouched
public final class Pair<S, T> {
	private S s;
	private T t;

	public Pair(S s, T t) {
		this.s = s;
		this.t = t;
	}

	public S one() {
		// postcondition: returns the first element of "this" pair
		return s;
	}

	public T two() {
		// postcondition: returns the second element of "this" pair
		return t;
	}

	public String toString() {
		return "<\"" + s + "\", " + t + ">";
	}

	public boolean equals(Object o) {
		// postcondition: returns "true" iff both elements match for "this" and
		// "o"
		if (!(o instanceof Pair))
			return false;
		Pair<S, T> p = (Pair<S, T>) o;
		return s == p.s && t == p.t;
	}

	public int hashCode() {
		return s.hashCode() + t.hashCode();
	}
}
