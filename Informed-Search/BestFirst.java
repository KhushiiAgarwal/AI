import java.util.*;

class PuzzleState {
    int[][] board;
    int emptyRow, emptyCol;
    int heuristic; 

    PuzzleState(int[][] board, int emptyRow, int emptyCol, int heuristic) {
        this.board = board;
        this.emptyRow = emptyRow;
        this.emptyCol = emptyCol;
        this.heuristic = heuristic;
    }

    boolean isGoalState() {
        int count = 1;
        for (int i = 0; i < Main.N; i++) {
            for (int j = 0; j < Main.N; j++) {
                if (i == Main.N - 1 && j == Main.N - 1) {
                    if (board[i][j] != 0) return false;
                } else {
                    if (board[i][j] != count++) return false;
                }
            }
        }
        return true;
    }

    void printPuzzleBoard() {
        System.out.println("---------");
        for (int i = 0; i < Main.N; i++) {
            System.out.print("| ");
            for (int j = 0; j < Main.N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}

public class Main {
    static final int N = 3;

    static int calculateHeuristic(int[][] board) {
        int count = 1;
        int heuristic = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != count++) heuristic++;
            }
        }
        return heuristic;
    }

    static void swapTiles(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    static boolean bestFirstSearch(PuzzleState initialState) {
        PriorityQueue<PuzzleState> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic));
        HashSet<String> visited = new HashSet<>();

        pq.offer(initialState);

        while (!pq.isEmpty()) {
            PuzzleState currentState = pq.poll();

            if (currentState.isGoalState()) {
                System.out.println("Goal state found!");
                System.out.println("Solution:");
                currentState.printPuzzleBoard();
                return true;
            }

            visited.add(Arrays.deepToString(currentState.board));

            int row = currentState.emptyRow;
            int col = currentState.emptyCol;

            if (row > 0) {
                PuzzleState newState = new PuzzleState(Arrays.copyOf(currentState.board, currentState.board.length),
                                                        row - 1, col,
                                                        calculateHeuristic(currentState.board));
                swapTiles(newState.board, row, col, row - 1, col);
                if (!visited.contains(Arrays.deepToString(newState.board))) pq.offer(newState);
            }
            if (row < N - 1) {
                PuzzleState newState = new PuzzleState(Arrays.copyOf(currentState.board, currentState.board.length),
                                                        row + 1, col,
                                                        calculateHeuristic(currentState.board));
                swapTiles(newState.board, row, col, row + 1, col);
                if (!visited.contains(Arrays.deepToString(newState.board))) pq.offer(newState);
            }
            if (col > 0) {
                PuzzleState newState = new PuzzleState(Arrays.copyOf(currentState.board, currentState.board.length),
                                                        row, col - 1,
                                                        calculateHeuristic(currentState.board));
                swapTiles(newState.board, row, col, row, col - 1);
                if (!visited.contains(Arrays.deepToString(newState.board))) pq.offer(newState);
            }
            if (col < N - 1) {
                PuzzleState newState = new PuzzleState(Arrays.copyOf(currentState.board, currentState.board.length),  row, col + 1,                                        calculateHeuristic(currentState.board));

                swapTiles(newState.board, row, col, row, col + 1);
                if (!visited.contains(Arrays.deepToString(newState.board))) pq.offer(newState);
            }
        }

        System.out.println("Goal state not found!");
        return false;
    }

    public static void main(String[] args) {
        int[][] initialBoard = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
        PuzzleState initialState = new PuzzleState(initialBoard, 1, 1, calculateHeuristic(initialBoard));

        System.out.println("Initial state:");
        initialState.printPuzzleBoard();

        bestFirstSearch(initialState);
    }
}
