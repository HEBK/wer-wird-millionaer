package eu.flrkv.wwm.Game;

import eu.flrkv.wwm.Exceptions.QuestionNotFoundException;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionController {

    /**
     * Objekt des aktuellen Spiels
     */
    private Game game;

    /**
     * Kontruktor der Klasse QuestionController
     * @param pGame Objekt der Klasse Game -> Referenzobjekt für die Fragenasuwahl
     */
    public QuestionController(Game pGame)
    {
        this.game = pGame;
    }

    /**
     * Holte eine neu noch nicht verwendete Frage aus der MySQL-Dtaenbanktabelle 'wwm_questions'
     * @param pDifficulty Schwierigkeitskategorie der Frage (0 -> Leicht, 1 -> Mittel, 2 -> Schwer)
     * @return Gibt die Frage als Objekt zurück.
     */
    public Question getNewQuestion(int pDifficulty)
    {
        return null;
    }



    /*
     * Static section
     */


    /**
     * Speichert eine neue Frage in die Datenbank
     * @param pDifficulty Schwierigkeitsgrad (1-3) der Frage
     * @param pQuestion Frage als String
     * @param pAnswer0 Antwort als String
     * @param pAnswer1 Antwort als String
     * @param pAnswer2 Antwort als String
     * @param pRightAnswer Richtige Antwort als String
     * @return Gibt true als boolean zurück, wenn die Frage erfolgreich abgespeichert wurde.
     */
    public static boolean addQuestion(int pDifficulty, String pQuestion, String pAnswer0, String pAnswer1, String pAnswer2, String pRightAnswer)
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO wwm_questions (difficulty, question, answer0, answer1, answer2, answer3) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, pDifficulty);
            ps.setString(2, pQuestion);
            ps.setString(3, pAnswer0);
            ps.setString(4, pAnswer1);
            ps.setString(5, pAnswer2);
            ps.setString(6, pRightAnswer);

            if(ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Speichert eine neue Frage in der Datenbank
     * @param pQuestion Frage als Objekt des Datentyps Frage
     * @return Gibt true als boolean zurück, wenn die Frage erfolgreich abgespeichert wurde.
     */
    public static boolean addQuestion(Question pQuestion)
    {
        return addQuestion(pQuestion.getDifficulty(), pQuestion.getQuestion(), pQuestion.getWrongAnswers()[0], pQuestion.getWrongAnswers()[1], pQuestion.getWrongAnswers()[2], pQuestion.getRightAnswer());
    }

    /**
     * Holt eine Frage mit einer bestimmten ID aus der Datenbank.
     * @param pQuestionID ID der Frage, die gelesen werden soll.
     * @return Gibt die Frage als Objekt zurück. Wenn keine Frage mit der ID existiert wird null zurückgegeben.
     */
    public static Question getQuestion(int pQuestionID) throws QuestionNotFoundException {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_questions WHERE ID = ?");
            ps.setInt(1, pQuestionID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Question(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch(SQLException e) {
            Utils.consoleLog("ERROR", "A Question with that ID could not be found!");
            throw new QuestionNotFoundException("A Question with that ID could not be found!");
        }
        return null;
    }

    public static boolean deleteQuestion(int pQuestionID) {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM wwm_questions WHERE ID = ?");
            ps.setInt(1, pQuestionID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "There was an error connecting to the database!");
        }
        return false;
    }

    /**
     * Holt alle Fragen aus der Datenbank und gibt diese zurück.
     * @return Gibt die Fragen als ArrayList mit den Question Objekten zurück
     */
    public static ArrayList<Question> getAllQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM wwm_questions");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questions.add(new Question(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Could not read questions from database!");
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Zählt alle Zeilen (Frageanzahl) der Tabelle wwm_questions
     * @return Gibt die Anzahl der Zeilen als Integer zurück.
     */
    public static int getQuestionCount()
    {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT COUNT(*) AS row_count FROM wwm_questions");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("row_count");
        } catch (SQLException e) {
            return -1;
        }
    }
}
