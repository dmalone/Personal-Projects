.ORIG x3000
	
	LD R1, hello
  	ADD R2, R1, #0
HERE   	ADD R3, R2, #-1
	AND R3, R3, R2
	BRz END
	ADD R2, R2, #1
	BRnzp HERE
END	HALT

hello	.FILL #10
.END