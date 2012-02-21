#include <stdio.h>
#include <math.h>
int CountDigits(int num) 
{
	int x, count = 0;	
	for (x = num; x > 0; x = x/10) 
		count++;
	return count;
}
int BlahBlah (int test)
{
	int a, g;
	
	while (1)
			{
				if (test == 0) 
					{g = g - 1;}
				else a = test; return test;
			}	
}
int maxNum, i, z, y, c, h, l, j, n, digOne, digTwo, test, temp;
int a = 2;
int x = 0;
int e = 0;
int f = 1;
int g = 0;
int b = 1;
int d = 1;
int startNum = 2;
int listA[50000], listB[50000];
int main( void )

{
	while (1)
		{
			
			printf("Please input a maximum number to search for primes with: ");
			scanf("%d", &maxNum);
			if ( maxNum >= 2 && maxNum <= 50000)
				break;
			else printf("Incorrect input. Please try again.\n");
				
		}

	
	for (i = 0; i <= maxNum - 1; i++)
	{
		listA[i] = startNum;
		startNum = startNum + 1;
	}
	
	listB[0] = 2;
	for (i = 1; i<=maxNum; i ++)
	{
		for (z = 0; z <= (maxNum - 1)^(1/2); z++)
				{
					listA[e] = 0;
					z = z + (a - 1);
					e = e + a;
				}
		while (1)
			{
				a = listA[f];
				if (a != 0) 
					break;
				else f = f + 1;
						
			}
		if (a <= maxNum) 
			{
				listB[d] = a;
				d++;
				g++;
				e = f;
			}
		else break;	
	}
	
	
	g = maxNum -1;
		
	for (i = 0; i <= maxNum; i++)
		{			
			
			if ((i)%8 == 0){
				printf("\n");}
			else if (listB[g] <= maxNum && listB[g] != 0){
				printf("%10d", listB[g]);
				g--;}
				else break;					
		}
	printf("\n\n\nPALINDROMES:\n");	
	g = maxNum - 1;
	i = 1;
	while (1)
	{
			while (1)
				{
					z = listB[g];
					if (z >= 2) 
					break;
					else g = g - 1;
						
				}
			j = CountDigits (z);
			switch (j)
				{
				case 1:	printf("%10d", z); 
						if (i%7 == 0){
							printf("\n");}
						i++;
						break;				
				case 2: digOne = z/10;
						digTwo = z%10;
						digOne = digOne - digTwo;
						if (digOne == 0){
						printf("%10d", z);
						if (i%7 == 0){
							printf("\n");}
						i++;
						}
						else break;
				case 3: digOne = z/100;
						digTwo = z%10;
						digOne = digOne - digTwo;
						if (digOne == 0){
						printf("%10d", z);
						if (i%7 == 0){
							printf("\n");}
						i++;
						}
						else break;
				}
			
			/*if (i%7 == 0)
				printf("\n");*/
			g--;
			if (g < 0)
				break;
			
		}	


	printf("\n\nWHAT NOW????");
return 0;
}
