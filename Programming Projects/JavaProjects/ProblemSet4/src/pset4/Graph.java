package pset4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

	// public Map<T, Set<Pair<Integer, T>>> edges() {
	// return edges;
	// }

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
		// post-condition: returns a path (as a list of nodes) from "src" to
		// "dest" of
		// length "length" where "length" is the shortest length of any path
		// from
		// "src" to "dest", if such a path exists; otherwise, returns empty list
		// IMPLEMENT THIS METHOD (AND ANY HELPER METHODS)
		Queue<T> q = new LinkedList<T>();
		Queue<Pair<T, T>> q2 = new LinkedList<Pair<T, T>>();
		LinkedList<T> emptyList = new LinkedList<T>();
		Set<T> visitedNodes = new HashSet<T>();
		q.add(src);

		while (!q.isEmpty()) {
			T currentNode = q.poll();

			visitedNodes.add(currentNode);

			Set<Pair<Integer, T>> neighbors = this.edges.get(currentNode);

			Iterator<Pair<Integer, T>> it = neighbors.iterator();
			for (int i = 0; i < neighbors.size(); i++) {

				T current = it.next().two();

				if (!visitedNodes.contains(current)) {
					visitedNodes.add(current);
					q.add(current);
					Pair<T, T> hi = new Pair<T, T>(current, currentNode);
					q2.add(hi);
				}

				if (current.equals(dest)) {
					LinkedList<T> alist = new LinkedList<T>();
					T node2 = dest;

					while (!node2.equals(src)) {
						alist.addFirst(node2);
						Queue<Pair<T, T>> myQ = new LinkedList<Pair<T, T>>();
						myQ.addAll(q2);
						node2 = parentOf(node2, myQ);

					}

					alist.addFirst(src);

					if (alist.size() == length) {
						return alist;
					}

				}

			}

		}

		return emptyList;
	}

	// Determines the parent of the selected node.
	public T parentOf(T node, Queue<Pair<T, T>> q) {
		T parent = null;
		while (!q.isEmpty()) {
			Pair<T, T> test = q.poll();
			if (test.one().equals(node)) {
				// System.out.println(test.two());
				parent = test.two();
				return parent;
			}

		}
		return parent;
	}
}
