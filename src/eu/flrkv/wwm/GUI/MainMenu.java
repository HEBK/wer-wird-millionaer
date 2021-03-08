package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame f&uuml;r das Hauptmen&uuml;
 */
public class MainMenu extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel mainMenu;

    /**
     * JLabel für das Logo
     */
    private JLabel wwmImage;

    /**
     * Button um ein neues Spiel zu starten
     */
    private JButton newGameButton;

    /**
     * Button um das Spiel zu beenden
     */
    private JButton exitGameButton;

    /**
     * Button um die Spielinformationen anzuzeigen
     */
    private JButton aboutButton;

    /**
     * Button um die Fragenliste zu &ouml;ffnen
     */
    private JButton questionListButton;

    /**
     * Button um die Spielst&auml;nde anzuzeigen
     */
    private JButton saveGamesButton;

    /**
     * Button um die Bestenliste zu &ouml;ffnen
     */
    private JButton highscoresButton;

    /**
     * GUIController für dieses Fenster
     */
    private GUIController myController;

    /**
     * Konstruktor der Klasse MainMenu
     * @param pController GUIController für dieses Fenster
     */
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

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        wwmImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
