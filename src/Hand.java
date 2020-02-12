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
import java.util.Collections;
import java.util.Scanner;

public class Hand {

    public static ArrayList<Integer> hand = new ArrayList(Dice.numDie);
    public ArrayList<Character> keepArr = new ArrayList(Dice.numDie);
    public int numN;
    public String keep;
    public char playAgain;
    public Scanner playAgainSc = new Scanner(System.in);
    public static final int maxTurns = 3; //move to yahtzee
    public static int curTurn;
    private static char userAns;

    Hand() {
        keep = "";
        //initialize handArr and keepArr
        for (int i = 0; i < Dice.numDie; i++) {
           // keep = 'n'.charAt(i);
            keep = keep + 'n';
            System.out.println("keep check: " + keep);
            hand.add(i, Dice.rollDie());
        }
        numN = Dice.numDie;

        playAgain = 'y';
        curTurn = 0;
    }

    /**
     * Initializes keep to an array of all 'n' at the beginning of each turn
     */
    /*public void initKeep() {
        for (int i = 0; i < Dice.numDie; i++) {
            keepArr.add(i, 'n');
        }
        numN = Dice.numDie;
    }*/

    /**
     * Rolls a new hand if the keep array in same index is 'n' and then stores the new value in indexed
     * position of array list.
     */
    public void newHand() {
        ArrayList<Integer> tempHandArr = new ArrayList(Dice.numDie);
        System.out.println("Display check: before");
        displayArr();
        numN = 0;
        System.out.print("Your roll was: ");
        for (int i = 0; i < Dice.numDie; i++) {
            if (keep.charAt(i) == 'n') {

                tempHandArr.add(i, Dice.rollDie());
                System.out.println("Reached new die. check: " + tempHandArr.get(i) + " at index " + i);
            }
            else {
                tempHandArr.add(i, hand.get(i));
            }
            //keep = "";
            //keep += 'n';
        }


        Collections.copy(hand, tempHandArr);
        numN = Dice.numDie;
        displayArr();
        System.out.print("\n");
    }

    /**
     * Prompts user to enter which die to keep and load them into the keep arrayList
     */
    public void keepDie() {
        Scanner toKeepSc = new Scanner(System.in);

        if (curTurn < maxTurns - 1) { //might need later for scoring
            System.out.print("Enter dice to keep (y or n): ");
            keep = toKeepSc.nextLine();
            System.out.print('\n');
        }

        //calcs number of n for while loop control
        for(int i = 0; i < Dice.numDie; i++) {
            if (keep.charAt(i) == 'n') {
                numN++;
            }
        }
    }

    /**
     * This program sorts the die hand, and thus the keep array accordingly. The first sort of the if statement
     * sorts the last hand, regardless of the keep value at the same indexed position. The second sort occurs after
     * the first and second re-roll and sorts and pushes all kept die (now in descending order) to the front of the
     * hand array for viewing ease.
     */
    public void sortKeep() { //TODO: change to sortHand and update for generic
        int idx = 0;
        int tempNum;
        char tempChar;
        ArrayList<Integer> tempHandArr = new ArrayList(Dice.numDie);
        char[] keepArr = keep.toCharArray();
        //ArrayList<Character> tempKeepArr = new ArrayList(Dice.numDie);

        // Sort of final hand
       /* if (curTurn == maxTurns || numN == 0) {
            for (int i = 1; i <= Dice.max; i++) {
                for (int j = 0; j < Dice.numDie; j++) {
                    if (hand.get(j) == i) {
                        tempHandArr.add(idx, i);
                        idx++;
                    }
                }
            }
            Collections.copy(hand, tempHandArr);
        }*/
        // Sorts kept dice after each re-roll
       /* else {
            for (int i = 1; i <= Dice.max; i++) {
                for (int j = 0; j < Dice.numDie; j++) {
                    if (hand.get(j) == i && keep.charAt(j) == 'y') {
                        tempHandArr.add(idx, hand.get(j));

                        idx++;
                    }
                }
            }*/
            for (int i = 0; i < Dice.numDie - 1; i++) {
                if (hand.get(i) > hand.get(i + 1)) {
                    //tempHandArr.add(idx, hand.get(j));
                    tempNum = hand.get(i);
                    hand.add(i, hand.get(i + 1));
                    hand.add(i+1,tempNum);

                    tempChar = keepArr[i];
                    keepArr[i] = keepArr[i + 1];
                    keepArr[i + 1] = tempChar;
                }
            }
            keep = keepArr.toString();
            /*for (int i = 0; i < Dice.numDie; i++) {
                if (keepArr.get(i) == 'n') {
                    tempHandArr.add(idx, hand.get(i));
                    tempKeepArr.add(idx, keepArr.get(i));
                    idx++;
                }
            }*/
           // Collections.copy(hand, tempHandArr);
           // Collections.copy(keepArr, tempKeepArr);
            //tempHandArr.clear();
            //tempKeepArr.clear();

    }

    /**
     * Prompts the user to play again when either the number of current rounds reaches the maximum amount, or
     * when the user wants to keep all of their dice.
     * @return 'y' for yes to play again and continue the loop, 'n' to exit the game.
     */
    public char askToPlayAgain() {
        System.out.println("\nEnter 'y' to play again or 'n' ");
        userAns = playAgainSc.next().charAt(0);

        if (userAns != 'y') {
            // Eventually call a change player function, for now exits program
            System.exit(0);
        }
        return userAns;
    }

    /**
     * Displays the hand array which contains the die values
     */
    public void displayArr() { //TODO: change name to display hand
        for (int i = 0; i < Dice.numDie; i++) {
            System.out.print(hand.get(i) + " ");
        }
        System.out.print('\n');

        for(int i = 0; i < Dice.numDie; i++) {
            System.out.print(keep.charAt(i) + " ");
        }
        System.out.print('\n');
    }

}
