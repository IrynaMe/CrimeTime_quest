import lombok.Data;

import java.time.LocalTime;

public @Data class Person {
    private String name;
    //registrate the time when limit of questions for 1 visit is over
    private LocalTime timeAskedSister;
    private LocalTime timeAskedBoss;
    private LocalTime timeAskedAssistant;
    private LocalTime timeAskedGrandmother;

    //set a flag that limit of questions for 1 visit is over
    private boolean askedSister;
    private boolean askedBoss;
    private boolean askedAssistant;
    private boolean askedGrandmother;
    private boolean nextQuestionGrandmother;

    //talkSister&&talkBoss->possible to visit assistant
    private boolean talkedSister;
    private boolean talkedBoss;
  //  private boolean canGoRoomAssistant;
    //unlock options
    private boolean canMeetGrandmother;
    private boolean canOpenGarage;
    private boolean witnessBoss;
    private boolean moneyAssistant;
    private boolean messageAssistant;
    private boolean arrestedBoss;

    //after array with questions is empty, a person takes action, that gives a clue
    private boolean actionBoss;
    private boolean actionAssistant;
    private boolean actionGrandmother;
    private boolean actionSister;

    //unlock puzzles to solve
    private boolean codeFromSister;//after last question in array sisterQuestions
    private boolean codeFromAssistant;//after detective finished questions in array assistantQuestions and follows him to garage
    private boolean garageUnlocked;//after that grandmother remembers about the clock
    private boolean puzzleLocker;//after detective has 2 pieces of instruction->CodeFromSister+codeFromAssistant
    private boolean puzzleWhoTellsTruth;//after boss talks with collector and detective visits sister
private boolean puzzleSafeBox;
    //constructors
    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }


    //Getter and setter
    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAskedBoss() {
        return askedBoss;
    }


    public void setAskedBoss(boolean askedBoss) {
        this.askedBoss = askedBoss;
    }

    public boolean isAskedAssistant() {
        return askedAssistant;
    }

    public void setAskedAssistant(boolean askedAssistant) {
        this.askedAssistant = askedAssistant;
    }

    public boolean isAskedGrandmother() {
        return askedGrandmother;
    }

    public void setAskedGrandmother(boolean askedGrandmother) {
        this.askedGrandmother = askedGrandmother;
    }

    *//*public boolean isCanGoRoomAssistant() {
        return canGoRoomAssistant;
    }

    public void setCanGoRoomAssistant(boolean canGoRoomAssistant) {
        this.canGoRoomAssistant = canGoRoomAssistant;
    }
*//*
    public boolean isAskedSister() {
        return askedSister;
    }


    public void setAskedSister(boolean askedSister) {
        this.askedSister = askedSister;
    }

    public LocalTime getTimeAskedSister() {
        return timeAskedSister;
    }

    public void setTimeAskedSister(LocalTime timeAskedSister) {
        this.timeAskedSister = timeAskedSister;
    }

    public LocalTime getTimeAskedBoss() {
        return timeAskedBoss;
    }

    public void setTimeAskedBoss(LocalTime timeAskedBoss) {
        this.timeAskedBoss = timeAskedBoss;
    }

    public LocalTime getTimeAskedAssistant() {
        return timeAskedAssistant;
    }

    public void setTimeAskedAssistant(LocalTime timeAskedAssistant) {
        this.timeAskedAssistant = timeAskedAssistant;
    }

    public LocalTime getTimeAskedGrandmother() {
        return timeAskedGrandmother;
    }

    public void setTimeAskedGrandmother(LocalTime timeAskedGrandmother) {
        this.timeAskedGrandmother = timeAskedGrandmother;
    }

    public boolean isActionBoss() {
        return actionBoss;
    }

    public void setActionBoss(boolean actionBoss) {
        this.actionBoss = actionBoss;
    }

    public boolean isActionAssistant() {
        return actionAssistant;
    }

    public void setActionAssistant(boolean actionAssistant) {
        this.actionAssistant = actionAssistant;
    }

    public boolean isActionGrandmother() {
        return actionGrandmother;
    }

    public void setActionGrandmother(boolean actionGrandmother) {
        this.actionGrandmother = actionGrandmother;
    }

    public boolean isCodeFromSister() {
        return codeFromSister;
    }

    public void setCodeFromSister(boolean codeFromSister) {
        this.codeFromSister = codeFromSister;
    }

    public boolean isCodeFromAssistant() {
        return codeFromAssistant;
    }

    public void setCodeFromAssistant(boolean codeFromAssistant) {
        this.codeFromAssistant = codeFromAssistant;
    }

    public boolean isGarageUnlocked() {
        return garageUnlocked;
    }

    public boolean isPuzzleLocker() {
        return puzzleLocker;
    }

    public void setPuzzleLocker(boolean puzzleLocker) {
        this.puzzleLocker = puzzleLocker;
    }

    public void setGarageUnlocked(boolean garageUnlocked) {
        this.garageUnlocked = garageUnlocked;
    }

    public boolean isActionSister() {
        return actionSister;
    }

    public void setActionSister(boolean actionSister) {
        this.actionSister = actionSister;
    }

    public boolean isCanMeetGrandmother() {
        return canMeetGrandmother;
    }

    public void setCanMeetGrandmother(boolean canMeetGrandmother) {
        this.canMeetGrandmother = canMeetGrandmother;
    }

    public boolean isTalkedSister() {
        return talkedSister;
    }

    public void setTalkedSister(boolean talkedSister) {
        this.talkedSister = talkedSister;
    }

    public boolean isTalkedBoss() {
        return talkedBoss;
    }

    public void setTalkedBoss(boolean talkedBoss) {
        this.talkedBoss = talkedBoss;
    }

    public boolean isPuzzleWhoTellsTruth() {
        return puzzleWhoTellsTruth;
    }

    public void setPuzzleWhoTellsTruth(boolean puzzleWhoTellsTruth) {
        this.puzzleWhoTellsTruth = puzzleWhoTellsTruth;
    }

    public boolean isCanOpenGarage() {
        return canOpenGarage;
    }

    public void setCanOpenGarage(boolean canOpenGarage) {
        this.canOpenGarage = canOpenGarage;
    }

    public boolean isWitnessBoss() {
        return witnessBoss;
    }

    public void setWitnessBoss(boolean witnessBoss) {
        this.witnessBoss = witnessBoss;
    }

    public boolean isMoneyAssistant() {
        return moneyAssistant;
    }

    public void setMoneyAssistant(boolean moneyAssistant) {
        this.moneyAssistant = moneyAssistant;
    }

    public boolean isMessageAssistant() {
        return messageAssistant;
    }

    public void setMessageAssistant(boolean messageAssistant) {
        this.messageAssistant = messageAssistant;
    }

    public boolean isArrestedBoss() {
        return arrestedBoss;
    }

    public void setArrestedBoss(boolean arrestedBoss) {
        this.arrestedBoss = arrestedBoss;
    }

    public boolean isPuzzleSafeBox() {
        return puzzleSafeBox;
    }

    public void setPuzzleSafeBox(boolean puzzleSafeBox) {
        this.puzzleSafeBox = puzzleSafeBox;
    }

    public boolean isNextQuestionGrandmother() {
        return nextQuestionGrandmother;
    }

    public void setNextQuestionGrandmother(boolean nextQuestionGrandmother) {
        this.nextQuestionGrandmother = nextQuestionGrandmother;
    }
*/
    @Override
    public String toString() {
        return "Person: " +
                "name - " + name;
    }
}
