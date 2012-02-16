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
}
