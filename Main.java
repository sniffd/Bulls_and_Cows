package bullscows;

import java.util.*;

public class Main {

    private static int turn = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int length;
        int radix;
        String secretCode;
        String input = null;
        boolean isGuessed = false;

        try {
            System.out.println("Input the length of the secret code:");
            input = sc.nextLine();
            length = Integer.parseInt(input);
            System.out.println("Input the number of possible symbols in the code:");
            input = sc.nextLine();
            radix = Integer.parseInt(input);
            if (length < 1) {
                System.out.println("Error: it's not possible to generate a code with a length of " + length + ".");
                return;
            }
            if (length > radix) {
                System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " +
                        radix + " unique symbols.");
                return;
            }
            if (radix > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            }
            secretCode = getSecretCode(length, radix);
            System.out.println("Okay, let's start a game!");

            while (!isGuessed) {
                isGuessed = getGrade(secretCode);
            }

            System.out.println("Congratulations! You guessed the secret code.");
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
        }
    }

    private static String getSecretCode(int length, int radix) {
        StringBuilder sb = new StringBuilder();
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Set<Integer> indexes = new HashSet<>();
        Random random = new Random();

        if (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
        } else {
            while (sb.length() < length) {
                int index = random.nextInt(radix);
                if (!indexes.contains(index)) {
                    indexes.add(index);
                    sb.append(chars.charAt(index));
                }
            }
        }
        System.out.println("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print('*');
        }
        System.out.print(" 0-");
        if (radix >= 10) {
            System.out.print(9);
        } else
            System.out.println(radix - 1 + ").");
        if (radix > 10) {
            System.out.print(", a-" + chars.charAt(radix - 1) + ").");
        }
        return sb.toString();
    }

    public static boolean getGrade(String secret) {
        Scanner sc = new Scanner(System.in);
        String number;
        String grade;
        int bulls = 0;
        int cows = 0;

        System.out.println("Turn " + turn + ":");
        turn++;
        number = sc.next();

        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == secret.charAt(i)) {
                bulls++;
            }
            for (int j = 0; j < secret.length(); j++) {
                if (j == i) {
                    continue;
                }
                if (number.charAt(i) == secret.charAt(j)) {
                    cows++;
                }
            }
        }

        if (bulls > 0 && cows > 0) {
            grade = bulls + " bull(s) and " + cows + " cow(s)";
        } else if (bulls > 0) {
            grade = bulls + " bull(s)";
        } else if (cows > 0) {
            grade = cows + " cow(s)";
        } else {
            grade = "None";
        }

        System.out.println("Grade: " + grade);

        return bulls == secret.length();
    }
}
