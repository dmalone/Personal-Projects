package pset2;

public class Tester {
	public static void main(String args[]) {
		MyString o1 = new MyString();
		System.out.println(o1);

		MyString o2 = new MyString("This is a test");
		System.out.println(o2);

		o2 = new MyString(123);
		System.out.println(o2);

		o2 = new MyString(-123);
		System.out.println(o2);

		System.out.println(o2.intValue());

		o2.append(7);
		System.out.println(o2);

		o2.append(" is a number");
		System.out.println(o2);

		MyString o3 = new MyString();
		o3.insert(0, 456);
		System.out.println(o3);

		o3.insert(5, 3000);

	}
}
