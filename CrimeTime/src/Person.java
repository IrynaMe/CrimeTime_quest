import lombok.Data;

import java.time.LocalTime;

public @Data class Person {
    public Person(String name) {
        this.name = name;
    }

    private String name;
    //register the time when limit of questions for 1 visit is over
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

    //unlock options
    private boolean canMeetGrandmother;

    private boolean witnessBoss; //after solved puzzle Who tells truth

    private boolean messageAssistant;
    private boolean arrestedBoss;// after arrested Boss can visit assistant and find laptopCode
    private boolean atGrandmothers;//to see from where detective solves puzzle-> to go back to menu or continue in method
    private boolean atAssistants;//to see from where detective solves puzzle-> to go back to menu or continue in method

    //after array with questions is empty, a person takes action, that gives a clue
    private boolean actionBoss;//after garage unlocked->phone call
    private boolean actionAssistant;//after finish all questions in array->go to garage
    //private boolean actionGrandmother;
    private boolean actionSister;//after visiting victim's room-> part of garage code

    //unlock puzzles to solve
    private boolean codeFromSister;//after last question in array sisterQuestions
    private boolean codeFromAssistant;//after detective finished questions in array assistantQuestions and follows him to garage

    private boolean puzzleLocker;//after detective has 2 pieces of instruction->CodeFromSister+codeFromAssistant
    private boolean puzzleWhoTellsTruth;//after boss talks with collector and detective visits sister
    private boolean puzzleSafeBox;//detective asks grandmother to see assistant's room
    private boolean puzzleLaptopPassword;//after detective gets arrested
    private boolean puzzleDecodeMessage;//after solving laptop password
    private boolean puzzleClock;//after decoding message
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
