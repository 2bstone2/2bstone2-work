/**
 * This program controls the scoring for the game once the final hand is reached.
 *
 * CPSC 224-01 Spring 2020
 * HW #2
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v2.0 2/14/2020
 */

import java.util.ArrayList;

//TODO: make functions return a val so you can put that in the scorecard array
public class Score {

    //upper scorecard
    /**
     * Totals the quantity of each number of the die and outputs the score
     */
    public static int totalOfEachNumScore(int numCheck) {
        int count = 0;

        for (int j = 0; j < Hand.numDie; j++) {
            if (Hand.dieArr.get(j) == numCheck) {
                count++;
            }
        }
        return count;
    }

    //lower scorecard
    /**
     * Totals the value of the final hand of die
     * @return the sum of the final hand of die
     */
    public static int totalAllDice() {
        int total = 0;
        for (int i = 0; i < Hand.numDie; i++) {
            total += Hand.dieArr.get(i);
        }
        return total;
    }

    /**
     * Finds if there is a full house in the final hand.
     * @return 0 if there is no full house, 25 if a full house was found
     */
    public static int findFullHouse() {
        int count;
        int lowCount;
        int highCount;
        boolean lowCountBool = false;
        boolean highCountBool = false;

        //get low and high count values for variable numDie
        if (Hand.numDie % 2 == 0) {
            highCount = (Hand.numDie/2);
        }
        else {
            highCount = (Hand.numDie/2) + 1;
        }
        lowCount = Hand.numDie - highCount;

        for (int i = 1; i <= Dice.numSides; i++) {
            count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i) {
                    count++;
                }
            }
            if (lowCount == highCount && count == lowCount || count == Hand.numDie) {
                lowCountBool = true;
                highCountBool = true;
            }
            else if (lowCount != highCount && count == lowCount) {
                lowCountBool = true;
            }
            else if (lowCount != highCount && count == highCount) {
                highCountBool = true;
            }
        }

        if (lowCountBool && highCountBool) {
            return 25;
        }
            return 0;
    }

    /**
     * Searches the final hand for the highest recurrence of a number
     * @return
     */
    public static int[] findMaxOfAKind() {
        int count;
        int maxCount = 0;
        int lowCount = Hand.numDie - 2;
        int highCount = Hand.numDie - 1;
        int[] scoreArr = new int [2];

        for (int i = 1; i <= Dice.numSides; i++) {
            count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
            }
        }

        if (maxCount == lowCount) {
            scoreArr[0] = totalAllDice();
            scoreArr[1] = 0;
        }
        else if (maxCount >= highCount) {
            scoreArr[0] = totalAllDice();
            scoreArr[1] = totalAllDice();
        }
        else {
            scoreArr[0] = 0;
            scoreArr[1] = 0;
        }
        return scoreArr;
    }

    /**
     * Iterates through final hand and looks for the longest straight
     */

    public static int[] findMaxStraight() {
        int curCount = 1;
        int maxCount = 1;
        int lowStraight = Hand.numDie - 1;
        int highStraight = Hand.numDie;
        int[] straightArr = new int [2];

        for(int i = 0; i < Hand.numDie - 1; i++) {
            if (Hand.dieArr.get(i) + 1 == Hand.dieArr.get(i + 1)) {
                curCount++;
            }
            else if (Hand.dieArr.get(i) + 1 < Hand.dieArr.get(i+ 1)){
                curCount = 1;
            }

            if (curCount > maxCount)
                maxCount = curCount;
        }

        if (maxCount == lowStraight) {
            straightArr[0] = totalAllDice();
            straightArr[1] = 0;
        }
        else if (maxCount == highStraight) {
            straightArr[0] = totalAllDice();
            straightArr[1] = totalAllDice();
        }
        else  {
            straightArr[0] = 0;
            straightArr[1] = 0;
        }
        return straightArr;
    }

    /**
     * iterates through final hand and looks for a yahtzee
     */
    public static int findYahtzee () {
        int num = Hand.dieArr.get(0);
        int count = 0;

        for (int i = 0; i < Hand.numDie; i++) {
            if (Hand.dieArr.get(i) == num)
                count++;
        }

        if (count == Hand.numDie && Hand.dieArr.get(0) != 0) {
            return 50;
        }

        return 0;
    }
}
