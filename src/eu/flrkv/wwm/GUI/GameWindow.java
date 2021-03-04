package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Game.Game;
import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Question.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.*;

public class GameWindow extends FrameTemplate {

    // Base panel
    private JPanel panel1;

    // Logo
    private JLabel logo;

    // Question Label
    private JLabel questionLabel;

    // Answer Buttons
    private JButton buttonAnswerA; // 0
    private JButton buttonAnswerB; // 1
    private JButton buttonAnswerC; // 2
    private JButton buttonAnswerD; // 3
    private JButton[] answerButtons = new JButton[4];
    private boolean[] answerSet = new boolean[4];

    // Jokers
    private JLabel fiftyFiftyJoker;
    private JLabel phoneJoker;
    private JLabel audienceJoker;

    // Main Menu Button
    private JButton mainMenuButton;

    // Label that displays the current amount question
    private JLabel currentQuestionMoneyAmount;
    private JButton takeMoneyExitButton;
    private JButton saveGameButton;


    /**
     * GUIController für dieses Fenster.
     */
    private final GUIController myController;

    /**
     * Aktuelles Spiel
     */
    private final Game currentGame;


    /**
     * GameWindow Konstuktor
     * @param pController GUIController für dieses Fenster
     * @param pGame ZU verwendendes Spiel
     */
    public GameWindow(GUIController pController, Game pGame) {
        super("Wer wird Millionär | InGame - @", new Dimension(1300, 800));

        // Attribute setzen
        myController = pController;
        currentGame = pGame;

        // Debug
        Utils.consoleLog("INFO", "Game window initialized!");


        setWindowProperties();
        initJokerListeners();
        initButtonListeners();


        setFrameTitle(currentGame.getGamerTag(), currentGame.getGameName(), currentGame.getGameID());

        this.add(panel1);

        buildGameWindow();
    }

    private void setWindowProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(new Dimension(1300, 800));


