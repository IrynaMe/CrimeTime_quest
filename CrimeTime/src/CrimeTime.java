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
        grandmotherQuestions = conversations.grandmotherQuestions(personaggi);
        grandmotherAnswers = conversations.grandmotherAnswers(personaggi);
        personaggi.get("detective").setTimeAskedSister(LocalTime.now().minusMinutes(10));

        personaggi.get("detective").setTimeAskedBoss(LocalTime.now().minusMinutes(10));
        personaggi.get("detective").setTimeAskedAssistant(LocalTime.now().minusMinutes(10));
        personaggi.get("detective").setTimeAskedGrandmother(LocalTime.now().minusMinutes(10));
        System.out.println();
        menu();

    }

    public void menu() {
        LocalTime currentTime = LocalTime.now();
        boolean flag0 = false;
        //flag1-flag6-control for valid scanner choice
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        System.out.println();
        //      if (answersReceived.size() > 0) {   }
        System.out.println("--------------------------------------------");
        System.out.println("*** What would you like to do? ***");
        System.out.println("S -> Visit victim's sister " + personaggi.get("sister").getName());
        if (personaggi.get("detective").isArrestedBoss() == false) {
            System.out.println("B -> Visit victim's boss " + personaggi.get("boss").getName());
        }

        if (personaggi.get("detective").isTalkedSister() && personaggi.get("detective").isTalkedBoss()) {
            System.out.println("A -> Visit victim's assistant " + personaggi.get("assistant").getName());
            flag1 = true;
        }
        if (personaggi.get("detective").isCanMeetGrandmother()) {
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
        if (personaggi.get("detective").isPuzzleLocker() || personaggi.get("detective").isPuzzleWhoTellsTruth()
                || personaggi.get("detective").isPuzzleSafeBox()||personaggi.get("detective").isPuzzleLaptopPassword()
                ||personaggi.get("detective").isPuzzleDecodeMessage()||personaggi.get("detective").isPuzzleClock()) {
            System.out.println("P -> Solve a puzzle");
            flag5 = true;
        }
        //if puzzle Locker code is solved
        if (personaggi.get("detective").isSolvedGarageLocker()&&personaggi.get("detective").isOpenedGarage()==false) {
            System.out.println("O -> Open the garage door");
            flag6 = true;
        }
        //if puzzle Laptop is solved
        if (personaggi.get("detective").isSolvedLaptopPassword()) {
            System.out.println("L -> Go through assistant's laptop");
            flag7 = true;
        }
        if (personaggi.get("detective").isPuzzleWhoTellsTruth()) {
            System.out.println("W -> Talk to witnesses");
            flag8 = true;
        }


        String choice = "";

        do {
            System.out.println();
            System.out.println("*** Input your choice: ***");
            choice = scanner.next().toUpperCase();
            if (choice.equals("S")) {
                goSister(currentTime);
                flag0 = true;
            } else if (choice.equals("B") && personaggi.get("detective").isArrestedBoss() == false) {
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
                solvePuzzles();
                flag0 = true;
            } else if ((choice.equals("O") && flag6 == true)) {
                openGarage();
                flag0 = true;
            } else if ((choice.equals("L") && flag7 == true)){
                readMessage();
                flag0=true;
            }else if ((choice.equals("W") && flag8 == true)){
                solvePuzzleWhoTellsTruth();
                flag0=true;
            }
            else {
                System.out.println("*** Input error: try again  ***");
            }


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
                    if (sisterQuestions.length>1){
                        System.out.println("1 - " + (sisterQuestions.length) + " -> Ask the question");
                    }else{
                        System.out.println("1  -> Ask the question");
                    }

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
                    if (choice == sisterQuestions.length - 1) {
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
                    if(choice != sisterQuestions.length - 1) {
                        answersReceived.add(givenAnswer);
                    }
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
            } else if (personaggi.get("detective").isActionSister() == false) {
                //if there are no more questions in the array
                System.out.println();
                System.out.println("*** " + personaggi.get("sister").getName() + ": has no more valuable information to share for now. ***");
                menu();
            }
            //when after detective heard boss speaks with collector->boolean sisterAction=true->sister shares new info
            if (personaggi.get("detective").isActionSister()) {
                System.out.println();
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                String newWitness = personaggi.get("sister").getName() + ": " + conversations.getSisterAboutWitness();
                System.out.println(newWitness);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                answersReceived.add(newWitness);
                personaggi.get("detective").setActionSister(false);
                personaggi.get("detective").setPuzzleWhoTellsTruth(true);
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
                boolean isDigit = false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    if (bossQuestions.length>1){
                        System.out.println("1 - " + (bossQuestions.length) + " -> Ask the question");
                    }else{
                        System.out.println("1  -> Ask the question");
                    }
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
                  /*  //set boolean for boss to take action after all the questions from the array are asked
                    if (personaggi.get("detective").isActionBoss()) {

                    }*/

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

            } else if (personaggi.get("detective").isWitnessBoss() == false) {
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
        if (personaggi.get("detective").isWitnessBoss()) {
            System.out.println();
            conversations.imInnocentBoss(personaggi);
            System.out.println();
            personaggi.get("detective").setArrestedBoss(true);
        }

        menu();
    }

    @Override
    public void goAssistant(LocalTime timeStart) {
        personaggi.get("detective").setAtAssistants(true);


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
                boolean isDigit = false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    if (assistantQuestions.length>1){
                        System.out.println("1 - " + (assistantQuestions.length) + " -> Ask the question");
                    }else{
                        System.out.println("1  -> Ask the question");
                    }
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
                    if (choice == assistantQuestions.length - 1) {
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
                    personaggi.get("detective").setAtAssistants(false);
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
                        personaggi.get("detective").setPuzzleLocker(true);
                        evidence.add(completeCode);
                    }
                }

            } else if (personaggi.get("detective").isArrestedBoss()&&personaggi.get("detective").isSolvedLaptopPassword()==false) {
                System.out.println(conversations.getFindLaptop());
                solveLaptopPassword();
            } else if (personaggi.get("detective").isSolvedLaptopPassword()&&personaggi.get("detective").isSolvedMessage()==false){
                readMessage();
            } else if (personaggi.get("detective").isSolvedClock()&&personaggi.get("detective").isSolvedBusNumber()==false){
                System.out.println(conversations.getAssistantBus());
                puzzles.busNumber();
            }
            else if(personaggi.get("detective").isOpenedGarage()==false) {
                //if there are no more questions in the array
                System.out.println();
                // System.out.println("*** " + personaggi.get("assistant").getName() + ": has no more valuable information to share. ***");
                //detective should unlock the garage
                System.out.println("*** You should unlock the garage and understand what assistant wanted to take from there ***");
                personaggi.get("detective").setAtAssistants(false);
                menu();
            }else{
                System.out.println();
                System.out.println("*** You should find more information to incriminate assistant ***");
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
            personaggi.get("detective").setAtAssistants(false);
            menu();
        }
        personaggi.get("detective").setAtAssistants(false);
        menu();
    }


    public void goGrandmother(LocalTime timeStart) {
        personaggi.get("detective").setAtGrandmothers(true);
        boolean solvedSafeBox = false;
        //limit for 3 questions at a time
        //ask the last question only 2 or more min after the last question

        Duration pauseTalk = Duration.ZERO;
        pauseTalk = Duration.between(personaggi.get("detective").getTimeAskedGrandmother(), timeStart);
        long minutesPause = pauseTalk.toMinutes();
        if (minutesPause < BREAKTIME && grandmotherQuestions.length > 0) {
            System.out.println();
            System.out.println("*** It seems that " + personaggi.get("grandmother").getName() + " is still busy, come back later. ***");
            personaggi.get("detective").setAtGrandmothers(false);
            menu();
        }
        String choiceStr = "";
        int choice = -1;
        System.out.println();
        //reset questions counter if 2 or more minutes passed after reaching the limit for questions
        if (personaggi.get("detective").isAskedGrandmother()) {
            countQuestionsGrandmother = 1;
            personaggi.get("detective").setAskedGrandmother(false);
        }
        while (countQuestionsGrandmother <= 3 && personaggi.get("detective").isAskedGrandmother() == false) {
            if (grandmotherQuestions.length > 1||(grandmotherQuestions.length > 0 && personaggi.get("detective").isSolvedMessage())) {
                int index = 1;
                int askUpstairsIndex = -1;
                System.out.println();

                //if the method is recalled 2 or more min after the last question
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.println("*** You came to visit " + personaggi.get("grandmother").getName() + " ***");

                System.out.println("*** What would you like to ask? ***");
                //print the list of questions except ones that need to be asked later
                int length = 0;
                if (personaggi.get("detective").isAskedGrandmother() == false) {
                    length = grandmotherQuestions.length - 3;
                }
                if (personaggi.get("detective").isNextQuestionGrandmother()) {
                    length = grandmotherQuestions.length - 2;
                }
                if (personaggi.get("detective").isSolvedMessage()) {
                    length = grandmotherQuestions.length - 1;
                }
                for (int i = 0; i <= length; i++) {
                    System.out.println((index++) + " -> " + grandmotherQuestions[i]);

                }
                if ((personaggi.get("detective").isPuzzleSafeBox()||personaggi.get("detective").isSolvedSafeBox())
                &&personaggi.get("detective").isFoundMoney()==false) {
                    askUpstairsIndex = index++;
                    System.out.println((askUpstairsIndex) + " -> " + conversations.getAskGoUpstairs());
                    length += 1;
                }

                //control if choice is valid
                boolean flag;
                boolean isDigit = false;
                do {
                    System.out.println();
                    System.out.println("*** Input your choice  ***");
                    if (length>0){
                        System.out.println("1 - " + (length + 1) + " -> Ask the question");
                    }else{
                        System.out.println("1  -> Ask the question");
                    }

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
                    flag = (isDigit && (choice <= length || choice == -1));
                    if (!flag) {
                        System.out.println("Input error, try again");
                    }
                } while (!flag);
                //detective asks a question
                String givenAnswer = "";
                if (choice != -1 && choice != askUpstairsIndex - 1) {

                    //print the chosen question and the answer
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(personaggi.get("detective").getName() + ": " + grandmotherQuestions[choice]);
                    givenAnswer = grandmotherAnswers[choice];
                    System.out.println(givenAnswer);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    //put the answer to the ArrayList-> to use in seeNotes();
                    answersReceived.add(givenAnswer);
                    //count questions
                    countQuestionsGrandmother++;
                    //if detective goes to the assistant's room
                    if (grandmotherQuestions[choice].trim().toLowerCase().contains("room")&& personaggi.get("detective").isSolvedSafeBox() == false){//&&personaggi.get("detective").isAskedNextQuestion()==false
                        personaggi.get("detective").setPuzzleSafeBox(true);
                        conversations.findSafeBox(personaggi);

                        solvedSafeBox = puzzles.safeBoxCode();
                        if (solvedSafeBox == true) {
                            personaggi.get("detective").setSolvedSafeBox(true);
                            openSafeBox();
                        }
                    }
                    if (grandmotherQuestions[choice].trim().toLowerCase().contains("room")&& personaggi.get("detective").isSolvedSafeBox()){//&&personaggi.get("detective").isAskedNextQuestion()==false
                            openSafeBox();
                    }

/*
                    if ((choice == grandmotherQuestions.length - 3 && personaggi.get("detective").isSolvedSafeBox() == false
                            &&personaggi.get("detective").isAskedNextQuestion()==false)
                            ||(choice == grandmotherQuestions.length - 2 && personaggi.get("detective").isSolvedSafeBox() == false
                            &&personaggi.get("detective").isAskedNextQuestion())) {
                        personaggi.get("detective").setPuzzleSafeBox(true);
                        conversations.findSafeBox(personaggi);

                        solvedSafeBox = puzzles.safeBoxCode();
                        if (solvedSafeBox == true) {
                            personaggi.get("detective").setSolvedSafeBox(true);
                            openSafeBox();
                        }
                    }*/
/*                    if ((choice == grandmotherQuestions.length - 3 && personaggi.get("detective").isSolvedSafeBox()
                            && personaggi.get("detective").isFoundMoney() == false&&personaggi.get("detective").isAskedNextQuestion()==false)
                    ||(choice == grandmotherQuestions.length - 2 && personaggi.get("detective").isSolvedSafeBox()
                            && personaggi.get("detective").isFoundMoney() == false&&personaggi.get("detective").isAskedNextQuestion())) {
                        openSafeBox();
                    }*/
                    if (choice == 0) {
                        personaggi.get("detective").setNextQuestionGrandmother(true);
                    }
                  /*  if (grandmotherQuestions[choice].trim().toLowerCase().contains("unnoticed")){
                        personaggi.get("detective").setAskedNextQuestion(true);
                    }*/



                    //show another question if asked about alibi

                    //delete the used question and answer from the arrays;

                    for (int i = choice; i < grandmotherQuestions.length - 1; i++) {

                            grandmotherQuestions[i] = grandmotherQuestions[i + 1];

                    }
                    grandmotherQuestions = Arrays.copyOf(grandmotherQuestions, grandmotherQuestions.length - 1);
                    for (int i = choice; i < grandmotherAnswers.length - 1; i++) {

                        grandmotherAnswers[i] = grandmotherAnswers[i + 1];

                    }
                    grandmotherAnswers = Arrays.copyOf(grandmotherAnswers, grandmotherAnswers.length - 1);

                }
                else if (choice == askUpstairsIndex - 1 ) {
                    System.out.println();
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(personaggi.get("detective").getName() + ": " + conversations.getAskGoUpstairs());
                    givenAnswer = conversations.getAnswerGoUpstairs();
                    System.out.println(givenAnswer);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    if (personaggi.get("detective").isPuzzleSafeBox() && personaggi.get("detective").isSolvedSafeBox()==false){
                        solvePuzzleSafeBox();
                    }else if (personaggi.get("detective").isSolvedSafeBox()) {
                        openSafeBox();
                    }
                }
                else if (choice==grandmotherQuestions.length-1){
                    solvePuzzleClock();
                } else if (choice == -1) {
                    personaggi.get("detective").setAtGrandmothers(false);
                    menu();
                } else {
                    System.out.println("*** Wrong input, try again ***");
                }
            }
            if (grandmotherQuestions.length==1 &&personaggi.get("detective").isSolvedMessage()==false){
                System.out.println();
                System.out.println("*** " +personaggi.get("grandmother").getName()+" has nothing to tell for now. You should get more information to ask right questions ***");
                personaggi.get("detective").setAtGrandmothers(false);
                menu();
            }
        }
        //when detective reached the limit for questions
        if (countQuestionsGrandmother > 3) {
            personaggi.get("detective").setAskedGrandmother(true);
            //register the time when limit for questions was reached
            personaggi.get("detective").setTimeAskedGrandmother(LocalTime.now());
            System.out.println();
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(personaggi.get("grandmother").getName() + ": Sorry darling, I'm so tired, I need to sleep. Come back later, please");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //      personaggi.get("detective").setCanGoRoomAssistant(true);
            personaggi.get("detective").setAtGrandmothers(false);
            menu();
        }

        personaggi.get("detective").setAtGrandmothers(false);
        menu();
    }

    @Override
    public void talkPerson() {

    }

    public void openSafeBox() {
        System.out.println("*** You set the code and opened the box. ***");
        System.out.println(conversations.getInsideSafeBox());
        System.out.println("*** You made a photo to keep it as evidence. ***");
        evidence.add(conversations.getInsideSafeBox());
        personaggi.get("detective").setFoundMoney(true);
        if (personaggi.get("detective").isAtGrandmothers()) {
            goGrandmother(LocalTime.now());
        } else {
            menu();
        }
    }

    public void readMessage() {
        boolean isSolved = puzzles.decodeMessage(personaggi);
        if (isSolved == true) {
            String message = puzzles.getMessageToEncode();
            answersReceived.add("Puzzle 'Decode the message' is solved:\t" + message);
            personaggi.get("detective").setSolvedMessage(true);
            personaggi.get("detective").setPuzzleClock(true);
            personaggi.get("detective").setPuzzleDecodeMessage(false); //to not suggest to solve puzzle again
            System.out.println("*** So, you read it. But " +personaggi.get("assistant").getName()+ " has an alibi..." +
                    "\n Perhaps you should talk to his grandmother again...***");
        } else {
            menu();
        }
    }

public void solvePuzzleClock(){
    boolean isSolved = puzzles.clock(personaggi);
    if (isSolved == true) {
        String time = puzzles.getAnswerPuzzleClock();
        answersReceived.add("Puzzle 'Clock' is solved, the right time when "+personaggi.get("assistant").getName()+ " got home was: " + time);
        personaggi.get("detective").setSolvedClock(true);
        personaggi.get("detective").setPuzzleClock(false); //to not suggest to solve puzzle again
        System.out.println("*** Now you have enough evidence to have " + personaggi.get("assistant").getName() + " arrested ***");
        conversations.getTalkPolice();
    } else {
        menu();
    }
}
    private void solveLaptopPassword() {
        boolean isSolved = puzzles.laptopPassword();
        if (isSolved == true) {
            String laptopPassword = puzzles.getAnswerLaptopPassword();
            answersReceived.add("Puzzle 'Laptop password' is solved:\t" + laptopPassword);
            personaggi.get("detective").setSolvedLaptopPassword(true);
            personaggi.get("detective").setPuzzleLaptopPassword(false); //to not suggest to solve puzzle again
            System.out.println("*** Now you can find out more about " + personaggi.get("assistant").getName() + " ***");
            if (personaggi.get("detective").isAtAssistants()) {
                readMessage();
            }else{
                System.out.println("You can go to the assistant's office to enter the password ans check his laptop");
            }
        } else {
            menu();
        }
    }

    public void solvePuzzleLockerCode() {
        boolean isSolved = puzzles.lockerCode();
        if (isSolved == true) {
            String codePuzzleLocker = puzzles.getAnswerPuzzleLocker();
            answersReceived.add("Puzzle 'Locker code' is solved:\n" + codePuzzleLocker);
            personaggi.get("detective").setSolvedGarageLocker(true);
            personaggi.get("detective").setPuzzleLocker(false); //to not suggest to solve puzzle again
            System.out.println("*** Now you can open the garage. ***");
        }
        menu();
    }

    public void solvePuzzleWhoTellsTruth() {
        boolean isSolved = puzzles.whoTellsTruth();
        System.out.println();
        if (isSolved == true) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(personaggi.get("detective").getName() + ": So tell me what you know.");
            System.out.println("BEN: " + conversations.getWitnessBoss());
            System.out.println(personaggi.get("detective").getName() + ": Ok, thank you.");
            answersReceived.add("BEN: " + conversations.getWitnessBoss());
            personaggi.get("detective").setWitnessBoss(true);
        }

    }

    public void solvePuzzleSafeBox() {
        boolean isSolved = puzzles.safeBoxCode();
        System.out.println();
        if (isSolved == true) {
            personaggi.get("detective").setPuzzleSafeBox(false);//to not suggest to solve puzzle again
            personaggi.get("detective").setSolvedSafeBox(true);
            String safeBoxLock = puzzles.getAnswerSafeBoxCode();
            answersReceived.add("*** Puzzle 'Safe box combination lock' is solved: ***\n   *** code: " + safeBoxLock + " ***");

            if (personaggi.get("detective").isAtGrandmothers()) {
                System.out.println("*** Now you can open the safe box and find out what is inside! ***");
                openSafeBox();
            } else {
                System.out.println("*** Now you can visit " + personaggi.get("grandmother").getName() + " to go upstairs and find out what is inside the safe box! ***");
            }
        } else {
            personaggi.get("detective").setSolvedSafeBox(false);
            System.out.println("*** Come back to open the box when you solve the puzzle. In the meantime you can continue your investigation. *** ");
        }
        menu();
    }
    public void solvePuzzleBusNumber(){
        boolean isSolved = puzzles.safeBoxCode();
        System.out.println();
        if (isSolved == true) {
            personaggi.get("detective").setSolvedBusNumber(true);
            String busNumber = puzzles.getAnswerBusNumber();
            answersReceived.add("*** Puzzle 'Bus number' is solved: ***\n   *** code: " + busNumber + " ***");
            System.out.println("*** Now you should inform the police and get "+personaggi.get("assistant").getName()+ " arrested! ***");
            System.out.println("*** Let's hope that he is still in that bus! ***");
            try {
                Thread.sleep(1000);
                conversations.arrestAssistant(personaggi);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            personaggi.get("detective").setSolvedSafeBox(false);
            System.out.println("*** This is the last riddle to solve! Hurry up or "+personaggi.get("assistant").getName()+" will run away! *** ");
        }
        menu();
    }

    @Override
    public void solvePuzzles() {
        if (personaggi.get("detective").isPuzzleLocker() || personaggi.get("detective").isPuzzleWhoTellsTruth() || personaggi.get("detective").isPuzzleSafeBox()) {
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("*** Choose a puzzle ***");
            boolean isSolved = false;

            //puzzle with code locker
            if (personaggi.get("detective").isPuzzleLocker()&&personaggi.get("detective").isSolvedGarageLocker() == false) {
                System.out.println("G -> Garage door locker: find the missing number");
            }
            if (personaggi.get("detective").isPuzzleWhoTellsTruth()&&personaggi.get("detective").isSolvedWhoTellsTruth() == false) {
                System.out.println("W -> Who tells the truth: find the witness");

            }
            if (personaggi.get("detective").isPuzzleSafeBox() && personaggi.get("detective").isSolvedSafeBox() == false) {
                System.out.println("S-> Safe box combination lock");

            }
            if (personaggi.get("detective").isPuzzleLaptopPassword() && personaggi.get("detective").isSolvedLaptopPassword()== false) {
                System.out.println("L-> Laptop password");

            }
            if (personaggi.get("detective").isPuzzleDecodeMessage() && personaggi.get("detective").isSolvedMessage()== false) {
                System.out.println("D-> Decode message");

            }
            if (personaggi.get("detective").isPuzzleClock() && personaggi.get("detective").isSolvedClock()== false) {
                System.out.println("C-> Clock");
            }
            if (personaggi.get("detective").isSolvedClock() && personaggi.get("detective").isSolvedBusNumber()== false) {
                System.out.println("B-> Bus number");
            }
            System.out.println("Any other input -> Go back to the menu");
            String answerLockerCode = "";
            String answerWhoTellsTruth = "";
            String choice = "";
            System.out.println();
            System.out.println("*** Input your choice, a number from 1 to 5 ***");
            choice = scanner.next().toUpperCase();
            if (choice.equals("G") && personaggi.get("detective").isPuzzleLocker()) {
                solvePuzzleLockerCode();
            } else if (choice.equals("W") && personaggi.get("detective").isPuzzleWhoTellsTruth()) {
                solvePuzzleWhoTellsTruth();

            } else if (choice.equals("S") && personaggi.get("detective").isPuzzleSafeBox()) {
                solvePuzzleSafeBox();
            } else if (choice.equals("L") && personaggi.get("detective").isPuzzleLaptopPassword()){
                solveLaptopPassword();
            } else if (choice.equals("D") && personaggi.get("detective").isPuzzleDecodeMessage()){
                readMessage();
            } else if (choice.equals("C") && personaggi.get("detective").isPuzzleClock()){
                solvePuzzleClock();
            } else if (choice.equals("B") && personaggi.get("detective").isSolvedClock()){
                solvePuzzleBusNumber();
            }
            else {
                System.out.println("*** Input error for puzzle choice, try again ***");
            }
        } else {
            System.out.println("*** You don't have any puzzles to solve for now. ***");
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


    public void openGarage() {
        System.out.println();
        System.out.println("To unlock the door, enter six 2-digit numbers from 11 to 99, separated with ',' : ex. 22,35,59,17,44,93 ");
        System.out.println("Make an input: ");
        System.out.println("xx,xx,xx,xx,xx,xx -> Insert the code");
        System.out.println("L -> Try later");
        String inputCode = scanner.next().toUpperCase();
        if (inputCode.equals(puzzles.getCodeGarage())) {
            System.out.println("Congratulations! The door is unlocked! You can enter now.");
            personaggi.get("detective").setActionBoss(true);
            personaggi.get("detective").setOpenedGarage(true);
            String foundPainting = conversations.getEnterGarage()[0];
            System.out.println(foundPainting);
            String painting = conversations.getEnterGarage()[1];
            evidence.add(painting);
            menu();
        } else {
            System.out.println("Sorry, the code is not correct. Try again.");
            menu();
        }

    }

    public void enterGarage() {

    }

    @Override
    public void distractPolice() {

    }
}
