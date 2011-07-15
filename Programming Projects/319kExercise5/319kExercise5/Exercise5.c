const struct countTestCase{
unsigned char Letter;        // Letter for which to search
unsigned char Buffer[12];    // String in which to search
unsigned short CorrectCount; // proper result of Count()
};
typedef const struct countTestCase countTestCaseType;
countTestCaseType countTests[7]={
{ 'o', "Hello World", 2},
{ 'b', "Bill Bard  ", 0},
{ 'V', "Jon Valvano", 1},
{ 'a', "Yerraballi ", 2},
{ 's', "Mississippi", 4},
{ '2', "21212121212", 6},
{ '1', "11111111111", 11}};
unsigned short Count(unsigned char letter, unsigned char string[12]){
  unsigned char *ptr;    //create a pointer to index through array of characters
  unsigned char a;		//create a dummy variable to check for correct letter
  unsigned short count;    //create a counter
  unsigned char letterSearch;    //create a variable to hold the letter that we need to look for
  ptr = string;   //store beginning address into the pointer
  count = 0;     //re-initialize the counter
  letterSearch = letter;  //assign target letter to variable
  while(1)   //infinitely loop until the end of the string
  {
	a = *ptr;   //store the value of the current letter of the string into a
	if (*ptr == 0)  //if the value of the string is 0, break out of the while loop
	{break;}   
	if (a - letterSearch == 0)  //if the currently selected letter matches the target, increment the counter
	{count++;
	}      
	ptr++;    //increment the pointer
  }
  return count;  //return the number of occurences
}
int main(void){
  unsigned short i,result;
  unsigned short errors=0;
  for (i = 0; i < 7; i++){
    result = Count(countTests[i].Letter,countTests[i].Buffer);
    if (result != countTests[i].CorrectCount){
      errors++;
      printf("i=%d, result=%d\n",i,result);
    }
  }
  if (errors==0){
    printf("Program works");
  } else {
    printf("Does not work");
  }
  return 0;
} 