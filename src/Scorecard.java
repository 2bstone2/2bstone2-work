import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program controls the scorecard for the game and
 *
 * CPSC 224-01 Spring 2020
 * HW #3
 * No sources to cite;
 *
 * @author Bailey Stone
 * @version v2.0 2/14/2020
 */

public class Scorecard {
    public static int sizeSC; //6 additional constant scoring lines
    public static ArrayList<ArrayList<String>> cardList = new ArrayList<>();
    public static ArrayList<ArrayList<String>> possList = new ArrayList<>();
    public static int numY = 0;

    Scorecard (int numSides) {
        sizeSC = numSides + 7;
        cardList.ensureCapacity(sizeSC);
        possList.ensureCapacity(sizeSC);
    }

    /**
     * initializes the scorecard file
     * @param inFileName
     */
    public static void createScoreFile (String inFileName) {

        try {
            PrintStream scoreFile = new PrintStream(new File(inFileName));

            for(int i = 1; i <= Dice.numSides; i++) {
                scoreFile.println(i + ",n,u,0");
            }
            scoreFile.println("3K,n,l,0");
            scoreFile.println("4K,n,l,0");
            scoreFile.println("FH,n,l,0");
            scoreFile.println("SS,n,l,0");
            scoreFile.println("LS,n,l,0");
            scoreFile.println("Y,n,l,0");
            scoreFile.println("C,n,l,0");
            scoreFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file for writing");
        }
    }

