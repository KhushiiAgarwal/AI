#include <stdio.h>
#include <stdbool.h>

#define N 3 // Size of the puzzle board

// Struct to represent a state in the puzzle
typedef struct {
    int board[N][N]; // Puzzle board
    int emptyRow, emptyCol; // Position of the empty tile
} PuzzleState;

// Struct to represent a node in the DFS traversal
typedef struct {
    PuzzleState state; // Current state of the puzzle
    int depth; // Depth of the node in the DFS tree
} DFSNode;

// Function to check if a state is the goal state
bool isGoalState(PuzzleState state) {
    int count = 1;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i == N - 1 && j == N - 1)
                return state.board[i][j] == 0; // Last tile should be empty
            if (state.board[i][j] != count++)
                return false; // Incorrect tile position
        }
    }
    return true;
}

// Function to create a new puzzle state
PuzzleState createPuzzleState(int board[N][N], int emptyRow, int emptyCol) {
    PuzzleState newState;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            newState.board[i][j] = board[i][j];
        }
    }
    newState.emptyRow = emptyRow;
    newState.emptyCol = emptyCol;
    return newState;
}

// Function to swap two tiles on the puzzle board
void swapTiles(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;
}

// Function to perform DFS traversal and print the solution
bool dfs(PuzzleState state, int depth, int maxDepth) {
    if (isGoalState(state)) {
        printf("Goal state found!\n");
        printf("Solution:\n");
        printSolution(state);
        return true;
    }
    if (depth == maxDepth)
        return false;

    int row = state.emptyRow;
    int col = state.emptyCol;

    // Try moving empty tile in all directions
    if (row > 0) { // Move up
        swapTiles(&state.board[row][col], &state.board[row - 1][col]);
        PuzzleState newState = createPuzzleState(state.board, row - 1, col);
        if (dfs(newState, depth + 1, maxDepth))
            return true;
        swapTiles(&state.board[row][col], &state.board[row - 1][col]); // Undo move
    }
    if (row < N - 1) { // Move down
        swapTiles(&state.board[row][col], &state.board[row + 1][col]);
        PuzzleState newState = createPuzzleState(state.board, row + 1, col);
        if (dfs(newState, depth + 1, maxDepth))
            return true;
        swapTiles(&state.board[row][col], &state.board[row + 1][col]); // Undo move
    }
    if (col > 0) { // Move left
        swapTiles(&state.board[row][col], &state.board[row][col - 1]);
        PuzzleState newState = createPuzzleState(state.board, row, col - 1);
        if (dfs(newState, depth + 1, maxDepth))
            return true;
        swapTiles(&state.board[row][col], &state.board[row][col - 1]); // Undo move
    }
    if (col < N - 1) { // Move right
        swapTiles(&state.board[row][col], &state.board[row][col + 1]);
        PuzzleState newState = createPuzzleState(state.board, row, col + 1);
        if (dfs(newState, depth + 1, maxDepth))
            return true;
        swapTiles(&state.board[row][col], &state.board[row][col + 1]); // Undo move
    }

    return false;
}

// Function to print the puzzle board
void printPuzzleBoard(int board[N][N]) {
    printf("-----------------\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf("| %d ", board[i][j]);
        }
        printf("|\n");
        printf("-----------------\n");
    }
}

// Function to print the solution
void printSolution(PuzzleState state) {
    while (!isGoalState(state)) {
        printPuzzleBoard(state.board);
        printf("\n");
        // Perform one of the possible moves
        int row = state.emptyRow;
        int col = state.emptyCol;
        if (row > 0 && state.board[row - 1][col] != 0) { // Move up
            swapTiles(&state.board[row][col], &state.board[row - 1][col]);
            state.emptyRow = row - 1;
        } else if (row < N - 1 && state.board[row + 1][col] != 0) { // Move down
            swapTiles(&state.board[row][col], &state.board[row + 1][col]);
            state.emptyRow = row + 1;
        } else if (col > 0 && state.board[row][col - 1] != 0) { // Move left
            swapTiles(&state.board[row][col], &state.board[row][col - 1]);
            state.emptyCol = col - 1;
        } else if (col < N - 1 && state.board[row][col + 1] != 0) { // Move right
            swapTiles(&state.board[row][col], &state.board[row][col + 1]);
            state.emptyCol = col + 1;
        }
    }
    // Print the final state
    printPuzzleBoard(state.board);
}

int main() {
    int initialBoard[N][N] = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}}; // Initial state of the puzzle
    PuzzleState initialState = createPuzzleState(initialBoard, 1, 1); // Empty tile initially at (1, 1)
    int maxDepth = 50; // Maximum depth for DFS traversal

    if (!dfs(initialState, 0, maxDepth))
        printf("Goal state not found within maximum depth!\n");

    return 0;
}
