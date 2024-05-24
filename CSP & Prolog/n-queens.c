#include <stdio.h>
#include <stdbool.h>

#define MAX_N 20 

void printSolution(int board[MAX_N][MAX_N], int N) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf("%2d ", board[i][j]);
        }
        printf("\n");
    }
}

bool isSafe(int board[MAX_N][MAX_N], int row, int col, int N) {
    for (int i = 0; i < col; i++) {
        if (board[row][i])
            return false;
    }
    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
        if (board[i][j])
            return false;
    }
    for (int i = row, j = col; i < N && j >= 0; i++, j--) {
        if (board[i][j])
            return false;
    }
    return true;
}

bool solveNQueensUtil(int board[MAX_N][MAX_N], int col, int N) {
    if (col >= N)
        return true;
    for (int i = 0; i < N; i++) {
        if (isSafe(board, i, col, N)) {
            board[i][col] = 1;
            if (solveNQueensUtil(board, col + 1, N))
                return true;
            board[i][col] = 0; 
        }
    }
    return false;
}

bool solveNQueens(int N) {
    int board[MAX_N][MAX_N] = {0};
    if (solveNQueensUtil(board, 0, N) == false) {
        printf("Solution does not exist\n");
        return false;
    }
    printSolution(board, N);
    return true;
}

int main() {
    int N;
    printf("Enter the size of the chessboard (N): ");
    scanf("%d", &N);
    if (N <= 0 || N > MAX_N) {
        printf("Invalid size for N. Please enter a value between 1 and %d.\n", MAX_N);
        return 1;
    }
    solveNQueens(N);
    return 0;
}

