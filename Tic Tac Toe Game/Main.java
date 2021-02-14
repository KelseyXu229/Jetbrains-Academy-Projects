package tictactoe;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static int[][] printSave(String str, int[][] intMat) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j =0; j < 3; j++){
                String temp = str.substring(3 * i + j, 3 * i + j+1);
                switch (temp) {
                    case "X":
                        intMat[i][j] = 1;
                        break;
                    case "O":
                        intMat[i][j] = -1;
                        break;
                    case "_":
                        temp = " ";
                        intMat[i][j] = 0;
                        break;
                }
                System.out.print(temp + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
        return intMat;
    }
    public static int[] rowWinCount(int[][] intMat, int[] resCount) {
        for (int i = 0; i < 3; i++) {
            int rowSum = Arrays.stream(intMat[i]).sum();
            if (rowSum == 3) {
                resCount[0] += 1;
            }else if (rowSum == -3) {
                resCount[1] += 1;
            }
        }
        return resCount;
    }
    public static int[] colWinCount(int[][] intMat, int[] resCount) {
        for (int i = 0; i < 3; i++) {
            int colSum =0;
            for (int j =0; j < 3; j++) {
                if (intMat[j][i] == 0) {
                    resCount[2] = 1;
                }
                colSum += intMat[j][i];
            }
            if (colSum == 3) {
                resCount[0] += 1;
            }
            if (colSum == -3) {
                resCount[1] += 1;
            }
        }
        return resCount;
    }
    public static int[] diagWinCount(int[][] intMat, int[] resCount) {
        int posDiagSum = 0;
        int negDiagSum = 0;
        for (int i = 0; i < 3; i++) {
            posDiagSum += intMat[i][i];
            negDiagSum += intMat[i][2 - i];
        }
        if (posDiagSum == 3) {
            resCount[0] += 1;
        }
        if (posDiagSum == -3) {
            resCount[1] += 1;
        }
        if (negDiagSum == 3) {
            resCount[0] += 1;
        }
        if (negDiagSum == -3) {
            resCount[1] += 1;
        }
        return resCount;
    }

    public static void main(String[] args) {
        // write your code here
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter cells: ");
        //String str = scanner.next();
        String str = "         ";
        StringBuilder strBuilder = new StringBuilder(str);
        int[][] intMat = new int[3][3];
        intMat = printSave(str, intMat);
        int round = 0;
        while (true) {
            try {
                System.out.print("Enter the coordinates: ");
                Scanner inputInt = new Scanner(System.in);
                int x = inputInt.nextInt();
                int y = inputInt.nextInt();
                int index = 3 * (x - 1) + y - 1;
                if (x > 3 | x < 1 | y < 1 | y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (intMat[x - 1][y - 1] != 0) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    if (round % 2 == 0) {
                        strBuilder.setCharAt(index, 'X');
                    } else {
                        strBuilder.setCharAt(index, 'O');
                    }
                    round++;
                    str = strBuilder.toString();
                    intMat = printSave(str, intMat);
                    int[] resCount = new int[3]; //[xwin, owin, _exist]
//        for (int i = 0; i < 3; i++) {
//            System.out.println(Arrays.toString(intMat[i]));
//        }
                    int totalSum = 0;
                    for (int i = 0; i < 3; i++) {
                        totalSum += Arrays.stream(intMat[i]).sum();
                        //System.out.println(totalSum);
                    }
                    if (totalSum >= -1 && totalSum <=1 ) {
                        resCount = rowWinCount(intMat, resCount);
                        resCount = colWinCount(intMat, resCount);
                        resCount = diagWinCount(intMat, resCount);
                        if (resCount[0] > 0 && resCount[1] >0) {
                            System.out.println("Impossible");
                            break;
                        } else if (resCount[0] > 0) {
                            System.out.println("X wins");
                            break;
                        } else if (resCount[1] >0) {
                            System.out.println("O wins");
                            break;
                        } else if (resCount[2] > 0) {
                            //System.out.println("Game not finished");
                            continue;
                        } else {
                            System.out.println("Draw");
                            break;
                        }
                    } else {
                        System.out.println("Impossible");
                    }
                }

            } catch (Exception e) {
                System.out.println("You should enter numbers!");
            }
        }
    }
}