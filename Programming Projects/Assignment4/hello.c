#include<stdio.h>
#define BEGIN_FCN {
#define END_FCN }	

int main()
   BEGIN_FCN

		printf("Hello World\n");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");
		printf("daslkjfasdf");

		return 0;
END_FCN
	
void fillArray(int a[])
BEGIN_FCN
int i, j;
fill = -3; /* initialization; neg value */
for( i = 0, j=2; i < sizeof(a); i++, j--)
a[i] = i + j * fill; /* of course ; a
comment may span multiple
lines; pages */
printf ("departing fill array function;");
return;
END_FCN