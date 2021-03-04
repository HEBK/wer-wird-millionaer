package eu.flrkv.wwm.Highscore;

import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class HighscoreController {



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


}
