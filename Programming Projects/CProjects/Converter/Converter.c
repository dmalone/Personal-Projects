/*
Name: Thinh Lam
EID: TVL225
Section: 15995
EE312-Assignment 2
Purpose: To be able to effectively utilize the material that was taught 
		 in class and accurately apply it to the current assignment.
*/



#include <stdio.h>
#include <math.h>

#define EUROEQUIV .70646f /*Declares the Euro equivalent to 1 Dollar*/
#define	DOLLAREQUIV 1.4155f /*Declares the Dollar equivalent to 1 Euro*/
#define F_CONSTANT (9.0f/5.0f) /*Declares the constant found within the formula for converting Fahrenheit to Celsius*/
#define C_CONSTANT (5.0f/9.0f) /*Declares the constant found within the formula for converthing Celsius to Fahrenheit*/
#define OUNCE_EQUIV 16 /*Ounce equivalent for a pound*/
#define LB_EQUIV  2.2046226f /*Equivalent weight of kilograms in pounds*/
#define KGRAM_EQUIV .45359237f /*Equivalent weight of pounds in terms of kilograms*/
#define KILO_TO_OUNCES .028349523125f /*Equivalent amount of 1 ounce in terms of grams*/
#define MILE_EQUIV .6213712f /*The amount of miles per kilometers*/
#define KMETER_EQUIV 1.609344f /*The amount of kilometers per mile*/

int main(void)

