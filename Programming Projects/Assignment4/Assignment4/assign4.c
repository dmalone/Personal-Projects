/*
Name: Thinh Lam
EID: TVL225
Section: 15995
EE312-Assignment 4
Purpose: To be able to use a combination of pointers and previously learned programming techniques
		in order to successfully create a program that counts the number of SLOCs in a given file.
*/

#include<stdio.h>
#include<string.h>
#define BEGIN_FCN {
#define END_FCN }

/*Functions used in the program*/
int transferTotal(int d, int e);
int checkError(int g, int i, int p);

int main (void)
BEGIN_FCN
	/*Declared variables*/
	FILE *fp;
	FILE *fp2;
	char fileName[500];
	char a[5000];
	char b[5000] = {0};
	int lineCounter = 0;
	int positionPointer = 0;
	int error = 0;
	int counter = 0;
	char *str;
	char *str2;
	int isComment = 0;
	int isString = 0;
	int temp = 0;
	int startFunction = 0;
	int beginCounter = 0;
	int endCounter = 0;
	int totalSLOC = 0;		
	int i =0;
	str2 = &b;
	str = &a;
	/*Open a blank text file to write results to*/
	fp2 = fopen("SLOC Results.txt", "w");

	while(1)
	{
		
		int functionCounter = 0;
		system("cls");
		
		/*Prompts user for an input*/
		while(1)
		{
			printf("Please input a file to be read: ");
			gets(fileName);
			fp = fopen(fileName, "r");

			if (fp == NULL)
			{
				printf("Error. Cannot open %s\n", fileName);
			}
			else { printf("File %s has been successfully loaded.\n", fileName);		 
			break;		
			}
		}
		
		/*Print out the beginning parts of the table both on the console and in a text file*/
		printf("\nFile Counting Summary\n");
		fprintf(fp2,"File Counting Summary\n");
		printf("\nFilename: %s\n", fileName);
		fprintf(fp2,"\nFilename: %s\n", fileName);
		printf("\nFunctions (Read in the format of - filename: SLOCs): \n");
		fprintf(fp2,"\nFunctions (Read in the format of - filename: SLOCs): \n");
		printf("\n");
		fprintf(fp2, "\n");
		
		while(!feof(fp))
		{
			/*Beginning of SLOC scanning process*/

			fgets(fileName, 500, fp);
			positionPointer = 0;

			/*Look for a BEGIN_FCN*/
			while(fileName[positionPointer] != NULL)
			{
			/*If a BEGIN_FCN is encountered*/
			if (fileName[positionPointer] == 66 && fileName[positionPointer + 1] == 69 && fileName[positionPointer + 2] == 71 && fileName[positionPointer + 3] == 73 && fileName[positionPointer + 4] == 78 && fileName[positionPointer + 5] ==  95 && fileName[positionPointer + 6] == 70 && fileName[positionPointer + 7] == 67 && fileName[positionPointer + 8] == 78 && fileName[positionPointer + 10] != 123)
			{
				/*Increment all related counters*/
				startFunction = 1;
				beginCounter++;
				functionCounter++;
				while(1)
				{					
					/*Search for the specific function name*/
					if(str[positionPointer] == 32)
					{
						for (i = 0; i < 5000; i++)
							{ b[i] = 0;}
						i = 0;
						positionPointer++;
						while(str[positionPointer] != 32 && str[positionPointer] != 40)
						{
							b[i] = str[positionPointer];
							positionPointer++;
							i++;
						}

						break;
					}
					positionPointer++;
										
				}
				/*Print results to console and text file*/
				printf("\n%s\n ", str2);
				fprintf(fp2, "\n%s\n ", str2);
				
			}
			positionPointer++;
			/*If a function is encountered, break out of the loop and continue on*/
			if (startFunction == 1)
				break;
			}

			/*Semicolon exceptions*/
			while (fileName[positionPointer] != NULL)
			{

				/*Make exceptions for a semicolon in a comment by turning on a flag*/
				if (fileName[positionPointer] == 47 && fileName[positionPointer + 1] == 42)
				{isComment = 1;}
				else if (fileName[positionPointer] == 42 && fileName[positionPointer + 1] == 47)
				{isComment = 0;}
			
				/*Make exceptions for semicolons contained within quotes by using a flag*/
				 if (fileName[positionPointer] == 34)
				{ 
					if (isString == 1)
					{temp = 0;}
					else if (isString == 0)
					{temp = 1;}					
					isString = temp;
				}
				
				 /*Only add to the SLOC counter if the semicolon is not in a string or comment but is contained within the funtion*/
				if (fileName[positionPointer] == 59 && isComment == 0 && isString == 0 && startFunction == 1)
				{counter++;}
				positionPointer++;
			}

			/*Reset the position pointer*/
			positionPointer = 0;

			/*Check for END_FCN*/
			while(fileName[positionPointer] != NULL)
			{

			/*If an END_FCN is encountered, sum up the total amount of SLOCs thus far and print out the current number of SLOC in the function*/
			if (fileName[positionPointer] == 69 && fileName[positionPointer + 1] == 78 && fileName[positionPointer + 2] == 68 && fileName[positionPointer + 3] == 95 && fileName[positionPointer + 4] == 70 && fileName[positionPointer + 5] == 67 && fileName[positionPointer + 6] == 78 && fileName[positionPointer + 8] != 125)
			{		
				int temp = 0;								
				endCounter++;
				if (counter != 0)
				{printf("%d lines\n", counter);
				 fprintf(fp2,"%d lines\n", counter); }
				totalSLOC = transferTotal(counter, totalSLOC);
				counter = 0;
				
			}
			positionPointer++;
			}

			/*Copy the line of code into a temporary place holder to check for when a BEGIN_FCN is encountered*/
			strcpy(str, fileName);
			
		}
		
		/*Print out total results*/
		printf("\nTotal Functions: %d \n", functionCounter);
		fprintf(fp2, "\nTotal Functions: %d \n", functionCounter);
		printf("\nTotal SLOCs: %d \n", totalSLOC);
		fprintf(fp2, "\nTotal SLOCs: %d \n", totalSLOC);
		
		/*Check for any mismatches or errors*/
		error = checkError(beginCounter, endCounter, functionCounter);
		if (error == 1)
			return 0;

		/*Close the files*/
		fclose(fp);
		fclose(fp2);

		/*Prompt user for a repeat*/
		while(1)
		{
			int x = 0;
			printf("\nPlease press 1 to continue or 0 to quit: ");
			scanf("%d", &x);
			if (x == 1)
				break;
			if (x == 0)
				return 0;
			else printf("Incorrect input. Please try again.");
		}

	}
END_FCN

/*Functions*/

/*Transfers the current SLOCs into a total SLOC counter to keep track of the total SLOC*/
int transferTotal(int d, int e)
BEGIN_FCN
	int x = 0;
	x = e + d;
	return x;
END_FCN

/*Checks to see if correct amounts of BEGIN and END were inserted and see if there were any functions in the file or not*/
int checkError(int g, int i, int p)
BEGIN_FCN
	int u = 0;
	if (g != i)
		{
			system ("cls");
			printf("Error. Mis-Matched Begin-End. Terminating.\n");
			u = 1;
		}
		if (p == 0)
		{
			system ("cls");
			printf("Error. No functions were found in the file. Terminating.\n");
			u = 1;
		}
	return u;
END_FCN