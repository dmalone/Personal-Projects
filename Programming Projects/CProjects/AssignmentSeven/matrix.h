#include<stdio.h>
class Matrix
{ private:
int ** mat_array; 
int ** mat_array2;
int ** mat_array3;
int rows;
int cols;
static int ** create(int, int);

public:
	Matrix(int nRows, int nCols)
	{
	rows = nRows;
	cols = nCols;
	mat_array = create(rows, cols);
	}
	void add(Matrix, Matrix);
	void subtract(Matrix, Matrix);
	void multiply(Matrix, Matrix);
	void transpose(Matrix);
	void transCreate(int, int, Matrix);
	void scalar(Matrix, int);
	void freeMatrix(Matrix);
	~Matrix(){};	
};