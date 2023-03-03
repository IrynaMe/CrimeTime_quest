import java.time.LocalTime;

public class Person {
    private String name;
    private boolean askedSister;
    private LocalTime timeAskedSister;



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

    public boolean isAskedSister() {
        return askedSister;
    }

    public LocalTime getTimeAskedSister() {
        return timeAskedSister;
    }

    public void setTimeAskedSister(LocalTime timeAskedSister) {
        this.timeAskedSister = timeAskedSister;
    }

    public void setAskedSister(boolean askedSister) {
        this.askedSister = askedSister;
    }

    @Override
    public String toString() {
        return "Person" +
                "name='" + name;
    }
}
