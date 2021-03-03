package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Game.Game;
import eu.flrkv.wwm.Game.Question;
import eu.flrkv.wwm.Game.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        super("Wer wird Millionär | InGame - @", new Dimension(1300, 700));

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
        autoResizeFrame();


        buildGameWindow();
    }

    private void setWindowProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DO_NOTHING_ON_CLOSE);


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

                JOptionPane.showConfirmDialog(null, "Publikumsjoker gedrückt");
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


        // Antwortenvalidierung
        ActionListener answerValidation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals(currentGame.getCurrentQuestion().getRightAnswer())) {
                    System.out.println("Right Answer!");
                } else {
                    currentGame.lost();
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

    private void buildGameWindow()
    {
        // Geldbetrag für die aktuelle Frage setzen
        currentQuestionMoneyAmount.setText(Utils.getQuestionMoneyAmount(currentGame.getCurrentQuestionNumber()));

        // Frage setzen -> Neue Frage erhalten => Mit bestimmter Schwierigkeit
        currentGame.setCurrentQuestion(currentGame.getQuestionController().getNewQuestion(Utils.getQuestionDifficulty(currentGame.getCurrentQuestionNumber())));
        questionLabel.setText(currentGame.getCurrentQuestion().getQuestion());                      // Label für die Frage überschreiben

        // Antworten mischen
        Question q = currentGame.getCurrentQuestion();
        String[] answers = QuestionController.getMixedAnswerArray(q);

        answerButtons[0].setText(answers[0]);
        answerButtons[1].setText(answers[1]);
        answerButtons[2].setText(answers[2]);
        answerButtons[3].setText(answers[3]);

        setRigthInAnswerSet();
        System.out.println(Arrays.toString(answerSet));
    }



    private void useFiftyFiftyJoker()
    {

    }

    private void usePhoneJoker()
    {

    }

    private void useAudienceJoker()
    {

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
