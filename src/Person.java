import java.time.LocalTime;

public class Person {
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
    private boolean askedGrandma;

    //after array with questions is empty, a person takes action, that gives a clue
    private boolean actionBoss;
    private boolean actionAssistant;
    private boolean actionGrandmother;

    //2 pieces of code, both should be collected
    private boolean codeFromSister;
    private boolean codeFromAssistant;
    private boolean garageUnlocked;//after that grandmother remembers about the clock
    private boolean puzzle;

    //constructors
    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }


    //Getter and setter
    public String getName() {
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

    public boolean isAskedGrandma() {
        return askedGrandma;
    }

    public void setAskedGrandma(boolean askedGrandma) {
        this.askedGrandma = askedGrandma;
    }

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

    public boolean isPuzzle() {
        return puzzle;
    }

    public void setPuzzle(boolean puzzle) {
        this.puzzle = puzzle;
    }

    public void setGarageUnlocked(boolean garageUnlocked) {
        this.garageUnlocked = garageUnlocked;
    }

    @Override
    public String toString() {
        return "Person: " +
                "name - " + name;
    }
}
