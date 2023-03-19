import lombok.Data;

import java.time.LocalTime;

public @Data class Person {
    public Person(String name) {
        this.name = name;
    }

    private String name;
    //registrate the time when limit of questions for 1 visit is over
    private LocalTime timeAskedSister;
    private LocalTime timeAskedBoss;
    private LocalTime timeAskedAssistant;
    private LocalTime timeAskedGrandmother;

    //set a flag that limit of questions for 1 visit is over
    private boolean askedSister;//limit for 3 questions
    private boolean askedBoss;//limit for 2 questions
    private boolean askedAssistant;//limit for 2 questions
    private boolean askedGrandmother;//limit for 3 questions
    private boolean nextQuestionGrandmother;//show question if assistant can go unnoticed

    //talkSister&&talkBoss->possible to visit assistant
    private boolean talkedSister;
    private boolean talkedBoss;
    //  private boolean canGoRoomAssistant;
    //unlock options
    private boolean canMeetGrandmother;
    //private boolean canOpenGarage;
    private boolean witnessBoss;
    //private boolean moneyAssistant;
    private boolean messageAssistant;
    private boolean arrestedBoss;// after arrested Boss can visit assistant and find laptopCode
    private boolean atGrandmothers;//to see from where detective solves puzzle
    private boolean atAssistants;

    //after array with questions is empty, a person takes action, that gives a clue
    private boolean actionBoss;//after garage unlocked->phone call
    private boolean actionAssistant;//after finish all questions in array->go to garage
    //private boolean actionGrandmother;
    private boolean actionSister;//after visiting victim's room-> part of garage code

    //unlock puzzles to solve
    private boolean codeFromSister;//after last question in array sisterQuestions
    private boolean codeFromAssistant;//after detective finished questions in array assistantQuestions and follows him to garage
    //private boolean garageUnlocked;//after that bossAction
    private boolean puzzleLocker;//after detective has 2 pieces of instruction->CodeFromSister+codeFromAssistant
    private boolean puzzleWhoTellsTruth;//after boss talks with collector and detective visits sister
    private boolean puzzleSafeBox;//detective asks grandmother to see assistant's room
    private boolean puzzleLaptopPassword;//after detective gets arrested
    private boolean puzzleDecodeMessage;//after solving laptop password
    private boolean puzzleClock;
    private boolean solvedSafeBox;
    private boolean solvedGarageLocker;
    private boolean solvedLaptopPassword;
    private boolean solvedWhoTellsTruth;
    private boolean solvedMessage;//after readMessage -> grandmother remembers about the clock
    private boolean solvedClock;//can solve puzzleBusNumber
    private boolean solvedBusNumber;
    private boolean foundMoney;
   private boolean openedGarage;



    //constructors
    public Person() {

    }


    @Override
    public String toString() {
        return "Person: " +
                "name - " + name;
    }
}
