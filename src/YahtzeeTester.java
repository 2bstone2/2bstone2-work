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
    public static int maxTurns; //TODO: read in from file
    public static int curTurn;
    static ConfigFile yahtzeeConfig = new ConfigFile(); //maybe can move to readYahtzeeConfig;
   // static Hand yHand = new Hand();

    /*Yahtzee() { //TODO: need this constructor??
        maxTurns = 3; //TODO: read in from file
    }*/

    /**
     * Yahtzee Tester drives the Yahtzee program by making use of classes Dice, Hand and Score
     */
    public static void main(String[] args) {

        Dice yahtzee = new Dice();

        char playAgain = 'y';
        // int curTurn; //TODO: pass into eventually if needed maybe?

        //begin game, prompt change of vals

        readYahtzeeConfig();
        promptConfigChange();
        Hand yHand = new Hand();
        //turn loop
        while(playAgain != 'n') {
            curTurn = 1; //TODO: ?ok to have in main?.. need a function?

            yHand.keepStr = yHand.keepStr.replace('y','n'); //TODO: make a function?

            //number of rounds control loop
            while (curTurn <= maxTurns && yHand.checkContainsN()) {
                yHand.popNewHand();
                yHand.askToKeepHand();
                curTurn++;
            }

            yHand.sortHand();
            System.out.print("Your sorted hand is: ");
            yHand.displayHand();
            System.out.println('\n');

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

    public static void readYahtzeeConfig() {
        yahtzeeConfig.readFromFile();

       Dice.numSides = yahtzeeConfig.configArr.get(0);
       Hand.numDie = yahtzeeConfig.configArr.get(1);
       maxTurns = yahtzeeConfig.configArr.get(2);
    }

    public static void promptConfigChange() {
        Scanner configChangeSc = new Scanner(System.in);
        char changeConfig;

        System.out.println("You are playing with " + Hand.numDie + " " + Dice.numSides + "-sided dice");
        System.out.println("You get " + maxTurns + " rolls per hand");

        System.out.print("\nEnter 'y' to change the configuration or 'n' to continue to the game: ");
        changeConfig = configChangeSc.next().charAt(0);
        System.out.print('\n');

       if (changeConfig == 'y') {
           yahtzeeConfig.changeConfig();
           readYahtzeeConfig(); //re initializes values
          // System.out.println("reinit check: numDie")

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

        System.out.print("\nEnter 'y' to play again or 'n' to exit: ");
        userAns = playAgainSc.next().charAt(0);
        System.out.print('\n');

        if (userAns != 'y')
            System.exit(0);
        // Eventually call a change player function, for now, only exits program

        return userAns;
    }
}
