package eu.flrkv.wwm.Game;

import eu.flrkv.wwm.Exceptions.GameNotFoundException;
import eu.flrkv.wwm.Exceptions.QuestionNotFoundException;
import eu.flrkv.wwm.Highscore.HighscoreController;
import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Question.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.util.HashMap;

/**
 * Klasse eines Spiels
 */
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
        // Überprüfen ob Spiel mit dieser ID existiert
        if (!GameController.gameExists(pGameID)) {
            Utils.consoleLog("ERROR", "Game not found!");
            throw new GameNotFoundException("The Game with the ID '"+pGameID+"' could not be found!");
        }

        // SpielID setzen
        this.gameID = pGameID;

        // Gespeicherte Daten für das Spiel anfordern und setzen
        HashMap<String, String> data = GameController.getGameData(this.gameID);
        if (data != null) {
            this.currentQuestionNumber = Integer.parseInt(data.get("questionNumber"));
            this.currentQuestionID = Integer.parseInt(data.get("currentQuestionID"));
            this.usedJokers = data.get("usedJokers");
            this.usedQuestions = data.get("usedQuestions");
            this.gamerTag = data.get("gamerTag");
            this.gameName = data.get("gameName");

            // Prüfen ob die letzte angezeigte Frage noch existiert
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

        // Prüfen ob spiel erfolgreich erstellt wurde
        if (GameController.createGame(pGamerTag, pGameName)) {
            // ID dieses Spiels bekommen indem die letzte SpielID abgefragt wird
            if (GameController.getLastGameID() != null) {
                this.gameID = GameController.getLastGameID();
                Utils.consoleLog("INFO", "The Current GameID is '"+this.gameID+"'");
            } else {
                Utils.consoleLog("ERROR", "Fatal error while getting game ID!");
                Utils.exitProgram(1);
            }
        } else {
            // Kritischer Fehler bei der erstellung des Spiels
            Utils.consoleLog("ERROR", "Fatal error while creating game!");
            Utils.exitProgram(1);
        }

        // Übergebenen Spiel- & Spielernamen setzen
        this.gameName = pGameName;
        this.gamerTag = pGamerTag;

        // Joker noch nicht verwendet setzen & Nummer der aktuellen Frage setzen
        this.usedJokers = null;
        this.currentQuestionNumber = 1;

        // Frage setzen
        nextQuestion(true);
    }

    /**
     * Holt eine neue Frage, die in diesem Spiel noch nicht benutzt wurde und setzt diese als aktuelle Frage
     * @param pFirst Erste Frage (Falls vorher noch keine Frage gesetzt wurde)
     */
    public void nextQuestion(boolean pFirst)
    {
        if (!pFirst) this.currentQuestionNumber = currentQuestionNumber+1;                                                  // Aktuelle Fragennummer um eins erhöhen
        this.currentQuestion = questionController.getNewQuestion(Utils.getQuestionDifficulty(currentQuestionNumber));       // Frage setzen
        this.currentQuestionID = currentQuestion.getId();                                                                   // FragenID setzen
    }

    /**
     * Setzt eine vorgegebene Frage als derzeitige Frage
     * @param pQuestion Zu setzende Frage
     * @param pQuestionNumber Fragennummer
     */
    private void setQuestion(Question pQuestion, int pQuestionNumber)
    {
        this.currentQuestion = pQuestion;                       // Frage setzen
        this.currentQuestionNumber = pQuestionNumber;           // FragenID setzen
    }

    /**
     * Gibt alle bereits genutzten Fragen Komma-Separiert zurück
     * @return Komma-Separierter String mit allen FragenIDs die bereits genutzt wurden
     */
    public String getUsedQuestions() {
        return this.usedQuestions;
    }

    /**
     * Setzt einen Komma-Separierten String mit FragenIDs als bereits genutzte Fragen
     * @param usedQuestions Komma-Separierter String mit allen FragenIDs die bereits genutzt wurden
     */
    public void setUsedQuestions(String usedQuestions) {
        this.usedQuestions = usedQuestions;
    }

    /**
     * Gibt die aktuelle SpielID zurück
     * @return SpielID als Integer
     */
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

    /**
     * Setzt das Attribut für die aktuelle Frage (Ändert nicht die angezeigte Frage!)
     * @param pQuestion Objekt des Datentyps Question
     */
    public void setCurrentQuestion(Question pQuestion)
    {
        this.currentQuestion = pQuestion;
    }

    /**
     * Gibt ein String-Array mit allen genutzen Jokern zurück
     * @return String-Array mit genutzen Jokern. Falls leer wird ein leeres Array zurückgegeben.
     */
    public String[] getUsedJokersArray()
    {
        if (this.usedJokers == null) {
            return new String[]{};
        }
        return this.usedJokers.split(",");
    }

    /**
     * Prüft ob ein Joker bereits eingesetzt wurde
     * @param pJoker Zu überprüfender Joker
     * @return Gibt true zurück falls der Joker bereits genutzt wurde
     */
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

    /**
     * Setzt einen Joker als eingesetzt
     * @param pJoker Joker
     */
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
     * Zeigt Dialogfenster mit den Spielstatistiken und speichert ggf. den Highscore.
     * Löscht das Spiel
     */
    public void lost()
    {
        int select = JOptionPane.showConfirmDialog(null, "Du leider falsch geantwortet!\n\nRichtige Antwort: "+currentQuestion.getRightAnswer()+"\nDein erspielter Betrag:  "+Utils.getSecurityLevelMoneyAmount(currentQuestionNumber-1)+"\n\nSoll dein Spielstand in die Bestenliste aufgenommen werden?", "Wer wird Millionär | Falsche Antwort", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (select == JOptionPane.YES_OPTION) {
            if (!addToHighscores(true)) {
                JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Highscores!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        deleteGame();
    }

    /**
     * Fügt den Spielstand mit den derzeitigen Daten der Bestenliste hinzu
     * @param pBackToSecurityLevel Wenn true wird die letzte Sicherheitsstufe als Score gespeichert. Wenn false der bereits erspielte Geldbetrag
     * @return Gibt true zurück sofern der Highscore erfolgreich gespeichert wurde
     */
    public boolean addToHighscores(boolean pBackToSecurityLevel)
    {
        return HighscoreController.addHighscore(gamerTag, gameName, pBackToSecurityLevel ? Utils.getLastSecurityLevel(currentQuestionNumber-1) : currentQuestionNumber-1, getUsedJokersArray().length);
    }

    /**
     * Löscht das aktuelle Spiel / den aktuellen Spielstand
     * @return Gibt true zurück wenn das Spiel erfolgreich gelöscht wurde
     */
    public boolean deleteGame()
    {
        return GameController.deleteGame(this.gameID);
    }

    /**
     * Speichert/Überschreibt den aktuellen Spielstand mit den aktuellen Spieldaten
     * @return Gibt true zurück wenn das Spiel erfolgreich gespeichert wurde
     */
    public boolean saveGame() {
        return GameController.updateGame(this.getGameID(), this.getCurrentQuestionNumber(), this.getCurrentQuestion().getId(), this.usedQuestions, this.usedJokers);
    }
}
