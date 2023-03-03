import java.util.HashMap;
import java.util.Scanner;

public class Puzzles {
    Scanner scanner=new Scanner(System.in);

    private String messageToEncode="Meet me at the gallery at midnight, it's important";
    public static String caesarCipher(String message) {
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

    public void decodeMessage(HashMap<String, Person> personaggi){
        System.out.println("Checking "+personaggi.get("assistant").getName()+"'s laptop, you found a message, that he sent to"
                +personaggi.get("victim").getName()+" on the day of the murder.");
        System.out.println("The message is encoded, here it is:");
        System.out.println(caesarCipher(messageToEncode));
        System.out.println("Can you decode it?");
        System.out.println("Insert your answer, avoid adding unnecessary white spaces or punctuation marks: | or insert M -> go back to menu" );
    }
    public void clock(HashMap<String, Person> personaggi){
        System.out.println();
        System.out.println("According to "+personaggi.get("grandmother").getName()+", ");
        System.out.println("the clock goes fast: it adds 30 extra minutes to every passed hour. " +
                "\nBut every time at 12:00, 3:00, 6:00 and 9:00 (both a.m or p.m.), " +
                "\nthe clock freezes and doesn't work for 2 hours. " +
                "\nOn the day of the murder "+personaggi.get("grandmother").getName()+" set the right time on the clock at 1:00 p.m. " +
                "\nWhat was the real time when the clock showed 9:45 p.m., when "+personaggi.get("assistant").getName()+" came back home?");
        System.out.println();
        System.out.println("Input your answer in format: hh:mm, ex. 09:45 | or input M -> go back to menu" );

    }

    public void lockerCode() {
        System.out.println("| 4 | 5 | 6 | 7 | 8 | 9 |");
        System.out.println("|61 |52 |63 |94 |46 | X |");
        System.out.println("What number should be instead of X?");
        System.out.println("Input your answer | or input M -> go back to menu" );
    }
 public void whoTellsTruth(){
     System.out.println();
     System.out.println("Ben, Pit and Sam where near the gallery at the time of the murder.");
     System.out.println("One of them saw someone going out of the gallery.");
     System.out.println("Ben says: Pit saw someone.");
     System.out.println("Pit agrees: Yes, I saw someone.");
     System.out.println("Sam says: Ben saw someone");
     System.out.println("You know, that two of them are definitely lying. ");
     System.out.println("You have to find out who of them is the witness to interrogate him.");
     System.out.println("Input your answer | or input M -> go back to menu" );
 }

}
