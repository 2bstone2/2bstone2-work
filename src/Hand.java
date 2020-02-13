/**
 * This program controls the hand and re-rolls of the dice based on user inputted keep values.
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
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Hand {

    public static ArrayList<Integer> hand = new ArrayList(Dice.numDie);
    public int numN;
    public String keep;
    public char playAgain;
    public static final int maxTurns = 3; //move to yahtzee
    public static int curTurn;
    private static char userAns;

    Hand() {
        keep = "";
        //initialize handArr and keepArr
        for (int i = 0; i < Dice.numDie; i++) {
            keep = keep + 'n';
            hand.add(i, Dice.rollDie());
        }
        numN = Dice.numDie;

        playAgain = 'y';
        curTurn = 0;
    }

    /**
     * Initializes keep to an array of all 'n' at the beginning of each turn
     */
    public void initHand() {
        keep = keep.replace('y','n');
        curTurn = 0;
        numN = Dice.numDie;
    }

    /**
     * Rolls a new hand if the keep array in same index is 'n' and then stores the new value in indexed
     * position of array list.
     */
    public void newHand() {

        System.out.print("Your roll was: ");
        for (int i = 0; i < Dice.numDie; i++) {
            if (keep.charAt(i) == 'n') {
                hand.set(i, Dice.rollDie()); //change to hand.set
            }
        }
        displayHand();
        System.out.print("\n");
    }

    /**
     * Prompts user to enter which die to keep and load them into the keep arrayList
     */
    public void keepDie() { //TODO should this be in Dice?
        Scanner toKeepSc = new Scanner(System.in);
        int tempN = 0;

        if (curTurn < maxTurns - 1) { //might need later for scoring
            System.out.print("Enter dice to keep (y or n): ");
            keep = toKeepSc.nextLine();
            System.out.print('\n');
        }
        //calcs number of n for while loop control
        for(int i = 0; i < Dice.numDie; i++) {
            if (keep.charAt(i) == 'n') {
                tempN++;
            }
        }
        curTurn++;
        numN = tempN;
    }

    /**
     * This program sorts the die hand, and thus the keep array accordingly. The first sort of the if statement
     * sorts the last hand, regardless of the keep value at the same indexed position. The second sort occurs after
     * the first and second re-roll and sorts and pushes all kept die (now in descending order) to the front of the
     * hand array for viewing ease.
     */
    public void sortHand() { //TODO: update for generic
        for (int i = 0; i < Dice.numDie; i++) {
            for (int j = 0; j < Dice.numDie - i - 1; j++) {
                if (hand.get(j) > hand.get(j + 1)) {
                    int tempNum = hand.get(j);
                    hand.set(j, hand.get(j + 1));
                    hand.set(j + 1, tempNum);
                }
            }
        }
    }

    /**
     * Prompts the user to play again when either the number of current rounds reaches the maximum amount, or
     * when the user wants to keep all of their dice.
     * @return 'y' for yes to play again and continue the loop, 'n' to exit the game.
     */
    public char askToPlayAgain() { //TODO: move to yahtzeeTester?
        Scanner playAgainSc = new Scanner(System.in);

        System.out.println("\nEnter 'y' to play again or 'n' ");
        userAns = playAgainSc.next().charAt(0);

        if (userAns != 'y')
            System.exit(0);
            // Eventually call a change player function, for now, only exits program

        return userAns;
    }

    /**
     * Displays the hand array which contains the die values
     */
    public void displayHand() {
        for (int i = 0; i < Dice.numDie; i++)
            System.out.print(hand.get(i) + " ");
    }

}
