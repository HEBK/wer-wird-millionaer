package eu.flrkv.wwm.Utils;

import javax.swing.*;

/**
 * Klasse mit hilfreichen Methoden
 */
public class Utils {

    /**
     * Gibt eine Statusmeldung in der Konsole aus.
     * @param pPrefix Prefix/Typ der Statusmeldung (z.B. WARNING/ERROR/INFO)
     * @param pMessage Auszugebende Statusmeldung.
     */
    public static void consoleLog(String pPrefix, String pMessage)
    {
        pPrefix = "[" + pPrefix + "]";
        System.out.println(pPrefix + " " + pMessage);
    }

    /**
     * Beendet das Programm und loggt den Vorgang in der Konsole.
     * @param pStatus Exit status als integer
     */
    public static void exitProgram(int pStatus)
    {
        consoleLog("INFO", "Program is stopping...");
        System.exit(pStatus);
    }

    /**
     * Pausiert die Ausführung des Threads für eine bestimmte Zeit.
     * @param pMillis Zeit in Millisekunden
     */
    public static void sleep(long pMillis)
    {
        try {
            Thread.sleep(pMillis);
        } catch (InterruptedException e) {
            // Null
        }
    }

    /**
     * Gibt anhand der Fragennummer den Geldbetrag der Frage als String zur&uuml;ck
     * @param pQuestionNumber Fragennummer
     * @return Geldbetrag der Fragestufe. (Wenn nicht zwischen 0 - 15 -> NaN)
     */
    public static String questioNumbertoString(int pQuestionNumber)
    {
        return switch (pQuestionNumber) {
            case 0 -> "€ 0";
            case 1 -> "€ 50";
            case 2 -> "€ 100";
            case 3 -> "€ 200";
            case 4 -> "€ 300";
            case 5 -> "€ 500";
            case 6 -> "€ 1.000";
            case 7 -> "€ 2.000";
            case 8 -> "€ 4.000";
            case 9 -> "€ 8.000";
            case 10 -> "€ 16.000";
            case 11 -> "€ 32.000";
            case 12 -> "€ 64.000";
            case 13 -> "€ 125.000";
            case 14 -> "€ 500.000";
            case 15 -> "€ 1.000.000";
            default -> "€ NaN";
        };
    }

    /**
     * Gibt den sicheren Geldbetrag zurück
     * @param pQuestionNo Letzte gewonnene Frage (Nummer)
     * @return Geldsumme als String
     */
    public static String getSecurityLevelMoneyAmount(int pQuestionNo)
    {
        return pQuestionNo >= 15 ? "€ 1.000.000" : pQuestionNo >= 10 ? "€ 16.000" : pQuestionNo >= 5 ? "€ 500" : "€ 0";
    }

    /**
     * Gibt die Fragenstufe aus, welche das letzte Sicherheitslevel war.
     * @param pQuestionNo Fragennummer der aktuellen Frage
     * @return Fragenstufe des letzten sicheren Geldbetrags
     */
    public static int getLastSecurityLevel(int pQuestionNo)
    {
        return pQuestionNo >= 15 ? 15 : pQuestionNo >= 10 ? 10 : pQuestionNo >= 5 ? 5 : 0;
    }

    /**
     * Gibt den Schwierigkeitsgrad der Fragennummer als Integer zur&uuml;ck
     * @param pQuestionNumber Fragennummer
     * @return Schwierigkeitsgrad als Integer
     */
    public static int getQuestionDifficulty(int pQuestionNumber)
    {
        return (pQuestionNumber > 0 && pQuestionNumber < 6) ? 1 : (pQuestionNumber > 5 && pQuestionNumber < 11) ? 2 : 3;
    }

    /**
     * Gibt anhand der ButtonNummer im ButtonArray den Antwortbuchstaben zur&uuml;ck
     * @param pNo ButtonNummer
     * @return Antwortbuchstaben
     */
    public static char buttonNoToChar(int pNo)
    {
        return switch (pNo) {
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            default -> 'Z';
        };
    }

    /**
     * Zeigt ein Dialogfenster, welches auf einen Fehler hinweist
     */
    public static void unknownErrorPopup()
    {
        JOptionPane.showMessageDialog(null, "Es ist ein Fehler bei der Verarbeitung der Anfrage aufgetreten!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Rechnet die Werte aller Elemente im Array zusammen und gibt diesen zur&uuml;ck
     * @param pArray Array
     * @return Zusammengerechneter Wert
     */
    public static int sumUpArrayVals(int[] pArray)
    {
        int ret = 0;
        for (int i : pArray) {
            ret += i;
        }
        return ret;
    }

    /**
     * Berechnet den Prozentsatz aus Prozentwert und Grundwert
     * @param pVal Prozentwert
     * @param pBaseVal Grundwert
     * @return Prozentsatz
     */
    public static double calcPercent(double pVal, double pBaseVal)
    {
        return pVal/pBaseVal*100;
    }

    /**
     * Gibt eine zuf&auml;llige Zahl zwischen zwei Grenzwerten zur&uuml;ck
     * @param pMax Maximaler Wert
     * @param pMin Minimaler Wert
     * @return Zuf&auml;lliger Wert
     */
    public static int getRandomNumber(int pMax, int pMin)
    {
        return (int) ((Math.random() * (pMax - pMin)) + pMin);
    }
}
