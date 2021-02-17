package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController implements ActionListener {


    // GUI-Windows
    private MySQLConn sql;
    private MainMenu menu;
    private CreateNewGame newGame;
    private About about;
    private QuestionList questionList;

    public GUIController()
    {
        System.out.println("[INFO] GUIController was initialized successfully!");
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
                Error e = new Error("Die Datenbankverbindung ist ungültig!", "Wer wird Millionär | Datenbankfehler");
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
    private boolean focusFrame(FrameTemplate pFrame)
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
                if (!focusFrame(questionList)) {
                    questionList = new QuestionList();
                }
                break;
            default:
                Utils.consoleLog("WARNING", "Could not assign performed Action to component!");
        }
    }


}
