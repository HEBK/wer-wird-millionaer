package eu.flrkv.wwm.Game;

import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;
import org.apache.ibatis.jdbc.SQL;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {


    /**
     * Gibt die aktuellste GameID zurück.
     * @return Die aktuellste GameID als Integer. Wenn kein Spiel existiert wird null zurückgegeben.
     */
    public static Integer getLastGameID()
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT MAX(ID) FROM wwm_savedGames");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Can not connect to the database!");
        }
        return null;
    }

    public static boolean deleteGame(int pID)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM wwm_savedGames WHERE ID = ?");
            ps.setInt(1, pID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Can not connect to the database!");
        }
        return false;
    }

    public static HashMap<String, String> getGameData(int pID)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_savedGames WHERE ID = ?");
            ps.setInt(1, pID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HashMap<String, String> ret = new HashMap<String, String>();

                ret.put("gameID", Integer.toString(pID));
                ret.put("gamerTag", rs.getString("gamerTag"));
                ret.put("gameName", rs.getString("gameName"));
                ret.put("questionNumber", rs.getString("questionNumber"));
                ret.put("currentQuestionID", rs.getString("currentQuestionID"));
                ret.put("usedQuestions", rs.getString("usedQuestions"));
                ret.put("usedJokers", rs.getString("usedJokers"));
                ret.put("createdAt", rs.getString("createdAt"));
                return ret;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * Erstellt ein neues Spiel
     * @param pGamerTag Zu verwendender Spielername
     * @param pGameName ZU verwendender Spielname
     * @return Gibt true zurück, falls das Spiel erfolgreich ertsellt wurde.
     */
    public static boolean createGame(String pGamerTag, String pGameName)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO wwm_savedGames (gameName, gamerTag, questionNumber) VALUES (?, ?, ?)");
            ps.setString(1, pGameName);
            ps.setString(2, pGamerTag);
            ps.setInt(3, 1);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Can not connect to the database!");
        }
        return false;
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

    public static boolean updateGame(int pGameID, int questionNumber, int currentQuestionID, String usedQuestions, String usedJokers)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE wwm_savedGames SET questionNumber = ?, currentQuestionID = ?, usedQuestions = ?, usedJokers = ? WHERE ID = ?");
            ps.setInt(1, questionNumber);
            ps.setInt(2, currentQuestionID);
            ps.setString(3, usedQuestions);
            ps.setString(4, usedJokers);
            ps.setInt(5, pGameID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Error while connecting to the database!");
        }
        return false;
    }

    public static ArrayList<HashMap<String, String>> getAllSaveGames()
    {
        ArrayList<HashMap<String, String>> games = new ArrayList<>();
        HashMap<String, String> game;
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_savedGames");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                game = new HashMap<>();
                game.put("gameID", rs.getString("ID"));
                game.put("gameName", rs.getString("gameName"));
                game.put("gamerTag", rs.getString("gamerTag"));
                game.put("questionNumber", Integer.toString(rs.getInt("questionNumber")));
                game.put("currentQuestionID", Integer.toString(rs.getInt("currentQuestionID")));
                game.put("usedQuestions", rs.getString("usedQuestions"));
                game.put("usedJokers", rs.getString("usedJokers"));
                game.put("createdAt", rs.getString("createdAt"));
                game.put("lastUpdate", rs.getString("lastUpdate"));
                games.add(game);
            }
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Could not read savegames from database!");
            e.printStackTrace();
        }
        return games;
    }




}