{
	int number, dTimeH, dTimeM, aTimeH, aTimeM, totalDollar, totalCent, givenF, fEquiv, poundResult, ounceResult; /*List all existing integer type variables in the program*/
	float givenEuro, givenDollar, givenCent, totalEuro, givenC, cEquiv, givenKilo, roundCent, i, roundOunce, givenPound, roundF, givenOunce, totalKilo, givenKilometer, givenMile, finalMile, finalKilometer; /*Lists all existing float type variables in the program*/
	
	/*Prints out all possible choices and initiates an infinite while loop*/
	
	printf("1. Convert a given Austin time to Dublin time\n2. Convert a given Dublin time to Austin time\n3. Convert a given USD value to EUR\n4. Convert a given EUR value to USD value\n5. Convert a given Fahrenheit temperature to Celsius\n6. Convert a given Celsius temperature to Fahrenheit\n7. Convert a given weight in kg to pounds, ounces\n8. Convert a given weight in pounds, ounces to kg\n9. Convert a given distance in km to miles\n10. Convert a given distance in miles to km\n11. Stop doing conversions and quit the program\n");
	
	/*Prompts the user to pick an option and then executes the requested command. After the action has been performed, the 
	  program will once again loop back to the beginning and request another command unless it is told to terminate*/
	while(1)
			{
				printf("Please enter a number from above: ");
				scanf("%d", &number);

				/*Executes a switch statement which then checks the number that the user has pressed and then matches it with
				  the corresponding option*/
				switch(number)
					{
						/*Picking the first choice will take the user to case 1, where the time in terms of Austin will be converted
						  into Dublin time by adding 6 hours to the given time and then checking that value through a series of conditions
						  which must be met in order to confirm the time's validity*/
						case 1: printf("Please enter an Austin time to be converted (in hours and minutes): ");
								scanf("%d %d", &aTimeH, &aTimeM);
								dTimeH = aTimeH + 6%24;
								if (dTimeH > 24)
									printf("The equivalent time in Dublin is: %d %-2.2d (day after)\n", dTimeH - 24, aTimeM);
								else 
									if (dTimeH == 24)
										printf("The equivalent time in Dublin is: %d %-2.2d (day after)\n", dTimeH * 0, aTimeM);
									else printf("The equivalent time in Dublin is: %d %-2.2d\n", dTimeH, aTimeM);break;
						
						/*When selecting number 2, the user will be taken to case 2 where the hours in Dublin will be converted to Austin
						  time depending on the value of the hour.  The selection process is all controlled through a series of switch
						  statements*/
						case 2: printf("Please enter an Dublin time to be converted (in hours and minutes): ");
								scanf("%d %d", &dTimeH, &dTimeM);
								switch (dTimeH)
									{
										case 0: printf("The equivalent time in Austin is: 6 %-2.2d (day before)\n", dTimeM); break;
										case 1: printf("The equivalent time in Austin is: 7 %-2.2d (day before)\n", dTimeM); break;
										case 2: printf("The equivalent time in Austin is: 8 %-2.2d (day before)\n", dTimeM); break;
										case 3: printf("The equivalent time in Austin is: 9 %-2.2d (day before)\n", dTimeM); break;
										case 4: printf("The equivalent time in Austin is: 10 %-2.2d (day before)\n", dTimeM); break;
										case 5: printf("The equivalent time in Austin is: 11 %-2.2d (day before)\n", dTimeM); break;
										case 6: printf("The equivalent time in Austin is: 12 %-2.2d (day before)\n", dTimeM); break;
										case 7: printf("The equivalent time in Austin is: 1 %-2.2d\n", dTimeM); break;
										case 8: printf("The equivalent time in Austin is: 2 %-2.2d\n", dTimeM); break;
										case 9: printf("The equivalent time in Austin is: 3 %-2.2d\n", dTimeM); break;
										case 10 : printf("The equivalent time in Austin is: 4 %-2.2d\n", dTimeM); break;
										case 11: printf("The equivalent time in Austin is: 5 %-2.2d\n", dTimeM); break;
										case 12: printf("The equivalent time in Austin is: 6 %-2.2d\n", dTimeM); break;
										case 13: printf("The equivalent time in Austin is: 7 %-2.2d\n", dTimeM); break;
										case 14: printf("The equivalent time in Austin is: 8 %-2.2d\n", dTimeM); break;
										case 15: printf("The equivalent time in Austin is: 9 %-2.2d\n", dTimeM); break;
										case 16: printf("The equivalent time in Austin is: 10 %-2.2d\n", dTimeM); break;
										case 17: printf("The equivalent time in Austin is: 11 %-2.2d\n", dTimeM); break;
										case 18: printf("The equivalent time in Austin is: 12 %-2.2d\n", dTimeM); break;
										case 19: printf("The equivalent time in Austin is: 13 %-2.2d\n", dTimeM); break;
										case 20: printf("The equivalent time in Austin is: 14 %-2.2d\n", dTimeM); break;
										case 21: printf("The equivalent time in Austin is: 15 %-2.2d\n", dTimeM); break;
										case 22: printf("The equivalent time in Austin is: 16 %-2.2d\n", dTimeM); break;
										case 23: printf("The equivalent time in Austin is: 17 %-2.2d\n", dTimeM);  break;
								}								
							break;
						
						
						/*If the user has pressed the number 3, the case statement will select the USD to Euro converter, upon which
						  the user must input 2 integer values to be translated into Euros.  The program will then multiply each 
						  component of the USD by the Euro equivalent and add the two together to create the total Euro equivalent*/
						case 3: printf("Please enter the amount of U.S. Dollars (in dollars and cents) to be converted to Euros: "); 
								scanf("%f %f", &givenDollar, &givenCent); 
								totalEuro = (givenDollar * EUROEQUIV) + ((givenCent * EUROEQUIV)/100);
								printf("The equivalent amount in Euros is: %f Euros\n", totalEuro); break;
						
								
						/*If the user has selected option 4, the case statement will activate case 4 - the Euro to USD converter.  
						  The program will then prompt the user to input a real number in terms of Euros which will then be converted
					      into USD by multiplying the input by the dollar equivalent to form the amount of whole dollars.  It then 
						  subtracts that amount from the previus whole dollar to form the amount of cents in the dollar*/
						case 4: printf("Please enter the amount of Euros to be converted to U.S. Dollars: "); 
								scanf("%f", &givenEuro);
								totalDollar = (givenEuro * DOLLAREQUIV);
								totalCent = (((givenEuro * DOLLAREQUIV) - totalDollar) * 100);
								roundCent = (((givenEuro * DOLLAREQUIV) - totalDollar) * 100) - totalCent;
								if (roundCent >= .5)
									totalCent++;
								else totalCent = totalCent + 0;
														
						printf("The equivalent amount in U.S. Dollars is %d Dollar(s) and %d Cent(s)\n", totalDollar, totalCent); break;
														
						/*When the user presses the number 5, the case statement will automatically initiate the Fahrenheit
						  to Celsius conversion portion of the program.  In doing so, the program will prompt the user for 
						  a Fahrenheit value.  After the user has inputted a whole number value, the program will then plug 
						  it into the variable "givenF," where it will be used in the temperature conversion formula*/
						case 5: printf("Please enter a Fahrenheit temperature value to be converted to Celsius: "); 
								scanf("%d", &givenF); 
								cEquiv = (C_CONSTANT) * (givenF - 32); 
								printf("The equivalent temperature in Celsius is %f degree(s)\n", cEquiv); break;
						
						/*Selecting option 6 will initiate the Celsius to Fahrenheit temperature converter.  After the user
						  has inputted a real number value in terms of Celsius, the program will then assign that value to the
						  float type variable "givenC" and plug it into the Celsius to Fahrenheit conversion formula, returning
						  to the user the resulating Fahrenheit equivalent*/
						case 6: printf("Please enter a Celsius temperature value to be converted to Fahrenheit: "); 
								scanf("%f", &givenC);
								fEquiv = (F_CONSTANT) * givenC + 32;
								roundF = ((F_CONSTANT) * givenC + 32) - fEquiv;
								if (roundF >= .5)
									fEquiv++;									
								else fEquiv = fEquiv + 0;
														
								printf("The equivalent temperature in Fahrenheit is %d degree(s)\n", fEquiv); break;
						
					
						/*Option number 7 is the kilogram to ounces and pounds weight conversion.  It takes the given amount of kilograms
						  and multiplies it by the equivalent amount in pounds and subtracts the integer value of that from the float value
						  to get the remaining part, which represents the amount of pounds to convert to ounces.  That amount is then 
						  multiplied with 16 ounces to create the total equivalent amount of ounces*/
						case 7: printf("Please input an amount in kilograms to be converted to pounds and ounces: ");
								scanf("%f", &givenKilo);
								poundResult = givenKilo * LB_EQUIV;								
								ounceResult = (((givenKilo * LB_EQUIV) - poundResult) * OUNCE_EQUIV);
								roundOunce = (((givenKilo * LB_EQUIV) - poundResult) * OUNCE_EQUIV)-ounceResult;
								i = roundOunce;
								if (i > .4)
									ounceResult = ounceResult + 1;
								else 
									ounceResult = ounceResult + 0;								
								printf("The equivalent amound is %d pound(s) and %d ounce(s)\n", poundResult, ounceResult); break;
						

						/*Conversion number 8 changes the amount of pounds and ounces given into kilograms by taking each respective amount and
						  multiplying it by the equivalent kilogram amount.  The two resulting products are added together to form the final 
						  equivalent amount in kilograms*/
						case 8: printf("Please input an amount in pounds and ounces in the form of (number of pounds, number of ounces) to be converted to kilograms: ");
								scanf("%f, %f", &givenPound, &givenOunce);
								givenPound = givenPound * KGRAM_EQUIV;
								givenOunce = (givenOunce * KILO_TO_OUNCES);
								totalKilo = givenPound + givenOunce;
								printf("The equivalent amount is %f kilogram(s)\n", totalKilo); break;

						/*Multiplies the amount of kilometers by the equivalent amount of miles*/
						case 9: printf("Please input a distance (in kilometers) to be converted to miles: ");
								scanf("%f", &givenKilometer);
								finalMile = givenKilometer * MILE_EQUIV; 
								printf("The equivalent distance is %f mile(s)\n", finalMile); break;

						/*Muiltiplies the amount of miles by the equivalent amount of kilometers*/
						case 10: printf("Please input a distance (in miles) to be converted to kilometers: ");
								 scanf("%f", &givenMile);
								 finalKilometer = givenMile * KMETER_EQUIV; 
								 printf("The equivalent distance is %f kilometer(s)\n", finalKilometer); break; 
						
						
							
						/*If the user has picked number 11, the program will then terminate, thus ending the while loop and 
						  shutting down the program*/
						case 11: printf("Quitting program..\n"); 
								return 0; break;
						default: printf("Error. There is no such option from the above menu.\n");
					}
				
			}

	return 0;
}