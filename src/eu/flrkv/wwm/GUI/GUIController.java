package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController implements ActionListener {


    // GUI-Windows
    private MySQLConn sql = new MySQLConn(this);
    private MainMenu menu = new MainMenu(this);
    private CreateNewGame newGame;
    private About about;
    private QuestionList questionList;

    public GUIController()
    {
        System.out.println("[INFO] GUIController was initialized successfully!");
    }

    public void run()
    {
        if (DatabaseConfiguration.configExists()) {
            if (DatabaseConnection.credentialsValid()) {
                menu.setVisible(true);
            } else {
                Error e = new Error("Die Datenbankverbindung ist ungültig!", "Wer wird Millionär | Datenbankfehler");
                Utils.consoleLog("ERROR", "Could not start game software. Database connection is not functional!");
            }
        } else {
            sql.setVisible(true);
        }
    }


    /**
     *
     * @param e
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
                sql.setVisible(false);
                menu.setVisible(true);
                Utils.consoleLog("INFO", "Button 'SQL_checkSave' was pressed!");
                break;
            case "MainMenu_newGame":
                newGame = new CreateNewGame(this);
                break;
            case "MainMenu_about":
                about = new About(this);
                break;
            case "MainMenu_questionList":
                questionList = new QuestionList();
                break;
            default:
                Utils.consoleLog("WARNING", "Could not assign performed Action to component!");
        }
    }
}
