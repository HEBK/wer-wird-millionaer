package eu.flrkv.wwm.Game;

public class Question {

    /**
     * Fragestellung als String
     */
    private final String question;

    /**
     * Eindeutige ID als Integer
     */
    private final int id;

    /**
     * Antworten als Strings in einem Array
     */
    private final String[] answer = new String[4];

    /**
     * Schwierigkeitsgrad als Integer
     */
    private final int difficulty;


    /**
     * Konstruktor der Klasse Question
     * Konstruiert die Frage und setzt alle Klassesattribute
     *
     * @param pID Eindeutige ID der Frage
     * @param pDifficulty Schwierigkeitsgrad der Frage als Integer (1 -> Leicht, 2 -> Mittel, 3 -> Schwer)
     * @param pQuestion Fragestellung als String
     * @param pAnswer1 Antwortmöglichkeit als String
     * @param pAnswer2 Antwortmöglichkeit als String
     * @param pAnswer3 Antwortmöglichkeit als String
     * @param pRightAnswer Richtige Antwort als String
     */
    public Question(int pID, int pDifficulty, String pQuestion, String pAnswer1, String pAnswer2, String pAnswer3, String pRightAnswer){

        this.id = pID;
        this.question = pQuestion;

        this.answer[0] = pAnswer1;
        this.answer[1] = pAnswer2;
        this.answer[2] = pAnswer3;
        this.answer[3] = pRightAnswer;

        this.difficulty = pDifficulty;
    }

    /**
     * Gibt alle falschen Antworten zurück.
     * @return falsche Antworten als Array.
     */
    public String[] getWrongAnswers()
    {
        String[] retAnswers = new String[3];
        for (int i=0; i < this.answer.length; i++) {
            retAnswers[i] = answer[i];
        }
        return retAnswers;
    }

    /**
     * Gibt die richtige Antwort zurück.
     * @return Richtige Antwort als String
     */
    public String getRightAnswer()
    {
        return answer[3];
    }

    /**
     * Gibt die Fragestellung zurück.
     * @return Fragestellung als String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gibt den Schwierigkeitsgrad zurück
     * 1 -> Leicht
     * 2 -> Mittel
     * 3 -> Schwer
     * @return Schwierigkeitsgrad als Integer
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gibt die eindeutige ID der Frage zurück.
     * @return Eindeutige ID als Integer
     */
    public int getId() {
        return id;
    }

    public String getPlainDifficulty()
    {
        switch (this.difficulty) {
            case 1:
                return "Leicht";
            case 2:
                return "Mittel";
            case 3:
                return "Schwer";
            default:
                return "n/A";
        }
    }
}
