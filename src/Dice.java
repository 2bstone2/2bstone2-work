/**
 *This program performs the re-roll functionality of the Yahtzee game and holds dice specific values.
 * CPSC 224-01 Spring 2020
 * HW #1
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v1.0 2/3/2020
 */

import java.util.Random;

public class Dice {
    public static int numDie;
    public static int min;
    public static int max;

    public Dice() {
        numDie = 5; //TODO read in for hw 2
        min = 1; //TODO read in
        max = 6; //TODO read in and adjust index
    }

    public static int rollDie(){
       Random dieVal = new Random();

       return dieVal.ints(1, min, max + 1).findFirst().getAsInt();
    }
}
