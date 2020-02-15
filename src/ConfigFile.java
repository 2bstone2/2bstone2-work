/**
 * This program reads from an input file, can change the input file, then write to the file
 * CPSC 224-01 Spring 2020
 * HW #2
 * No sources to cite;
 *
 * Bailey Stone
 * @author bstone
 * @version v2.0 2/14/2020
 */

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ConfigFile {
    public static ArrayList<Integer> configArr = new ArrayList(3);

    /**
     * Reads from an input file and assigns values to configArr which holds the values to be updated later on
     */
    public static void readFromFile () {
        int i = 0;

        try {
           Scanner inFile = new Scanner(new File("yahtzeeConfig.txt"));
            while (inFile.hasNextLine()) {
                configArr.add(i, Integer.parseInt(inFile.nextLine()));
                i++;
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Writes to the file if the user wants to change the game settings
     */

    public static void writeToFile() {
        try {
            PrintStream outFile = new PrintStream(String.valueOf("yahtzeeConfig.txt"));
            for(int i = 0; i < configArr.size(); i++) {
                outFile.println(configArr.get(i));
            }
            outFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file for writing");
        }
    }

    /**
     * gets the new config settings and calls to write them to the input file
     */
    public static void changeConfig() {
        Scanner configSc = new Scanner(System.in);

        System.out.print("Enter the number of sides on each dice : ");
        configArr.set(0, configSc.nextInt());

        System.out.print("Enter the number of dice in play : ");
        configArr.set(1, configSc.nextInt());

        System.out.print("Enter the number of rolls per hand : ");
        configArr.set(2, configSc.nextInt());

        System.out.print('\n');

        writeToFile();
    }
}
