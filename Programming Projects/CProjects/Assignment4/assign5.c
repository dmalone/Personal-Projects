/*
Name: Thinh Lam
EID: TVL225
Section: 15995
EE312-Assignment 5
Purpose: To be able to implement the use of structs in the form of linked lists in conjunction with previously learned programming 
techniques into a program that will find the mean, median and mode of not only the individual files, but a combination of all of 
them as well.
*/

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define SCOREONE "scores1.txt"
#define SCORETWO "scores2.txt"
#define SCORETHREE "scores3.txt"
#define BEGIN_FCN {
#define END_FCN }

enum SAME {FALSE, TRUE};

/*define all structs*/

/*struct for file one*/
struct node {
	double score;
	struct node *next;
};

/*struct for file two*/
struct node2 {
	double score2;
	struct node2 *next;
};

/*struct for file three*/
struct node3 {
	double score3;
	struct node3 *next;
};

/*struct for tracking the number count*/
struct gradetracker {
	double gradeNum;
	int gradeCount;
	struct gradetracker *next;
};

/*struct for containing the merged list of numbers*/
struct mergedlist {
	double totalscore;
	struct mergedlist *next;
};

/*all definitions of functions*/
void organize(struct node *list, struct node *tempor);
void averageme(struct node *list, struct node *tempor);
void medianme(struct node *list, struct node *tempor);

void organize2(struct node2 *list, struct node2 *tempor);
void averageme2(struct node2 *list, struct node2 *tempor);
void medianme2(struct node2 *list, struct node2 *tempor);

void organize3(struct node3 *list, struct node3 *tempor);
void averageme3(struct node3 *list, struct node3 *tempor);
void medianme3(struct node3 *list, struct node3 *tempor);

void mergeme(struct node *header, struct node2 *header2, struct node *tempor, struct node2 *tempor2, struct mergedlist *mergehead, struct mergedlist *mergetemp, struct node3 *header3, struct node3 *tempor3);
void mergeme2(struct node2 *starter, struct node3 *starter2);

void modeme(struct gradetracker *list, struct gradetracker *tempor);



