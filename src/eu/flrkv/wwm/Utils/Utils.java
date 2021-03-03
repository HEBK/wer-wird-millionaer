package eu.flrkv.wwm.Utils;

import javax.swing.*;

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

    public static ImageIcon getImageIcon()
    {
        return new ImageIcon("common/logos/wwm.png");
    }

    public static String getQuestionMoneyAmount(int pQuestionNumber)
    {
        return switch (pQuestionNumber) {
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

    public static int getQuestionDifficulty(int pQuestionNumber)
    {
        return (pQuestionNumber > 0 && pQuestionNumber < 6) ? 1 : (pQuestionNumber > 5 && pQuestionNumber < 11) ? 2 : 3;
    }

    public static char buttonNoToChar(int pNo)
    {
        return switch (pNo) {
            case 1 -> 'A';
            case 2 -> 'B';
            case 3 -> 'C';
            case 4 -> 'D';
            default -> 'Z';
        };
    }


}
