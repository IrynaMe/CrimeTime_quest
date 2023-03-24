import lombok.Data;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Scanner;

public @Data class Puzzles {
    Scanner scanner = new Scanner(System.in);
    private String codeSister = "|61 |52 |63 |94 |46 | X |\t\tpart of a code from victim's sister";
    private String codeAssistant = "| 4 | 5 | 6 | 7 | 8 | 9 |\t\tpart of a code from victim's assistant";
    private String completeCode = "| 4 | 5 | 6 | 7 | 8 | 9 |\n  |61 |52 |63 |94 |46 | X |";

    private String messageToEncode = "Meet me at the gallery at midnight, it's important";
    private String answerPuzzleLocker = "18";
    private String answerPuzzleDecodeMessage = caesarCipher(messageToEncode).toUpperCase();
    private String answerPuzzleClock = "00:45";
    private String codeGarage = "61,52,63,94,46,18";
    private String answerLaptopPassword = "COURBET";

    private String answerPuzzleWhoTellsTruth = "BEN";
    private String answerSafeBoxCode = "042";
    private String answerBusNumber = "185";

    public String caesarCipher(String message) {
        String encrypted = "";
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if (Character.isLetter(ch)) {
                if (ch == 'z') {
                    encrypted += 'a';
                } else if (ch == 'Z') {
                    encrypted += 'A';
                } else {
                    encrypted += (char) (ch + 1);
                }
            } else {
                encrypted += ch;
            }
        }
        return encrypted;
    }

    public boolean decodeMessage2() { //buffered reared
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        boolean isSolved = false;
        System.out.println("*** Checking " + "'s laptop, you found a message, that he sent to "
                + " on the day of the murder. ***");
        System.out.println("*** The message is encoded, here it is: ***");
        System.out.println(caesarCipher(messageToEncode));
        System.out.println("*** Can you decode it? ***");

        do {
            System.out.println("*** Input your answer, avoid adding unnecessary white spaces or punctuation marks | or input L -> to try later ***");
            try {
                answer = reader.readLine().trim().toUpperCase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!answer.equals(messageToEncode.trim().toUpperCase()) && !answer.equals("L")) {
                System.out.println("*** Sorry, the answer is not correct ***");
            }
        } while (!answer.equals("L") && !answer.equals(messageToEncode.toUpperCase()));
        if (answer.equals(messageToEncode.toUpperCase())) {
            isSolved = true;
            System.out.println("*** Congratulations! You solved the puzzle! ***");
            System.out.println("*** The message is: " + messageToEncode + " ***");

        } else {
            isSolved = false;
            System.out.println("*** See you next time! ***");
        }
        return isSolved;
    }


    public boolean decodeMessage(HashMap<String, Person> personaggi) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        String answer = "";
        boolean isSolved = false;
        System.out.println("*** Checking " + personaggi.get("assistant").getName() + "'s laptop, you found a message, that he sent to "
                + personaggi.get("victim").getName() + " on the day of the murder. ***");
        System.out.println("*** The message is encoded, here it is: ***");
        System.out.println(caesarCipher(messageToEncode));
        System.out.println("*** Can you decode it? ***");

        do {
            System.out.println("*** Input your answer, avoid adding unnecessary white spaces or punctuation marks | or input L -> to try later ***");
            try {
                answer = reader.readLine().trim().toUpperCase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!answer.equals(messageToEncode.trim().toUpperCase()) && !answer.equals("L")) {
                System.out.println("*** Sorry, the answer is not correct ***");
            }
        } while (!answer.equals("L") && !answer.equals(messageToEncode.toUpperCase()));
        if (answer.equals(messageToEncode.toUpperCase())) {
            isSolved = true;
            System.out.println("*** Congratulations! You solved the puzzle! ***");
            System.out.println("*** The message is: " + messageToEncode + " ***");

        } else {
            isSolved = false;
            System.out.println("*** See you next time! ***");
        }
        return isSolved;
    }

    public boolean clock(HashMap<String, Person> personaggi) {
        System.out.println();
        String answer = "";
        boolean isSolved = false;
        System.out.println("According to " + personaggi.get("grandmother").getName() + ", ");
        System.out.println("the clock goes fast: it adds 30 extra minutes to every passed hour. " +
                "\nBut every time at 00:00, 3:00, 6:00, 9:00, 12:00, 15:00, 18:00, 21:00, " +
                "\nthe clock freezes and doesn't work for 2 hours. " +
                "\nOn the day of the murder " + personaggi.get("grandmother").getName() + " set the right time on the clock at 1:00 p.m. " +
                "\nWhat was the real time when the clock showed 21:45 p.m., when " + personaggi.get("assistant").getName() + " came back home?");
        System.out.println();
        System.out.println("Input your answer in format: hh:mm, ex. 21:45 | or input M -> go back to menu");
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (!answer.equals(answerPuzzleClock)) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerPuzzleClock));
        if (answer.equals(answerPuzzleClock)) {
            isSolved = true;
            System.out.println("Congratulations! You solved the puzzle!");
            System.out.println(answerPuzzleClock);

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }
        return isSolved;
    }

    public boolean lockerCode() {
        System.out.println();
        String answer = "";
        boolean isSolved = false;
        System.out.println("| 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("|61 |52 |63 |94 |46 | X |");
        System.out.println("What number should be instead of X?");
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (!answer.equals("L") && !answer.equals(answerPuzzleLocker)) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerPuzzleLocker));
        if (answer.equals(answerPuzzleLocker)) {
            isSolved = true;
            System.out.println("Congratulations! You solved the puzzle!");
            System.out.println(answerPuzzleLocker);

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }
        return isSolved;
    }


    public boolean whoTellsTruth() {
        boolean isSolved = false;
        String answer = "";
        System.out.println();
        System.out.println("Ben, Pit and Sam where near the gallery at the time of the murder.");
        System.out.println("One of them saw someone going out of the gallery.");
        System.out.println("Ben says: Pit saw someone.");
        System.out.println("Pit agrees: Yes, I saw someone.");
        System.out.println("Sam says: Ben saw someone");
        System.out.println("You know, that two of them are definitely lying. ");
        System.out.println("You have to find out who of them is the witness to interrogate him.");

        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (!answer.equals("L") && !answer.equals(answerPuzzleWhoTellsTruth)) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerPuzzleWhoTellsTruth));
        if (answer.equals(answerPuzzleWhoTellsTruth)) {
            isSolved = true;
            System.out.println("Congratulations! You solved the puzzle!");
            System.out.println(answerPuzzleLocker);

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }
        return isSolved;

    }


    public boolean safeBoxCode() {
        boolean isSolved = false;
        String answer = "";
        System.out.println("The combination lock has 3 numbers ");
        System.out.println("| 6 | 8 | 2 |    -> One number is correct and on the right place");
        System.out.println("| 6 | 1 | 4 |    -> One number is correct, but on a wrong place");
        System.out.println("| 2 | 0 | 6 |    -> Two numbers are correct, but on wrong places");
        System.out.println("| 7 | 3 | 8 |    -> All the numbers aren't correct");
        System.out.println("| 3 | 8 | 0 |    -> One number is correct, but on a wrong place");
        System.out.println("What is the code to open the lock?");
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (!answer.equals(answerSafeBoxCode) && !answer.equals("L")) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerSafeBoxCode));
        if (answer.equals(answerSafeBoxCode)) {
            isSolved = true;
            System.out.println("*** Congratulations! You solved the puzzle! ***");

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }

        return isSolved;
    }

    public boolean laptopPassword() {
        System.out.println("*** Password reminder: ***");
        String answer = "";
        boolean isSolved = false;
        //draw grid with letters
        int gridSize = 3; // Number of squares in each row/column
        int subSquareSize = 1; // Number of subsquares in each square

        // Array of letters to be printed in each cell, nested from top left to bottom right
        char[] letters = {'O', 'B', 'R', 'T', 'A', 'C', 'E', 'U', 'S'};
        int letterIndex = 0;
        for (int i = 0; i < gridSize; i++) {
            // top line of each square
            System.out.print("\t");
            for (int j = 0; j < gridSize; j++) {
                System.out.print("+---");
            }
            System.out.println("+");
            System.out.print("\t");
            //  middle rows of each square
            for (int k = 0; k < subSquareSize; k++) {
                for (int j = 0; j < gridSize; j++) {
                    System.out.print("| " + letters[letterIndex++] + " ");
                }
                System.out.println("|");
            }
        }
        System.out.print("\t");
        //  bottom line of the last square
        for (int j = 0; j < gridSize; j++) {
            System.out.print("+---");
        }

        System.out.println("+");
        System.out.println("0,0");
        System.out.println();

        //key to solve
        System.out.println("Key: 3,2; 1,3; 2,1; 3,3; 2,3; 1,1; 1,2");

        System.out.println("What is the word?");
        System.out.println();
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (!answer.equals(answerLaptopPassword) && !answer.equals("L")) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerLaptopPassword));
        if (answer.equals(answerLaptopPassword)) {
            isSolved = true;
            System.out.println("*** Congratulations! You solved the puzzle! ***");

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }

        return isSolved;
    }

    public boolean busNumber() {
        System.out.println("*** Number of the bus to take = A.  ***");
        String answer = "";
        boolean isSolved = false;
        for (int i = 0; i < 3; i++) {
            System.out.println("      *      #      .  ");
            System.out.println("A    ***    ###    ... ");
            System.out.println("    *****  #####  .....");
            if (i < 2) {
                System.out.println("+");
            }

        }
        System.out.println("=  ___________________");
        System.out.println();
        System.out.println("      .      .      .  ");
        System.out.println("B    ...    ...    ... ");
        System.out.println("    .....  .....  .....");
        do {
            System.out.println("What is the bus number? Input your answer | or input L -> to try later");
            answer = scanner.next();
            if (!answer.equals(answerBusNumber) && !answer.equals("L")) {
                System.out.println("Sorry, the answer is not correct");
            }
        } while (!answer.equals("L") && !answer.equals(answerBusNumber));
        if (answer.equals(answerBusNumber)) {
            isSolved = true;
            System.out.println("*** Congratulations! You solved the puzzle! ***");

        } else {
            isSolved = false;
            System.out.println("See you next time!");
        }

        return isSolved;

    }

}
