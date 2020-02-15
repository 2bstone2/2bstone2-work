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
public class Score {

    //upper scorecard
    /**
     * Totals the quantity of each number of the die and outputs the score
     */
    public static void totalOfEachNumScore() {

        for (int i = 1; i <= Dice.numSides; i++) {
            int count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i) {
                    count++;
                }
            }
            System.out.println("Score: " + count*i + " on the " + i + " line.");
        }
    }

    //lower scorecard
    /**
     * Totals the value of the final hand of die
     * @return the sum of the final hand of die
     */
    public static int totalAllDice() {
        int total = 0;
        for (int i = 0; i < Hand.numDie; i++)
            total+= Hand.dieArr.get(i);

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
        lowCount = Hand.numDie/2;

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
            System.out.println("Score: 25 on the Full House line.");
            return 25;
        }
        else {
            System.out.println("Score: 0 on the Full House line.");
            return 0;
        }
    }

    /**
     * Searches the final hand for the highest recurrence of a number
     */
    public static void findMaxOfAKind() {
        int count;
        int maxCount = 0;
        int lowCount = Hand.numDie - 2;
        int highCount = Hand.numDie - 1;

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
            System.out.println("Score: " + totalAllDice() + " on the small of a Kind Line." );
            System.out.println("Score: 0 on the large of a Kind Line.");
        }
        else if (maxCount >= highCount) {
            System.out.println("Score: " + totalAllDice() + " on the small of a Kind Line." );
            System.out.println("Score: " + totalAllDice() + " on the large of a Kind Line.");
        }
        else {
            System.out.println("Score: 0 on the small of a Kind Line.");
            System.out.println("Score: 0 on the large of a Kind Line.");
        }
    }

    /**
     * Iterates through final hand and looks for the longest straight
     */

    public static void findMaxStraight() {
        int curCount = 1;
        int maxCount = 1;
        int lowStraight = Hand.numDie - 1;
        int highStraight = Hand.numDie;

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
            System.out.println("Score: " + totalAllDice() + " on the Small Straight Line.");
            System.out.println("Score: 0 on the Large Straight Line.");
        }
        else if (maxCount == highStraight) {
            System.out.println("Score: " + totalAllDice() + " on the Small Straight Line.");
            System.out.println("Score: " + totalAllDice() + " on the Large Straight Line.");
        }
        else  {
            System.out.println("Score: 0 on the Small Straight Line.");
            System.out.println("Score: 0 on the Large Straight Line.");
        }
    }

    /**
     * iterates through final hand and looks for a yahtzee
     */
    public static void findYahtzee () {
        int num = Hand.dieArr.get(0);
        int count = 0;

        for (int i = 0; i < Hand.numDie; i++) {
            if (Hand.dieArr.get(i) == num)
                count++;
        }

        if (count == Hand.numDie) {
            System.out.println("Score: 50 on the Yahtzee Line.");
        }
        else  {
            System.out.println("Score: 0 on the Yahtzee Line.");
        }
    }
}
