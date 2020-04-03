import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class YahtzeeGUI extends JFrame{
    ConfigMenu configMenu;

    public YahtzeeGUI() {
        super("Yahtzee");
        configMenu = new ConfigMenu();
    }

    static class ConfigMenu extends JFrame{
        JFrame configFrame;
        MainFrame mainFrame;
        JOptionPane welcomePane;
        JLabel welcomeLbl;
        String numDieStr = Integer.toString(Hand.numDie);
        String numSidesStr = Integer.toString(Dice.numSides);
        JOptionPane changePane;

        int configAns;
        JComboBox numDieCB;
        JComboBox numSidesCB;;

        public ConfigMenu() {
            super("Settings");
            configFrame = new JFrame();
            welcomePane = new JOptionPane();
            welcomeLbl = new JLabel("Welcome to Yahtzee! You are currently playing with " + numDieStr + " " + numSidesStr + "-sided Dice.\n" + "Would you like to change this?");

            configFrame.setPreferredSize(new Dimension(400, 300));
            configFrame.setLocationRelativeTo(null);
            configFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


            configAns = welcomePane.showConfirmDialog(configFrame, welcomeLbl, "Yahtzee settings", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            configFrame.setVisible(true);
            readConfigAns(configAns);
            this.pack();
        }
        public void readConfigAns(int configAns) {
            if (this.configAns == JOptionPane.YES_OPTION)
                makeChangePane();

            mainFrame = new MainFrame();
        }

        public void makeChangePane() {
            String[] numDieOptions = {"5", "6", "7"};
            String[] numSidesOptions = {"6", "8", "12"};

            numDieCB = new JComboBox(numDieOptions);
            numSidesCB = new JComboBox(numSidesOptions);
            changePane = new JOptionPane();

            //TODO: Make Title a label (maybe add label, cb, add second label, then second cb
            changePane.showMessageDialog(configFrame, numDieCB, "Select the number of die in a hand ", JOptionPane.PLAIN_MESSAGE);
            changePane.showMessageDialog(configFrame, numSidesCB, "Select the number of sides on each die ", JOptionPane.PLAIN_MESSAGE);

            Hand.numDie = Integer.parseInt((String) numDieCB.getItemAt(numDieCB.getSelectedIndex()));
            Dice.numSides = Integer.parseInt((String) numSidesCB.getItemAt(numSidesCB.getSelectedIndex()));

        }
    }

    static class MainFrame extends JFrame {
        JFrame mainFrame;
        JPanel mainPanel;
        JPanel dicePanel;
        JPanel SCPanel;
        JButton seeSCButton;
        JFrame SCFrame;
        JTextArea scorelines;
        JButton rollButton;
        JPanel possLinesPane; //TODO
        JRadioButton possLines; //TODO remember must have radio button to select line
        ButtonGroup possLinesGroup;

        MainFrame() {
            //stand up frame
            mainFrame = new JFrame();
            mainFrame.pack();
            mainFrame.setTitle("Yahtzee");
            mainFrame.setLayout(new BorderLayout());
            mainFrame.setSize(800,700);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            new Hand();
            Hand.popNewHand();
            loadImageFrame();
            mainFrame.add(dicePanel, BorderLayout.CENTER);

            seeSCButton = new JButton("See your Scorecard"); //just do a little dialog box
            seeSCButton.addActionListener((ActionEvent e) -> {
                SCFrame = new JFrame("Your Scorecard");
                SCFrame.pack();
                SCFrame.setSize(170,550);
                SCFrame.setLocationRelativeTo(null);
                SCFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                SCFrame.setVisible(true);

                loadSC();
                SCFrame.add(scorelines, BorderLayout.CENTER);

            });
            mainFrame.add(seeSCButton, BorderLayout.PAGE_START);

            rollButton = new JButton("Roll");
            rollButton.addActionListener((ActionEvent e) -> {
                //roll functionality calls and changing of die

                //maybe become unclickable or change to next round after 3rd turn.
                    });
            mainFrame.add(rollButton, BorderLayout.PAGE_END);

            new Scorecard(Dice.numSides);
            populatePossLines();
            mainFrame.add(possLinesPane, BorderLayout.LINE_END);


            mainFrame.setVisible(true);
        }

        String getImageName(int idx) {
            int dieIdx = idx;
            String diceVal = Integer.toString(Hand.dieArr.get(dieIdx));
            String imageStr;

            imageStr = "Dice_" + diceVal + ".jpg";

            return imageStr;
        }

        private void loadImageFrame() {
            String imageName;
            ImageIcon dicePic;
            JLabel imageLbl;

            seeSCButton = new JButton();
            dicePanel = new JPanel();

            for (int i = 0; i < Hand.numDie; i++) {
                imageName = getImageName(i);
                System.out.println(imageName);
                dicePic = new ImageIcon(imageName);
                imageLbl = new JLabel(dicePic, JLabel.CENTER);
                dicePanel.add(imageLbl);
            }
        }

        private void loadSC() {
            int i = 0;
            int upperScore = 0;
            int lowerScore = 0;
            int bonus = 0;

            //upper
            scorelines = new JTextArea(Scorecard.sizeSC, 20);
            scorelines.setEditable(false);
            scorelines.append("Line                         Score\n");
            scorelines.append("--------------------\n");
            while (Scorecard.cardList.get(i).get(2).equals("u")) {
                scorelines.append(Scorecard.cardList.get(i).get(0) + "                                    " + Scorecard.cardList.get(i).get(3) + "\n");
                upperScore+= Integer.parseInt(Scorecard.cardList.get(i).get(3));
                i++;
            }

            if (upperScore >= 63) {
                bonus = 35;
            }

            //lower
            scorelines.append("--------------------" + "\n");
            scorelines.append("Sub Total                       " + upperScore + "\n");
            scorelines.append("Bonus                            " + bonus + "\n");
            scorelines.append("-------------------" + "\n");
            scorelines.append("Upper Total                    " + (upperScore+=bonus) + "\n \n");

            for (int j = i; j < Scorecard.sizeSC; j++) {
                scorelines.append(Scorecard.cardList.get(j).get(0) + "                                   " + Scorecard.cardList.get(j).get(3) + "\n");
                lowerScore+= Integer.parseInt(Scorecard.cardList.get(j).get(3));
            }

            scorelines.append("--------------------" + "\n");
            scorelines.append("Lower Total                    " + (lowerScore) + "\n");
            scorelines.append("--------------------" + "\n");
            scorelines.append("Grand Total                    " + (upperScore + lowerScore) + "\n");

        }

        private void populatePossLines() {
            possLinesGroup = new ButtonGroup();
            possLinesPane = new JPanel();

            possLines = new JRadioButton("Score is " + Scorecard.possList.get(0).get(1) + " if you choose the " + Scorecard.possList.get(0).get(0), true);
            possLinesGroup.add(possLines);
            possLinesPane.add(possLines);

            for(int i = 1; i < Scorecard.possList.size(); i++) {
                possLines = new JRadioButton("Score is " + Scorecard.possList.get(i).get(1) + " if you choose the " + Scorecard.possList.get(i).get(0), false);
                possLinesGroup.add(possLines);
                possLinesPane.add(possLines);
            }

            //possLinesPane.add(possLinesGroup);
        }
    }
}



