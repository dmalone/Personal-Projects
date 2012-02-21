package pset2;

public class MyString {
	// the string ‘‘this’’ is the sequence of characters in ‘‘contents’’
	// starting at index 0 and ending at index (size - 1); if size == 0,
	// ‘‘this’’ is empty;
	private char[] contents;
	private int size; // invariant: 0 <= size <= contents.length
	private final static int INITIAL_LENGTH = 10;

	public MyString() { // creates an empty string
		contents = new char[INITIAL_LENGTH];
		size = 0;
	}

	public String toString() {
		return "\"" + new String(contents, 0, size) + "\", arraylength: "
				+ contents.length;
	}

	public MyString(String s) {
		// postcondition: initializes ‘‘this’’ to represent ‘‘s’’
		size = s.length();
		contents = new char[size];
		// for (int i = 0; i <= size - 1; i++) {
		// contents[i] = s.charAt(i);
		// }
		contents = s.toCharArray();
		// System.out.print(contents);

	}

	public MyString(int x) {
		// postcondition: initializes ‘‘this’’ to represent ‘‘x’’

		String tempString = String.valueOf(x);
		size = tempString.length();
		contents = new char[size];
		contents = tempString.toCharArray();
		// System.out.print(contents);
	}

	public int intValue() {
		// returns the integer represented by ‘‘this’’
		// throws number format exception error if value is now an int

		System.out.print(contents);
		// int n = 0;
		int n = Integer.parseInt(new String(contents));
		// System.out.prin1t(contents);
		return n;
	}

	public void append(String s) {
		// postcondition: modifies ‘‘this’’ to append contents of ‘‘s’’
		// after the old string represented by ‘‘this’’, can only use
		// String.length(), String.charAt(), and Integer.toString()

		// index for adding individual letters
		int index = 0;

		// create a temporary char array containing the original string
		char[] temp = contents;

		// increase the size to accommodate for the new string that is being
		// appended
		size += s.length();

		// clears the char array 'contents'
		contents = new char[size];

		// first, add the original string to the new 'contents'
		for (int i = 0; i <= temp.length - 1; i++) {
			contents[i] = temp[i];

		}

		// then add to the end of the char array the additional string
		for (int i = temp.length; i <= size - 1; i++) {
			contents[i] = s.charAt(index);
			index++;
		}

		System.out.print(contents);

	}

	public void append(int x) {
		// postcondition: modifies "this" to append the string
		// representation of x

		// convert the value of x into a string
		String tempString = String.valueOf(x);

		// use the append method for strings since appending a string of an int
		// would do the same thing
		append(tempString);
	}

	public void insert(int offset, int x) {
		// postcondition: modifies "this" to insert the string representation
		// of "x" starting at index "offset", making space for "x" by
		// moving the original characters up; if "offset" is invalid, throws
		// StringIndexOutOfBoundsException

	}

}
