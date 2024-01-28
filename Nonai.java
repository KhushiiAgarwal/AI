import java.util.Scanner;

public class Nonai {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();

        do {
            displayBoard();

            int move = getPlayerMove();

            int row = (move - 1) / 3;
            int col = (move - 1) % 3;

            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
                System.out.println("Invalid move. Try again.");
                continue; 
            }

            board[row][col] = currentPlayer;

            if (checkWin()) {
                displayBoard();
                System.out.print("Player " + currentPlayer + " wins! Congratulations!");
                break;
            }

            if (isBoardFull()) {
                displayBoard();
                System.out.println("The game is a draw!");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        } while (true);
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void displayBoard() {
        System.out.println("_____________\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("_____________\n");
        }
    }

    private static int getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int move;

        System.out.print("\nPlayer " + currentPlayer + ", enter your move (1-9): ");
        move = scanner.nextInt();
        return move;
    }

    private static boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }

        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true;
        }

        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
