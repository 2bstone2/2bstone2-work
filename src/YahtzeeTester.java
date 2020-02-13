/**
 *This program is the beginning of a yahtzee program and takes in user input to keep or reroll dice. The player will
 * see their scorecard and then be prompted to play again once their turn is over or they would like to keep all of
 * their dice in the dieArr
 *CPSC 224-01 Spring 2020
 * HW #1
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v1.0 2/3/2020
 * @see no borrowed code
 */
//TODO: fix name to yahtzee

import java.util.Scanner;

public class YahtzeeTester {
    public static int maxTurns = 3; //TODO: read in from file
    public static int curTurn;
    /**
     * Yahtzee Tester drives the Yahtzee program by making use of classes Dice, Hand and Score
     */
    public static void main(String[] args) {

        //TODO: fix this
        Dice yahtzee = new Dice();
        Hand yHand = new Hand();
        //Score yScore = new Score();


        char playAgain = 'y';
        boolean containsN = true; //TODO: get rid of numN and move to hand
       // int curTurn; //TODO: pass into eventually if needed maybe?
        //turn loop
        while(playAgain != 'n') {
            yHand.initHand();
            curTurn = 0;
            containsN = true;
            //number of rounds control loop
            while (curTurn < maxTurns && containsN == true) {
                yHand.newHand();
                yHand.keepHand();
                containsN = Hand.keepStr.contains("n");
                curTurn++;
            }
            yHand.sortHand();
            System.out.print("Your sorted hand is: ");
            yHand.displayHand();
            System.out.print('\n');

            //scoring begins:
            Score.totalOfEachNumScore();
            Score.findMaxOfAKind();
            Score.findFullHouse();
            Score.findMaxStraight();
            Score.findYahtzee();
            System.out.println("Score: " + Score.totalAllDice() + " on the Chance line.");

            playAgain = askToPlayAgain(); //after turn
        }
    }

    /**
     * Prompts the user to play again when either the number of current rounds reaches the maximum amount, or
     * when the user wants to keep all of their dice.
     * @return 'y' for yes to play again and continue the loop, 'n' to exit the game.
     */
    public static char askToPlayAgain() { //TODO: make get play again?
        char userAns;
        Scanner playAgainSc = new Scanner(System.in);

        System.out.println("\nEnter 'y' to play again or 'n' ");
        userAns = playAgainSc.next().charAt(0);

        if (userAns != 'y')
            System.exit(0);
        // Eventually call a change player function, for now, only exits program

        return userAns;
    }
}
