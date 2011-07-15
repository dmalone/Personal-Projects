#include <iostream>
#include <string>


using namespace std;
class Dog
{ protected:
string name;
char gender;
public:
	Dog (string theName, char theGender) /* constructor */
	{ name = theName;
	gender = theGender;
	}
	~Dog ( ) { cout << "good bye: " << name << endl; } /* destructor */
	void bark(int n)
	{ cout << name << ":";
	for (int i =1; i <=n; i++) cout << "rff" << endl;
	}
	void sleep()
	{ cout << name << ": zzzz, zzzz" << endl; }
	void eat()
	{ cout << name << ": slurp" << endl; }
} /* end of Dog class definition */