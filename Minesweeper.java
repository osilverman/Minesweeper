import java.util.*;

public class Minesweeper {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        playGame(input);
    }

    public static void playGame(Scanner input) {
        // Hidden board: "x" = bomb, "-" = empty
        String[][] board = new String[9][9];
        // Visible board: "-" = unrevealed, "F" = flagged, number = revealed
        String[][] board2 = new String[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = "-";
                board2[i][j] = "-";
            }
        }

        // Place 10 bombs
        int bombsPlaced = 0;
        while (bombsPlaced < 10) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
            if (board[x][y].equals("-")) {
                board[x][y] = "x";
                bombsPlaced++;
            }
        }

        // Main game loop
        while (true) {
            printBoard(board2);

            System.out.println("Enter action: R (reveal) or F (flag)");
            String action = input.next().toUpperCase();

            if (!action.equals("R") && !action.equals("F")) {
                System.out.println("Invalid action. Enter R to reveal or F to flag.");
                continue;
            }

            System.out.print("Enter row (0-8): ");
            if (!input.hasNextInt()) { input.next(); System.out.println("Invalid input."); continue; }
            int row = input.nextInt();

            System.out.print("Enter column (0-8): ");
            if (!input.hasNextInt()) { input.next(); System.out.println("Invalid input."); continue; }
            int col = input.nextInt();

            if (row < 0 || row > 8 || col < 0 || col > 8) {
                System.out.println("Coordinates out of bounds. Enter values between 0 and 8.");
                continue;
            }

            if (action.equals("F")) {
                // Toggle flag
                if (board2[row][col].equals("F")) {
                    board2[row][col] = "-";
                    System.out.println("Flag removed.");
                } else if (board2[row][col].equals("-")) {
                    board2[row][col] = "F";
                    System.out.println("Flag placed.");
                } else {
                    System.out.println("Can't flag a revealed cell.");
                }
            } else {
                // Reveal
                if (board2[row][col].equals("F")) {
                    System.out.println("Unflag this cell before revealing.");
                    continue;
                }
                if (!board2[row][col].equals("-")) {
                    System.out.println("Cell already revealed.");
                    continue;
                }

                if (board[row][col].equals("x")) {
                    // Hit a bomb — reveal all bombs and end game
                    System.out.println("\nBOOM! You hit a mine!");
                    revealBombs(board, board2);
                    printBoard(board2);
                    System.out.println("Game over.");
                    System.out.print("Play again? (Y/N): ");
                    if (input.next().toUpperCase().equals("Y")) playGame(input);
                    return;
                }

                int count = mineFind(board, col, row);
                board2[row][col] = String.valueOf(count);
            }

            // Check win after every action
            if (checkWin(board, board2)) {
                printBoard(board2);
                System.out.println("You win! All mines flagged correctly!");
                System.out.print("Play again? (Y/N): ");
                if (input.next().toUpperCase().equals("Y")) playGame(input);
                return;
            }
        }
    }

    // Print the visible board with row/column headers
    public static void printBoard(String[][] board2) {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < 9; i++) System.out.print(i + " ");
        System.out.println();
        System.out.println("  ==================");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < 9; j++) {
                System.out.print(board2[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Reveal all bomb locations (called on loss)
    public static void revealBombs(String[][] board, String[][] board2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].equals("x")) board2[i][j] = "X";
            }
        }
    }

    // Win: all 10 bombs are correctly flagged
    public static boolean checkWin(String[][] board, String[][] board2) {
        int correctFlags = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board2[i][j].equals("F") && board[i][j].equals("x")) {
                    correctFlags++;
                }
            }
        }
        return correctFlags == 10;
    }

    // Count adjacent mines for cell (col=a, row=b)
    public static int mineFind(String[][] board, int a, int b) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                int ni = b + di;
                int nj = a + dj;
                if (ni >= 0 && ni < 9 && nj >= 0 && nj < 9) {
                    if (board[ni][nj].equals("x")) count++;
                }
            }
        }
        return count;
    }
}