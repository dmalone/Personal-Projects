.ORIG x3000
;Asks user to input a name so that the program can search for a corresponding room
;
	
	LEA R2, NAME
	LEA R0, A
	trap x22
	
LOOP 	trap x20
	ADD R3, R0, #-10
	BRz SEARCH; check if the enter sign has been pressed. if so, go to the next part of the program
	STR R0, R2, #0; store the typed character into memory starting with NAME
	trap x21
	ADD R2, R2, #1; increment the address of NAME so that the next letter can be stored in it
	BRnzp LOOP; repeat until the enter key has been pressed

;takes the inputed name and checks for a matching room
;
;
;	
	
SEARCH	LD R1, ENTER
	TRAP X21
	LD R3, START
	LDR R3, R3, #0; load the address of the first node contained in x3300

NODE	LDR R4, R3, #0; load the address of the next node contained in the address contained in x3300
	
	ST R4, PTR; store the address of the next node into PTR for future reference 
	LDR R4, R3, #2
	ST R4, PTR2	

	
	LDR R5, R3, #1; load the contents of the next address in the node into R5
	LDR R6, R5, #0 ;R6 now contains the first ascii code to the last name
	LEA R2, NAME; loads the address of NAME
	LDR R1, R2, #0; R1 now has the first ascii of the name that was typed

	
	
COMPARE	NOT R1, R1
	ADD R1, R1, #1
	ADD R4, R6, R1; subtract R7 and R6
	Brnp NEXTN; go to the next node to see if there are any other possible matches
	ADD R2, R2, #1
	ADD R5, R5, #1
	LDR R6, R5, #0; check to see if there are any letters left
	BRz YES
	LDR R1, R2, #0; check to see if there are any letters left
	BRz YES
	BRnzp COMPARE; loop until it finds a match or doesn't find one
	
NEXTN	LD R3, PTR; go to the next node
	BRz NO
	BRnzp NODE	
		
NO	LEA R0, B; display "No Entry" for names that do not match
	trap x22
	BRnzp END

YES	LD R0, PTR2; display room number for names that match
	trap x22
	BRnzp END
	


END	halt

ENTER 	.FILL #10
START	.FILL x3300
PTR	.FILL x0000
PTR2	.FILL x0000
A	.stringz "Type a last name and press Enter:"
B	.stringz "No Entry"
NAME	.BLKW 15
.end

