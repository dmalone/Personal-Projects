package pset4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class MinLengthLadderUsingAGraph {
	public static void main(String[] a) throws IOException {
		String filename = "words.dat";
		Graph<String> dictionary = new Graph<String>();
		initializeWordGraph(filename, dictionary);

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("enter starting word:");
		String start = stdin.readLine();
		System.out.println("enter ending word:");
		String end = stdin.readLine();
		System.out.println("enter min-length");
		int length = Integer.parseInt(stdin.readLine());
		List<String> stringList = dictionary.minLengthPath(start, end, length);
		if (stringList.isEmpty()) {
			System.out.println("No such word ladder!");
			return;
		}
		System.out.print(stringList);
		// generate a ladder from start to end if the min-length ladder is of
		// the
		// given length; otherwise, notify the user that no such ladder exists
		// IMPLEMENT THIS METHOD (AND ANY HELPER METHODS)
		// You must use the method Graph.minLengthPath(...) to solve
		// the min-length word ladder problem
	}

	private static void initializeWordGraph(String filename, Graph<String> dict)
			throws IOException {
		// post-condition: initialize "dict" to represent the word graph for the
		// collection
		// of words in "filename"
		// IMPLEMENT THIS METHOD (AND ANY HELPER METHODS)

		BufferedReader in = new BufferedReader(new FileReader(filename));
		String text;
		while (in.ready()) {
			text = in.readLine();
			if (text.charAt(0) != '*') {
				dict.addNode(text.substring(0, 5));
			}

		}
		//System.out.println(dict.nodes());
		Iterator<String> it = dict.nodes().iterator();
		

		for (int i = 0; i < dict.nodes().size(); i++) {
			Iterator<String> it2 = dict.nodes().iterator();
			String currentWord = it.next();
			for (int j = 0; j < dict.nodes().size(); j++) {
				String comparedWord = it2.next();
				//System.out.println("Word Being Compared: " + currentWord + ", "+ comparedWord);
				if (oneDiff(currentWord, comparedWord)) {
					//System.out.println("Words are one-different.***********************************************************************************************");
					dict.addEdge(currentWord, comparedWord, oneDiffIndex(currentWord, comparedWord));
				}
			}
		}
		//System.out.println(dict.edges());
	}

	private static boolean oneDiff(String word1, String word2) {
		int counter = 0;
		for (int i = 0; i < 5; i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				counter++;
				
			}
		}
		if (counter == 1) {
			return true;
		} else {
			return false;
		}
	}
	private static int oneDiffIndex(String word1, String word2) {
		int index = 0;
		for (int i = 0; i < 5; i ++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				index = i;
			}
		}
		return index;
	}
}