        answerButtons[0] = buttonAnswerA;
        answerButtons[1] = buttonAnswerB;
        answerButtons[2] = buttonAnswerC;
        answerButtons[3] = buttonAnswerD;

    }

    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));

        audienceJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerAudience_h64.png"));
        fiftyFiftyJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerFiftyFifty_h64.png"));
        phoneJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerPhone_h64.png"));
    }


    private void initJokerListeners()
    {
        audienceJoker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                useAudienceJoker();
            }
        });
        phoneJoker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                usePhoneJoker();
            }
        });
        fiftyFiftyJoker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                useFiftyFiftyJoker();
            }
        });

    }


    private void initButtonListeners()
    {
        // MainMenu Button
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myController.exitToMainMenu();
            }
        });

        // Spiel speichern
        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentGame.saveGame()) {
                    JOptionPane.showMessageDialog(null, "Spiel erfolgreich gespeichert!", "Wer wird Millionär | Spiel gespeichert", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Das Spiel konnte nicht gespeichert werden!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        // Mit bestimmter Geldsumme das Spiel verlassen
        takeMoneyExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie das Spiel mit "+Utils.getQuestionMoneyAmount(currentGame.getCurrentQuestionNumber()-1) + " verlassen möchten?", "Wer wird Millionär | Spiel verlassen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirmExit == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Herzlichen Glückwunsch! Sie haben " + Utils.getQuestionMoneyAmount(currentGame.getCurrentQuestionNumber()-1) + " erspielt!", "Wer wird Millionär | Spiel verlassen", JOptionPane.INFORMATION_MESSAGE);
                    int bestlistSelect = JOptionPane.showConfirmDialog(null, "Soll dieser Spielstand in die Bestenliste aufgenommen werden?", "Wer wird Millionär | Bestenliste", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (bestlistSelect == JOptionPane.YES_OPTION) {
                        if (!currentGame.addToHighscores(false)) {
                            JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Highscores!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (currentGame.deleteGame()) {
                        dispose();
                    }
                }
            }
        });

        // Antwortenvalidierung
        ActionListener answerValidation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals(currentGame.getCurrentQuestion().getRightAnswer())) {

                    if (currentGame.getCurrentQuestionNumber() < 15) {
                        JOptionPane.showMessageDialog(null, "Richtige Antwort!\n\nWeiter geht's mit Frage " + (currentGame.getCurrentQuestionNumber()+1), "Wer wird Millionär | Richtige Antwort!", JOptionPane.INFORMATION_MESSAGE);
                        currentGame.nextQuestion(false);
                        buildGameWindow();
                    } else {
                        JOptionPane.showMessageDialog(null, "Du hast die eine Millionen geknackt!", "Wer wird Millionär | 1 Million!", JOptionPane.INFORMATION_MESSAGE);
                        if (JOptionPane.showConfirmDialog(null, "Soll dieser Spielstand in die Bestenliste aufgenommen werden?", "Wer wird Millionär | Bestenliste", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                            if (!currentGame.addToHighscores(false)) {
                                JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Highscores!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        currentGame.deleteGame();
                        dispose();
                    }
                } else {
                    // Spiel verloren
                    currentGame.lost();
                    dispose();
                }
            }
        };
        buttonAnswerA.addActionListener(answerValidation);
        buttonAnswerB.addActionListener(answerValidation);
        buttonAnswerC.addActionListener(answerValidation);
        buttonAnswerD.addActionListener(answerValidation);
    }

    private void setRigthInAnswerSet()
    {
        answerSet[0] = buttonAnswerA.getText().equals(currentGame.getCurrentQuestion().getRightAnswer());
        answerSet[1] = buttonAnswerB.getText().equals(currentGame.getCurrentQuestion().getRightAnswer());
        answerSet[2] = buttonAnswerC.getText().equals(currentGame.getCurrentQuestion().getRightAnswer());
        answerSet[3] = buttonAnswerD.getText().equals(currentGame.getCurrentQuestion().getRightAnswer());
    }


    /**
     * Array Position der richtigen Frage bekommen.
     * @return Gibt die Position der Richtigen Antwort zurück
     */
    private Integer getRightAnswerID()
    {
        for (int j=0; j < answerSet.length; j++){
            if(answerSet[j]) return j;
        }
        return null;
    }

    /**
     * Spielfenster "bauen"
     * Elemente für die aktuelle Frage und Punktestand setzen
     */
    private void buildGameWindow()
    {
        // Geldbetrag für die aktuelle Frage setzen
        currentQuestionMoneyAmount.setText(Utils.getQuestionMoneyAmount(currentGame.getCurrentQuestionNumber()));

        // Label für die Frage überschreiben
        questionLabel.setText(currentGame.getCurrentQuestion().getQuestion());

        // Antworten mischen
        Question q = currentGame.getCurrentQuestion();
        String[] answers = QuestionController.getMixedAnswerArray(q);

        // Buttons ggf. wieder aktivieren
        buttonAnswerA.setEnabled(true);
        buttonAnswerB.setEnabled(true);
        buttonAnswerC.setEnabled(true);
        buttonAnswerD.setEnabled(true);

        // Antworten setzen
        answerButtons[0].setText(answers[0]);
        answerButtons[1].setText(answers[1]);
        answerButtons[2].setText(answers[2]);
        answerButtons[3].setText(answers[3]);

        setRigthInAnswerSet();
        System.out.println(Arrays.toString(answerSet));
    }


    /**
     * Fifty-Fifty Joker
     * Deaktiviert zwei Buttons mit falschen Antworten
     */
    private void useFiftyFiftyJoker()
    {
        if (currentGame.jokerIsUsed("fifty")) {
            JOptionPane.showMessageDialog(this, "Dieser Joker wurde bereits eingesetzt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Möchten Sie den Fünfzig-Fünfzig-Joker einsetzen?", "Wer wird Millionär | Joker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (getRightAnswerID() == null) {
                Utils.unknownErrorPopup();
                return;
            }
            int rightAnswer = getRightAnswerID();

            Random r = new Random();
            int rINT;

            int buttonsDisabled = 0;
            int replace = 54;
            while (buttonsDisabled < 2) {
                rINT = r.nextInt(4);
                while (rINT == rightAnswer || rINT == replace) {
                    rINT = r.nextInt(4);
                }
                answerButtons[rINT].setEnabled(false);
                System.out.println(answerButtons[rINT].getText());
                replace = rINT;
                buttonsDisabled++;
            }
            currentGame.useJoker("fifty");
        }
    }

    /**
     * Telefon-Joker
     * Gibt zu 60% die richtige Antwort als Dialogfenster wieder
     */
    private void usePhoneJoker()
    {
        if (currentGame.jokerIsUsed("phone")) {
            JOptionPane.showMessageDialog(this, "Dieser Joker wurde bereits eingesetzt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Möchten Sie den Telefon-Joker einsetzen?", "Wer wird Millionär | Joker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (getRightAnswerID() == null) {
                Utils.unknownErrorPopup();
                return;
            }
            int rightAnswer = getRightAnswerID();
            char propablyAnswer;
            Random r = new Random();
            if (r.nextInt(101) <= 60) {
                System.out.println(rightAnswer);
                propablyAnswer = Utils.buttonNoToChar(rightAnswer);
            } else {
                int wrong = r.nextInt(3);
                while (wrong == rightAnswer) {
                    wrong = r.nextInt(4);
                }
                propablyAnswer = Utils.buttonNoToChar(wrong);
            }
            JOptionPane.showMessageDialog(this, "Ich denke es ist Antwort " + propablyAnswer + ")", "Wer wird Millionär | Joker", JOptionPane.INFORMATION_MESSAGE);
            currentGame.useJoker("phone");
        }
    }



    private void useAudienceJoker() {
        if (currentGame.jokerIsUsed("audience")) {
            JOptionPane.showMessageDialog(this, "Dieser Joker wurde bereits eingesetzt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Möchten Sie den Publikums-Joker einsetzen?", "Wer wird Millionär | Joker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (getRightAnswerID() == null) {
                Utils.unknownErrorPopup();
                return;
            }


            Random r = new Random();
            int[] sortedArray = {
                    Utils.getRandomNumber(1000, 750),
                    Utils.getRandomNumber(750, 500),
                    Utils.getRandomNumber(500, 250),
                    Utils.getRandomNumber(250, 0)
            };
            double[] percentVals = new double[4];

            // 66% Chance
            if (r.nextInt(3) > 0) {
                ArrayList<Integer> used = new ArrayList<Integer>();
                percentVals[getRightAnswerID()] = sortedArray[0];
                used.add(getRightAnswerID());


            }



            currentGame.useJoker("audience");
            DecimalFormat df = new DecimalFormat("#0.00");
            JOptionPane.showMessageDialog(this, "Ergebnisse der Publikumsbefragung:\n\nA) "+df.format(Utils.calcPercent(percentVals[0], Utils.sumUpArrayVals(sortedArray)))+" %\nB) "+df.format(Utils.calcPercent(percentVals[1], Utils.sumUpArrayVals(sortedArray)))+" %\nC) "+df.format(Utils.calcPercent(percentVals[2], Utils.sumUpArrayVals(sortedArray)))+" %\nD) "+df.format(Utils.calcPercent(percentVals[3], Utils.sumUpArrayVals(sortedArray))) + " %");
        }
    }




    public void setFrameTitle(String pGamerTag, String pGameName, int pGameID)
    {
        this.setTitle("Wer wird Millionär | InGame - " + pGamerTag + "@" + pGameName + " ("+pGameID+")");
    }

    private void autoResizeFrame()
    {
        this.pack();
        this.setMinimumSize(new Rectangle(getBounds()).getSize());
    }
}
