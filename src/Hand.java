/**
 * This program controls the hand and re-rolls of the dice based on user inputted keepStr values.
 * Additionally, it tracks the number of turns and redirects a player to play again. Keep and hand are
 * parallel array lists.
 *
 * CPSC 224-01 Spring 2020
 * HW #1
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v1.0 2/3/2020
 *
 **/
/*
TODO:
- to yahtzeeTest
    - when to get the ones to keep function

???maybe a turn class later on
*/

import java.util.ArrayList;
import java.util.Scanner; //take out maybe if keepHand() goes to yahtzeeTester

public class Hand {
    public static String keepStr;
    public static int numDie;
    public static ArrayList<Integer> dieArr = new ArrayList(numDie); //change to no size and use ensureCapacity() in construct

    Hand() {
        keepStr = "";

        //initialize handArr and keepArr
        for (int i = 0; i < numDie; i++) {
            keepStr = keepStr + 'n';
            dieArr.add(i, 0);
        }
    }

    /**
     * Rolls a new hand if the keep array in same index is 'n' and then stores the new value in indexed
     * position of array list.
     */
    public static void popNewHand() {
        for (int i = 0; i < numDie; i++) {
            if (keepStr.charAt(i) == 'n')
                dieArr.set(i, Dice.rollDie());
        }
        System.out.print("Your roll was: ");
        displayHand();
        System.out.print("\n");
    }

    /**
     * Prompts user to enter which die to keep and load them into the keep arrayList
     */
    public static void askToKeepHand() { //should this be in yahtzee?
        Scanner toKeepSc = new Scanner(System.in);

        if (YahtzeeTester.curTurn < YahtzeeTester.maxTurns) { //might need later for scoring
            System.out.print("Enter dice to keep (y or n) or 'S' to see your scorecard: ");
            keepStr = toKeepSc.nextLine();
            System.out.print('\n');
        }

        if(keepStr.equals("S")) {
            Scorecard.displaySC();
            return;
        }

        //input checks
        if (keepStr.length() != numDie) {
            System.out.println("Please enter a correct input");
            askToKeepHand();
        }

        for(int i = 0; i < keepStr.length(); i++) {
            if (keepStr.charAt(i) != 'y' && keepStr.charAt(i) != 'n') {
                System.out.println("Please enter a correct input");
                askToKeepHand();
            }
        }
    }

    /**
     * checks if keepStr contains the letter n
     * @return true if keepStr contains n, false if it does not
     */
    public static boolean checkContainsN() {
        return keepStr.contains("n");
    }

    /**
     * This program sorts the die hand, and thus the keep array accordingly. The first sort of the if statement
     * sorts the last hand, regardless of the keep value at the same indexed position. The second sort occurs after
     * the first and second re-roll and sorts and pushes all kept die (now in descending order) to the front of the
     * hand array for viewing ease.
     */
    public static void sortHand() {
        for (int i = 0; i < numDie; i++) {
            for (int j = 0; j < numDie - i - 1; j++) {
                if (dieArr.get(j) > dieArr.get(j + 1)) {
                    int tempNum = dieArr.get(j);
                    dieArr.set(j, dieArr.get(j + 1));
                    dieArr.set(j + 1, tempNum);
                }
            }
        }
    }

    /**
     * Displays the hand array which contains the die values
     */
    public static void displayHand() {
        for (int i = 0; i < numDie; i++)
            System.out.print(dieArr.get(i) + " ");
    }
}
