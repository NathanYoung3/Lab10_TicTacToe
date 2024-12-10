import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean playAgain = false;
        String[][] board = new String[3][3];
        boolean player1ValidMove = false;
        boolean player2ValidMove = false;
        int r = 0;
        int c = 0;
        int winner = 0;

        System.out.println("Welcome to Tic-Tac-Toe!\n");//start of program
        do {//this is the start of every game
            clearBoard(board);
            winner = 0;

            do {//this part runs until a winner or tie is decided

                do {//Repeats until a valid move for player 1 is done
                    System.out.println("Player 1:");
                    r = InputHelper.getRangedInt(scan, "Enter your move row [1 - 3]", 1, 3) - 1;
                    c = InputHelper.getRangedInt(scan, "Enter your move col [1 - 3]", 1, 3) - 1;
                    if (isValidMove(board, r, c)) {
                        player1ValidMove = true;
                        board[r][c] = "X";
                        displayBoard(board);

                        if (isTie(board)) {//check for tie and sets winner value to tie
                            winner = 3;
                        }
                        if (isWin(board, "X")) {//if there is a win now, it has to be player 1 because win is checked after every move and player 1 just went meaning there is no way it can be player 2
                            winner = 1;
                        }
                    } else {
                        System.out.println("Invalid move, try again.");
                    }
                } while (!player1ValidMove);
                player1ValidMove = false;


                if (winner == 0) {//need this for player 2 but not 1 because it's checking for a winner and if player 1 wins this makes sure player 2 doesn't get to go again, but if player 2 wins it exits the do while loop of turns meaning player 1 can't go anyways
                    do {//Repeats until a valid move for player 2 is done
                        System.out.println("Player 2:");
                        r = InputHelper.getRangedInt(scan, "Enter your move row [1 - 3]", 1, 3) - 1;
                        c = InputHelper.getRangedInt(scan, "Enter your move col [1 - 3]", 1, 3) - 1;
                        if (isValidMove(board, r, c)) {
                            player2ValidMove = true;
                            board[r][c] = "O";
                            displayBoard(board);

                            if (isTie(board)) {//checks for tie and sets winner value to tie
                                winner = 3;
                            }
                            if (isWin(board, "O")) {//if there is a win now, it has to be player 2 because win is checked after every move and player 2 just went meaning there is no way it can be player 1
                                winner = 2;
                            }
                        } else {
                            System.out.println("Invalid move, try again.");
                        }
                    } while (!player2ValidMove);
                    player2ValidMove = false;
                }


            } while (winner == 0); //I'm using this instead of a boolean where winner is set to 0 by default and only changed by a win where 1 means player 1 wins, 2 means player 2 wins, 3 means tie

            if (winner == 1) {//declares winner
                System.out.println("Player 1 wins!");
            } else if (winner == 2) {
                System.out.println("Player 2 wins!");
            } else {
                System.out.println("It's a tie!");
            }

            playAgain = InputHelper.getYNConfirm(scan, "Do you want to play again?");
        } while (playAgain);
    }

    private static void clearBoard(String[][] board){
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = "-";
            }
        }
    }

    private static void displayBoard(String[][] board){
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.printf("%-5s", board[r][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isValidMove(String[][] board, int row, int col){
        if (board[row][col].equals("-")) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isWin(String[][] board, String player){ //calling this method just calls on the 3 methods below to check for all win possibilities
        if (isColWin(board, player) || isRowWin(board, player) || isDiagonalWin(board, player)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isColWin(String[][] board, String player){
        int combo = 0;

        for (int c = 0; c < board[0].length; c++) {
            for (int r = 0; r < board.length; r++) {
                if (board[r][c].equals(player)) {
                    combo++;
                }
            }
            if (combo == 3) {
                return true;
            } else {
                combo = 0;
            }
        }
        return false;
    }

    private static boolean isRowWin(String[][] board, String player){
        int combo = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].equals(player)) {
                    combo++;
                }
            }
            if (combo == 3) {
                return true;
            } else {
                combo = 0;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String[][] board, String player){

        for (int rc = 0; rc < board.length; rc++) {//check from top left to bottom right
            if (!board[rc][rc].equals(player)) {
                break;
            } else if (rc == board.length - 1) {
                return true;
            }
        }

        int combo = 0;
        for (int r = 0; r < board.length; r++) {
            if (!board[r][ board[0].length - (r + 1) ].equals(player)) {
                return false;
            } else if (r == board.length - 1) {
                return true;
            }
        }

        return false;
    }

    private static boolean isTie(String[][] board){
        boolean isTied = true;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].equals("-")) {
                    isTied = false;
                    break;//I think this only breaks out of the closest for loop
                }
            }
            if (!isTied) {//I added this to break all the way out
                break;
            }
        }
        return isTied;
    }
}