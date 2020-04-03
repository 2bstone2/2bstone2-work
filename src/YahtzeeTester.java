
/**
 *This program is the beginning of a yahtzee program and takes in user input to change the configuration, keep or reroll dice. The player will
 * see their scorecard and then be prompted to play again once their turn is over or they would like to keep all of
 * their dice in the dieArr
 * CPSC 224-01 Spring 2020
 * HW #3
 * No sources to cite;
 *
 * Bailey Stone
 * @author bstone
 * @version v2.0 2/28/2020
 */

/* TODO for HW 4:
    - fix scorecard cutting turn short
    - press enter to continue
    - getters/setters
        - static/nonstatic + privacies
    - pass in objects
    - sort out functions (specifically what should be in ytester (mostly prompt__ functions ? ) and what should be in scorecard
    - clean up functions and their calls to others (ie improve game functionality/logic
    - work on scorecard formatting
 */

import java.util.Scanner;
import javax.swing.*;

public class YahtzeeTester {
    public static int maxTurns; //TODO: read in from file
    public static int curTurn;
    static ConfigFile yahtzeeConfig = new ConfigFile(); //maybe can move to readYahtzeeConfig;
    private static Dice yahtzee;
    private static Hand yHand;
    private static Scorecard yahtzeeSC;
    private static YahtzeeGUI yFrame;
   // private static JFrame yConfigFrame;


    /**
     * Yahtzee Tester drives the Yahtzee program by making use of classes Dice, Hand, Score, and ConfigFile
     * @param args doesnt get used
     */
    public static void main(String[] args) {
        char playAgain = 'y';

        
        readYahtzeeConfig();
        //promptConfigChange();

        //yFrame = new YahtzeeGUI();
        new YahtzeeGUI();
        yahtzee = new Dice(yahtzee.numSides);
        yHand = new Hand();
        //yFrame = new YahtzeeGUI();
        yahtzeeSC = new Scorecard(yahtzee.numSides);

        //yConfigFrame = new YahtzeeGUI.ConfigMenu();


        yahtzeeSC.createScoreFile("scorecard.txt");
        yahtzeeSC.readScoreFile("scorecard.txt");


        //turn loop
        while(playAgain != 'n' || !Scorecard.checkNumLines()) {
            curTurn = 1;

            yHand.keepStr = yHand.keepStr.replace('y','n');

            //promptSeeSC();
            System.out.println("\nstarting a new hand\n");

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
            yahtzeeSC.promptKeepLine();

            playAgain = askToPlayAgain(); //after turn
        }
    }

    /**
     * This program prompts the config fig to be read and updates values of Dice.numSides, Hand.numDie and maxTurns
     * @see Hand
     * @see Dice
     */
    public static void readYahtzeeConfig() {
        yahtzeeConfig.readFromFile("yahtzeeConfig.txt");

        yahtzee.numSides = yahtzeeConfig.configArr.get(0);
        yHand.numDie = yahtzeeConfig.configArr.get(1);
        maxTurns = yahtzeeConfig.configArr.get(2);
    }

    /**
     * displays the current settings of the game from the configFile and asks the user if they want to change
     * the settings. If yes, then user inputs their new settings. If no, then the user continues to the game
     */
    public static void promptConfigChange() {
        Scanner configChangeSc = new Scanner(System.in);
        char changeConfig;

        System.out.println("You are playing with " + Hand.numDie + " " + Dice.numSides + "-sided dice");
        System.out.println("You get " + maxTurns + " rolls per hand");

        System.out.print("Enter 'y' to change the configuration or 'n' to continue to the game: ");
        changeConfig = configChangeSc.next().charAt(0);
        System.out.print('\n');

        //input check
        if ((changeConfig != 'y') && (changeConfig != 'n')) {
            System.out.println("Please enter a correct input");
            promptConfigChange();
        }

        if (changeConfig == 'y') {
            yahtzeeConfig.changeConfig("yahtzeeConfig.txt");
            readYahtzeeConfig(); //re initializes values
        }
    }

    /**
     * Prompts the user to play again when either the number of current rounds reaches the maximum amount, or
     * when the user wants to keep all of their dice.
     * @return 'y' for yes to play again and continue the loop, 'n' to exit the game.
     */
    public static char askToPlayAgain() {
        char userAns;
        Scanner playAgainSc = new Scanner(System.in);

        System.out.print("Enter 'y' to play again or 'n' to exit: ");
        userAns = playAgainSc.next().charAt(0);
        System.out.print('\n');

        if (userAns == 'n') {
            System.exit(0);
        }

        //input checks
        if (userAns != 'y' && userAns != 'n') {
            System.out.println("Please a correct input");
            askToPlayAgain();
        }

        return userAns;
    }

}