int main (void)
BEGIN_FCN
	FILE *fp;
	FILE *fp2;
	FILE *fp3;
	FILE *fp4;
	FILE *fp5;
	double i, n;
	char fileName[] = SCOREONE;
	char fileName2[] = SCORETWO;
	char fileName3[] = SCORETHREE;
	

	/*assign all possible pointers to structs*/
	struct node *first = NULL, *temp, *ptr;
	struct node2 *first2 = NULL, *temp2, *ptr2;
	struct node3 *first3 = NULL, *temp3, *ptr3;
	struct mergedlist *first4 = NULL, *temp4, *ptr4;
	struct gradetracker *head = NULL, *tempr, *ptrr;


	typedef struct node ExamScores;
	typedef struct node2 ExamScores2;
	typedef struct node3 ExamScores3;





	/*begin opening all needed files*/
	fp = fopen(fileName, "r");
	fp2 = fopen(fileName2, "r");
	fp3 = fopen(fileName3, "r");
	fp4 = fopen("C://ee312/ScoresResult.txt", "w");
	fp5 = fopen("C://ee312/ScoresAll.txt", "w");

	/*beginning of statistical calculations for first file*/
	printf("Statistics for %s \n", fileName);
	fprintf(fp4,"Statistics for %s \n", fileName);
	while(!feof(fp))
	{
		int e = FALSE;
		int counter;
		fgets(fileName, 100, fp);
		n = atof(fileName);

		/*transfer numbers over to structs*/

		temp = malloc(sizeof(ExamScores));
		temp -> score = n;
		temp -> next = first;
		first = temp;
		/*create a new counter node if it hasn't been made already*/
		for(tempr = head; tempr != NULL; tempr = tempr ->next)
		{
			if(n == tempr->gradeNum)
			{
				e = TRUE;
				tempr->gradeCount++;
			}	

		}

		/*increment an existing counter node to the corresponding number*/
		if(e == FALSE)
		{
			tempr = malloc(sizeof(struct gradetracker));
			tempr -> gradeNum = n;
			tempr ->gradeCount = 1;
			tempr -> next = head;
			head = tempr;
		}
		e = FALSE;

	}

	/*perform necessary statistical calculations*/
	organize(first, temp, fp4);
	averageme(first, temp, fp4);
	medianme(first, temp, fp4);
	tempr = head;
	modeme(head, tempr, fp4);


	/*clear out the gradetracker and prepare for a new set of numbers to track*/
	memset(head, NULL, sizeof(struct gradetracker));



	/*move to next file*/
	printf("\n\nStatistics for %s \n", fileName2);
	fprintf(fp4,"\n\nStatistics for %s \n", fileName2);
	while(!feof(fp2))
	{
		int s = FALSE;
		fgets(fileName2, 100, fp2);
		n = atof(fileName2);
		/*transfer data over to linked lists*/
		temp2 = malloc(sizeof(ExamScores2));
		temp2 -> score2 = n;
		temp2 -> next = first2;
		first2 = temp2;

		/*create a new node in the counter linked list to keep track of any new numbers*/
		for(tempr = head; tempr != NULL; tempr = tempr ->next)
		{
			if(n == tempr->gradeNum)
			{
				s = TRUE;
				tempr->gradeCount++;
			}	

		}
		/*if a node for that number already exists, increment it*/
		if(s == FALSE)
		{
			tempr = malloc(sizeof(struct gradetracker));
			tempr -> gradeNum = n;
			tempr ->gradeCount = 1;
			tempr -> next = head;
			head = tempr;
		}
		s = FALSE;

	}


	/*perform necessary statistical calulations*/
	organize2(first2, temp2, fp4);	
	averageme2(first2, temp2, fp4);
	medianme2(first2, temp2, fp4);
	tempr = head;
	modeme(head, tempr, fp4);

	/*clear grade tracker and prepare for next file*/
	memset(head, NULL, sizeof(struct gradetracker));


	/*move on to third and final file*/
	printf("\n\nStatistics for %s \n", fileName3);
	fprintf(fp4,"\n\nStatistics for %s \n",fileName3);

	/*transfer data from file into appropriate linked lists*/
	while(!feof(fp3))
	{
		int q = FALSE;
		fgets(fileName3, 100, fp3);
		n = atof(fileName3);

		temp3 = malloc(sizeof(ExamScores3));
		temp3 -> score3 = n;
		temp3 -> next = first3;
		first3 = temp3;

		/*create nodes for any new number encountered*/
		for(tempr = head; tempr != NULL; tempr = tempr ->next)
		{
			if(n == tempr->gradeNum)
			{
				q = TRUE;
				tempr->gradeCount++;
			}	

		}

		/*if node already exists, increment that specific node*/
		if(q == FALSE)
		{
			tempr = malloc(sizeof(struct gradetracker));
			tempr -> gradeNum = n;
			tempr ->gradeCount = 1;
			tempr -> next = head;
			head = tempr;
		}
		q = FALSE;

	}

	/*perform all necessary calculations*/
	organize3(first3, temp3, fp4);	
	averageme3(first3, temp3, fp4);
	medianme3(first3, temp3, fp4);
	tempr = head;
	modeme(head, tempr, fp4);

	/*clear grade tracker linked list*/
	memset(head, NULL, sizeof(struct gradetracker));
	temp3 = first3;
	temp4 = first4;
	mergeme(first, first2, temp, temp2, first4, temp4, first3, temp3, fp5, fp4);

	memset(first, NULL, sizeof(struct node));
	memset(first2, NULL, sizeof(struct node2));
	memset(first3, NULL, sizeof(struct node3));
	memset(head, NULL, sizeof(struct gradetracker));

	/*close all files*/
	fclose(fp);
	fclose(fp2);
	fclose(fp3);
	fclose(fp4);
	fclose(fp5);
	printf("\nStatistics and a list of the combined score list will be sent to 'C://ee312/'\n");
	printf("Please press enter to continue.");

	getchar();






END_FCN

/*finds mode of particular linked list*/
void modeme(struct gradetracker *list, struct gradetracker *tempor, FILE *pointer)
BEGIN_FCN
	struct gradetracker *point;
	double i = 0;
	double p = 0;

	tempor = list;
	point = tempor;

	/*hold highest count*/
	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		if(tempor->gradeCount > p)
		{
			i = tempor->gradeNum;
			p = tempor->gradeCount;
		}
		point = tempor;
	}

	printf("Mode, %f", i);
	fprintf(pointer,"Mode, %f", i);

	/*find other identical counts with the exception of the current number being held*/
	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		if(tempor->gradeCount == p && tempor->gradeNum != i )
		{printf(", %f", tempor->gradeNum);
		fprintf(pointer,", %f", tempor->gradeNum);}
	}
	printf("\n");
	fprintf(pointer, "\n");
