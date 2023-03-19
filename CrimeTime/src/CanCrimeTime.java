import java.time.LocalTime;

public interface CanCrimeTime {
    public void startGame();

    void talkPerson();

    void solvePuzzles();

    void goGallery();

    void goAssistant(LocalTime time);

    void goCollector();

    void goBoss(LocalTime time);

    void goVictim();

    void goSister(LocalTime time);


    void seeEvidence();

    void seeNotes();

    void distractPolice();


}
