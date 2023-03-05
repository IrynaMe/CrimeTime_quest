import javax.swing.*;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Scanner;

public class Puzzles {
    Scanner scanner = new Scanner(System.in);
    private String codeSister = "|61 |52 |63 |94 |46 | X |";
    private String codeAssistant = "| 4 | 5 | 6 | 7 | 8 | 9 |";
    private String completeCode = "| 4 | 5 | 6 | 7 | 8 | 9 |\n|61 |52 |63 |94 |46 | X |";

    private String messageToEncode = "Meet me at the gallery at midnight, it's important";
    private String answerPuzzleLocker="18";
    private String answerPuzzleDecodeMessage=caesarCipher(messageToEncode).toUpperCase();
    private String answerPuzzleClock="00:45";

    private String answerPuzzleWhoTellsTruth="BEN";

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

    public void decodeMessage(HashMap<String, Person> personaggi) {
        System.out.println("Checking " + personaggi.get("assistant").getName() + "'s laptop, you found a message, that he sent to"
                + personaggi.get("victim").getName() + " on the day of the murder.");
        System.out.println("The message is encoded, here it is:");
        System.out.println(caesarCipher(messageToEncode));
        System.out.println("Can you decode it?");
        System.out.println("Insert your answer, avoid adding unnecessary white spaces or punctuation marks: | or insert M -> go back to menu");
    }

    public void clock(HashMap<String, Person> personaggi) {
        System.out.println();
        System.out.println("According to " + personaggi.get("grandmother").getName() + ", ");
        System.out.println("the clock goes fast: it adds 30 extra minutes to every passed hour. " +
                "\nBut every time at 00:00, 3:00, 6:00, 9:00, 12:00, 15:00, 18:00, 21:00, " +
                "\nthe clock freezes and doesn't work for 2 hours. " +
                "\nOn the day of the murder " + personaggi.get("grandmother").getName() + " set the right time on the clock at 1:00 p.m. " +
                "\nWhat was the real time when the clock showed 21:45 p.m., when " + personaggi.get("assistant").getName() + " came back home?");
        System.out.println();
        System.out.println("Input your answer in format: hh:mm, ex. 21:45 | or input M -> go back to menu");

    }

    public boolean lockerCode() {
        System.out.println();
        String answer = "";
        boolean isSolved=false;
        System.out.println("| 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("|61 |52 |63 |94 |46 | X |");
        System.out.println("What number should be instead of X?");
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (answer.equals(answerPuzzleLocker)){
                System.out.println("Sorry, the answer is not correct");
            }
        } while (answer.equals("L") || answer.equals(answerPuzzleLocker));
        if (answer.equals(answerPuzzleLocker)) {
            isSolved=true;
            System.out.println("Congratulations! You solved the puzzle!");
            System.out.println(answerPuzzleLocker);

        } else {
            isSolved=false;
            System.out.println("See you next time!");
        }
        return isSolved;
    }


    public boolean whoTellsTruth() {
       boolean isSolved=false;
        String answer="";
        System.out.println();
        System.out.println("Ben, Pit and Sam where near the gallery at the time of the murder.");
        System.out.println("One of them saw someone going out of the gallery.");
        System.out.println("Ben says: Pit saw someone.");
        System.out.println("Pit agrees: Yes, I saw someone.");
        System.out.println("Sam says: Ben saw someone");
        System.out.println("You know, that two of them are definitely lying. ");
        System.out.println("You have to find out who of them is the witness to interrogate him.");
        System.out.println("Input your answer | or input M -> go back to menu");
        do {
            System.out.println("Input your answer | or input L -> to try later");
            answer = scanner.next().toUpperCase();
            if (answer.equals(answerPuzzleWhoTellsTruth)){
                System.out.println("Sorry, the answer is not correct");
            }
        } while (answer.equals("L") || answer.equals(answerPuzzleWhoTellsTruth));
        if (answer.equals(answerPuzzleWhoTellsTruth)) {
            isSolved=true;
            System.out.println("Congratulations! You solved the puzzle!");
            System.out.println(answerPuzzleLocker);

        } else {
            isSolved=false;
            System.out.println("See you next time!");
        }
        return isSolved;

    }

    public void laptopPassword() {

    }

    public String getCodeSister() {
        return codeSister;
    }


    public String getCodeAssistant() {
        return codeAssistant;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public String getMessageToEncode() {
        return messageToEncode;
    }

    public String getAnswerPuzzleLocker() {
        return answerPuzzleLocker;
    }

    public String getAnswerPuzzleDecodeMessage() {
        return answerPuzzleDecodeMessage;
    }

    public String getAnswerPuzzleClock() {
        return answerPuzzleClock;
    }

    public String getAnswerPuzzleWhoTellsTruth() {
        return answerPuzzleWhoTellsTruth;
    }
}
