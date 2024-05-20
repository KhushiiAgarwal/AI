import java.util.Scanner;
import java.util.InputMismatchException;

public class AlphaBeta {

    private static final int SIDE = 3;
    private static final char COMPUTERMOVE = 'O';
    private static final char HUMANMOVE = 'X';
    private static final char[][] board = new char[SIDE][SIDE];

    private static void showBoard() {
        for (int i = 0; i < SIDE; i++) {
            System.out.printf("\t\t\t %c | %c | %c \n", board[i][0], board[i][1], board[i][2]);
            if (i < SIDE - 1) {
                System.out.println("\t\t\t-----------");
            }
        }
        System.out.println();
    }

    private static void initialiseBoard() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static boolean isMovesLeft() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (board[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private static int evaluate(char player) {
        for (int row = 0; row < SIDE; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return (player == COMPUTERMOVE) ? 10 : -10;
            }
        }

        for (int col = 0; col < SIDE; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return (player == COMPUTERMOVE) ? 10 : -10;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return (player == COMPUTERMOVE) ? 10 : -10;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return (player == COMPUTERMOVE) ? 10 : -10;
        }

        return 0;
    }

    private static int minimax(int depth, int alpha, int beta, boolean isMax) {
        int score = evaluate(COMPUTERMOVE);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (!isMovesLeft()) {
            return 0;
        }

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIDE; i++) {
                for (int j = 0; j < SIDE; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = COMPUTERMOVE;
                        best = Math.max(best, minimax(depth + 1, alpha, beta, !isMax));
                        alpha = Math.max(alpha, best);
                        board[i][j] = ' ';
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < SIDE; i++) {
                for (int j = 0; j < SIDE; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = HUMANMOVE;
                        best = Math.min(best, minimax(depth + 1, alpha, beta, !isMax));
                        beta = Math.min(beta, best);
                        board[i][j] = ' ';
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return best;
        }
    }

    private static void findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = COMPUTERMOVE;
                    int moveVal = minimax(0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board[i][j] = ' ';
                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.printf("Computer's move: %d\n", bestRow * 3 + bestCol + 1);
        board[bestRow][bestCol] = COMPUTERMOVE;
    }

   public static void main(String[] args) {
    System.out.println("\n---------------------------------------------------------------\n");
    System.out.println("\t\t\t Tic-Tac-Toe\n");
    System.out.println("---------------------------------------------------------------\n");

    initialiseBoard();
    showBoard();

    Scanner scanner = new Scanner(System.in);
    while (isMovesLeft()) {
        System.out.print("Enter your move (1-9): ");
        try {
            int n = scanner.nextInt() - 1;
            int x = n / SIDE;
            int y = n % SIDE;
            if (n < 0 || n >= 9 || board[x][y] != ' ') {
                System.out.println("Invalid move");
                continue;
            }
            board[x][y] = HUMANMOVE;
            showBoard();
            if (evaluate(HUMANMOVE) == 10) {
                System.out.println("You win!");
                return;
            } else if (!isMovesLeft()) {
                System.out.println("It's a draw!");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid integer.");
            scanner.next(); // Clear the invalid input
        }

        findBestMove();
        showBoard();
        if (evaluate(COMPUTERMOVE) == 10) {
            System.out.println("Computer wins!");
            return;
        } else if (!isMovesLeft()) {
            System.out.println("It's a draw!");
            return;
        }
    }
}
}

