package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends FrameTemplate {
    private JPanel mainMenu;
    private JButton newGameButton;
    private JButton exitGameButton;
    private JButton aboutButton;
    private JLabel wwmImage;
    private JButton questionListButton;
    private JButton saveGamesButton;
    private JButton highscoresButton;

    private GUIController myController;

    public MainMenu(GUIController pController)
    {
        // Call of the parent constructor
        super("Wer wird Millionär | Hauptmenü", new Dimension(900, 600));

        // Set window controller
        myController = pController;

        this.newGameButton.setName("MainMenu_newGame");
        this.aboutButton.setName("MainMenu_about");
        this.questionListButton.setName("MainMenu_questionList");
        this.saveGamesButton.setName("MainMenu_saveGames");
        this.highscoresButton.setName("MainMenu_highscores");

        // Minimum Size of the window
        this.setMinimumSize(new Dimension(700, 575));

        // Exit program on close
        this.setDefaultCloseOperation(FrameTemplate.EXIT_ON_CLOSE);

        // Set visible
        this.setVisible(true);

        this.add(mainMenu);


        // Exit button Listener -> Exits program if clicked
        exitGameButton.addActionListener(new ActionListener() {

            /**
             * Listener für den Spiel beenden Knopf. Beendet das Programm
             * @param e ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close Program
                Utils.exitProgram(-1);
            }
        });

        aboutButton.addActionListener(myController);
        newGameButton.addActionListener(myController);
        questionListButton.addActionListener(myController);
        saveGamesButton.addActionListener(myController);
        highscoresButton.addActionListener(myController);

    }


    private void createUIComponents() {
        wwmImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
