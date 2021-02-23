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

public class GUIController implements ActionListener {


    // GUI-Windows
    private MySQLConn sql;
    private MainMenu menu;
    private CreateNewGame newGame;
    private About about;
    private QuestionList questionList;
    private AddNewQuestion addNewQuestion;


    // Ingame situation
    private GameWindow gameWindow;
    private Game game;

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
    private static boolean focusFrame(FrameTemplate pFrame)
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
                // Neues Spielfenster
                gameWindow = new GameWindow(this);

                // Neues Spiel erstellen & Spielfenster dafür verwenden
                game = new Game(gameWindow, newGame.getGameName(), newGame.getGamerTag());

                // Event bei Schließung des Spielfensters setzen
                setWindowListener();
                menu.setVisible(false);
                newGame.dispose();
                break;
            default:
                Utils.consoleLog("WARNING", "Could not assign performed Action to component!");
        }
    }

    private void setWindowListener()
    {
        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(gameWindow, "Zum Hauptmenü zurückkehren?", "Wer wird Millionär | Zurück zum Hauptmenü", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    gameWindow.dispose();
                    game = null;
                    gameWindow = null;
                    menu.setVisible(true);
                }
            }
        });
    }




}
