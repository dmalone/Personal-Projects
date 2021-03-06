.ORIG x3000


TURN	LEA R0, ROW1
	PUTS
	LEA R0, ROW12
	PUTS
	LD R0, NL
	OUT
	LEA R0, ROW2
	PUTS
	LEA R0, ROW22
	PUTS
	LD R0, NL
	OUT
	LEA R0, ROW3
	PUTS
	LEA R0, ROW32
	PUTS
	LD R0, NL
	OUT 
TRY	LEA R0, PLAYER1
	PUTS
	LEA R1, WHICHPLYR
	LD R5, WHICHPLYR
	ADD R5, R5, #1
	STR R5, R1, #0
	BRnzp BEGIN

TURN2	LEA R0, ROW1
	PUTS
	LEA R0, ROW12
	PUTS
	LD R0, NL
	OUT
	LEA R0, ROW2
	PUTS
	LEA R0, ROW22
	PUTS
	LD R0, NL
	OUT
	LEA R0, ROW3
	PUTS
	LEA R0, ROW32
	PUTS
	LD R0, NL
	OUT 
TRY2	LEA R0, PLAYER2
	PUTS
	LEA R1, WHICHPLYR
	LD R5, WHICHPLYR
	ADD R5, R5, #1
	STR R5, R1, #0
	BRnzp BEGIN


BEGIN	LD R1, TWO
	LEA R2, BLANK
START	GETC
	STR R0, R2, #0
	ADD R2, R2, #1
	OUT
	ADD R1, R1, #-1
	BRnp START
	
	ADD R2, R2, #-2
	LDR R4, R2, #0
	LD R5, A
	JSR CHECK
	BRz NEXT	
	LD R5, B
	JSR CHECK
	BRz NEXT2
	LD R5, C
	JSR CHECK
	BRz NEXT3
	LD R7, SAVER7
	RET	

CHECK	NOT R5, R5
	ADD R5, R5, #1
	ADD R5, R4, R5
	RET

NENTRY	LD R0, NL
	OUT
	LEA R0, ERROR
	PUTS
	LD R0, NL
	OUT
	LD R1, BITMSK	
	LEA R6, WHICHPLYR
	LD R5, WHICHPLYR
	ADD R5, R5, #1
	STR R5, R6, #0
	AND R1, R5, R1
	BRp TRY2
	BRnzp TRY	
	
NEXT	LD R6, ROWA
	BRnzp NUMBER
NEXT2	LD R6, ROWB
	BRnzp NUMBER2
NEXT3	LD R6, ROWC
	BRnzp NUMBER3

NUMBER	JSR COUNTER
	LD R7, SAVER71
	RET	
NUMBER2	JSR COUNTER
	LD R7, SAVER72
	RET
NUMBER3	JSR COUNTER
	LD R7, SAVER73
	RET

COUNTER	ADD R2, R2, #1
	LDR R3, R2, #0
	LD R5, BITMK
	AND R3, R3, R5
	RET	
	
AGAIN	JSR OKAY
	BRnzp OKAYA
AGAIN2	JSR OKAY
	BRnzp OKAYB
AGAIN3	JSR OKAY
	BRnzp OKAYC

OKAY	LDR R0, R6, #0
	LD R5, FULL
	NOT R5, R5
	ADD R5, R5, #1
	ADD R5, R0, R5
	BRz NENTRY	
	ADD R6, R6, #-1
	ADD R3, R3, #-1
	BRnp OKAY
	RET

OKAYA	LDR R3, R2, #0
	LD R6, ROWA
	LEA R5, ROWA		
	BRnzp BACK
OKAYB	LDR R3, R2, #0
	LD R6, ROWB
	LEA R5, ROWB
	BRnzp BACK
OKAYC	LDR R3, R2, #0
	LD R6, ROWC
	LEA R5, ROWC
	BRnzp BACK


BACK	LD R0, BITMK
	AND R3, R3, R0
	LDR R0, R6, #0
	AND R0, R0, #0
	STR R0, R6, #0
	ADD R6, R6, #-1
	ADD R3, R3, #-1
	BRnp BACK
	STR R6, R5, #0
	BRz RIGHT
	

RIGHT	LD R6, ROW12
	BRnp WHICH
	LD R6, ROW22
	BRnp WHICH
	LD R6, ROW32
	BRnp WHICH
	LD R0, NL
	OUT
	LD R0, NL
	OUT
	LD R1, BITMSK	
	LD R5, WHICHPLYR
	AND R1, R5, R1
	BRp YES
	LEA R0, LOSER2
	PUTS
	BRnzp END
YES	LEA R0, LOSER
	PUTS
	BRnzp END
	
WHICH	LD R0, NL
	OUT
	LD R0, NL
	OUT
	LD R1, BITMSK	
	LD R5, WHICHPLYR
	AND R1, R5, R1
	BRp TURN2
	BRnzp TURN

END	HALT
BITMSK	.FILL x0001
WHICHPLYR .FILL x0000
NL   	.FILL x000A
TWO	.FILL #2
BLANK	.BLKW #2 
ROW1	.stringz "Row A: "
	.FILL x0FFF
ROW12	.stringz "ooo"
ROW2 	.stringz "Row B: "
	.FILL x0FFF
ROW22	.stringz "ooooo"
ROW3 	.stringz "Row C: "
	.FILL x0FFF
ROW32	.stringz "oooooooo"
A	.FILL x0041
PLAYER1 .stringz "Player 1, choose a row and number of rocks:"
PLAYER2 .stringz "Player 2, choose a row and number of rocks:"	
B	.FILL x0042
ROWA	.FILL x30C2
BITMK	.FILL x000F
C	.FILL x0043
ROWC	.FILL x30E3
SAVER7	.FILL x304B
SAVER71	.FILL x306D
SAVER72	.FILL x306F
SAVER73	.FILL x3071
ROWB	.FILL x30D1
ERROR	.stringz "Invalid move. Try again."
FULL	.FILL x0FFF
LOSER	.stringz "Player 2 Wins."
LOSER2	.stringz "Player 1 Wins."

.END
