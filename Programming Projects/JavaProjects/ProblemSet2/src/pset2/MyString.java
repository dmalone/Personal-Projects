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

	}

	public MyString(int x) {
		// postcondition: initializes ‘‘this’’ to represent ‘‘x’’

		String tempString = Integer.toString(x);
		// System.out.println(tempString.length());
		size = tempString.length();
		contents = new char[size];
		contents = tempString.toCharArray();

	}

	public int intValue() {
		// returns the integer represented by ‘‘this’’
		// throws number format exception error if value is now an int
		int n;

		try {
			// System.out.print(contents);
			// int n = 0;
			// System.out.println(Integer.parseInt(new String(contents)));
			// n = Integer.parseInt(new String(contents));
			n = Integer.valueOf(new String(contents));
		} catch (NumberFormatException o) {
			System.out.println(o.getClass());
			n = 0;
		}
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

	}

	public void append(int x) {
		// postcondition: modifies "this" to append the string
		// representation of x

		// convert the value of x into a string
		String tempString = Integer.toString(x);

		// use the append method for strings since appending a string of an int
		// would do the same thing
		append(tempString);
	}

	public void insert(int offset, int x) {
		// postcondition: modifies "this" to insert the string representation
		// of "x" starting at index "offset", making space for "x" by
		// moving the original characters up; if "offset" is invalid, throws
		// StringIndexOutOfBoundsException
		// String.length(), String.charAt(), and Integer.toString()
		int index = 0;
		char[] oldContents = contents;
		String test = new String(contents);

		//attempt to test if the index is out of bounds
		try {

			test.charAt(offset);

		} catch (StringIndexOutOfBoundsException o) {
			throw new StringIndexOutOfBoundsException();
			//System.out.println(o.getClass());
			//return;

		}

		//initialize variables
		String stringEquiv = Integer.toString(x);
		size += stringEquiv.length();
		contents = new char[size];
		
		//add in all the original characters before the insertion point
		for (int i = 0; i < offset; i++) {
			contents[i] = oldContents[i];
		}

		//add the string to be inserted
		for (int n = offset; n < (offset + stringEquiv.length()); n++) {
			contents[n] = stringEquiv.charAt(index);
			index++;
		}

		//add the remaining characters from the original string
		for (int x1 = offset + stringEquiv.length(); x1 <= size - 1; x1++) {
			contents[x1] = oldContents[offset];
			offset++;
		}
		return;
		

	}

}
