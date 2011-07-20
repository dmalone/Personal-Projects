/*
Name: Thinh Lam
EID: TVL225
Section: 15995
EE312-Assignment 4
Purpose: To be able to use double pointers and object oriented programming techniques to create a program that will perform arithmetic on
matrices.
*/
#include <iostream>
#include <string>
#include <stdio.h>
#include "matrix.h"
#include <time.h>

#define TEST 0




int main()
{
/*Preprocessor debug check for the average time calculation*/
#if(TEST == 1)
	clock_t start, end;
	double runTime = 0;
#endif

	int x = 0;
	int y = 0;
	int x2 = 0;
	int y2 = 0;

	while(1)
	{
		int choice = 0;
/*Prompt user for dimensions*/
		printf("FIRST MATRIX\n\n\n");
		printf("Please type in the desired length of your rows: ");
		scanf("%d", &x);
		printf("Please type in the desired length of your columns: ");
		scanf("%d", &y);
		system("cls");

		printf("SECOND MATRIX\n\n\n");
		printf("Please type in the desired length of your rows: ");
		scanf("%d", &x2);
		printf("Please type in the desired length of your columns: ");
		scanf("%d", &y2);
		system("cls");



		Matrix matrixA(x,y);
		Matrix matrixB(x2,y2);

		printf("Which operation would you like to do(type in a number)? \n1. Addition the two matrices. \n2. Subtract the two matrices. \n3. Multiply the two matrices.\n4. Transpose a matrix. \n5. Multiply a matrix by a scalar number. \nChoice: ");
		scanf("%d", &choice);
		system("cls");

		switch(choice)
		{
			/*If 1, add the two matrices*/
		case 1: 
			if(x == x2 && y == y2)
			{	
				matrixA.add(matrixA, matrixB);
				/*Time average addition time*/
#if (TEST == 1)
				{
					for(int i = 0; i < 100; i++)
					{start = clock();
					matrixA.add(matrixA, matrixB);
					end = clock();
					runTime = runTime + ((end - start)/(double)CLOCKS_PER_SEC);
					}
					printf("%g Seconds\n", runTime/100);
				}

#endif
				break;
			}
			else {printf("Error:  The matrices must have the same dimensions. Please try again.\n");
			break;}
		case 2: 
/*If 2, subtract matrixA with matrixB*/
			matrixA.subtract(matrixA, matrixB);
			matrixA.freeMatrix(matrixA);
			break;
		case 3:
			/*If 3, multiply if conditions are met*/
			if(y == x2)
			{
				/*Time average multiplication time*/
#if (TEST == 1)
			{
			for(int i = 0; i < 100; i++)
			{
				start = clock();
				matrixA.multiply(matrixA, matrixB);
				end = clock();
				runTime = runTime + ((end - start)/(double)CLOCKS_PER_SEC);
			}
			printf("%g Seconds\n", runTime/100);}
#endif
			matrixA.multiply(matrixA, matrixB);
			matrixA.freeMatrix(matrixA);
			break;
			}
			else printf("Incorrect dimensions.  The columns of the first matrix must equal the rows of the second matrix.\n");break;
		case 4: /*Transpose selected matrices*/
			while(1)
				{			
					printf("Which matrix would you like to transpose?\n1. Matrix A\n2. Matrix B\nChoice: ");
					scanf("%d", &choice);
					if (choice == 1)
						matrixA.transpose(matrixA);break;
					 if (choice == 2)
					{matrixB.transpose(matrixB);break;}
						 printf("Error: Incorrect selection. Please try again.\n\n");
				}break;
		case 5:/*If 5, multiply by selected scalar*/
			printf("Please input a scalar number that you would like to multiply by: ");
			scanf("%d", &x);
			matrixA.scalar(matrixA, x);			
			matrixB.scalar(matrixB, x);

			break;
			/*Defaulat error*/
		default:  printf("You did not pick one of the available choices.");
			return 0;
		}
		while(1)
		{/*Primpt for repeat*/
			printf("Would you like to do it again?(Y = 1/N = 0)\nChoice: ");
			scanf("%d", &x);

			if(x == 1){
				system("cls");
				break;}
			else if(x == 0)
				return 0;
			else printf("Error:  The answer you chose is not a valid choice. Please try again.\n\n");
		}	
		x = 0;
		y = 0;
	}

	return 0;
}
/*Average time for adding two 900 x 900 matrices: 0.01217 seconds
  Average time for adding two 1800 x 1800 matrices: 0.06038 seconds
  Average time for multiplying two 200 x 200 matrices: 0.19376 seconds
  Average time for multiplying two 400 x 400 matrices: 1.31368 seconds*/