import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

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
        ArrayList<JRadioButton> possLines; //TODO remember must have radio button to select line
        ButtonGroup possLinesGroup;
        JButton possLinesChoiceButton;

        MainFrame() {
            //stand up frame
            mainFrame = new JFrame();
            mainFrame.pack();
            mainFrame.setTitle("Yahtzee");
            mainFrame.setLayout(new BorderLayout());
            mainFrame.setSize(800,700);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setResizable(false);

            possLinesPane = new JPanel();
            possLinesPane.setLayout(new BoxLayout(possLinesPane, BoxLayout.PAGE_AXIS));

            new Hand();
            Hand.popNewHand();
            loadImageFrame();
            //populatePossLines();
            //mainFrame.add(dicePanel, BorderLayout.CENTER);

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
                Dice.rollDie();
                System.out.println("rollllll");
                loadImageFrame();
                //mainFrame.add(dicePanel, BorderLayout.CENTER);
                //Scorecard.storePossiblePlaces();
                populatePossLines();
                //mainFrame.setVisible(true);
                //maybe become unclickable or change to next round after 3rd turn.
                    });
            mainFrame.add(rollButton, BorderLayout.PAGE_END);
            mainFrame.add(possLinesPane, BorderLayout.LINE_END);
            System.out.println("possLinesPane");

            new Scorecard(Dice.numSides);
            populatePossLines();
            //Scorecard.readScoreFile("scorecard.txt");
            //Scorecard.storePossiblePlaces();
            //populatePossLines();
            //mainFrame.add(possLinesPane, BorderLayout.LINE_END);

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
            JToggleButton imageLbl;
            //Boolean[] selectedDie = new Boolean[Hand.numDie];
            String[] names = new String[Hand.numDie];

            seeSCButton = new JButton();
            dicePanel = new JPanel();

           // dicePanel.removeAll();
            for (int i = 0; i < Hand.numDie; i++) {
                imageName = getImageName(i);
                names[i] = imageName;
                dicePic = new ImageIcon(imageName);
                imageLbl = new JToggleButton(dicePic);
                if (checkIsToggled(imageLbl, i)) {
                    //imageLbl.setIcon();
                }
                dicePanel.add(imageLbl);
                //imageLbl.addActionListener();
            }
            mainFrame.add(dicePanel, BorderLayout.CENTER);
           // mainFrame.setVisible(true);
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
            Box possLinesBox = Box.createVerticalBox();
            JLabel scorecardLbl = new JLabel("Your Possible Scores: ");
            //BorderLayout layout = (BorderLayout)mainFrame.getLayout();
            possLinesChoiceButton = new JButton("Choose line");
            possLines = new ArrayList<>();
            possLines.ensureCapacity(Scorecard.possList.size());
            possLinesGroup = new ButtonGroup();
            //possLinesPane.removeAll();
           // mainFrame.remove(layout.getLayoutComponent(BorderLayout.LINE_END));
            Scorecard.storePossiblePlaces();
            possLinesPane.removeAll();
            for(int i = 0; i < Scorecard.possList.size(); i++) {
                possLines.add(new JRadioButton("Score is " + Scorecard.possList.get(i).get(1) + " if you choose the " + Scorecard.possList.get(i).get(0), false));
                possLinesBox.add(possLines.get(i));
                possLinesGroup.add(possLines.get(i));
            }

            possLinesPane.add(scorecardLbl);
            possLinesPane.add(possLinesBox);
            possLinesPane.add(possLinesChoiceButton);

            possLinesChoiceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scoreChosenLine(possLinesGroup);
                    populatePossLines();
                }
            });

            possLinesPane.repaint();
            mainFrame.setVisible(true);
        }

        void scoreChosenLine(ButtonGroup buttonG) {
            ButtonGroup buttonGroup = buttonG;
            Enumeration buttons;
            AbstractButton absButton;
            int i = 0;
            String lineName;

            //iterate through button group (probs pass in) and look for selected one break once found and pass in i
            buttonG.getSelection();
            buttons = buttonGroup.getElements();

            while (buttons.hasMoreElements()) {
                absButton = (AbstractButton)buttons.nextElement();

                //locate index then call writeSC by passing extracted string name  in
                if (absButton.isSelected()) {
                    break;
                }
                i++;
            }

            System.out.println(Scorecard.possList.size() + " " + i);

            lineName = Scorecard.possList.get(i).get(0);
            Scorecard.changeCardList(lineName);
            //mainFrame.remove(layout.getLayoutComponent(BorderLayout.LINE_END));
            //populatePossLines();
        }

        boolean checkIsToggled(JToggleButton tButton, int i) {
            JToggleButton togBut = tButton;
            int idx = i;
            char[] charArr = Hand.keepStr.toCharArray();

            if (togBut.isSelected()) {
                charArr[idx] = 'y';
                return true;
            }
            else {
                charArr[idx] = 'n';
            }

            Hand.keepStr = new String(charArr);
            return false;
        }
    }
}



