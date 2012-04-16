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
		Queue<Pair<T, T>> pathQ = new LinkedList<Pair<T, T>>();
		LinkedList<T> emptyList = new LinkedList<T>();
		Set<T> visitedNodes = new HashSet<T>();

		q.add(src);

		// Use breadth-first search to traverse through all nodes
		while (!q.isEmpty()) {
			// Remove first element at top of queue
			T currentNode = q.poll();
			// Update visited nodes set
			visitedNodes.add(currentNode);
			// Find all children of the current node
			Set<Pair<Integer, T>> neighbors = this.edges.get(currentNode);
			// Iterate through all elements of the children set and see if the
			// end word exists
			Iterator<Pair<Integer, T>> it = neighbors.iterator();
			for (int i = 0; i < neighbors.size(); i++) {
				// Look at each children node (it.next().two() because each
				// children node is organized as <one-difference index, word>
				T current = it.next().two();
				// If the visited nodes set does not already contain the child
				// node, add it to the set
				if (!visitedNodes.contains(current)) {
					visitedNodes.add(current);
					q.add(current);
					// Also, add a pair that holds <child, parent> to a separate
					// queue in order to keep track of the path. That way, we
					// can backtrack if we happen to find the end word
					Pair<T, T> nodefatherPair = new Pair<T, T>(current,
							currentNode);
					pathQ.add(nodefatherPair);
				}
				// While search the children nodes, if we find the destination,
				// immediately dequeue the pathQ to find the complete path
				if (current.equals(dest)) {
					// List that is to be returned and printed to the console
					LinkedList<T> alist = new LinkedList<T>();
					// Make a T object that holds the node value
					T node2 = dest;

					while (!node2.equals(src)) {
						// Populated "alist," starting from the end word
						alist.addFirst(node2);
						// Make a new queue to hold a copy of the original pathQ
						// so it can be reused indefinitely
						Queue<Pair<T, T>> myQ = new LinkedList<Pair<T, T>>();
						myQ.addAll(pathQ);
						// Find the parent of the current node and make that the
						// new node of interest to find its parent. Keep
						// recording this and updating until the source word is
						// met
						node2 = parentOf(node2, myQ);

					}
					// Lastly, add the first starting word to complete the word
					// latter
					alist.addFirst(src);
					// If the ladder is of the desired length, return the
					// completed ladder
					if (alist.size() == length) {
						return alist;
					}

				}

			}

		}
		// If no ladder of the specified length can be found, return an
		// emptyList
		return emptyList;
	}

	// Determines the parent of the selected node.
	public T parentOf(T node, Queue<Pair<T, T>> q) {
		T parent = null;
		// Take the pathQ, and see if node is in one of the pairs. If it is,
		// then its parent is the complimentary node in the pair
		while (!q.isEmpty()) {
			Pair<T, T> test = q.poll();
			if (test.one().equals(node)) {
				parent = test.two();
				return parent;
			}

		}
		return parent;
	}
}
