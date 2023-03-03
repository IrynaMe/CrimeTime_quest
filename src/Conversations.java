import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class Conversations {


    private String[] hurryUpSister = {
            "Sorry, I have to ask you to leave, I have urgent work to do. See you later, bye",
            "Sorry, I can't talk any longer, I need to be in a different place now. Bye!",
            "Sorry, let's talk another time, I have my pilates class in 10 minutes. Bye!",
            "Sorry, I am meeting a friend, I need to go. Bye! "
    };
    private String[] hurryUpAssistant = {
            "Sorry, I need to go back to work. See you later.",
            "Unfortunately, I can't talk any longer, I need to be in a different place.",
            "Unfortunately, I can't talk any longer, I have a doctor appointment, I should hurry up.",
            "Sorry, I'm meeting a friend, I should go. ",
            "Unfortunately, I'm going to check up my grandma, I should go. Good bye!",
    };
    private String[] hurryUpBoss = {
            "My apologies, you should leave now, I have a business meeting. ",
            "My apologies, I have no more time to talk, I'm in a hurry.",
            "My apologies, I'm very busy today, you have to leave. ",
            "My apologies, I'm having lunch with business partners, you have to leave. ",
            "My apologies, I have and important call, you should leave.",
    };

    public void introduction(HashMap<String, Person> personaggi) {

        System.out.println("Here is the story: ");
        System.out.println();
        System.out.println(personaggi.get("victim").getName() + " was a young girl who lived together with her sister and worked as an art curator " +
                "\nat a prestigious art gallery, where she was organizing a new exhibition of artworks " +
                "\nof the beginning of the 20th century.");
        System.out.println();
        System.out.println("Last week " + personaggi.get("victim").getName() + " was found dead in the gallery. According to the police, the time of death " + //check: last week
                "\nis between 12:00 a.m. and 1:00 a.m. The murder weapon-a blunt heavy object, which was never found." +
                "\nA valuable piece of art was stolen from the gallery, " +
                "\nand the police concluded, that the girl had been a victim of a robbery. ");
        System.out.println();
        System.out.println("However, the victim's sister " + personaggi.get("sister").getName() + " doesn't believe that " + personaggi.get("victim").getName() + "'s death was a tragic coincidence, " +
                "\nso she  hired a private detective to investigate the case: it's you, " + personaggi.get("detective").getName() + "!");
        System.out.println();
        System.out.println("Your task is to interrogate suspects, collect evidence and bring the killer to justice." +
                "\nGood luck!");

    }


    public String[] sisterQuestions(HashMap<String, Person> personaggi) {

        String[] sisterQuestions = {
                "When did you see " + personaggi.get("victim").getName() + " the last time?",
                "Do you know someone who could  have intention to harm " + personaggi.get("victim").getName() + "?",
                "Was she going to meet someone on the day of the crime? ",
                "Were you two close? ",
                "Where were you between 12:00 - 1:00 a.m.? And who was with you?",
                "Did " + personaggi.get("victim").getName() + " shared any information about the stolen picture?",
                "Where did she manage to get this famous picture for the exhibition?",
                "What do you think about the traces of lead intoxication in " + personaggi.get("victim").getName() + "'s blood?",
                "Was there anything unusual in her behaviour lately? ",
        };
        return sisterQuestions;
    }


//6->contradicts to boss 6, 5->contradiction boss 5
    public String[] sisterAnswers(HashMap<String, Person> personaggi) {
        String[] sisterAnswers = {
                personaggi.get("sister").getName() + ": The last time I saw " + personaggi.get("victim").getName() + " on the day she was killed, " +
                        "in the morning, before she went to work. ",
                personaggi.get("sister").getName() + ": " + personaggi.get("victim").getName() + " She got on well with everyone. However...A couple of days before this tragedy" +
                        "\n she told me that someone in her close surrounding is not the person that it seemed to be.",
                personaggi.get("sister").getName() + ": " + personaggi.get("victim").getName() + " didn't mention meeting anyone, but she received a message and said she would come home late. ",
                personaggi.get("sister").getName() + ": Yes, " + personaggi.get("victim").getName() + " and I had very good relations. ",
                personaggi.get("sister").getName() + ": Between 12:00 - 1:00 a.m. I was sleeping at home, alone.",
                personaggi.get("sister").getName() + ": " + personaggi.get("victim").getName() + " was very excited to have this picture for the exhibition, but a day before this tragedy" +
                        "\nshe mentioned that something didn't add up and probably they should have postpone the exhibition opening. ",
                personaggi.get("sister").getName() + ": " + personaggi.get("victim").getName() + " said that Mr.Green, her boss, had presented her some new art Collector, " +
                        "\nwho had this painting in a private family collection for a long time",
                personaggi.get("sister").getName() + ": I know from " + personaggi.get("victim").getName() + " that some painting restorers work with lead dust, but her work wasn't connected with restoring. " +
                        "\nI have no idea how she could get this intoxication. ",
                personaggi.get("sister").getName() + ": Well, yes. "+ personaggi.get("victim").getName() +", seemed nervous and a bit secretive in the last few days, " +
                        "\nI thought it could be connected with the importance of the exhibition, but now I'm sure there was something more. "
        };
        return sisterAnswers;
    }


    public String[] bossQuestions(HashMap<String, Person> personaggi) {

        String[] bossQuestions = {
                "Where were you between 12:00 - 1:00 a.m.? And who was with you?",
                "When did you see " + personaggi.get("victim").getName() + " the last time?",
                "Do you know someone who could  have intention to harm " + personaggi.get("victim").getName() + "?",

                "Was there anything unusual in her behaviour lately? ",
                "What do you think about the traces of lead intoxication in " + personaggi.get("victim").getName() + "'s blood?",
                "What did she think about the stolen picture?",
                "Where did she manage to get this famous picture for the exhibition?"
        };
        return bossQuestions;
    }

    //0->puzzle 6->contradiction sister 6, 5->contradiction sister 5
    public String[] bossAnswers(HashMap<String, Person> personaggi) {
        String[] bossAnswers = {
                personaggi.get("boss").getName() + ": Between 12:00 - 1:00 a.m. As usual, I was sleeping at home, alone. Am I a suspect now?",
                personaggi.get("boss").getName() + ": The last time I saw " + personaggi.get("victim").getName() + " around lunchtime here in the Gallery, " +
                        "\neverything was going according to the plan so I left soon, " +
                        "\n we didn't talk much that time.",
                personaggi.get("boss").getName() + ": " + personaggi.get("victim").getName() + " I don't think so. She just got unlucky.",
                personaggi.get("boss").getName() + ": " + personaggi.get("victim").getName() + " Not that I noticed, she behaved in a normal way. ",


                personaggi.get("boss").getName() + ": No idea, maybe " + personaggi.get("victim").getName() + " May be she had hobbies, involving usage of dangerous materials, " +
                        "\nI don't know much about her personal life. ",

                personaggi.get("boss").getName() + ": It was a very precious piece of art, "+ personaggi.get("victim").getName() +
                        " was happy to have it for the exhibition. She was looking forward to it's opening.",
                personaggi.get("boss").getName() + ": "+ personaggi.get("victim").getName() +" had her sources, I don't know where she got the painting. "

        };
        return bossAnswers;
    }

//Assistant
public String[] assistantQuestions(HashMap<String, Person> personaggi) {

    String[] assistantQuestions = {
            "Where were you between 12:00 - 1:00 a.m.? And who was with you?",
            "When did you see " + personaggi.get("victim").getName() + " the last time?",
            "Do you know someone who could harm " + personaggi.get("victim").getName() + "?",

            "Did you notice anything unusual in her behaviour lately? ",
            "What do you think about the traces of lead intoxication in " + personaggi.get("victim").getName() + "'s blood?",
            "What did she think about the stolen picture?",
            "Where did she get this famous picture for the exhibition?"
    };
    return assistantQuestions;
}

    //0->opens in menu ->talk to grandmother
    // 6->contradiction sister 6, 5->contradiction sister 5
    public String[] assistantAnswers(HashMap<String, Person> personaggi) {
        String[] assistantAnswers = {
                personaggi.get("assistant").getName() + ": Between 12:00 - 1:00 a.m. As usual, I was at home, my grandma ca prove it!",
                personaggi.get("assistant").getName() + ": The last time I saw " + personaggi.get("victim").getName() + " around 8 in the evening on this tragic day, I was going home, " +
                        "\n she was staying in the gallery to finish some work",
                personaggi.get("assistant").getName() + ": " + personaggi.get("victim").getName() + " No, it was a horrible accident.",
                personaggi.get("assistant").getName() + ": " + personaggi.get("victim").getName() + " was acting normally. ",


                personaggi.get("assistant").getName() + ": I don't know how " + personaggi.get("victim").getName() + " could get this intoxication.",

                personaggi.get("assistant").getName() + ": "+ personaggi.get("victim").getName() + " said the painting was a treasure, she couldn't wait for the exhibition opening.",
                personaggi.get("assistant").getName() + ": "+ personaggi.get("victim").getName() +" didn't tell me where she found the painting, and I didn't ask. "

        };
        return assistantAnswers;
    }

    //grandmother
    public String[] grandmotherQuestions(HashMap<String, Person> personaggi) {

        String[] grandmotherQuestions = {
                "Where was your grandson last Wednesday  between 12:00 - 1:00 a.m.?",
                "Did you notice anything unusual in his behaviour lately?",
                "Is it possible that your grandson left the house unnoticed later while you were sleeping ?",
                "Can I take a look at "+personaggi.get("assistant").getName()+"'s room?",
                "Is your clock working correctly? I see the different time on my watch."
        };
        return grandmotherQuestions;
    }

    //1->opens new question: can I take a look at his room?->after the answer opens possibility to find a message in his laptop

    public String[] grandmotherAnswers(HashMap<String, Person> personaggi) {
        String[] grandmotherAnswers = {
                personaggi.get("grandmother").getName() + ": I remember it well, because I looked at the clock: my grandson came back at 21.45, and stayed at home all night.",
                personaggi.get("grandmother").getName() + ": Hmm, nothing special, but.. Last week he came back home at lunchtime, " +
                        "\n I was surprised, because he never comes home util the evening. He didn't stay long, just went to his room for a while and then left the house.",
                personaggi.get("grandmother").getName() + ": "+ personaggi.get("assistant").getName() +" couldn't go out unnoticed, as I was sleeping on the couch near the door, and I would wake up: our door makes a lot of noise ",
                personaggi.get("grandmother").getName() +": Yes, his room is upstairs on the right, I will not accompany you, it's hard for me to go up and down the stairs. ?",
                personaggi.get("grandmother").getName() +": Oh, that.. I forgot to tell you: the clock has a defect. I will explain you how it works.. "
        };
        return grandmotherAnswers;
    }



//Getter and setter


    public String[] getHurryUpSister() {
        return hurryUpSister;
    }

    public void setHurryUpSister(String[] hurryUpSister) {
        this.hurryUpSister = hurryUpSister;
    }

    public String[] getHurryUpAssistant() {
        return hurryUpAssistant;
    }

    public void setHurryUpAssistant(String[] hurryUpAssistant) {
        this.hurryUpAssistant = hurryUpAssistant;
    }

    public String[] getHurryUpBoss() {
        return hurryUpBoss;
    }

    public void setHurryUpBoss(String[] hurryUpBoss) {
        this.hurryUpBoss = hurryUpBoss;
    }
}//
