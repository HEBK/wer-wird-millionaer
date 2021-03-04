package eu.flrkv.wwm.Game;

import eu.flrkv.wwm.Exceptions.GameNotFoundException;
import eu.flrkv.wwm.Exceptions.QuestionNotFoundException;
import eu.flrkv.wwm.Highscore.HighscoreController;
import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Question.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.time.Year;
import java.util.HashMap;

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
    private Integer currentQuestionID;

    /**
     * Verbleibende Joker (phone,audience,fifty) Komma separiert.
     */
    private String usedJokers;

    /**
     * Alle benutzten Fragen als String Komma separiert.
     */
    private String usedQuestions;

    /**
     * Name des aktuellen Spiels
     */
    private String gameName;

    /**
     * Fragen Controller
     */
    private final QuestionController questionController = new QuestionController(this);

    /**
     * Aktuelle Frage als Objekt der Klasse Question
     */
    private Question currentQuestion;


    /**
     * Konstruktor um ein bereits vorhandenes Spiel fortzuführen.
     * @param pGameID Unique ID des Spiels, welches fortgeführt werden soll
     */
    public Game(int pGameID) throws GameNotFoundException
    {
        if (!GameController.gameExists(pGameID)) {
            Utils.consoleLog("ERROR", "Game not found!");
            throw new GameNotFoundException("The Game with the ID '"+pGameID+"' could not be found!");
        }

        this.gameID = pGameID;
        HashMap<String, String> data = GameController.getGameData(this.gameID);
        if (data != null) {
            this.currentQuestionNumber = Integer.parseInt(data.get("questionNumber"));
            this.currentQuestionID = Integer.parseInt(data.get("currentQuestionID"));
            this.usedJokers = data.get("usedJokers");
            this.usedQuestions = data.get("usedQuestions");
            this.gamerTag = data.get("gamerTag");
            this.gameName = data.get("gameName");

            try {
                setQuestion(QuestionController.getQuestion(currentQuestionID), currentQuestionNumber);
            } catch (QuestionNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Dieser Spielstand ist beschädigt, da die aktuelle Frage aus dem Spiel entfernt wurde!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);

            }
        }

    }

    /**
     * Konstruktor um ein neues Spiel zu starten.
     * @param pGameName Name des Spielstandes
     * @param pGamerTag Name des Spielers
     */
    public Game(String pGameName, String pGamerTag) {

        // Create Game
        if (GameController.createGame(pGamerTag, pGameName)) {
            if (GameController.getLastGameID() != null) {
                this.gameID = GameController.getLastGameID();
                Utils.consoleLog("INFO", "The Current GameID is '"+this.gameID+"'");
            } else {
                Utils.consoleLog("ERROR", "Fatal error while getting game ID!");
                Utils.exitProgram(1);
            }
        } else {
            Utils.consoleLog("ERROR", "Fatal error while creating game!");
            Utils.exitProgram(1);
        }

        this.gameName = pGameName;
        this.gamerTag = pGamerTag;

        HashMap<String, String> data = GameController.getGameData(this.gameID);
        if (data != null) {
            this.currentQuestionNumber = Integer.parseInt(data.get("questionNumber"));
            this.usedJokers = data.get("usedJokers");
        }


        // Set Question
        nextQuestion(true);
    }


    public void nextQuestion(boolean pFirst)
    {
        if (!pFirst) this.currentQuestionNumber = currentQuestionNumber+1;
        this.currentQuestion = questionController.getNewQuestion(Utils.getQuestionDifficulty(currentQuestionNumber));
        this.currentQuestionID = currentQuestion.getId();
    }

    private void setQuestion(Question pQuestion, int pQuestionNumber)
    {
        this.currentQuestion = pQuestion;
        this.currentQuestionNumber = pQuestionNumber;
    }



    /**
     * Gibt den QuestionController für dieses Spiel zurück.
     * @return Question controller in Form eines Objekts der Klasse QuestionController
     */
    public QuestionController getQuestionController()
    {
        return questionController;
    }


    public String getUsedQuestions() {
        return this.usedQuestions;
    }

    public void setUsedQuestions(String usedQuestions) {
        this.usedQuestions = usedQuestions;
    }

    /**
     * Gibt die ID der aktuellen Frage zurück.
     * @return ID der aktuellen Frage als Integer
     */
    public int getCurrentQuestionID()
    {
        return this.currentQuestionID;
    }

    public int getGameID()
    {
        return this.gameID;
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

    /**
     * Gibt die aktuelle Frage zurück
     * @return Aktzuelle Frage in Form eines Question Objekts
     */
    public Question getCurrentQuestion()
    {
        return this.currentQuestion;
    }

    public void setCurrentQuestion(Question pQuestion)
    {
        this.currentQuestion = pQuestion;
    }


    public String[] getUsedJokersArray()
    {
        if (this.usedJokers == null) {
            return new String[]{};
        }
        return this.usedJokers.split(",");
    }

    public boolean jokerIsUsed(String pJoker)
    {
        if (this.usedJokers == null || this.usedJokers.isEmpty()) {
            return false;
        }
        boolean bool = false;
        for (String s: getUsedJokersArray()) {
            if (s.equals(pJoker)) {
                bool = true;
            }
        }
        return bool;
    }

    public void useJoker(String pJoker)
    {
        if (this.usedJokers == null) {
            this.usedJokers = pJoker;
            return;
        }
        this.usedJokers = usedJokers + "," + pJoker;
    }

    /**
     * Spiel verloren
     */
    public void lost()
    {
        int select = JOptionPane.showConfirmDialog(null, "Du leider falsch geantwortet!\n\nDein erspielter Betrag:  "+Utils.getSecurityLevelMoneyAmount(currentQuestionNumber-1)+"\n\nSoll dein Spielstand in die Bestenliste aufgenommen werden?", "Wer wird Millionär | Falsche Antwort", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (select == JOptionPane.YES_OPTION) {
            if (!addToHighscores(true)) {
                JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Highscores!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        deleteGame();
    }

    public boolean addToHighscores(boolean pBackToSecurityLevel)
    {
        return HighscoreController.addHighscore(gamerTag, gameName, pBackToSecurityLevel ? Utils.getLastSecurityLevel(currentQuestionNumber-1) : currentQuestionNumber-1, getUsedJokersArray().length);
    }

    public boolean deleteGame()
    {
        return GameController.deleteGame(this.gameID);
    }

    public boolean saveGame() {
        return GameController.updateGame(this.getGameID(), this.getCurrentQuestionNumber(), this.getCurrentQuestion().getId(), this.usedQuestions, this.usedJokers);
    }
}
