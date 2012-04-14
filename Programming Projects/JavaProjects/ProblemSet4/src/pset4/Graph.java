package pset4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
	// class invariant: if there is an edge "m->n", both "m" and "n" are in
	// "nodes"
	private Set<T> nodes = new HashSet<T>(); // set of nodes
	// adjacency set, where each edge has a label (integer)
	private Map<T, Set<Pair<Integer, T>>> edges = new HashMap<T, Set<Pair<Integer, T>>>();

	public Set<T> nodes() {
		return nodes;
	}
	public Map<T, Set<Pair<Integer, T>>> edges() {
		return edges;
	}

	public void addNode(T n) {
		// postcondition: adds node n to the set of nodes, and if the
		// node is not previously in the set, updates the adjacency-set
		// representation to map the given node to an empty set
		nodes.add(n);
		Set<Pair<Integer, T>> nbrs = edges.get(n);
		if (nbrs == null) {
			nbrs = new HashSet<Pair<Integer, T>>();
			edges.put(n, nbrs);
		}
	}

	public void addEdge(T m, T n, int label) {
		// postcondition: adds edge "m -> n" with edge-label "label" to "this";
		// if any of the two nodes is not already in the set of nodes, the
		// set is updated to include them
		nodes.add(m);
		nodes.add(n);
		Set<Pair<Integer, T>> nbrs = edges.get(m);
		nbrs.add(new Pair<Integer, T>(label, n));
		edges.put(m, nbrs);
	}

	public List<T> minLengthPath(T src, T dest, int length) {
		return null;
	// post-condition: returns a path (as a list of nodes) from "src" to "dest" of
	// length "length" where "length" is the shortest length of any path from
	// "src" to "dest", if such a path exists; otherwise, returns empty list
	// IMPLEMENT THIS METHOD (AND ANY HELPER METHODS)
	
	}
}
