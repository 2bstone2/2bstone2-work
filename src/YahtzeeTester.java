/**
 *This program is the beginning of a yahtzee program and takes in user input to keep or reroll dice. The player will
 * see their scorecard and then be prompted to play again once their turn is over or they would like to keep all of
 * their dice in the hand.
 *CPSC 224-01 Spring 2020
 * HW #1
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v1.0 2/3/2020
 * @see no borrowed code
 */

import java.util.ArrayList;
public class YahtzeeTester {

    /**
     * Yahtzee Tester drives the Yahtzee program by making use of classes Dice, Hand and Score
     */
    public static void main(String[] args) {

        Dice yahtzee = new Dice();
        Hand yHand = new Hand();
        Score yScore = new Score();

        //turn loop
        while(yHand.playAgain != 'n') {
            //init keep string outside roll loop, reset each turn
           // yHand.initKeep();
            yHand.curTurn = 0;
            //number of rounds control loop
            while (yHand.curTurn < yHand.maxTurns && yHand.numN > 0) {
               // yHand.sortKeep();
                yHand.newHand();

                //yHand.displayArr();
                yHand.keepDie();
               //yHand.displayArr(yHand.hand);
                yHand.curTurn++;
                //yHand.sortKeep();
            }
            yHand.sortKeep();
            System.out.print("Your sorted hand is: ");
            yHand.displayArr();

            //scoring begins:
            yScore.totalOfEachNumScore();
            yScore.findMaxOfAKind();
            yScore.findFullHouse();
            yScore.findMaxStraight();
            yScore.findYahtzee();
            System.out.println("Score: " + yScore.totalAllDice() + " on the Chance line.");

            yHand.playAgain = yHand.askToPlayAgain(); //after turn

        }

    }
}
