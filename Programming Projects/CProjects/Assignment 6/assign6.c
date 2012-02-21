/*
Name: Thinh Lam
EID: TVL225
Section: 15995
EE312-Assignment 4
Purpose: To be able to use the knowlegde of bifields in order to check for even parity and matching checksums in
lines of transmitted text.
*/

#include<stdio.h>
#include<string.h>
#define CODE "CodedText_1.bin"

int checkEven(int number);


int main (void)
{
	FILE *fp;
	int x;
	int checksum_from_file = 0;
	int checksum_as_calculated = 0;
	int j;
	int i;
	int track;
	char a;
	unsigned short int b;
	unsigned short int r;
	unsigned short int k;
	unsigned short int placeHolder[10];
	unsigned char fileName[100];
	unsigned char outputArray[1000];
	int flagger;
	int index = 0;
	int errorCount = 0;
	int errorCount2 = 0;

	fp = fopen (CODE, "rb");
	if (fp == NULL)
	{
		printf("Error. Cannot open the file.\n");
		return 0;
	}
	/*Beginning of the parity bit search process*/
	while(!feof(fp))
	{
		/*Read in 1 byte and store it into a variable for manipulation*/
		x = fread(fileName, 1, 1, fp);
		k = fileName[0];
		/*Check for an even amount of ones in the byte of data*/
		a = checkEven(k);
		/*If the number of ones is odd, replace the current slot in a temporary array with an "@"*/
		if(a != 1)
		{
			/*Enable the flagger variable to notify the program that it does not need to check the checksum*/
			outputArray[index] = 64;

			flagger = 1;
		}
		/*If the number of ones is even, right shift the byte of data by one bit and place into the array for future outputting*/
		else 
		{
			i = k >> 1;
			outputArray[index] = i;
		}
		/*Search for the line feed*/
		if(k != 20)
			checksum_from_file = checksum_from_file + k;
		/*If line feed is found, proceed to gather the next 16 bit value and store it into a variable*/
		else if (k == 20)
		{	checksum_from_file = checksum_from_file + k;
		x = fread(fileName, 1, 1, fp);
		placeHolder[0]=fileName[0];
		x = fread(fileName, 1, 1, fp);
		placeHolder[1]=fileName[0];
		b = placeHolder[0];
		r = placeHolder[1];
		b = b<<8;
		b = b | r;
		checksum_as_calculated = b;
		/*If the checksum and total character value disagree and the flagger is not on, replace everything in the array with an "@" 
		symbol*/

		if(checksum_as_calculated != checksum_from_file && flagger != 1)
		{	
			for(track = 0; track < index; track++)
			{
				outputArray[track] = 64;
			}
			errorCount2++;	
			flagger = 0;
		}
		/*Reset the y variable so that it may keep track of a new character total*/
		checksum_from_file = 0;
		/*Find all @ symbols and keep a counter of it*/
		for(track = 0; track <= index; track++)
		{
			if(outputArray[track] == 64 && flagger ==1 )
			{
				errorCount++;
			}
		}
		/*Proceed to print out all characters stored within the array while clearing it at the same time*/
		for(track = 0; track <= index; track++)
		{
			printf("%c", outputArray[track]);
			outputArray[track] = 204;
		}
		/*Reset the flag and index and prepare for a new cycle*/
		index = -1;
		flagger = 0;

		}
		index++;

	}

	printf("\n\nNumber of corrupted bytes: %d \n", errorCount);
	printf("Number of corrupted lines: %d \n", errorCount2);
	return 0;
}
/*Checks for an even amount of ones by isolating each bit and recording all the ones*/
int checkEven(int number)
{
	int x = 0;
	int j = 0;

	j = number & 1;
	if(j == 1)
		x++;
	j = number & 2;
	if (j == 2)
		x++;
	j = number & 4;
	if(j == 4)
		x++;
	j = number & 8;
	if(j == 8)
		x++;
	j = number & 16;
	if(j == 16)
		x++;	
	j = number & 32;
	if(j == 32)
		x++;
	j = number & 64;
	if(j == 64)
		x++;
	j = number & 128;
	if(j == 128)
		x++;
	if((x%2) == 1)
		return 0;
	else
		return 1;
}
