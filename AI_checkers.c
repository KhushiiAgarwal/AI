#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define SIZE 8

void initializeBoard(char board[SIZE][SIZE]);
void printBoard(char board[SIZE][SIZE]);
int isValidMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol);
int isCaptureMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol);
int performMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol);
int isGameOver(char board[SIZE][SIZE]);
void makeAIMove(char board[SIZE][SIZE]);

int main() {
    char board[SIZE][SIZE];
    int player = 1; 

    initializeBoard(board);

    while (!isGameOver(board)) {
        printf("\nCurrent Board:\n");
        printBoard(board);

        if (player == 1) {
            int startRow, startCol, endRow, endCol;
            printf("\nPlayer %d's turn:\n", player);
            printf("Enter the starting position (row col): ");
            scanf("%d %d", &startRow, &startCol);
            printf("Enter the ending position (row col): ");
            scanf("%d %d", &endRow, &endCol);

            if (isValidMove(board, player, startRow, startCol, endRow, endCol)) {
                performMove(board, player, startRow, startCol, endRow, endCol);
                player = 3 - player; // Switch player (1 to 2, 2 to 1)
            } else {
                printf("Invalid move. Try again.\n");
            }
        } else {
            printf("\nAI's turn:\n");
            makeAIMove(board);
            player = 3 - player; // Switch player (1 to 2, 2 to 1)
        }
    }

    printf("\nGame over!\n");
    printBoard(board);

    return 0;
}

void initializeBoard(char board[SIZE][SIZE]) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if ((i + j) % 2 == 0) {
                board[i][j] = ' ';
            } else {
                if (i < 3) {
                    board[i][j] = 'X'; 
                } else if (i > 4) {
                    board[i][j] = 'O'; 
                } else {
                    board[i][j] = ' '; 
                }
            }
        }
    }
}

void printBoard(char board[SIZE][SIZE]) {
    // Display the current state of the board
    printf("  0 1 2 3 4 5 6 7\n");
    for (int i = 0; i < SIZE; i++) {
        printf("%d ", i);
        for (int j = 0; j < SIZE; j++) {
            printf("%c ", board[i][j]);
        }
        printf("\n");
    }
}

int isValidMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol) {
    // Check if the move is valid
    if (startRow < 0 || startRow >= SIZE || startCol < 0 || startCol >= SIZE ||
        endRow < 0 || endRow >= SIZE || endCol < 0 || endCol >= SIZE) {
        return 0; // Out of bounds
    }

    if (board[startRow][startCol] == ' ' || board[endRow][endCol] != ' ') {
        return 0; // Invalid starting or ending positions
    }

    if (player == 1 && board[startRow][startCol] != 'X') {
        return 0; // Invalid player 1 piece
    }

    if (player == 2 && board[startRow][startCol] != 'O') {
        return 0; // Invalid player 2 piece
    }

    if (abs(endRow - startRow) != 1 || abs(endCol - startCol) != 1) {
        if (!isCaptureMove(board, player, startRow, startCol, endRow, endCol)) {
            return 0; // Not a valid non-capture move
        }
    }

    return 1;
}

int isCaptureMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol) {
    // Check if the move is a valid capture move
    int midRow = (startRow + endRow) / 2;
    int midCol = (startCol + endCol) / 2;

    if (midRow < 0 || midRow >= SIZE || midCol < 0 || midCol >= SIZE) {
        return 0; // Out of bounds
    }

    if (player == 1 && board[midRow][midCol] != 'O') {
        return 0; // Invalid capture attempt for player 1
    }

    if (player == 2 && board[midRow][midCol] != 'X') {
        return 0; // Invalid capture attempt for player 2
    }

    return 1;
}

int performMove(char board[SIZE][SIZE], int player, int startRow, int startCol, int endRow, int endCol) {
    // Perform the move and update the board
    board[endRow][endCol] = board[startRow][startCol];
    board[startRow][startCol] = ' ';

    if (abs(endRow - startRow) == 2) {
        // Capture move, remove the captured piece
        int midRow = (startRow + endRow) / 2;
        int midCol = (startCol + endCol) / 2;
        board[midRow][midCol] = ' ';
    }

    return 1;
}   

int isGameOver(char board[SIZE][SIZE]) {
    // Check if the game is over
    int player1Pieces = 0;
    int player2Pieces = 0;

    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (board[i][j] == 'X') {
                player1Pieces++;
            } else if (board[i][j] == 'O') {
                player2Pieces++;
            }
        }
    }

    return (player1Pieces == 0 || player2Pieces == 0);
}

void makeAIMove(char board[SIZE][SIZE]) {
    // AI makes a random valid move
    srand(time(NULL));

    int startRow, startCol, endRow, endCol;

    do {
        startRow = rand() % SIZE;
        startCol = rand() % SIZE;
        endRow = rand() % SIZE;
        endCol = rand() % SIZE;
    } while (!isValidMove(board, 2, startRow, startCol, endRow, endCol));

    performMove(board, 2, startRow, startCol, endRow, endCol);
}
