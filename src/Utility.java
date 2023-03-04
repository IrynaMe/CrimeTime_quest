import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class Utility {
    Scanner scanner = new Scanner(System.in);
    private Properties properties = new Properties();
    private HashMap<String, Person> personaggi;

   /* public HashMap<String, Person> getPersonaggi() {
        personaggi=personsSetup();
        return personaggi;
    }*/


    public void hello() {
        System.out.println("Input your name");
        Scanner scanner = new Scanner(System.in);
        String nameDetective = scanner.nextLine();
        FileReader reader;
        FileWriter writer;

        try {
            // Load the properties file
            reader = new FileReader(("./config.properties"));
            properties = new Properties();
            properties.load(reader);
            reader.close();

            // Modify the value of the "detective" property
            properties.setProperty("detective", nameDetective);

            // Write the modified properties back to the file
            writer = new FileWriter("./config.properties");
            properties.store(writer, null);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't read/write in the file: " + e.getMessage());
        }

    }


    public HashMap<String, Person> personsSetup() {
        FileReader reader;
        try {
            reader = new FileReader(("./config.properties"));
            properties = new Properties();
            properties.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't read the file: " + e.getMessage());
        }
        Person detective = new Person(properties.getProperty("detective").toUpperCase());
        Person sister = new Person(properties.getProperty("sister").toUpperCase());
        Person boss = new Person(properties.getProperty("boss").toUpperCase());
        Person assistant = new Person(properties.getProperty("assistant").toUpperCase());
        Person grandmother = new Person(properties.getProperty("grandmother").toUpperCase());
        Person collector = new Person(properties.getProperty("collector").toUpperCase());
        Person victim = new Person(properties.getProperty("victim").toUpperCase());
        Person policeOfficer = new Person(properties.getProperty("police").toUpperCase());
        personaggi = new HashMap<>();
        personaggi.put("detective", detective);
        personaggi.put("victim", victim);
        personaggi.put("sister", sister);
        personaggi.put("boss", boss);
        personaggi.put("assistant", assistant);
        personaggi.put("grandmother", grandmother);
        personaggi.put("collector", collector);
        personaggi.put("policeOfficer", policeOfficer);

        return personaggi;
    }


}//
