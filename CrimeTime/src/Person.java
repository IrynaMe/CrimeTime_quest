import lombok.Data;

import java.time.LocalTime;

public @Data class Person {
    public Person(String name) {
        this.name = name;
    }

    private String name;
    //register the time when limit of questions for 1 visit is over
    private LocalTime timeAsked;

    //set a flag that limit of questions for 1 visit is over
    private boolean limitQuestions;//sister and grandmother limit 3 questions, boss and assistant 2 questions
    private boolean nextQuestionGrandmother;//show question if assistant can go unnoticed

    private boolean hasVisitor;//to see from where detective solves puzzle-> to go back to menu or continue in method

    //unlock options
    //if talked to both sister and boss-> to unlock options-> can talk to assistant
    //if asked assistant the question about alibi ->can talk to grandmother
    private boolean flagTalked;
    private boolean witnessBoss; //after solved puzzle Who tells truth

    // private boolean messageAssistant;
    private boolean arrestedBoss;// after arrested Boss can visit assistant and find laptopCode

    //after array with questions is empty, a person takes action, that gives a clue
    //boss->after finish all questions in array->makes a phone call
    //assistant ->after finish all questions in array ->goes to garage
    //sister-> after visiting garage unlocked-> tells about witness of boss at the crime scene
    private boolean takeAction;

    //unlock puzzles ready to solve
    private boolean partCode;//sister->after visiting victim's room, assistant->after finishing questions in array

    private boolean puzzleLocker;//after detective has 2 pieces of instruction->partCode sister and partCode assistant
    private boolean puzzleWhoTellsTruth;//after boss talks with collector and detective visits sister
    private boolean puzzleSafeBox;//detective asks grandmother to see assistant's room
    private boolean puzzleLaptopPassword;//after detective gets arrested
    private boolean puzzleDecodeMessage;//after solving laptop password
    private boolean puzzleClock;//after decoding message

    //mark solved puzzles
    private boolean solvedSafeBox;//can open box and find money
    private boolean solvedGarageLocker;//can open garage
    private boolean solvedLaptopPassword;//can see message
    private boolean solvedMessage;//after readMessage -> grandmother remembers about the clock
    private boolean solvedClock;//can solve puzzleBusNumber
    private boolean solvedBusNumber;//go arrest assistant
    private boolean foundMoney;// a flag to not open safe box again
    private boolean openedGarage;//after that can go to boss if all array questions are done


    //constructors
    public Person() {

    }


    @Override
    public String toString() {
        return "Person: " +
                "name - " + name;
    }
}