END_FCN


/*sorts all numbers of first file into descending order*/
void organize(struct node *list, struct node *tempor)
BEGIN_FCN
	struct node *str;
	int i = 0;
	double j = 0;

	str = tempor;
	while(i != 1000000)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if (tempor->score > str->score)
			{
				j = str->score;
				str->score = tempor->score;
				tempor->score = j;

			}
			str = tempor;		
		}
		str = list;
		tempor = list;
		i++;
	}
END_FCN

/*sorts all numbers of second file into descending order*/

void organize2(struct node2 *list, struct node2 *tempor)
BEGIN_FCN
	struct node2 *str;
	int i = 0;
	double j = 0;

	str = tempor;
	while(i != 1000000)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if (tempor->score2 > str->score2)
			{
				j = str->score2;
				str->score2 = tempor->score2;
				tempor->score2 = j;

			}
			str = tempor;		
		}
		str = list;
		tempor = list;
		i++;
	}
END_FCN

/*sorts all numbers of third file into descending order*/
void organize3(struct node3 *list, struct node3 *tempor)
BEGIN_FCN
	struct node3 *str;
	int i = 0;
	double j = 0;

	str = tempor;
	while(i != 1000000)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if (tempor->score3 > str->score3)
			{
				j = str->score3;
				str->score3 = tempor->score3;
				tempor->score3 = j;

			}
			str = tempor;		
		}
		str = list;
		tempor = list;
		i++;
	}
END_FCN

/*fine the mean of the first list of numbers*/
void averageme(struct node *list, struct node *tempor, FILE *pointer)
BEGIN_FCN
	struct node *str2;
	double ncount = 0;
	double total = 0;

	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		ncount++;
		total = tempor->score + total;
	}
	total = (total)/(ncount);
	printf("Mean,%f\n", total);
	fprintf(pointer,"Mean, %f\n", total);
END_FCN

/*fine the mean of the second list of numbers*/
void averageme2(struct node2 *list, struct node2 *tempor, FILE *pointer)
BEGIN_FCN
	struct node2 *str2;
	double ncount = 0;
	double total = 0;

	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		ncount++;
		total = tempor->score2 + total;
	}
	total = (total)/(ncount);
	printf("Mean,%f\n", total);
	fprintf(pointer,"Mean, %f\n", total);
END_FCN

/*fine the mean of the third list of numbers*/
void averageme3(struct node3 *list, struct node3 *tempor, FILE *pointer)
BEGIN_FCN
	struct node3 *str2;
	double ncount = 0;
	double total = 0;

	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		ncount++;
		total = tempor->score3 + total;
	}
	total = (total)/(ncount);
	printf("Mean,%f\n", total);
	fprintf(pointer,"Mean, %f\n", total);
END_FCN

