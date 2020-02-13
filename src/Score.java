/**
 * This program controls the scoring for the game once the final hand is reached.
 *
 * CPSC 224-01 Spring 2020
 * HW #1
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v1.0 2/3/2020
 */
//TODO change for more die sides
public class Score {
    /**
     * Totals the quantity of each number of the die and outputs the score
     */
    //upper scorecard
    public static void totalOfEachNumScore() {

        for (int i = Dice.min; i <= Dice.max; i++) {
            int count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i)
                    count++;
            }
            System.out.println("Score: " + count*i + " on the " + i + " line.");
        }
    }

    /**
     * Totals the value of the final hand of die
     * @return the sum of the final hand of die
     */
    //lower scorecard
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
        boolean fullHouse = false;
        int count;
        boolean twoCount = false;
        boolean threeCount = false;

        for (int i = 1; i <= Dice.max; i++) {
            count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i)
                    count++;
            }
            if (count == 2) {
                twoCount = true;
            }
            else if (count == 3) {
                threeCount = true;
            }
        }

        if (twoCount && threeCount) {
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
        int score;

        for (int i = 1; i <= Dice.max; i++) {
            count = 0;
            for (int j = 0; j < Hand.numDie; j++) {
                if (Hand.dieArr.get(j) == i)
                    count++;
            }
            if (count > maxCount)
                maxCount = count;
        }

            if (maxCount == 3) {
                System.out.println("Score: " + totalAllDice() + " on the 3 of a Kind Line." );
                System.out.println("Score: 0 on the 4 of a Kind Line.");
            }
            else if (maxCount >= 4) {
                System.out.println("Score: " + totalAllDice() + " on the 3 of a Kind Line." );
                System.out.println("Score: " + totalAllDice() + " on the 4 of a Kind Line.");
            }
            else {
                System.out.println("Score: 0 on the 3 of a Kind Line.");
                System.out.println("Score: 0 on the 4 of a Kind Line.");
            }
    }

    /**
     * Iterates through final hand and looks for the longest straight
     */

    public static void findMaxStraight() {
        int curCount = 1;
        int maxCount = 1;

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

        if (maxCount == 4) {
            System.out.println("Score: " + totalAllDice() + " on the Small Straight Line.");
            System.out.println("Score: 0 on the Large Straight Line.");
        }
        else if (maxCount == 5) {
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
        boolean yahtzee = false;
        int num = Hand.dieArr.get(0);
        int count = 0;

        for (int i = 0; i < Hand.numDie; i++) {
            if (Hand.dieArr.get(i) == num) {
                count++;
            }
            else {
                yahtzee = false;
            }
        }

        if (count == Hand.numDie) {
            System.out.println("Score: 50 on the Yahtzee Line.");
            yahtzee = true;
        }
        else  {
            System.out.println("Score: 0 on the Yahtzee Line.");
        }
    }
}
