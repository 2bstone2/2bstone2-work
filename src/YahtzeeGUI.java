import javax.swing.*;
import java.awt.*;


public class YahtzeeGUI extends JFrame{
    ConfigMenu configMenu;
    //JFrame configFrame;
    String numDieStr = Integer.toString(Hand.numDie);
    String numSidesStr = Integer.toString(Dice.numSides);

    public YahtzeeGUI() {
        super("Yahtzee");
        //this.setLayout(new BorderLayout());
       //this.setLocation(400, 400);
        //this.setSize(600,500);
        //this.getContentPane().setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        /*welcomeLbl = new JLabel("Welcome to Yahtzee! You are currently playing with " + numDieStr + " " + numSidesStr + "-sided Dice.\n" + "Would you like to change this?");

        configPanel.add(welcomeLbl);
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS));
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        configPanel.add(buttonPanel, BoxLayout.PAGE_AXIS);
        this.add(configPanel, BorderLayout.CENTER);

        //this.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this.pack();
        //configPanel.add(new JButton ("Yes"));
        //configPanel.add(new JButton("No"));
        //this.setVisible(true);*/
        //configFrame = new JFrame();
       configMenu = new ConfigMenu();

    }

    static class ConfigMenu extends JFrame{
        JFrame configFrame;
        JOptionPane welcomePane;
        JOptionPane changePane;
        JLabel welcomeLbl;
        String numDieStr = Integer.toString(Hand.numDie);
        String numSidesStr = Integer.toString(Dice.numSides);
        int configAns;
        JComboBox numDieCB;
        JComboBox numSidesCB;
        //JLabel welcomeStr = new JLabel("Welcome to Yahtzee! You are currently playing with " + numDieStr + " " + numSidesStr + "-sided Dice.\n" + "Would you like to change this?");

        public ConfigMenu() {
            super("Settings");
            configFrame = new JFrame();
            welcomePane = new JOptionPane();
            //configFrame.setLayout(new Border());
            configFrame.setPreferredSize(new Dimension(400, 300));
            configFrame.setLocationRelativeTo(null);
            configFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            configFrame.setVisible(true);
            welcomeLbl = new JLabel("Welcome to Yahtzee! You are currently playing with " + numDieStr + " " + numSidesStr + "-sided Dice.\n" + "Would you like to change this?");

            configAns = welcomePane.showConfirmDialog(configFrame, welcomeLbl, "Yahtzee settings", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

            readConfigAns(configAns);
            //if (configAns == JOptionPane.YES_OPTION) {

            //}

            //this.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
            //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
        }
        public void readConfigAns(int configAns) {
            if (this.configAns == JOptionPane.YES_OPTION) {
                //call card change to changePane
                makeChangePane();
            }
        }

        public void makeChangePane() {
            String[] numDieOptions = {"5", "6", "7"};
            String[] numSidesOptions = {"6", "8", "12"};
            String numDieStr;
            String numSidesStr;

            numDieCB = new JComboBox(numDieOptions);
            numSidesCB = new JComboBox(numSidesOptions);

            changePane = new JOptionPane();

            changePane.showMessageDialog(configFrame, numDieCB, "Select the number of die in a hand ", JOptionPane.PLAIN_MESSAGE);
            changePane.showMessageDialog(configFrame, numSidesCB, "Select the number of sides on each die ", JOptionPane.PLAIN_MESSAGE);

            //numDieStr = numDieCB.getSelectedItem();
            int numDieInt = (int) numDieCB.getSelectedItem();
            int numSidesInt = (int) numSidesCB.getSelectedItem();

            Hand.numDie = numDieInt;
            Dice.numSides = numSidesInt;

            //Hand.numDie = (int) numDieCB.getSelectedItem();
            //Dice.numSides = (int) numSidesCB.getSelectedItem();

        }
    }

    /*public void showConfigPane() {
        YahtzeeGUI configPane = mainPanel.getContentPane(mainPanel);
        int config = showConfirmDialog(pane, "Welcome to Yahtzee! You are currently playing with " + Hand.numDie + " " + Dice.numSides + "-sided Dice.\n" + "Would you like to change this?", "Yahtzee", YES_NO_OPTION, PLAIN_MESSAGE);

    }*/

    /*public void GUIrun(ConfigScreen configPane) {

        this.config.changeConfig(mainPanel);
    }*/
}

/*class ConfigScreen extends JOptionPane{

    public ConfigScreen (JOptionPane pane) {
        int config = showConfirmDialog(pane, "Welcome to Yahtzee! You are currently playing with " + Hand.numDie + " " + Dice.numSides + "-sided Dice.\n" + "Would you like to change this?", "Yahtzee", YES_NO_OPTION, PLAIN_MESSAGE);

        if (config == YES_OPTION) {
            //call changeConfig window
            changeConfig(pane);
        }

    }

    public void changeConfig(JOptionPane pane) {
        int[] numDieOptions = {5, 6, 7};
        int[] numSidesOptions = {6, 8, 12};

        JLabel numDieLabel = new JLabel("Pick the number of dice you want in");
        JComboBox<Integer> numDieCombo = new JComboBox<Integer>();
        numDieCombo.addItem(5);
        numDieCombo.addItem(6);
        numDieCombo.addItem(7);


       /* int numDieOps = (int) showInputDialog(null, "Pick the number of dice you want in/n" + "each hand from the drop down menu below./n",
                "Change Yahtzee Settings", QUESTION_MESSAGE, null, numDieOptions, numDieOptions[0]);// Use
        System.out.println(numDieOps);
    }
} */

