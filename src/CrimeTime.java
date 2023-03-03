import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CrimeTime implements CanCrimeTime {
    Scanner scanner = new Scanner(System.in);
    private Utility utility = new Utility();
    private HashMap<String, Person> personaggi = new HashMap<>();
    private Conversations conversations = new Conversations();
    ArrayList<String> answersReceived = null;
    ArrayList<String> evidence = null;
    String[] sisterQuestions = null;
    String[] sisterAnswers = null;

    static int countQuestionsSister = 1;
    @Override
    public void startGame() {
        utility.hello();

        personaggi = utility.personsSetup();

        System.out.println("Welcome, " + personaggi.get("detective").getName() + "!");

        System.out.println();
        conversations.introduction(personaggi);
        //get arrays with conversations
        sisterQuestions = conversations.sisterQuestions(personaggi);
        sisterAnswers = conversations.sisterAnswers(personaggi);
        System.out.println();
        menu();

    }

    public void menu() {
        LocalTime currentTime = LocalTime.now();
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println();
        System.out.println("What would you like to do: ");
        System.out.println("1 -> Visit victim's sister " + personaggi.get("sister").getName());
        System.out.println("2 -> Visit victim's boss " + personaggi.get("boss").getName());
        System.out.println("3 -> Visit victim's assistant " + personaggi.get("assistant").getName());
        System.out.println("4 -> Take a look at your notes");
        System.out.println("5 -> Take a look at the evidence");
        String choice = "";
        boolean flag;
        do {
            System.out.println("Input your choice: 1, 2 or 3: ");
            choice = scanner.next();
            flag = choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5");
            if (choice.equals("1")) {
                if (personaggi.get("detective").isAskedSister()){
                    System.out.println("It seems that "+personaggi.get("sister").getName() +" is still busy, come back later.");
                }else{
                    goSister();
                }
            } else if (choice.equals("2")) {
                goBoss();
            } else if (choice.equals("3")) {
                goAssistant();
            } else if (choice.equals("4")) {
                seeNotes();
            } else if (choice.equals("5")) {
                seeEvidence();
            } else {
                System.out.println("Input error: type a number 1, 2 or 3");
            }
        } while (!flag);
    }

    //
    @Override
    public void goSister() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println();
        System.out.println("You came to visit " + personaggi.get("sister").getName());

        int choice = -1;

        System.out.println();
        //Limit to ask max 3 questions
        //control to ask not more than 3 questions, then-break for 2 min


       LocalTime timeNewQuestion = LocalTime.now();
       // long minutesBtwQuestions = 0;
        long minutesFromLastQuestion = 4;
        if ( personaggi.get("detective").getTimeAskedSister()!= null) {
            LocalTime timeAskedSister = personaggi.get("detective").getTimeAskedSister();
         //   Duration durationBtwQuestions = Duration.between(timeAskedSister, timeNewQuestion);
            Duration durationFromStart = Duration.between(timeAskedSister, timeNewQuestion);
           // minutesBtwQuestions = durationBtwQuestions.toMinutes();

            minutesFromLastQuestion=durationFromStart.toMinutes();
        }
        //if the method is recaalled after 3 min
        if (minutesFromLastQuestion>=3){
            countQuestionsSister=1;
            personaggi.get("detective").setAskedSister(false);
        }

        while (countQuestionsSister < 4 && minutesFromLastQuestion>=3)  {
            if (sisterQuestions.length > 0) {
                int index = 1;
                System.out.println("--------------------------------------------");
                System.out.println("Which questions would you like to ask? ");
                //print the list of questions
                for (String element : sisterQuestions) {
                    System.out.println((index++) + " -> " + element);
                }
                System.out.println("--------------------------------------------");
                //control if choice is valid
                boolean flag;
                do {
                    System.out.println("Input your choice: 1-" + (sisterQuestions.length) + " -> Ask the question");
                    System.out.println("0 -> Go back to the menu");
                    choice = Integer.parseInt(scanner.next()) - 1;
                    flag = (choice <= sisterQuestions.length - 1 || choice == 0);
                } while (!flag);

                //detective asks a question
                if (choice != -1) {
                    //count questions
                    countQuestionsSister++;
                    //registrate the time of the question
                    personaggi.get("detective").setTimeAskedSister(LocalTime.now());
                    //print the chosen question and the answer
                    String givenAnswer = sisterAnswers[choice];
                    System.out.println(personaggi.get("detective").getName() + ": " + sisterQuestions[choice]);
                    System.out.println(givenAnswer);

                    //put the answer to the ArrayList-> to use in seeNotes();
                    answersReceived = new ArrayList<>();
                    answersReceived.add(givenAnswer);

                    //delete the used question and answer from the arrays;
                    for (int i = choice; i < sisterQuestions.length - 1; i++) {
                        sisterQuestions[i] = sisterQuestions[i + 1];
                    }
                    sisterQuestions = Arrays.copyOf(sisterQuestions, sisterQuestions.length - 1);
                    for (int i = choice; i < sisterAnswers.length - 1; i++) {
                        sisterAnswers[i] = sisterAnswers[i + 1];
                    }
                    sisterAnswers = Arrays.copyOf(sisterAnswers, sisterAnswers.length - 1);
                } else {
                    menu();
                }

            } else {
                //if there are no more questions in the array
                System.out.println(personaggi.get("sister").getName() + ": has no more valuable information to share.");
            }
        }
        if(countQuestionsSister >= 4  ){
            //when detective asked 3 questions at a time
            personaggi.get("detective").setAskedSister(true);
            int numReasonHurry=(int)(Math.random()*5);//to take index 0-4 of the array hurryUpSister
            System.out.println();
            System.out.println(personaggi.get("sister").getName() + ": "+conversations.getHurryUpSister()[numReasonHurry]);
            menu();
        }

        menu();
    }

    @Override
    public void talkPerson() {

    }

    @Override
    public void solvePuzzle() {

    }

    @Override
    public void goGallery() {

    }

    @Override
    public void goAssistant() {

    }

    @Override
    public void goCollector() {

    }


    @Override
    public void goBoss() {

    }

    @Override
    public void goVictim() {

    }


    @Override
    public void seeEvidence() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println();
        int index = 1;
        if (evidence != null) {
            System.out.println("The evidence that you found:");
            for (String element : evidence) {
                System.out.println((index++) + " " + element);
            }
        } else {
            System.out.println("You haven't found any evidence yet. ");
        }
        menu();

    }

    @Override
    public void seeNotes() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println();
        int index = 1;
        if (answersReceived != null) {
            System.out.println("The answers, that you wrote down:");
            for (String answer : answersReceived) {
                System.out.println((index++) + " " + answer);
            }
        } else {
            System.out.println("You haven't speak to anyone yet: there are no notes. ");
            System.out.println("Visit some people and ask some questions.");
        }
        menu();
    }

    @Override
    public void distractPolice() {

    }
}