/*fine the median of the first list of numbers*/
void medianme(struct node *list, struct node *tempor, FILE *pointer)
BEGIN_FCN
	int valueCount = 0;
	int oddEven = 0;
	int numCount = 1;
	double median = 0;
	double median2 = 0;
	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		valueCount++;
	}

	oddEven = valueCount % 2;
	if(oddEven == 0)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount/2)
			{
				median = tempor->score;
				tempor = tempor->next;
				median2 = tempor->score;
				median = (median + median2)/2;
				printf("Median, %f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
	else
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount - (valueCount/2))
			{
				median = tempor->score;
				printf("Median, %f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
END_FCN

/*fine the median of the second list of numbers*/
void medianme2(struct node2 *list, struct node2 *tempor, FILE *pointer)
BEGIN_FCN
	int valueCount = 0;
	int oddEven = 0;
	int numCount = 1;
	double median = 0;
	double median2 = 0;
	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		valueCount++;
	}

	oddEven = valueCount % 2;
	if(oddEven == 0)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount/2)
			{
				median = tempor->score2;
				tempor = tempor->next;
				median2 = tempor->score2;
				median = (median + median2)/2;
				printf("Median, %f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
	else
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount - (valueCount/2))
			{
				median = tempor->score2;
				printf("Median, %f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
END_FCN

/*fine the median of the third list of numbers*/
void medianme3(struct node3 *list, struct node3 *tempor, FILE *pointer)
BEGIN_FCN
	int valueCount = 0;
	int oddEven = 0;
	int numCount = 1;
	double median = 0;
	double median2 = 0;
	for (tempor = list; tempor != NULL; tempor = tempor->next)
	{
		valueCount++;
	}

	oddEven = valueCount % 2;
	if(oddEven == 0)
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount/2)
			{
				median = tempor->score3;
				tempor = tempor->next;
				median2 = tempor->score3;
				median = (median + median2)/2;
				printf("Median, %f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
	else
	{
		for (tempor = list; tempor != NULL; tempor = tempor->next)
		{
			if(numCount == valueCount - (valueCount/2))
			{
				median = tempor->score3;
				printf("Median,%f\n", median);
				fprintf(pointer,"Median, %f\n", median);
			}
			numCount++;
		}
	}
END_FCN

/*merge all three files into one sorted node with all necessary data calculations*/
void mergeme(struct node *header, struct node2 *header2, struct node *tempor, struct node2 *tempor2, struct mergedlist *mergehead, struct mergedlist *mergetemp, struct node3 *header3, struct node3 *tempor3, FILE *pointer, FILE *pointer2, struct gradetracker *temprr, struct gradetracker *headg)
BEGIN_FCN

	double y = 0;
	double t = 0;
	int g = FALSE;
	for (tempor = header; tempor != NULL; tempor = tempor->next)
	{
		y = tempor->score;
		mergetemp = malloc(sizeof(struct mergedlist));
		mergetemp -> totalscore = y;
		mergetemp -> next = mergehead;
		mergehead = mergetemp;
		for(temprr = headg; temprr != NULL; temprr = temprr ->next)
		{
			if(y == temprr->gradeNum)
			{
				g = TRUE;
				temprr->gradeCount++;
			}	

		}

		if(g == FALSE)
		{
			temprr = malloc(sizeof(struct gradetracker));
			temprr -> gradeNum = y;
			temprr ->gradeCount = 1;
			temprr -> next = headg;
			headg = temprr;
		}
		g = FALSE;
	}
	for (tempor2 = header2; tempor2 != NULL; tempor2 = tempor2->next)
	{
		y = tempor2->score2;
		mergetemp = malloc(sizeof(struct mergedlist));
		mergetemp -> totalscore = y;
		mergetemp -> next = mergehead;
		mergehead = mergetemp;

		for(temprr = headg; temprr != NULL; temprr = temprr ->next)
		{
			if(y == temprr->gradeNum)
			{
				g = TRUE;
				temprr->gradeCount++;
			}	

		}

		if(g == FALSE)
		{
			temprr = malloc(sizeof(struct gradetracker));
			temprr -> gradeNum = y;
			temprr ->gradeCount = 1;
			temprr -> next = headg;
			headg = temprr;
		}
		g = FALSE;
	}
	for (tempor3 = header3; tempor3 != NULL; tempor3 = tempor3->next)
	{
		y = tempor3->score3;
		mergetemp = malloc(sizeof(struct mergedlist));
		mergetemp -> totalscore = y;
		mergetemp -> next = mergehead;
		mergehead = mergetemp;

		for(temprr = headg; temprr != NULL; temprr = temprr ->next)
		{
			if(y == temprr->gradeNum)
			{
				g = TRUE;
				temprr->gradeCount++;
			}	

		}

		if(g == FALSE)
		{
			temprr = malloc(sizeof(struct gradetracker));
			temprr -> gradeNum = y;
			temprr ->gradeCount = 1;
			temprr -> next = headg;
			headg = temprr;
		}
		g = FALSE;
	}
	organize(mergehead, mergetemp);

	fprintf(pointer2, "\n\nStatistics for Combined Scores \n");
	printf("\n\nStatistics for Combined Scores \n");

	averageme(mergehead, mergetemp, pointer2);
	medianme(mergehead, mergetemp, pointer2);
	modeme(headg, temprr, pointer2);



	fprintf(pointer,"Combined Scores \n");

	for (mergetemp = mergehead; mergetemp != NULL; mergetemp = mergetemp->next)
	{		
		fprintf(pointer,"%f \n", mergetemp->totalscore);
	}


END_FCN


