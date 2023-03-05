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
    private Puzzles puzzles = new Puzzles();
    private final int BREAKTIME = 0;//how long is the pause after the limit of questions is reached
    private HashMap<String, Person> personaggi = new HashMap<>();
    private Conversations conversations = new Conversations();
    private ArrayList<String> answersReceived = new ArrayList<>();
    private ArrayList<String> evidence = new ArrayList<>();
    // private ArrayList<String> puzzlesToSolve= new ArrayList<>();
    private String[] sisterQuestions = null;
    private String[] sisterAnswers = null;

    private String[] bossQuestions = null;
    private String[] bossAnswers = null;
    private String[] assistantQuestions = null;
    private String[] assistantAnswers = null;
    private String[] grandmotherQuestions = null;
    private String[] grandmotherAnswers = null;

    private static int countQuestionsSister = 1;

    private static int countQuestionsBoss = 1;
    private static int countQuestionsAssistant = 1;
    private static int countQuestionsGrandmother = 1;


    @Override
    public void startGame() {
        utility.hello();

        personaggi = utility.personsSetup();
        System.out.println();
        System.out.println("*** Welcome, " + personaggi.get("detective").getName() + "! ***");

        System.out.println();
        conversations.introduction(personaggi);
        //get arrays with conversations
        sisterQuestions = conversations.sisterQuestions(personaggi);
        sisterAnswers = conversations.sisterAnswers(personaggi);
        bossQuestions = conversations.bossQuestions(personaggi);
        bossAnswers = conversations.bossAnswers(personaggi);
        assistantQuestions = conversations.assistantQuestions(personaggi);
        assistantAnswers = conversations.assistantAnswers(personaggi);
        grandmotherQuestions=conversations.grandmotherQuestions(personaggi);
        grandmotherAnswers=conversations.grandmotherAnswers(personaggi);
        personaggi.get("detective").setTimeAskedSister(LocalTime.now().minusMinutes(10));

        personaggi.get("detective").setTimeAskedBoss(LocalTime.now().minusMinutes(10));
        personaggi.get("detective").setTimeAskedAssistant(LocalTime.now().minusMinutes(10));
        System.out.println();
        menu();

    }

    public void menu() {
        LocalTime currentTime = LocalTime.now();
        boolean flag0 = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        System.out.println();
        if (answersReceived.size() > 0) {
            System.out.println("TEST arr:" + answersReceived.toString());
        }
        System.out.println("--------------------------------------------");
        System.out.println("*** What would you like to do? ***");
        System.out.println("S -> Visit victim's sister " + personaggi.get("sister").getName());
        if (personaggi.get("detective").isArrestedBoss()==false){
            System.out.println("B -> Visit victim's boss " + personaggi.get("boss").getName());
        }

        if (personaggi.get("detective").isTalkedSister() && personaggi.get("detective").isTalkedBoss()) {
            System.out.println("A -> Visit victim's assistant " + personaggi.get("assistant").getName());
            flag1 = true;
        }
        if (answersReceived.contains("grandma")) {
            System.out.println("G -> Visit " + personaggi.get("assistant").getName() + "'s grandmother " + personaggi.get("grandmother").getName());
            flag2 = true;
        }


        if (answersReceived.size() > 0) {
            System.out.println("N -> Take a look at your notes");
            flag3 = true;
        }
        if (evidence.size() > 0) {
            System.out.println("E -> Take a look at the evidence");
            flag4 = true;
        }
        //if puzzles are unlocked
        if (personaggi.get("detective").isPuzzleLocker()||personaggi.get("detective").isPuzzleWhoTellsTruth()) {
            System.out.println("P -> Solve a puzzle");
            flag5 = true;
        }
        //if puzzle Locker code is solved
        if (personaggi.get("detective").isCanOpenGarage()) {
            System.out.println("O -> Open the garage door");
            flag6 = true;
        }


        String choice = "";

        do {
            System.out.println();
            System.out.println("*** Input your choice: ***");
            choice = scanner.next().toUpperCase();
            if (choice.equals("S")) {
                goSister(currentTime);
                flag0 = true;
            } else if (choice.equals("B")&&personaggi.get("detective").isArrestedBoss()==false) {
                goBoss(currentTime);
                flag0 = true;
            } else if (choice.equals("A") && flag1 == true) {
                goAssistant(currentTime);
                flag0 = true;
            } else if (choice.equals("G") && flag2 == true) {
                goGrandmother(currentTime);
                flag0 = true;
            } else if (choice.equals("N") && flag3 == true) {
                seeNotes();
                flag0 = true;
            } else if (choice.equals("E") && flag4 == true) {
                seeEvidence();
                flag0 = true;
            } else if (choice.equals("P") && flag5 == true) {
                solvePuzzle();
                flag0 = true;
            } else System.out.println();
            System.out.println("*** Input error: try again  ***");

        } while (!flag0);

    }


    public void goSister(LocalTime timeStart) {
        //limit questions at a time: 3, then wait 2 min
        //ask questions only 2 or more min after the last question
        Duration pauseTalk = Duration.ZERO;
        pauseTalk = Duration.between(personaggi.get("detective").getTimeAskedSister(), timeStart);
        long minutesPause = pauseTalk.toMinutes();
        if (minutesPause < BREAKTIME && sisterQuestions.length > 0) {
            System.out.println();
            System.out.println("*** It seems that " + personaggi.get("sister").getName() + " is still busy, come back later. ***");
            menu();
        }
        //if the method is recalled 2 or more min after reaching the limit for questions
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("*** You came to visit " + personaggi.get("sister").getName() + " ***");

        String choiceStr = "";
        int choice = -1;
        System.out.println();

        //reset questions counter if 2 or more minutes passed after reaching the limit for questions
        if (personaggi.get("detective").isAskedSister()) {
            countQuestionsSister = 1;
            personaggi.get("detective").setAskedSister(false);
        }

        while (countQuestionsSister < 4 && personaggi.get("detective").isAskedSister() == false) {
            if (sisterQuestions.length > 0) {
                int index = 1;
                System.out.println();
                System.out.println("*** What would you like to ask? ***");
                //print the list of questions
                for (String element : sisterQuestions) {
                    System.out.println((index++) + " -> " + element);
                }

                //control if choice is valid
                boolean flag;
                boolean isDigit = false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    System.out.println("1 - " + (sisterQuestions.length) + " -> Ask the question");
                    System.out.println("0 -> Go back to the menu");
                    choiceStr = scanner.next();

                    for (char c : choiceStr.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            isDigit = false;
                            break;
                        } else {
                            isDigit = true;
                        }
                    }
                    if (isDigit) {
                        choice = Integer.parseInt(choiceStr) - 1;
                    }
                    flag = (isDigit && (choice <= sisterQuestions.length - 1 || choice == -1));
                    if (!flag) {
                        System.out.println("Input error, try again");
                    }
                } while (!flag);

                //detective asks a question
                if (choice != -1) {
                    //print the chosen question and the answer
                    String givenAnswer = sisterAnswers[choice];
                    String explainCode = "";
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(personaggi.get("detective").getName() + ": " + sisterQuestions[choice]);
                    System.out.println(givenAnswer);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    if (choice == sisterQuestions.length-1) {
                        if (personaggi.get("detective").isCodeFromSister() == false) {
                            // sister explains about garage locker code
                            explainCode = conversations.explainCode(personaggi);
                            personaggi.get("detective").setCodeFromSister(true);
                            answersReceived.add(explainCode);
                            if (personaggi.get("detective").isCodeFromAssistant() == false) {
                                String codeSister = puzzles.getCodeSister();
                                evidence.add(codeSister);
                            } else {
                                System.out.println("*** Now you have both parts of the instruction! ***");
                                System.out.println("*** When you are ready to solve the puzzle, choose this option in the menu. ***");
                                String completeCode = puzzles.getCompleteCode();
                                evidence.add(completeCode);
                                personaggi.get("detective").setPuzzleLocker(true);
                                // puzzlesToSolve.add("codeLocker");
                            }
                        }
                    }
                    //count questions
                    countQuestionsSister++;
                    personaggi.get("detective").setTalkedSister(true);
                    //put the answer to the ArrayList-> to use in seeNotes();
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
            } else if(personaggi.get("detective").isActionSister()==false){
                //if there are no more questions in the array
                System.out.println();
                System.out.println("*** " + personaggi.get("sister").getName() + ": has no more valuable information to share. ***");
                menu();
            }
        }
        //when detective reached the limit for questions
        if (countQuestionsSister >= 4) {
            personaggi.get("detective").setAskedSister(true);
            //registrate the time when the limit for questions was reached
            personaggi.get("detective").setTimeAskedSister(LocalTime.now());
            int numReasonHurry = (int) (Math.random() * 4);//to take index 0-3 of the array hurryUpSister
            System.out.println();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(personaggi.get("sister").getName() + ": " + conversations.getHurryUpSister()[numReasonHurry]);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            menu();
        }
        //when after detective heard boss speaks with collector->boolean sisterAction=true->sister shares new info
        if (personaggi.get("detective").isActionSister()){
            System.out.println();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            String newWitness=personaggi.get("sister").getName() + ": " + conversations.getSisterAboutWitness();
            System.out.println(newWitness);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            answersReceived.add(newWitness);
            personaggi.get("detective").setActionSister(false);
            personaggi.get("detective").setPuzzleWhoTellsTruth(true);
        }
        menu();
    }

    //
    @Override
    public void goBoss(LocalTime timeStart) {
        //limit questions at a time: 2, then wait 2 min
        //ask questions only 2 or more min after the last question
        Duration pauseTalk = Duration.ZERO;
        pauseTalk = Duration.between(personaggi.get("detective").getTimeAskedBoss(), timeStart);
        long minutesPause = pauseTalk.toMinutes();
        if (minutesPause < BREAKTIME && bossQuestions.length > 0) {
            System.out.println();
            System.out.println("*** It seems that " + personaggi.get("boss").getName() + " is still busy, come back later. ***");
            menu();
        }


        String choiceStr = "";
        int choice = -1;
        System.out.println();

        //reset questions counter if 2 or more minutes passed after reaching the limit for questions
        if (personaggi.get("detective").isAskedBoss()) {
            countQuestionsBoss = 1;
            personaggi.get("detective").setAskedBoss(false);
        }

        while (countQuestionsBoss < 3 && personaggi.get("detective").isAskedBoss() == false) {
            if (bossQuestions.length > 0) {
                int index = 1;
                System.out.println();

                //if the method is recalled 2 or more min after the last question
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.println("*** You came to visit " + personaggi.get("boss").getName() + " ***");

                System.out.println("*** What would you like to ask? ***");
                //print the list of questions
                for (String element : bossQuestions) {
                    System.out.println((index++) + " -> " + element);
                }

                //control if choice is valid
                boolean flag;
                boolean isDigit=false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    System.out.println("1 - " + (bossQuestions.length) + " -> Ask the question");
                    System.out.println("0 -> Go back to the menu");
                    choiceStr = scanner.next();
                    for (char c : choiceStr.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            isDigit = false;
                            break;
                        } else {
                            isDigit = true;
                        }
                    }
                    if (isDigit) {
                        choice = Integer.parseInt(choiceStr) - 1;
                    }
                    flag = (isDigit && (choice <= bossQuestions.length - 1 || choice == -1));
                    if (!flag) {
                        System.out.println("Input error, try again");
                    }
                } while (!flag);

                //detective asks a question
                if (choice != -1) {
                    //print the chosen question and the answer
                    String givenAnswer = bossAnswers[choice];
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(personaggi.get("detective").getName() + ": " + bossQuestions[choice]);
                    System.out.println(givenAnswer);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                    //count questions
                    countQuestionsBoss++;
                    personaggi.get("detective").setTalkedBoss(true);
                    //put the answer to the ArrayList-> to use in seeNotes();
                    answersReceived.add(givenAnswer);
                    //set boolean for boss to take action after all the questions from the array are asked
                    if (bossQuestions.length == 1) {
                        personaggi.get("detective").setActionBoss(true);
                    }

                    //delete the used question and answer from the arrays;
                    for (int i = choice; i < bossQuestions.length - 1; i++) {
                        bossQuestions[i] = bossQuestions[i + 1];
                    }
                    bossQuestions = Arrays.copyOf(bossQuestions, bossQuestions.length - 1);
                    for (int i = choice; i < bossAnswers.length - 1; i++) {
                        bossAnswers[i] = bossAnswers[i + 1];
                    }
                    bossAnswers = Arrays.copyOf(bossAnswers, bossAnswers.length - 1);
                } else {
                    menu();
                }
            } else if (personaggi.get("detective").isActionBoss()) {
                //boss takes action when all the questions from the array are asked

                System.out.println();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(personaggi.get("boss").getName() + ": " + conversations.getHurryUpBoss()[4]);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println();
                conversations.clueBoss(personaggi);
                //change value of boolean after the action as taken
                personaggi.get("detective").setActionBoss(false);
                //sister can share information about a witness->puzzle whoTellsTruth()
                personaggi.get("detective").setActionSister(true);

            } else if (personaggi.get("detective").isWitnessBoss()==false){
                //if there are no more questions in the array
                System.out.println();
                // System.out.println("*** " + personaggi.get("boss").getName() + ": has no more valuable information to share. ***");
                //detective should resolve puzzle whoTellsTruth()
                System.out.println("*** You should find evidence that incriminates " + personaggi.get("boss").getName() + " ***");
                menu();
            }
        }
        //when detective reached the limit for questions
        if (countQuestionsBoss >= 3) {
            personaggi.get("detective").setAskedBoss(true);
            //registrate the time when limit for questions was reached
            personaggi.get("detective").setTimeAskedBoss(LocalTime.now());
            int numReasonHurry = (int) (Math.random() * 4);//to take index 0-3 of the array hurryUpBoss
            System.out.println();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(personaggi.get("boss").getName() + ": " + conversations.getHurryUpBoss()[numReasonHurry]);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            menu();
        }
        if (personaggi.get("detective").isWitnessBoss()){
            System.out.println();
            conversations.imInnocentBoss(personaggi);
            System.out.println();
            personaggi.get("detective").setArrestedBoss(true);
        }

        menu();
    }

    @Override
    public void goAssistant(LocalTime timeStart) {
        //limit questions at a time: 2, then wait 2 min
        //ask questions only 2 or more min after the last question
        Duration pauseTalk = Duration.ZERO;
        pauseTalk = Duration.between(personaggi.get("detective").getTimeAskedAssistant(), timeStart);
        long minutesPause = pauseTalk.toMinutes();
        if (minutesPause < BREAKTIME && assistantQuestions.length > 0) {
            System.out.println();
            System.out.println("*** It seems that " + personaggi.get("assistant").getName() + " is still busy, come back later. ***");
            menu();
        }

        String choiceStr = "";
        int choice = -1;
        System.out.println();

        //reset questions counter if 2 or more minutes passed after reaching the limit for questions
        if (personaggi.get("detective").isAskedAssistant()) {
            countQuestionsAssistant = 1;
            personaggi.get("detective").setAskedAssistant(false);
        }

        while (countQuestionsAssistant < 3 && personaggi.get("detective").isAskedAssistant() == false) {
            if (assistantQuestions.length > 0) {
                int index = 1;
                System.out.println();

                //if the method is recalled 2 or more min after the last question
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.println("*** You came to visit " + personaggi.get("assistant").getName() + " ***");

                System.out.println("*** What would you like to ask? ***");
                //print the list of questions
                for (String element : assistantQuestions) {
                    System.out.println((index++) + " -> " + element);
                }

                //control if choice is valid
                boolean flag;
                boolean isDigit=false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    System.out.println("1 - " + (assistantQuestions.length) + " -> Ask the question");
                    System.out.println("0 -> Go back to the menu");
                    choiceStr = scanner.next();
                    for (char c : choiceStr.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            isDigit = false;
                            break;
                        } else {
                            isDigit = true;
                        }
                    }
                    if (isDigit) {
                        choice = Integer.parseInt(choiceStr) - 1;
                    }
                    flag = (isDigit && (choice <= assistantQuestions.length - 1 || choice == -1));
                    if (!flag) {
                        System.out.println("Input error, try again");
                    }
                } while (!flag);

                //detective asks a question
                if (choice != -1) {
                    if (choice == assistantQuestions.length-1) {
                        personaggi.get("detective").setCanMeetGrandmother(true);
                    }
                    //print the chosen question and the answer
                    String givenAnswer = assistantAnswers[choice];
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(personaggi.get("detective").getName() + ": " + assistantQuestions[choice]);
                    System.out.println(givenAnswer);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                    //count questions
                    countQuestionsAssistant++;

                    //put the answer to the ArrayList-> to use in seeNotes();
                    answersReceived.add(givenAnswer);
                    //set boolean for assistant to take action after all the questions from the array are asked
                    if (assistantQuestions.length == 1) {
                        personaggi.get("detective").setActionAssistant(true);
                    }

                    //delete the used question and answer from the arrays;
                    for (int i = choice; i < assistantQuestions.length - 1; i++) {
                        assistantQuestions[i] = assistantQuestions[i + 1];
                    }
                    assistantQuestions = Arrays.copyOf(assistantQuestions, assistantQuestions.length - 1);
                    for (int i = choice; i < assistantAnswers.length - 1; i++) {
                        assistantAnswers[i] = assistantAnswers[i + 1];
                    }
                    assistantAnswers = Arrays.copyOf(assistantAnswers, assistantAnswers.length - 1);
                } else {
                    menu();
                }
            } else if (personaggi.get("detective").isActionAssistant()) {
                //assistant takes action when all the questions from the array are asked
                System.out.println();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(personaggi.get("assistant").getName() + ": " + conversations.getHurryUpAssistant()[4]);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println();
                conversations.clueAssistant(personaggi);
                personaggi.get("detective").setActionAssistant(false);
                System.out.println("*** Here is the code, that " + personaggi.get("assistant").getName() + "gave you: ***");
                String codeAssistant = puzzles.getCodeAssistant();
                System.out.println();
                System.out.println(codeAssistant);
                System.out.println();

                if (personaggi.get("detective").isCodeFromAssistant() == false) {
                    personaggi.get("detective").setCodeFromAssistant(true);
                    if (personaggi.get("detective").isCodeFromSister() == false) {
                        System.out.println("*** You put the code in the bag to keep as evidence ***");
                        evidence.add(codeAssistant);
                    } else {
                        System.out.println("*** Now you have both parts of the instruction! ***");
                        System.out.println("*** When you are ready to solve the puzzle, choose this option in the menu. ***");
                        String completeCode = puzzles.getCompleteCode();
                        evidence.add(completeCode);
                    }
                }


            } else {
                //if there are no more questions in the array
                System.out.println();
                // System.out.println("*** " + personaggi.get("assistant").getName() + ": has no more valuable information to share. ***");
                //detective should unlock the garage
                System.out.println("You should unlock the garage and understand what assistant wanted to take there");
                // System.out.println("*** You should find evidence that incriminates " +personaggi.get("assistant").getName()+ " ***");
                menu();
            }
        }
        //when detective reached the limit for questions
        if (countQuestionsAssistant >= 3) {
            personaggi.get("detective").setAskedAssistant(true);
            //registrate the time when limit for questions was reached
            personaggi.get("detective").setTimeAskedAssistant(LocalTime.now());
            int numReasonHurry = (int) (Math.random() * 4);//to take index 0-3 of the array hurryUpAssistant
            System.out.println();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(personaggi.get("assistant").getName() + ": " + conversations.getHurryUpAssistant()[numReasonHurry]);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            menu();
        }

        menu();
    }

    public void goGrandmother(LocalTime timeStart) {
        //limit for 3 available questions at a time, the last question can be asked only with boolean condition
        //ask the last question only 2 or more min after the last question
        Duration pauseTalk = Duration.ZERO;
        pauseTalk = Duration.between(personaggi.get("detective").getTimeAskedGrandmother(), timeStart);
        long minutesPause = pauseTalk.toMinutes();
        if (minutesPause < BREAKTIME && grandmotherQuestions.length > 0) {
            System.out.println();
            System.out.println("*** It seems that " + personaggi.get("grandmother").getName() + " is still busy, come back later. ***");
            menu();
            String choiceStr = "";
            int choice = -1;
            System.out.println();
            //reset questions counter if 2 or more minutes passed after reaching the limit for questions
            if (personaggi.get("detective").isAskedGrandmother()) {
                countQuestionsGrandmother = 1;
                personaggi.get("detective").setAskedGrandmother(false);
            }
            while (countQuestionsGrandmother < 3 && personaggi.get("detective").isAskedGrandmother() == false) {
                if (grandmotherQuestions.length > 1) {
                    int index = 1;
                    System.out.println();

                    //if the method is recalled 2 or more min after the last question
                    System.out.println();
                    System.out.println("--------------------------------------------");
                    System.out.println("*** You came to visit " + personaggi.get("grandmother").getName() + " ***");

                    System.out.println("*** What would you like to ask? ***");
                    //print the list of questions except ones that need to be asked later
                    for (String element : grandmotherQuestions) {
                        if ((!element.contains("Can I take a look at")||element.contains("your clock"))&&personaggi.get("detective").isCanGoRoomAssistant()==false){
                            System.out.println((index++) + " -> " + element);
                        }
                    }
                    //control if choice is valid
                    boolean flag;
                    boolean isDigit = false;
                    do {
                        System.out.println();
                        System.out.println("*** Input your choice  ***");
                        System.out.println("1 - " + (grandmotherQuestions.length) + " -> Ask the question");
                        System.out.println("0 -> Go back to the menu");
                        choiceStr = scanner.next();
                        for (char c : choiceStr.toCharArray()) {
                            if (!Character.isDigit(c)) {
                                isDigit = false;
                                break;
                            } else {
                                isDigit = true;
                            }
                        }
                        if (isDigit) {
                            choice = Integer.parseInt(choiceStr) - 1;
                        }
                        if (choice == grandmotherQuestions.length-1&&personaggi.get("detective").isCanGoRoomAssistant()==false) {
                            flag=false;
                        } else flag = (isDigit && (choice <= grandmotherQuestions.length - 1 || choice == -1));
                        if (!flag) {
                            System.out.println("Input error, try again");
                        }
                    } while (!flag);
                    //detective asks a question
                    String givenAnswer="";
                    if (choice != -1) {
                        //print the chosen question and the answer
                        System.out.println();
                        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                        System.out.println(personaggi.get("detective").getName() + ": " + grandmotherQuestions[choice]);
                        System.out.println(givenAnswer);
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        //count questions
                        countQuestionsGrandmother++;
                        //put the answer to the ArrayList-> to use in seeNotes();
                        answersReceived.add(givenAnswer);
                        //set boolean for grandmother to take action after all the questions from the array are asked
                        if (grandmotherQuestions.length == 1) {
                            personaggi.get("detective").setActionGrandmother(true);
                        }
                        //delete the used question and answer from the arrays;
                        for (int i = choice; i < grandmotherQuestions.length - 1; i++) {
                            grandmotherQuestions[i] = grandmotherQuestions[i + 1];
                        }
                        grandmotherQuestions = Arrays.copyOf(grandmotherQuestions, grandmotherQuestions.length - 1);
                        for (int i = choice; i < grandmotherAnswers.length - 1; i++) {
                            grandmotherAnswers[i] = grandmotherAnswers[i + 1];
                        }
                        grandmotherAnswers = Arrays.copyOf(grandmotherAnswers, grandmotherAnswers.length - 1);
                    } else {
                        menu();
                    }
                } else if (personaggi.get("detective").isCanGoRoomAssistant()) {
                    //can  ask the last question after first 3 questions from the array are asked
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println();
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println();
                } else {
                    //if there are no more questions in the array
                    System.out.println();
                    System.out.println("*** " + personaggi.get("grandmother").getName() + ": has no more valuable information to share. ***");
                    menu();
                }
            }
            //when detective reached the limit for questions
            if (countQuestionsGrandmother >= 3) {
                personaggi.get("detective").setAskedGrandmother(true);
                //registrate the time when limit for questions was reached
                personaggi.get("detective").setTimeAskedGrandmother(LocalTime.now());
                System.out.println();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(personaggi.get("grandmother").getName() + ": Sorry darling, I'm so tired, I need to sleep. Come back later, please");
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
               //flag to go to the assistant's room next time
                personaggi.get("detective").setCanGoRoomAssistant(true);
                menu();
            }
            menu();
        }
    }
    @Override
    public void talkPerson() {

    }

    @Override
    public void solvePuzzle() {
        if (personaggi.get("detective").isPuzzleLocker()) {
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("*** Choose a puzzle ***");
           boolean isSolved=false;

            //puzzle with code locker
            if (personaggi.get("detective").isPuzzleLocker()) {
                System.out.println("C -> Code locker: find the missing number");
            }
            if (personaggi.get("detective").isPuzzleWhoTellsTruth()){
                System.out.println("W -> Who tells the truth: find the witness" );

            }
            System.out.println("Any other input -> Go back to the menu");
            String answerLockerCode="";
            String answerWhoTellsTruth="";
            String choice = "";
            System.out.println();
            System.out.println("*** Input your choice, a number from 1 to 5 ***");
            choice = scanner.next();
            if (choice.equals("C")&&personaggi.get("detective").isPuzzleLocker()) {
                isSolved=puzzles.lockerCode();
                if (isSolved==true){
                    String codePuzzleLocker= puzzles.getAnswerPuzzleLocker();
                    answersReceived.add("Puzzle 'Locker code' solved:\n"+codePuzzleLocker);
                    personaggi.get("detective").setCanOpenGarage(true);
                }
            }else if (choice.equals("C")&&personaggi.get("detective").isPuzzleWhoTellsTruth()){
                isSolved=puzzles.whoTellsTruth();
                System.out.println();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                System.out.println(personaggi.get("detective").getName()+": So tell me what you know.");
                System.out.println("BEN: "+conversations.getWitnessBoss());
                System.out.println(personaggi.get("detective").getName()+": Ok, thank you.");
                answersReceived.add("BEN: "+conversations.getWitnessBoss());
                personaggi.get("detective").setWitnessBoss(true);

            } else {
                menu();
            }
        } else {
            System.out.println("You don't have any puzzles to solve for now.");
            menu();
        }

    }

    @Override
    public void goGallery() {

    }


    @Override
    public void goCollector() {

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
            System.out.println("*** Here is the evidence that you found: ***");
            for (String element : evidence) {
                System.out.println((index++) + " " + element);
            }
        } else {
            System.out.println("*** You haven't found any evidence yet. ***");
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
            System.out.println("*** These are the answers, that you wrote down: ***");
            for (String answer : answersReceived) {
                System.out.println((index++) + " " + answer);
            }
        } else {
            System.out.println("*** You haven't speak to anyone yet: there are no notes. ***");
            System.out.println("*** Visit some people and ask some questions. ***");
        }
        menu();
    }

    public void lockerInput() {
        System.out.println();
        System.out.println("To unlock the door, enter six 2-digit numbers from 11 to 99, separated with ',' : ex. 22,35,59,17,44,93 ");
        System.out.println("Make an input: ");
        System.out.println("xx,xx,xx,xx,xx,xx -> Insert the code");
        System.out.println("L -> Try later");
        String inputCode = scanner.next().toUpperCase();
        if (inputCode.equals("61,52,63,94,46,18")) {
            System.out.println("Congratulations! The door is unlocked! You can enter now.");
            personaggi.get("detective").setGarageUnlocked(true);
        } else if (inputCode.equals("61,52,63,94,46,18")) {
            menu();
        } else {
            System.out.println("Sorry, the code is not correct. Try again.");
        }

    }

    @Override
    public void distractPolice() {

    }
}
