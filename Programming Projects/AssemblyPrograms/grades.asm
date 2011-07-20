.ORIG x3000
		
	LD R0, SIXTN; load all pointers
	LD R1, PTR1
	LD R2, PTR2
	LD R3, PTR3
	LD R4, PTR4

STEPONE LDR R5, R1, #0; transfer all numbers 
	STR R5, R2, #0
	ADD R1, R1, #1
	ADD R2, R2, #1
	ADD R0, R0, #-1
	BRp STEPONE
	
	LD R2, SORT
	LD R1, NEXT
	LD R0, SIXTN
	
STEPTWO	LDR R6, R3, #0; begin sorting numbers in descending order
	LDR R7, R4, #0
	NOT R7, R7
	ADD R7, R7, #1
	ADD R5, R6, R7
	BRp SWAP
	NOT R7, R7
	ADD R7, R7, #1
	STR R6, R4, #0
	STR R7, R3, #0
	JMP R1
SWAP	NOT R7, R7
	ADD R7, R7, #1
	STR R6, R3, #0
	STR R7, R4, #0
	ADD R3, R3, #1
	ADD R4, R4, #1
	ADD R0, R0, #-1
	BRp STEPTWO
	
	LD R0, SIXTN
	ADD R3, R3, #-16
	ADD R4, R4, #-16
	ADD R2, R2, #-1
	BRp STEPTWO
	
	
	LD R6, NEXTTWO; determine number of A's in class
	LD R5, A
	LD R4, ASC
	LD R1, GRADE
	AND R3, R3, #0
	LD R0, FOUR
STEPTHREE LDR R2, R1, #0
	ADD R2, R2, R4
	BRzp COUNT
	JMP R6
COUNT	ADD R3, R3, #1 	
	ADD R1, R1, #1
	ADD R0, R0, #-1
	BRp STEPTHREE
	STR R3, R5, #0
	
	AND R7, R7, #0; determine number of possible B's by subtracting the number of A's the top half of the class
	LD R1, GRADE	
	LD R6, JUMP
	LD R0, FOUR
	LD R4, ASC
BACK	LDR R2, R1, #0
	ADD R3, R2, R4
	BRzp GOOD
	JMP R6
GOOD	ADD R7, R7, #1
	ADD R1, R1, #1
	ADD R0, R0, #-1
	BRp BACK
	

	
	LD R1, GRADE; take number of possible B's and load it into the counter
	ADD R1, R1, R7
	NOT R7, R7
	ADD R7, R7, #9
	ST R7, STORED
	LD R6, NEXTTHREE
	LD R5, B
	AND R3, R3, #0
	LD R0, STORED; determine the amount of B's in the top half of the class
STEPFOUR LD R4, BSC		
	LDR R2, R1, #0	
	ADD R4, R2, R4
	BRzp COUNTTWO
	JMP R6
COUNTTWO ADD R3, R3, #1
	ADD R1, R1, #1
	ADD R0, R0, #-1
	BRp STEPFOUR
	STR R3, R5, #0
	
HALT


PTR1	.FILL x3200
PTR2	.FILL x4000
PTR3	.FILL x4000
PTR4	.FILL x4001
GRADE   .FILL x4000
STORED	.FILL x0000	
SIXTN   .FILL #16
SORT    .FILL #1600
FOUR    .FILL #4
A	.FILL x4100
B	.FILL x4101
NEXT	.FILL x301D
NEXTTWO	.FILL x3031
NEXTTHREE .FILL x3051
ASC	.FILL #-85
BSC	.FILL #-75
JUMP	.FILL x303F


.END