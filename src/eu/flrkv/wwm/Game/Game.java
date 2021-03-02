package eu.flrkv.wwm.Game;

import eu.flrkv.wwm.Exceptions.GameNotFoundException;
import eu.flrkv.wwm.GUI.GameWindow;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game {

    /**
     * Unique GameID
     */
    private int gameID;

    /**
     * InGame Name des aktuellen Spielers
     */
    private String gamerTag;

    /**
     * Nummer der akteullen Frage (1-15)
     */
    private int currentQuestionNumber;

    /**
     * ID der Frage in der Datenbank
     */
    private int currentQuestionID;

    /**
     * Name des aktuellen Spiels
     */
    private String gameName;

    /**
     * Fragen Controller
     */
    private QuestionController questionController;

    /**
     * Spielfenster
     */
    private GameWindow gameWindow;

    /**
     * Konstruktor um ein bereits vorhandenes Spiel fortzuführen.
     * @param pGameID Unique ID des Spiels, welches fortgeführt werden soll
     */
    public Game(GameWindow pGameWindow, int pGameID) throws GameNotFoundException {
        if (gameExists(pGameID)) {
            this.gameID = pGameID;
        } else {
            throw new GameNotFoundException("A Game with that ID could not be found!");
        }
    }

    /**
     * Konstruktor um ein neues Spiel zu starten.
     * @param pGameName Name des Spielstandes
     * @param pGamerTag Name des Spielers
     */
    public Game(GameWindow pGameWindow, String pGameName, String pGamerTag)
    {

    }

    /**
     * Prüft ob ein Spiel existiert.
     * @param pGameID Zu überprüfendes SaveGame
     * @return Gibt true zurück, sollte das Spiel existieren
     */
    public static boolean gameExists(int pGameID)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_savedGames WHERE ID = ?");
            ps.setInt(1, pGameID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Utils.consoleLog("INFO", "A Game with the ID '"+pGameID+"' does not exist.");
        }
        return false;
    }

    /**
     * Holt die ID's aller bereits benutzen Fragen für den Spielstand.
     * @return Gibt die IDs (integer) als Array zurück.
     */
    public int[] getIDsOfUsedQuestions()
    {
        String allIDS = "1,9,121,1854,15481,1815"; // DUMMY
        String[] f = allIDS.split(",");

        int[] i = new int[f.length];
        for(int m = 0; m < f.length; m++) {
            i[m] = Integer.parseInt(f[m]);
        }
        return i;
    }


    /**
     * Gibt die ID der aktuellen Frage zurück.
     * @return ID der aktuellen Frage als Integer
     */
    public int getCurrentQuestionID()
    {
        return this.currentQuestionID;
    }

    /**
     * Gibt den Namen des aktuellen Spielers zurück.
     * @return Name des Spielers als String
     */
    public String getGamerTag()
    {
        return this.gamerTag;
    }

    /**
     * Gibt den Namen des aktuellen Spielstands zurück
     * @return Name des Spiels als String
     */
    public String getGameName()
    {
        return this.gameName;
    }

    /**
     * Gibt die Nummer der aktuellen Frage zurück.
     * Fragen von (1-15)
     * @return Nummer der aktuellen Frage als Integer
     */
    public int getCurrentQuestionNumber()
    {
        return this.currentQuestionNumber;
    }
}
