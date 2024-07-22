package Inputs;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Inputs_And_Checks {
    void startTheGame(){
        int[][] board = new int[3][3];


        Scanner nameInput = new Scanner(System.in);
        System.out.print("Player 1 Enter your name : ");
        String name1 = nameInput.next();

        System.out.print("Player 2 Enter your name : ");
        String name2 = nameInput.next();

        boolean again = true;
        System.out.println();
        while (again) {
            System.out.println("It's a new Game :)");

            for (int i = 0; i < 3; i++) {
                Arrays.fill(board[i], -1);
            }
//        Storing some default for the comparison
            Random random = new Random();
            int first = random.nextInt(0, 2);
            printBoardPositions(board);

            boolean winner = false;
            while (!winner) {

                if (first == 0) {
                    winner = takeInputs(board, name1, 1);
                } else {
                    winner = takeInputs(board, name2, 0);
                }
                if (isFilled(board) && !winner) {
                    System.out.println("The game is drawn :(");
                    break;
                }
                if (winner) {
                    break;
                }

                if (first == 0) {
                    winner = takeInputs(board, name2, 0);
                } else {
                    winner = takeInputs(board, name1, 1);
                }
                if (isFilled(board) && !winner) {
                    System.out.println("The game is drawn :(");
                    break;
                }
            }
            String ans;
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Do you want to Play again ?\nY : Yes\nN : No");
                ans = scanner.next();
                if (ans.equalsIgnoreCase("n")){
                    again = false;
                    break;
                }
                else if (ans.equalsIgnoreCase("y")){
                    break;
                }
                else {
                    System.out.println("Please Enter Y or N");
                }
            }
        }
    }

    boolean takeInputs(int[][] board, String name, int val) {
        if (val == 1){
            System.out.println(name + "'s turn "+ "( x )");
        }
        else {
            System.out.println(name + "'s turn "+ "( "+val+" )");

        }
        boolean winner = false;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            int row = 0;
            int col = 0;
            try {
                System.out.print("Enter the row number : ");
                row = scanner.nextInt();

                System.out.print("Enter the column number : ");
                col = scanner.nextInt();
//                    scanner.close();
            } catch (InputMismatchException mismatchException) {
                System.out.println("Please enter numbers only !");
            }

                if (row > 3 || col > 3 || row < 1 || col < 1) {
                    System.out.println("Please enter a valid row and column :");
                    printBoardPositions(board);
                    continue;
                }
                if (board[row - 1][col - 1] != -1) {
                    System.out.println("The mentioned position is already taken, please choose an empty position :");
                    continue;
                }

                board[row - 1][col - 1] = val;
                printBoardPositions(board);
                winner = checkDown(board, col - 1, val);
                if (winner) {
                    System.out.println(name + " won !!!");
                    break;
                }
                winner = checkSide(board, row - 1, val);
                if (winner) {
                    System.out.println(name + " won !!!");
                    break;
                }

                if (row == col) {
                    winner = checkDiagonalRight(board, 0, val);
                    if (winner) {
                        System.out.println(name + " won !!!");
                        break;
                    }
                }

                if ((row - 1) + (col - 1) == 2) {
                    winner = checkDiagonalLeft(board, 2, val);
                    if (winner) {
                        System.out.println(name + " won !!!");
                        break;
                    }
                }
                break;

        }
        return winner;
    }
    boolean isFilled(int[][] board){

        for (int[] row : board){
            for (int element : row){
                if (element == -1){
                    return false;
                }
            }
        }

        return true;
    }

    boolean checkDown(int[][] board, int j, int val){
        int count = 0;
        for (int k = 0; k < 3; k++) {
            if (board[k][j] == -1){
                return false;
            }
            if (board[k][j] == val){
                count++;
            }
        }
        if (count == 3){
            return true;
        }
        return false;
    }

    boolean checkSide(int[][] board, int i, int val){
        int count = 0;
        for (int k = 0; k < 3; k++) {
            if (board[i][k] == -1){
                return false;
            }
            if (board[i][k] == val){
                count++;
            }
        }
        if (count == 3){
            return true;
        }
        return false;
    }

    boolean checkDiagonalRight(int[][] board, int j, int val){
        int count = 0;

            for (int i = 0; i < 3; i++) {
                if (board[i][i] == -1){
                    return false;
                }
                if (board[i][i] == val){
                    count++;
                }
            }

        if (count == 3){
            return true;
        }
        return false;
    }

    boolean checkDiagonalLeft(int[][] board, int j, int val){
        int count = 0;
        for (int i = 0; i < 3; i++, j--) {
            if (i+j == 2){
                if (board[i][j] == -1){
                    return false;
                }
                if (board[i][j] == val){
                    count++;
                }
            }
        }
        if (count == 3){
            return true;
        }
        return false;
    }

    /*
    void printBoard(int[][] board){
        System.out.println();
        System.out.println("Board");
        for (int[] row : board){
            for (int element : row){
                if (element == 1){
                    System.out.print("x ");
                }
                else {
                    System.out.print(element+" ");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
    }
    */

    void printBoardPositions(int[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    System.out.printf("   %-3s  ", "x");
                } else if (board[i][j] == 0) {
                    System.out.printf("   %-3s  ", "0");
                } else {
                    System.out.printf("  (%d,%d)  ", i + 1, j + 1);
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Inputs_And_Checks iac = new Inputs_And_Checks();
        iac.startTheGame();
    }

}
