#include"matrix.h"
#include<stdlib.h>
#include<stdio.h>
#define MAXNUM 10

int** Matrix::create (int nrows, int ncols)
{ 
	int **values;
	values = new int* [nrows];
	for(int i=0; i < nrows; i++)
		*(values + i) = new int[ncols];
	for(int i=0; i < nrows; i++)
	{
		for(int j=0; j < ncols; j++)
		{	values[i][j] = rand( ) % MAXNUM + 1;
		/*printf("%d ", values[i][j]);*/
		}
		/*printf("\n");*/
	}

	/*printf("\n\n\n\n");*/
	return values;
}

void Matrix::transCreate (int nrows, int ncols, Matrix A)
{
	int **values2;
	values2 = new int* [nrows];
	for(int i=0; i < nrows; i++)
		*(values2 + i) = new int[ncols];
	for(int i=0; i < nrows; i++)
	{
		for(int j=0; j < ncols; j++)
		{	values2[i][j] = A.mat_array[j][i];
		/*printf("%d ", values2[i][j]);*/
		}
		/*printf("\n");*/
	}

	/*printf("\n\n\n\n");*/
	return;
}
void Matrix::add(Matrix B, Matrix C)
{
	/*printf("Addition result:\n\n");*/
	mat_array2 = C.mat_array;

	for(int i=0; i < rows; i++)
	{
		for(int j=0; j < cols; j++)
		{
			mat_array[i][j] = mat_array[i][j] + mat_array2[i][j];
			/*printf("%d   ", mat_array[i][j]);*/
		}
		/*printf("\n");*/
	}

	/*printf("\n\n\n\n");*/
	return;
}

void Matrix::subtract(Matrix B, Matrix C)
{
	mat_array2 = C.mat_array;
	/*printf("Subtraction result:\n\n");*/
	for(int i=0; i < rows; i++)
	{
		for(int j=0; j < cols; j++)
		{
			mat_array[i][j] = mat_array[i][j] - mat_array2[i][j];
			/*printf("%d   ", mat_array[i][j]);*/
		}
		/*printf("\n");*/
	}

	/*printf("\n\n\n\n");*/
	return;
}

void Matrix::multiply(Matrix B, Matrix C)
{
	int t = 0;
	int q = 0;
	int r = 0;
	int g = 0;
	int s = 0;
	int i = 0;
	int j = 0;
	Matrix matrixY(B.rows, C.cols);
	mat_array2 = C.mat_array;
	mat_array3 = matrixY.mat_array;
	/*printf("Multiplication result:\n\n");*/
	for( i=0; i < matrixY.rows; i++)
	{
		for( j=0; j < matrixY.cols; j++)
		{
			for(int d = 0; d < B.cols; d++)
				{
					r = r + mat_array[t][q]*mat_array2[g][s];
					q++;
					g++;
				}
			mat_array3[i][j] = r;
			q = 0;
			g = 0;
			s++;
			r = 0;
			/*printf("%d   ", mat_array3[i][j]);*/
		}
		t++;
		s = 0;
		
		/*printf("\n");*/
	}

	/*printf("\n\n\n\n");*/
	return;
}

void Matrix::freeMatrix(Matrix)
{
	delete mat_array;
	delete mat_array2;
	return;
}
void Matrix::transpose(Matrix A)
{
	Matrix matrixX(A.cols, A.rows);
	system("cls");
	for(int i=0; i < matrixX.rows; i++)
	{
		for(int j=0; j < matrixX.cols; j++)
		{	matrixX.mat_array[i][j] = A.mat_array[j][i];
		/*printf("%d ", matrixX.mat_array[i][j]);*/
		}
		/*printf("\n");*/
	}
	delete matrixX.mat_array;
	return;
}
void Matrix::scalar(Matrix A, int n)
{
	mat_array2 = A.mat_array;
	/*printf("Multiplication result:\n\n");*/
	for(int i=0; i < rows; i++)
	{
		for(int j=0; j < cols; j++)
		{
			mat_array[i][j] = mat_array[i][j] * n;
			/*printf("%d   ", mat_array[i][j]);*/
		}
		/*printf("\n");*/
	}
	delete mat_array;
	/*printf("\n\n\n\n");*/
	return;
}