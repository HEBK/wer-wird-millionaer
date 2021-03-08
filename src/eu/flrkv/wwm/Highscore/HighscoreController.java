package eu.flrkv.wwm.Highscore;

import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Klasse zum Verwalten der Eintr&auml;ge der Bestenliste
 */
public class HighscoreController {

    /**
     * F&uuml;gt einen Highscore der Bestenliste hinzu
     * @param pGamerTag Spielername
     * @param pGameName Spielname
     * @param solvedQuestions Anzahl der gel&ouml;sten Fragen
     * @param usedJokersCount Anzahl der eingesetzten Joker
     * @return Gibt true zurück wenn der Highscore erstellt wurde
     */
    public static boolean addHighscore(String pGamerTag, String pGameName, int solvedQuestions, int usedJokersCount)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO wwm_highscores (gamerTag, gameName, solvedQuestions, usedJokersCount) VALUES (?, ?, ?, ?)");
            ps.setString(1, pGamerTag);
            ps.setString(2, pGameName);
            ps.setInt(3, solvedQuestions);
            ps.setInt(4, usedJokersCount);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            Utils.consoleLog("ERROR", "Could not connect to the database!");
        }
        return false;
    }

    /**
     * Liefert alle Highscores in einer ArrayList bestehend aus HashMaps zurück.
     * @return Highscores als ArrayList
     */
    public static ArrayList<HashMap<String, String>> getAllHighscores()
    {
        ArrayList<HashMap<String, String>> highscores = new ArrayList<>();
        HashMap<String, String> highscore;
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_highscores ORDER BY solvedQuestions DESC");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                highscore = new HashMap<String, String>();
                highscore.put("ID", String.valueOf(rs.getInt("ID")));
                highscore.put("gamerTag", rs.getString("gamerTag"));
                highscore.put("gameName", rs.getString("gameName"));
                highscore.put("solvedQuestions", rs.getString("solvedQuestions"));
                highscore.put("usedJokersCount", rs.getString("usedJokersCount"));
                highscores.add(highscore);
            }
        } catch (SQLException e) {
            //
        }
        return highscores;
    }

    /**
     * L&ouml;scht einen Highscore anhand seiner ID aus der Bestenliste
     * @param pID ID des zu l&ouml;schenden Highscores
     * @return Gibt true zurück wenn der Highscore gel&ouml;scht wurde
     */
    public static boolean deleteHighscore(int pID)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM wwm_highscores WHERE ID = ?");
            ps.setInt(1, pID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Failed to connect to the database!");
        }
        return false;
    }
}