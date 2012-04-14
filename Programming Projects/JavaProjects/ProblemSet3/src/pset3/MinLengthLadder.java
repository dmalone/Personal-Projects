package pset3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MinLengthLadder {
	public static void main(String[] a) throws IOException {
		String filename = "words.dat"; // your code
										// goes here
		Set<String> dictionary = new HashSet<String>();
		initializeDictionary(filename, dictionary);

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("enter starting word:");
		String start = stdin.readLine();
		System.out.println("enter ending word:");
		String end = stdin.readLine();
		System.out.println("enter min-length");
		String mLength = stdin.readLine();

		try {
			Integer.parseInt(mLength);
		} catch (NumberFormatException e) {
			System.out.println("The number is invalid!");
			return;
		}

		int length = Integer.parseInt(mLength);

		// input validation

		if ((start.length() != 5) || (end.length() != 5)) {
			System.out.println("Invalid word length!");
			return;
		}
		if (!dictionary.contains(start) && !dictionary.contains(end)) {
			System.out.println(start + " and " + end + ":"
					+ " Not legitimate 5-letter word(s) from the dictionary!");
			return;
		}
		if (!dictionary.contains(start)) {
			System.out.println(start + ":"
					+ " Not legitimate 5-letter word(s) from the dictionary!");
			return;
		}
		if (!dictionary.contains(end)) {
			System.out.println(end + ":"
					+ " Not legitimate 5-letter word(s) from the dictionary!");
			return;
		}

		List<String> ladder = minLengthLadder(start, end, length, dictionary);

		if (!ladder.isEmpty()) {
			System.out.println(ladder);
		} else {
			System.out.println("No ladder exists with the given length!");
		}
	}

	// opens file "filename" to initialize the set "dictionary" to contain
	// the words in the file
	static void initializeDictionary(String filename, Set<String> dictionary)
			throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String text;
		while (in.ready()) {
			text = in.readLine();
			if (text.charAt(0) != '*') {
				dictionary.add(text.substring(0, 5));
			}

		}

	}

	// returns a min-length word ladder between "start" and "end" of size
	// "length"
	static LinkedList<String> minLengthLadder(String start, String end,
			int length, Set<String> dictionary) {
		LinkedList<String> alist = new LinkedList<String>();
		LinkedList<String> alist2 = new LinkedList<String>();

		Queue<LinkedList<String>> myQ = new LinkedList<LinkedList<String>>();
		alist.add(start);
		myQ.add(alist);

		while (!myQ.isEmpty()) {
			LinkedList<String> s = myQ.poll();

			if (s.getLast().equals(end) && s.size() == length) {
				return s;

			}
			if (s.size() > length) {
				break;
			}
			alist = getOneDiffWords(s.getLast(), dictionary);
			for (int i = 0; i < alist.size(); i++) {
				LinkedList<String> list2 = new LinkedList<String>();
				if (!alist.get(i).contains(start)) {
					list2.addAll(s);
					list2.add(alist.get(i));
					dictionary.remove(alist.get(i));
					if (!myQ.contains(list2)) {
						myQ.add(list2);
					}
				}

			}
		}

		return alist2;

	}

	static LinkedList<String> getOneDiffWords(String start, Set<String> dictionary) {
		Iterator<String> it = dictionary.iterator();
		LinkedList<String> list = new LinkedList<String>();

		for (int i = 0; i < dictionary.size(); i++) {
			String data = it.next();
			if (oneDiff(start, data)) {
				list.add(data);

			}
		}

		return list;

	}

	static boolean oneDiff(String word1, String word2) {
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
}
