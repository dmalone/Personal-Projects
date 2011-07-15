#include <stdio.h>
#include <math.h>

int CountDigits (int num);
int FindFourthDig (int value);
int FindSecondDig (int value);
void ResetVariables(void);
void InitializeListA(int maxNum);

int listA[10], listB[10];

/* Global Variables */
int a = 2;
int x = 0;
int numOfD = 0;
int result = 0;
int placeHolder2 = 0;
int placeHolder = 0;
int temp = 0;
int sum = 0;
int test3 = 0;
int test2 = 0;
int test = 0;
int digFour = 0;
int digThree = 0;
int digTwo = 0;
int digOne = 0;
int offset = 0;
int endNumber;
int numberB;
int answer;
int counter;
int subCounter;
int positionPointer = 0;
int listAPointer = 0;
int listBPointer = 1;
int maxNum = 0;
int startNum = 2;
int currentMultiple = 2;	


int main (void)
BEGIN_FCN	

	
	while(1)
	{
		int maxNum = 0;
		int startNum = 2;
		while (1)/*prompt user for number*/
		{
			printf("Please input a maximum number to search for primes with: ");
			scanf("%d", &maxNum);
			printf("Compiling... Please wait... ");
			if ( maxNum >= 2 && maxNum <= 50000)
				break;
			else printf("Incorrect input. Please try again.\n");				
		}

		/*fill in array listA*/
		InitializeListA(maxNum);
				
		listB[0] = 2;
		/*move from listA to listB*/
		for(counter = 1; counter <= maxNum; counter++)
		{
			for (subCounter = 0; subCounter <= (maxNum - 1); subCounter++)
					{	
						listA[positionPointer + offset] = 0;
						subCounter = subCounter + (currentMultiple - 1);
						positionPointer = positionPointer + currentMultiple;
					}
			
			while (1)
				{
					currentMultiple = listA[listAPointer];
					if (currentMultiple != 0){positionPointer = 0;
					break;}
					else listAPointer++;
						 	
				}
			
			if (currentMultiple <= maxNum && currentMultiple > 2)
				{	
					listB[listBPointer] = currentMultiple;
					listBPointer++;		
					offset = listAPointer;				
				}
		
		}
			
		
		endNumber = maxNum -1;
		/*find last number and print out primes*/
		while (1)
				{
					if (listB[endNumber] == 0)
					endNumber = endNumber - 1;
					else if (listB[endNumber] > maxNum)
						endNumber = endNumber - 1;
						else break;
				}	
		for (counter = 0; counter <= maxNum; counter++)
			{	
				if ((counter)%8 == 0){
					printf("\n");}
				else if (listB[endNumber] <= maxNum && listB[endNumber] != 0){
					printf("%10d", listB[endNumber]);
					endNumber--;}
					else break;					
			}

		printf("\n\n\nPALINDROMES:\n");	
		endNumber = maxNum - 1;
		counter = 1;
		/*find all palindromes within function*/
		while (1)
		{
				while (1)
					{
						numberB = listB[endNumber];
						if (numberB >= 2) 
						break;
						else endNumber = endNumber - 1;
							
					}
				numOfD = CountDigits (numberB);
				/*check for number of digits*/
				switch (numOfD)
					{
					case 1:	printf("%10d", numberB); 
							if (counter%7 == 0){
								printf("\n");}
							counter++;
							break;				
					case 2: digOne = numberB/10;
							digTwo = numberB%10;
							digOne = digOne - digTwo;
							if (digOne == 0){
							printf("%10d", numberB);
							if (counter%7 == 0){
								printf("\n");}
							counter++;
							}
							else break;
					case 3: digOne = numberB/100;
							digTwo = numberB%10;
							digOne = digOne - digTwo;
							if (digOne == 0){
								printf("%10d", numberB);
							if (counter%7 == 0){
								printf("\n");}
							counter++;
							}
							else break;
					case 4: break;
							
					case 5: digOne = numberB/10000;
							digTwo = numberB%10;
							digOne = digOne - digTwo;
							switch (digOne)
							{
								case 0: placeHolder = FindSecondDig (numberB);
										placeHolder2 = FindFourthDig (numberB);
										result = placeHolder - placeHolder2;
										if (result == 0){
											printf("%10d", numberB);}
										else break;
										if (counter%7 == 0){
											printf("\n");
											}
											counter++; break;									
					default: break;
							}							
					}
				endNumber--;
				if (endNumber < 0)
					break;		
			}		
		/*test for CPSPs*/
		printf("\n\n\nCPSP:\n\n");
		for (counter = 0; counter <= maxNum; counter++)
		{
			test = listB[counter];
			test2 = listB[counter + 1];
			test3 = listB[counter + 2];
			sum = test + test2 + test3;
			
			if (sum<=maxNum && sum !=0)
			{
				for(subCounter = 0; subCounter <=maxNum; subCounter++)
					{
						a = listB[subCounter];
						x = sum - a;
						if ( x == 0)
						{	
							printf("The consecutive numbers %d, %d, and %d have a sum of the prime number: %d\n", test, test2, test3, sum);
							
						}	
						
					}
			 }	
			
		}
		while(1)
		{
			printf("Would you like to input another number? (1 = Y/ 0 = N): ");
			scanf("%d", &answer);
			if ( answer == 0)
			{
				return 0;
			}
			else if (answer == 1)
			{
				ResetVariables();
				system("cls");
				break;
			}
			else printf("Incorrect input. Please try again.\n");
		}			
}
END_FCN

	

int CountDigits (int num)
BEGIN_FCN
	int x, count = 0;
	for (x = num; x > 0; x = x/10)
		count++;
	return count;
END_FCN
int FindFourthDig (int value)
BEGIN_FCN
	int x;
	x = (value%10000);
	x = x%100;
	x = x/10;
	return x;
END_FCN
int FindSecondDig (int value)
BEGIN_FCN
	int x;
	x = (value%10000);
	x = x/1000;
	return x;
END_FCN

void ResetVariables(void)
BEGIN_FCN
	a = 2;
	 x = 0;
	 numOfD = 0;
	 result = 0;
	 placeHolder2 = 0;
	 placeHolder = 0;
	 temp = 0;
	 sum = 0;
	 test3 = 0;
	 test2 = 0;
	 test = 0;
	 digFour = 0;
	 digThree = 0;
	 digTwo = 0;
	 digOne = 0;
	 offset = 0;
	 endNumber = 0;
	 numberB = 0;
	 answer = 0;
	 counter = 0;
	 subCounter = 0;
	 positionPointer = 0;
	listAPointer = 0;
	listBPointer = 1;
	maxNum = 0;
	startNum = 2;
	currentMultiple = 2;	

END_FCN

void InitializeListA(int maxNum)
BEGIN_FCN
	for (counter = 0; counter <= maxNum - 1; counter++)
	{
		listA[counter] = startNum;
		startNum = startNum + 1;
	}	
END_FCN