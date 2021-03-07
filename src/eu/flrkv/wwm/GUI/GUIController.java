package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Game.Game;
import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasse zur Verwaltung der Fenster
 */
public class GUIController implements ActionListener {

    /**
     * Fenster für die Konfiguration der MySQL-Datenbankverbindung
     */
    private MySQLConn sql;

    /**
     * Fenster für das Hauptmenü
     */
    private MainMenu menu;

    /**
     * Fenster um ein neues Spiel zu erstellen
     */
    private CreateNewGame newGame;

    /**
     * Fenster mit Informationen über die Software
     */
    private About about;

    /**
     * Fenster mit der Liste aller Fragen (Fragenverwaltung)
     */
    private QuestionList questionList;

    /**
     * Fenster zum hinzufügen neuer Fragen
     */
    private AddNewQuestion addNewQuestion;

    /**
     * Fenster mit der Liste aller Spielstände (Spielstandverwaltung)
     */
    private GamesList saveGames;

    /**
     * Fenster mit der Liste aller aufgestellten HighScores
     */
    private HighscoresList highscoresList;


    /**
     * Fenster für das aktuelle Spiel
     */
    private GameWindow gameWindow;

    /**
     * Aktuelles Spielobjekt
     */
    private Game game;


    /**
     * Konstruktor der Klasse GUIController
     * Gibt eine Statusmeldung aus
     */
    public GUIController()
    {
        Utils.consoleLog("INFO", "GUIController was initialized successfully!");

    }

    /**
     * Versucht die GUI zu starten.
     */
    public void run()
    {
        if (DatabaseConfiguration.configExists()) {
            if (DatabaseConnection.credentialsValid()) {
                menu = new MainMenu(this);
            } else {
                JOptionPane.showMessageDialog(null , "Die hinterlegte Datenbankverbindung ist ungültig!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                Utils.consoleLog("ERROR", "Could not start game software. Database connection is not functional!");
            }
        } else {
            sql = new MySQLConn(this);
        }
    }

    /**
     * Versucht ein Fenster zu fokussieren.
     * Das übergebene Fenster darf nicht null und nicht geschlossen (disposed) sein.
     *
     * @param pFrame Objekt des zu fokussierenden JFrame/Fenster
     * @return Gibt true zurück, wenn das Fenster erfolgreich fokussiert werden konnte.
     */
    private static boolean focusFrame(JFrame pFrame)
    {
        // Prüfen ob das übergebene Fenster nicht null und nicht geschlossen ist.
        if (pFrame != null && pFrame.isDisplayable()) {
            // Set Frame visible
            pFrame.setVisible(true);

            // Try to focus Frame
            pFrame.requestFocus();
            return true;
        }
        return false;
    }


    /**
     * ActionListener für JButtons usw.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((Component)e.getSource()).getName();
        if (name == null) {
            Utils.consoleLog("ERROR", "ActionListener was set but element has no name.");
            return;
        }
        switch (name) {
            case "SQL_checkSave":
                sql.dispose();
                menu = new MainMenu(this);
                Utils.consoleLog("INFO", "Button 'SQL_checkSave' was pressed!");
                break;
            case "MainMenu_newGame":
                if (!focusFrame(newGame)) {
                    newGame = new CreateNewGame(this);
                }
                break;
            case "MainMenu_about":
                if (!focusFrame(about)) {
                    about = new About(this);
                }
                break;
            case "MainMenu_questionList":
                if (!focusFrame(questionList) && !focusFrame(addNewQuestion)) {
                    questionList = new QuestionList(this);
                }
                break;
            case "QuestionList_addQuestion":
                questionList.dispose();
                if (!focusFrame(addNewQuestion)) {
                    addNewQuestion = new AddNewQuestion(this);
                }
                break;
            case "CreateNewGame_start":
                // Spiel erstellen & laden
                loadGame(new Game(newGame.getGameName(), newGame.getGamerTag()));
                // Fenster zur erstellung des Spiels schließen
                newGame.dispose();
                break;
            case "MainMenu_saveGames":
                if (!focusFrame(saveGames)) {
                    saveGames = new GamesList(this);
                }
                break;
            case "MainMenu_highscores":
                if (!focusFrame(highscoresList)) {
                    highscoresList = new HighscoresList(this);
                }
                break;
            default:
                Utils.consoleLog("WARNING", "Could not assign performed Action to component!");
        }
    }

    /**
     * Lädt das übergebene Spiel
     * Öffnet das Spielfenster und blendet das Hauptmenü aus
     *
     * @param g Zu ladendes Spiel (Spielobjekt der Klasse Game)
     */
    public void loadGame(Game g)
    {
        if (g == null) {
            Utils.consoleLog("ERROR", "Critical error -> Game cannot be null!");
            return;
        }
        this.game = g;
        gameWindow = new GameWindow(this, this.game);
        menu.setVisible(false);
        setGameFrameListener();
    }

    /**
     * Setzt die Listener für das Spielfenster
     * Listener reagiert auf Fenster schließungen
     */
    private void setGameFrameListener()
    {
        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitToMainMenu();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                gameWindow = null;
                game = null;
                menu.setVisible(true);
            }
        });
    }


    /**
     * Zeigt ein Dialogfenster um zurück zum Hauptmenü zu gelangen und das aktuelle Spiel zu beenden.
     */
    public void exitToMainMenu()
    {
        if (game != null && gameWindow != null) {

            // Benutzerentscheidung
            int select = JOptionPane.showConfirmDialog(gameWindow, "Möchten Sie Ihr aktuelles Spiel speichern?", "Wer wird Millionär | Zurück zum Hauptmenü", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            // Spiel speichern
            if (select == JOptionPane.YES_OPTION) {
                if (!game.saveGame()) {
                    if (JOptionPane.showConfirmDialog(gameWindow, "Das Spiel konnte nicht gespeichert werden! Ohne Speichern verlassen?", "Wer wird Millionär | Fehler", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
            }

            // Doch weiter spielen
            if (select == JOptionPane.CANCEL_OPTION) {
                return;
            }

            // Fenster schließen
            gameWindow.dispose();

            // Aktuelles Spiel aus dem SPeicher entfernen
            game = null;

            // Zugehöriges Spielfenster aus dem Speicher entfernen
            gameWindow = null;

            // Hauptmenü anzeigen
            menu.setVisible(true);
        }
    }




}
