#include<stdio.h>
#include<stdlib.h>
#include<string.h>
void reverseStringdawg(char string);

int main (void){
	int n;
	int m;
	char str[10] = "Kevin Lam";
	char str2[10];

	strcpy(str2, str);
	
		
for (n = 0, m = 8; m >= 0; n++, m--)
	{
		str2[n] = str[m];
	}
	strcpy(str, str2);

for (n = 0, m = 2; m >= 0; n++, m--)
	{
		str2[n] = str[m];
	}
for (n = 4, m = 8; m > 3; n++, m--)
	{
		str2[n] = str[m];
	}

	
	
	

	/*struct node {
		int number;
		struct node *next;
	};

	struct node *first = NULL;
	
	
		struct node *new_node;
		struct node *cur, *prev;

		new_node = malloc(sizeof(struct node));

		new_node->number = 10;
		new_node->next = first;
		first = new_node;

		new_node = malloc(sizeof(struct node));
		new_node->number = 20;
		new_node->next = first;
		first = new_node;

		new_node = malloc(sizeof(struct node));
		new_node->number = 30;
		new_node->next = first;
		first = new_node;

		new_node = malloc(sizeof(struct node));
		new_node->number = 40;
		new_node->next = first;
		first = new_node;

		new_node = malloc(sizeof(struct node));
		new_node->number = 50;
		new_node->next = first;
		first = new_node;
		
		

		for(cur = first, prev = NULL; cur != NULL && cur->number != 30; prev = cur, cur = cur->next); 
		new_node = malloc(sizeof(struct node));
		new_node->number = 35;
		new_node->next = cur;
		prev->next = new_node;*/
		
}
	