    /**
     * reads in values to cardList from scorecard file
     * @param fileName
     */
    public static void readScoreFile (String fileName) { //Probs dont need file name
        String line;
        String name;
        String used;
        String section;
        String scoreStr;
        Scanner inFile = null;

        try {
            inFile = new Scanner(new File(fileName));

            while (inFile.hasNextLine()) {
                name = "";
                used = "";
                section = "";
                scoreStr = "";
                line = inFile.nextLine();
                line+= '\0';
                ArrayList<String> lineList = new ArrayList<>(4);
                int i = 0;

                while (line.charAt(i) != ',') {
                    name+= line.charAt(i);
                    i++;
                }
                i++;
                while (line.charAt(i) != ',') {
                    used+= line.charAt(i);
                    i++;
                }
                i++;
                while (line.charAt(i) != ',') {
                    section+= line.charAt(i);
                    i++;
                }
                i++;
                while (line.charAt(i) != '\0') {
                    scoreStr+= line.charAt(i);
                    i++;
                }
                lineList.add(name);
                lineList.add(used);
                lineList.add(section);
                lineList.add(scoreStr);
                cardList.add(lineList);
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        storePossiblePlaces("scorecard.txt");
    }

    /**
     * populates possLines from cardList
     * @param fileName
     */

    //put in scoring here
    public static void storePossiblePlaces(String fileName) { //basically same as readfile but only read if n
        int[] tempArr = new int [2];
        int i;
        int idx = 0;
        numY = 0;
        Scanner inFile = null;

        possList.clear();

        try {
            inFile = new Scanner(new File(fileName));

            for(i = 0; i < Dice.numSides; i++) {
                ArrayList<String> lineList = new ArrayList<String>(2);
                if(cardList.get(i).get(1).equals("y")) {
                    numY++;
                    checkNumLines();
                }
                else if (cardList.get(i).get(1).equals("n")){
                    lineList.add(cardList.get(i).get(0));
                    lineList.add(String.valueOf(Score.totalOfEachNumScore(i+1)*(i+1)));
                    possList.add(idx, lineList);
                    idx++;
                }
            }

            for(int j = i; j < cardList.size(); j++) {
                ArrayList<String> lineList = new ArrayList<String>(2);
                if(cardList.get(j).get(1).equals("y")) {
                    numY++;
                    checkNumLines();
                }
                else {
                     if (cardList.get(j).get(0).equals("3K") && cardList.get(j).get(1).equals("n")) {
                        lineList.add(cardList.get(j).get(0));
                        tempArr = Score.findMaxOfAKind();
                        lineList.add(String.valueOf(tempArr[0]));
                        possList.add(idx, lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("4K") && cardList.get(j).get(1).equals("n")) {
                        tempArr = Score.findMaxOfAKind();
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(tempArr[1]));
                        possList.add(idx, lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("FH") && cardList.get(j).get(1).equals("n")) {
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(Score.findFullHouse()));
                        possList.add(idx, lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("SS") && cardList.get(j).get(1).equals("n")) {
                        tempArr = Score.findMaxStraight();
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(tempArr[0]));
                        possList.add(idx,lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("LS") && cardList.get(j).get(1).equals("n")) {
                        tempArr = Score.findMaxStraight();
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(tempArr[1]));
                        possList.add(idx,lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("Y") && cardList.get(j).get(1).equals("n")) {
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(Score.findYahtzee()));
                        possList.add(idx,lineList);
                        idx++;
                    } else if (cardList.get(j).get(0).equals("C") && cardList.get(j).get(1).equals("n")) {
                        ;
                        lineList.add(0, cardList.get(j).get(0));
                        lineList.add(1, String.valueOf(Score.totalAllDice()));
                        possList.add(idx,lineList);
                        idx++;
                    }
                }
            }
            inFile.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * makes changes to cardList
     * @param name
     */

    //now only pass in arr to writing file
    public static void changeCardList(String name) { //change yes/no and might need to int parse for sc voring
        ArrayList<String> tempArr = new ArrayList<>(4);
        int i;
        int j = 0;

        storePossiblePlaces("scorecard.txt");

        for(i = 0; i < sizeSC - numY; i++) {
            if (possList.get(i).get(0).equals(name)) {
                j = i;
            }
        }
        for(i = 0; i < sizeSC; i++) {
            if (cardList.get(i).get(0).equals(name)) {
                tempArr.add(0,name);
                tempArr.add(1,"y");
                tempArr.add(2,cardList.get(i).get(2));
                tempArr.add(3,possList.get(j).get(1));
                break;
            }
        }
        cardList.set(i,tempArr);

        writeScoreFile("scorecard.txt");
        storePossiblePlaces("scorecard.txt");
    }

    /**
     * gets the line that the user wants to keep
     */

    public static void promptKeepLine() {
        String keepAns = "";
        Scanner keepAnsSc = new Scanner(System.in);

        storePossiblePlaces("scorecard.txt");
        System.out.println("Here are your available scorecard lines:");
        displayPossLines();

        System.out.println("Enter the code of the line you would like to keep: ");
        keepAns = keepAnsSc.nextLine();

        changeCardList(keepAns);
    }

    /**
     * updates the scorecard file with changes made to cardList
     * @param fileName
     */

//write writescorefile then readScoreFile, this will just copy the vals so you dont have to assign
    public static void writeScoreFile(String fileName) { //file name, score val, todo change y/n

        try {
            PrintStream file = new PrintStream(String.valueOf(fileName));
            for(int i = 0; i < sizeSC; i++) {
                file.println(cardList.get(i).get(0) + "," + cardList.get(i).get(1) + "," + cardList.get(i).get(2) + "," + cardList.get(i).get(3));
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file for writing");
        }
    }

    /**
     * checks that there are remaining lines, exits name if not
     * @return
     */

    public static boolean checkNumLines() {
        if ((sizeSC - numY) == 0) {
            System.out.println("Here is your final scorecard:");
            displaySC();
            System.out.println("Thanks for playing");
            System.exit(0);
            return true;
        }
        return false;
    }

    /**
     * displays the scorecard
     */
    public static void displaySC() {
        int upperScore = 0;
        int lowerScore = 0;
        int bonus = 0;
        int i = 0;

        //upper
        System.out.println("Line          Score");
        System.out.println("-------------------");

        while (cardList.get(i).get(2).equals("u")) {
            System.out.println(cardList.get(i).get(0) + "                " + cardList.get(i).get(3));
            upperScore+= Integer.parseInt(cardList.get(i).get(3));
            i++;
        }

        if (upperScore >= 63) {
            bonus = 35;
        }

        //lower
        System.out.println("-------------------");
        System.out.println("Sub Total        " + upperScore);
        System.out.println("Bonus            " + bonus);
        System.out.println("-------------------");
        System.out.println("Upper Total      " + (upperScore+=bonus) + "\n");

        for (int j = i; j < sizeSC; j++) {
            System.out.println(cardList.get(j).get(0) + "               " + cardList.get(j).get(3));
            lowerScore+= Integer.parseInt(cardList.get(j).get(3));
        }

        System.out.println("-------------------");
        System.out.println("Lower Total      " + (lowerScore));
        System.out.println("-------------------");
        System.out.println("Grand Total      " + (upperScore + lowerScore));
    }

    /**
     * displays the possible lines to choose from
     */
    public static void displayPossLines() {
        String score = "";
        String name = "";
        int size = sizeSC - numY;

        storePossiblePlaces("scorecard.txt");


        //make sure change card list is called before this
        for(int i = 0; i < size; i++ ) {
            name = possList.get(i).get(0);
            score = possList.get(i).get(1);
            System.out.println("Score is " + score + " if you choose the " + name + " line");
        }
    }
}
//TODO: upper scoring and update score card and figure out why its not displaying, also add var to play while lines left
//TODO: make alt if you dont want to see the scorecard
