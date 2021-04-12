package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Game.Game;
import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Question.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Spielfenster
 */
public class GameWindow extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel gamePanel;

    /**
     * JLabel für das Logo
     */
    private JLabel logo;

    /**
     * JLabel zum anzeigen der aktuellen Fragestellung
     */
    private JLabel questionLabel;

    /**
     * Button für die erste Antwortmöglichkeit (A)
     */
    private JButton buttonAnswerA; // 0#

    /**
     * Button für die zweite Antwortmöglichkeit (B)
     */
    private JButton buttonAnswerB; // 1

    /**
     * Button für die dritte Antwortmöglichkeit (C)
     */
    private JButton buttonAnswerC; // 2

    /**
     * Button für die vierte Antwortmöglichkeit (D)
     */
    private JButton buttonAnswerD; // 3

    /**
     * Buttons zum Antworten als Array
     */
    private JButton[] answerButtons = new JButton[4];

    /**
     * Array mit den Wahrheitswerten für die richtige und die falschen Antworten
     */
    private boolean[] answerSet = new boolean[4];

    /**
     * JLabel mit dem Icon für den F&uuml;nfzig-F&uuml;nfzig-Joker
     */
    private JLabel fiftyFiftyJoker;

    /**
     * JLabel mit dem Icon für den Telefon-Joker
     */
    private JLabel phoneJoker;

    /**
     * JLabel mit dem Icon für den Publikums-Joker
     */
    private JLabel audienceJoker;

    /**
     * Button um zum Hauptmen&uuml; zur&uuml;ck zu gelangen
     */
    private JButton mainMenuButton;

    /**
     * Button um bei der letzten gel&ouml;sten Frage das Spiel zu verlassen
     */
    private JButton takeMoneyExitButton;

    /**
     * Button um das Spiel zwischenzuspeichern
     */
    private JButton saveGameButton;

    /**
     * JLabel, welches den Geldwert f&uuml;r die aktuelle Frage anzeigt
     */
    private JLabel currentQuestionMoneyAmount;


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
        setJokerImages();


        setFrameTitle(currentGame.getGamerTag(), currentGame.getGameName(), currentGame.getGameID());

        this.add(gamePanel);

        buildGameWindow();
    }


    /**
     * Setzt die durchkreuzten Logos für die Joker falls diese bereits verwendet wurden
     */
    private void setJokerImages()
    {
        if (currentGame.jokerIsUsed("phone")) phoneJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerPhone_h64.png"));
        if (currentGame.jokerIsUsed("fifty")) fiftyFiftyJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerFiftyFifty_h64.png"));
        if (currentGame.jokerIsUsed("audience")) audienceJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerAudience_h64.png"));
    }

    /**
     * Setzt die Eigenschaften dieses Fensters
     */
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

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));

        audienceJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerAudience_h64.png"));
        fiftyFiftyJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerFiftyFifty_h64.png"));
        phoneJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerPhone_h64.png"));
    }


    /**
     * Initialisiert die EventListener für die Klick-Aktionen der Joker
     */
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

    /**
     * Setzt die EventListener/ActionListener für die Buttons
     */
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
                int confirmExit = JOptionPane.showConfirmDialog(null, "Sind Sie sicher, dass Sie das Spiel mit "+Utils.questioNumbertoString(currentGame.getCurrentQuestionNumber()-1) + " verlassen möchten?", "Wer wird Millionär | Spiel verlassen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirmExit == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Herzlichen Glückwunsch! Sie haben " + Utils.questioNumbertoString(currentGame.getCurrentQuestionNumber()-1) + " erspielt!", "Wer wird Millionär | Spiel verlassen", JOptionPane.INFORMATION_MESSAGE);
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
                            // Simulate being at question 16 to save higscore to 1 Mio
                            currentGame.setCurrentQuestionNumber(currentGame.getCurrentQuestionNumber()+1);
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

    /**
     * Setzt im Array für die Wahrheitswerte der Antworten die richtige Antwort auf true
     */
    private void setRightInAnswerSet()
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
        // Focus auf label
        questionLabel.requestFocus();

        // Geldbetrag für die aktuelle Frage setzen
        currentQuestionMoneyAmount.setText(Utils.questioNumbertoString(currentGame.getCurrentQuestionNumber()));

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

        setRightInAnswerSet();
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
            fiftyFiftyJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerFiftyFifty_h64.png"));
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
        if (JOptionPane.showConfirmDialog(this, "Möchten Sie den Telefon-Joker einsetzen? (65%-Chance)", "Wer wird Millionär | Joker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (getRightAnswerID() == null) {
                Utils.unknownErrorPopup();
                return;
            }
            int rightAnswer = getRightAnswerID();
            char propablyAnswer;
            Random r = new Random();
            if (r.nextInt(101) <= 65) {
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
            phoneJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerPhone_h64.png"));
        }
    }


    /**
     * Publikums-Joker
     * Gibt zu 70% die richtige Antwort als Publikumsbefragung im Dialogfenster wieder
     */
    private void useAudienceJoker() {


        /*
                     / \
                    / _ \
                   | / \ |
                   ||   || _______
                   ||   || |\     \
                   ||   || ||\     \
                   ||   || || \    |
                   ||   || ||  \__/
                   ||   || ||   ||
                    \\_/ \_/ \_//
                   /   _     _   \
                  /               \
                  |    O     O    |
                  |   \  ___  /   |
                 /     \ \_/ /     \
                /  -----  |  --\    \
                |     \__/|\__/ \   |
                \       |_|_|       /
                 \_____       _____/
                       \     /
                       |     |

               Hasi ist fröhlich weil der Joker endlich funktioniert :)
         */


        if (currentGame.jokerIsUsed("audience")) {
            JOptionPane.showMessageDialog(this, "Dieser Joker wurde bereits eingesetzt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Möchten Sie den Publikums-Joker einsetzen? (85%-Chance)", "Wer wird Millionär | Joker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            if (getRightAnswerID() == null) {
                Utils.unknownErrorPopup();
                return;
            }

            int rightAnswer = getRightAnswerID();
            int[] sortedArray = {
                    Utils.getRandomNumber(1000, 750),
                    Utils.getRandomNumber(750, 500),
                    Utils.getRandomNumber(500, 250),
                    Utils.getRandomNumber(250, 0)
            };
            int sumUp = Utils.sumUpArrayVals(sortedArray);
            double[] percentVals = new double[4];

            if (new Random().nextInt(101) <= 85) {
                percentVals[rightAnswer] = sortedArray[0];

                for (int i=1; i < sortedArray.length; i++) {
                    if (i != rightAnswer) {
                        percentVals[i] = sortedArray[i];
                        sortedArray[i] = -1;
                    }
                }
                for (int i=1; i < sortedArray.length; i++) {
                    if (sortedArray[i] != -1) {
                        percentVals[0] = sortedArray[i];
                    }
                }
            } else {
                sortedArray = Utils.shuffleArray(sortedArray);
                for (int i=0; i < sortedArray.length; i++) {
                    percentVals[i] = sortedArray[i];
                }
            }

            currentGame.useJoker("audience");
            audienceJoker.setIcon(new ImageIcon("common/icons/jokers/used_jokerAudience_h64.png"));
            DecimalFormat df = new DecimalFormat("#0.00");
            JOptionPane.showMessageDialog(this, "Ergebnisse der Publikumsbefragung:\n\nA) "+df.format(Utils.calcPercent(percentVals[0], sumUp))+" %\nB) "+df.format(Utils.calcPercent(percentVals[1], sumUp))+" %\nC) "+df.format(Utils.calcPercent(percentVals[2], sumUp))+" %\nD) "+df.format(Utils.calcPercent(percentVals[3], sumUp)) + " %", "Wer wird Millionär | Joker", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Setzt den Fenster-Titel bestehend aus dem Spielernamen und Spielnamen
     * @param pGamerTag Spielernamen
     * @param pGameName Spielname
     * @param pGameID SpielID
     */
    public void setFrameTitle(String pGamerTag, String pGameName, int pGameID)
    {
        this.setTitle("Wer wird Millionär | InGame - " + pGamerTag + "@" + pGameName + " ("+pGameID+")");
    }
}
