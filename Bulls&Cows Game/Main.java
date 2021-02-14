package bullscows;

import java.util.*;
class BullOrCow {
    int lengthCode;
    int preRange;
    String objRandom;
    String alphabet;

    public BullOrCow(int len, int range) {
        this.objRandom = "";
        this.alphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
        this.lengthCode = len;
        this.preRange = range;

    }
    public void info() {
        String secretinfo = "*".repeat(this.lengthCode);
        String info = String.format("The secret is prepared: %s ", secretinfo);
        if (this.preRange < 11) {
            info += String.format("(0-%d).", this.preRange - 1);
        } else if (this.preRange == 11){
            info += "(0-9, a).";
        } else {
            info += String.format("(0-9, a-%c).", this.alphabet.charAt(this.preRange - 1));
        }
        System.out.println(info);
        System.out.println("Okay, let's start a game!");
    }

    public void generateNum() {
        StringBuilder randomNum = new StringBuilder();
        Random random = new Random(10000);
        randomNum.append(this.alphabet.charAt(random.nextInt(this.preRange)));
        for (int i = 0; i < this.lengthCode - 1; i++) {
            char cur = this.alphabet.charAt(random.nextInt(this.preRange));
            String curStr = Character.toString(cur);
            if (randomNum.indexOf(curStr) == -1) {
                randomNum.append(cur);
            } else {
                i--;
            }
        }
        String res = randomNum.toString();
        //String res = String.format("The random secret number is %s.", randomNum.toString());
        this.objRandom = res;
    }

    public String giveGrade(String input) {
        int cows = 0;
        int bulls = 0;
        boolean isHave = false;
        for (int i = 0; i <input.length(); i++ ) {
            for (int j = 0; j < this.objRandom.length(); j++) {
                if (input.charAt(i) == this.objRandom.charAt(j)) {
                    isHave = true;
                    if (i == j) {
                        bulls += 1;
                    } else {
                        cows += 1;
                    }
                    break;
                }
            }
        }
        String grade = "";
        if (cows == 0 && bulls == 0) {
            grade = "Grade: None.";
            return grade;
        }

        String gradebull = "";
        String gradecow = "";
        if (cows == 1) {
            gradecow = "1 cow";
        } else if (cows > 1) {
            gradecow = String.format("%d cows", cows);
        }
        if (bulls == 1) {
            gradebull = "1 bull";
        } else if (bulls > 1) {
            gradebull = String.format("%d bulls", bulls);
        }
        if (gradebull.length() > 0) {
            if (gradecow.length() > 0) {
                grade = gradebull + " and " + gradecow;
            } else {
                grade = gradebull;
            }
        } else {
            grade = gradecow;
        }
        grade = "Grade: " + grade;
        return grade;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        try {
            int len = Integer.parseInt(input);
            System.out.println("Input the number of possible symbols in the code:");
            input = scanner.nextLine();
            try {
                int range = Integer.parseInt(input);
                if (len > range | len <= 0 | range <= 0) {
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", len, range);
                } else if (range > 36) {
                    System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else {
                    BullOrCow bullorcow = new BullOrCow(len, range);
                    bullorcow.info();
                    bullorcow.generateNum();
                    //System.out.println(bullorcow.objRandom);

                    int round = 1;
                    String guessInfo = String.format("Grade: %s bulls", len);
                    while (true) {
                        System.out.printf("Turn %d:", round);
                        System.out.println();
                        String guess = scanner.next();
                        String grade = bullorcow.giveGrade(guess);
                        System.out.println(grade);
                        if (grade.equals(guessInfo)) {
                            System.out.println("Congratulations! You guessed the secret code.");
                            break;
                        }
                        round++;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", input);
            }
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
        }
    }
}
