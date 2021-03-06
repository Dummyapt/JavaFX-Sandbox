package de.dummyapt.javafx_sandbox.motivationalquotes;

import javafx.application.Application;
import javafx.stage.Stage;

public final class MotivationalQuotes extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

/*
import java.awt.desktop.SystemEventListener;
import java.time.format.DateTimeFormatter;
import  java.util.Random;

// Allows the functioning of Arrays

import java.util.Arrays;
// allows importing of input module. Allows users to to interact with application
import java.util.Scanner;
// allows me to be able to use arraylist(like arrays, but i can edit and even add/remove elements from them)
import java.util.ArrayList;
// Allows me to display time/current day/day of the week. Hell, even times zones(current time zone or even other time zones like JST(Japanese standard time)
import java.time.*;

public class Motivate {

    public static void main(String[] args) {

        // Allows user to actually interact with the application(mainly to write their name)
        Scanner scan = new Scanner(System.in);

        System.out.println("--------------------");
        System.out.println("| Motivation        |");
        System.out.println("| Inspiration       |");
        System.out.println("| Determination     |");
        System.out.println("| Nation            |");
        System.out.println("--------------------");


        System.out.println("Just Curious, What time of day is it in your Location?" +
                "\nMorning" +
                "\nAfterNoon" +
                "\nEvening" +
                "\n:");
        String TOD = scan.nextLine();

        System.out.println();

        System.out.println("And... What is your name?:");
        String name = scan.nextLine();

        System.out.println("Good "+TOD+" "+name+"!"+
                "\nWell here some motivation for ya day!");

        // Bellow shows the formatting of the month, day and year. And current time

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu " +
                "\nHH:mm:ss");
        System.out.println();
        System.out.println("Current time zone:" +
                "\n"+ZoneOffset.systemDefault());
        System.out.println(OffsetDateTime.now().getOffset());


        System.out.println();
        //Actual time zone(current based on time zone off of System)
        // Outputs something the along the lines of -4:00 America/Washington_DC
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("Current Time/date:" +
                "\n"+dtf.format(now));
        // Outputs 06/02/2021  time:22:44:56
        // Quotes either from me, I've heard, or people i know personally(doesn't really include quotes from celebrities)
        ArrayList<String> regularmotivationquotes = new ArrayList<>();

        regularmotivationquotes.add("\"You can't expect someone to love you if you don't love yourself\"");
        regularmotivationquotes.add("\"You don't lose if you get knocked down,  you lose if you stay down\"");
        regularmotivationquotes.add("\"Everyone wants results, but nobody wants to put in work to get those results.\"");
        regularmotivationquotes.add("\"Everyday, put in that work!!");
        regularmotivationquotes.add("\"Some things in life are difficult; but difficult does not mean impossible\"");
        regularmotivationquotes.add("\"Little things make big days\"");
        regularmotivationquotes.add("\"Your limitation???it???s only your imagination.\"");
        regularmotivationquotes.add("\"Great things never come from comfort zones.\"");
        regularmotivationquotes.add("\"Do something today that your future self will thank you for\"");
        regularmotivationquotes.add("\"Everything comes from within\"");
        regularmotivationquotes.add("\"Love yourz\"");
        regularmotivationquotes.add("\"Trust thy self, Know they self, Love thy self\"");
        // quotes of 9/6/21
        regularmotivationquotes.add("\"Progress Demands Sacrifice\"");
        regularmotivationquotes.add("\"It always seems impossible, until it???s done.\"");
        regularmotivationquotes.add("\"You didn???t come this far, to only come this far.\"");
        regularmotivationquotes.add("\"An addict who overcomes addiction is capable of accomplishing anything.\"");
        regularmotivationquotes.add("\"The comeback must always be greater than the setback.\"");
        regularmotivationquotes.add("\"A mistake repeated more than once is a decision\"");
        regularmotivationquotes.add("\"We gain the strength of the temptation we resist.\"");
        regularmotivationquotes.add("\"If I quit now, I will soon be back to where I started. " +
                "\nAnd when I started, I was desperately wishing to be where I am now.\"");

        // quotes from people i know
        ArrayList<String> quotesdesamies = new ArrayList<>();

        quotesdesamies.add("\"\"Allow yourself to grow. That means giving up some things." +
                "\nThere means giving up some things. There are things we can't how forever" +
                "\nKnow when something is no longer serving its purpose to lift you up." +
                "\nKnow when to let go of things that keep holding you back and making you" +
                "\nfeel life you are not good at it. You will always be good for something.Go for it\"");

        quotesdesamies.add("\"A secret to happiness is letting every situation be what it is," +
                "\ninstead of what you think it should be, and then making the best of it.\"");


// Quotes from Celebrities/Famous people in history
        ArrayList<String> celebritiyquotes = new ArrayList<>();


//Quotes from nas
        celebritiyquotes.add("\"Mistakes make masterful teachers.\"-Nas");
        celebritiyquotes.add("\"Every great person educates themselves.\"-Nas");

        celebritiyquotes.add("\"No idea is original, there???s nothing new under the sun, " +
                "\nit???s never what you do, but how it???s done.\"-Nas");

        celebritiyquotes.add("\"you have to Keep your vision clear, Cause only a coward lives in fear\"-Nas");
        celebritiyquotes.add("\"If you scared to take chances, you'll never have the answers...\"-Nas");

// Quotes from Micheal Jordan
        celebritiyquotes.add("\"To learn to succeed, you must first learn to fail.\"-Micheal Jordan");
        celebritiyquotes.add("\"Failure is acceptable. but not trying is a whole different ball park\"-Micheal Jordan");

        // Quotes from DMX
        celebritiyquotes.add("\"It???s a waste of energy to think about what somebody else is doing and how they doing it. I???ma just do what I do.\"-DMX");
        celebritiyquotes.add("\"Never become so involved with something that it blinds you.\"-DMX");

        // quotes from Bruce Lee
        celebritiyquotes.add("\"Always be yourself, express yourself, have faith in yourself, " +
                "\ndo not go out and look for a successful personality and duplicate it.\"-Bruce Lee");

        celebritiyquotes.add("\"As you think, so shall you become.\"-Bruce Lee");
        celebritiyquotes.add("\"If you spend too much time thinking about a thing, you'll never get it done.\"-Bruce Lee");

        celebritiyquotes.add("\"Showing off is the fool's idea of glory\"-Bruce Lee");

        celebritiyquotes.add("\"Be like water making its way through cracks. " +
                "\nDo not be assertive, but adjust to the object, and you shall find a way around or through it. " +
                "\nIf nothing within you stays rigid, outward things will disclose themselves.\"-Bruce Lee");

        celebritiyquotes.add("\nIf you put water into a cup, it becomes the cup. You put water into a bottle and it becomes the bottle. " +
                "\nYou put it in a teapot, it becomes the teapot. " +
                "\nNow, water can flow or it can crash. Be water, my friend.-Bruce Lee\n");

        celebritiyquotes.add("\"I fear not the man who has practiced 10,000 kicks once," +
                "\nbut I fear the man who had practiced one kick 10,000 times.\"-Bruce Lee");

// Quote From Colin Powell
        celebritiyquotes.add("\"There are no secrets to success. It is the result of preparation, hard work, and learning from failure. " +
                "\n\"-Collin Powell");
// Quote from Frank Ocean
        celebritiyquotes.add("\"Work hard in silence, let your success be your noise.??? " +
                "\n\"-Frank Ocean");
//Quotes on Subconscious Mind/Success
        celebritiyquotes.add("\"It is almost impossible to be consistent with your goal if your subconscious mind " +
                "\nis constantly fed with negative perceptions of its road\"-Edmond Mbiaka");

        celebritiyquotes.add("\"Whatever your conscious mind assumes and believes to be true,your subconscious mind will accept and bring to pass." +
                "\nBelieve in good fortune, divine guidance, right action, and all the blessings of life.\" Joseph Murphy");

        celebritiyquotes.add("\"We are what we repeatedly do. Excellence then, is not an act, but a habit\"-Aristotle");

        celebritiyquotes.add("\"Strive not to be a success, but rather to be of value.\"-Albert Einstein");

// New quotes(8/11/21)
        celebritiyquotes.add("\"If you scared to take chances, you'll never have the answers..\"-Nas");
        celebritiyquotes.add("\"Don't speak to fools, they scorn the wisdom of your words.\"-Nas");
        celebritiyquotes.add("\"The secret of getting ahead is getting started\"-walt disney");
        celebritiyquotes.add("\"Keep grindin??? boy, your life can change in one year, And even when it???s dark out, " +
                "\nthe sun is shining somewhere.\"-J Cole");
        celebritiyquotes.add("\"Life is a movie, pick your own role, Climb your own ladder or you dig your own hole\"-J Cole ");
        celebritiyquotes.add("\"It does not matter how slowly you go as long as you do not stop.\"-Confucius");
        // new quotes-9/6/21
        celebritiyquotes.add("\"A warrior is not a person that carries something destructive. " +
                "\nThe biggest war you ever go through is right between your own ears. " +
                "\nIt???s in your mind. We???re all going through a war in our mind, " +
                "\nand we have to callus our mind to fight that war and to win that war???.\"-David goggins");

        celebritiyquotes.add("\"Like success, failure is many things to many people. " +
                "With a confident mental attitude, failure is a learning experience, a rung on the ladder, " +
                "and a plateau at which to get your thoughts in order to prepare to try again\"-W. Clement Stone");


        // helps with generating/randomizing quotes


        int index = (int)(Math.random() * regularmotivationquotes.size());
        int windex = (int)(Math.random() * celebritiyquotes.size());
        int apex =(int)(Math.random() * quotesdesamies.size());

        // Prints out Quotes(both celebrity and personal quotes

        System.out.println("Quote of the Day:"
                +regularmotivationquotes.get(index));
        System.out.println();
        System.out.println("Friends quote of the day:"
                +quotesdesamies.get(apex));
        System.out.println();
        System.out.println("Celebrity quote of the day:"
                +celebritiyquotes.get(windex));
    }
}
 */