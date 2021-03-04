package eu.flrkv.wwm.Utils;

import javax.swing.*;
import java.util.*;

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

    public static int getLastSecurityLevel(int pQuestionNo)
    {
        return pQuestionNo >= 15 ? 15 : pQuestionNo >= 10 ? 10 : pQuestionNo >= 5 ? 5 : 0;
    }

    public static int getQuestionDifficulty(int pQuestionNumber)
    {
        return (pQuestionNumber > 0 && pQuestionNumber < 6) ? 1 : (pQuestionNumber > 5 && pQuestionNumber < 11) ? 2 : 3;
    }

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

    public static void unknownErrorPopup()
    {
        JOptionPane.showMessageDialog(null, "Es ist ein Fehler bei der Verarbeitung der Anfrage aufgetreten!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean arrayContainsValue(int pValue, int[] pArray) {
        return Arrays.stream(pArray).anyMatch(a -> a == pValue);
    }

    public static int[] getRandomValueArray(int pPositionCount)
    {

        int[] retArray = new int[pPositionCount];
        Random r = new Random();

        int randomInt;
        for (int i=0; i < retArray.length; i++) {
            randomInt = r.nextInt(100);

            while (arrayContainsValue(randomInt, retArray)) {
                randomInt = r.nextInt(100);
            }
            retArray[i] = randomInt;
        }


        return retArray;
    }

    public static int[] sortArrayByValues(int[] pArray)
    {
        int tmp;
        for (int i = 0; i < pArray.length; i++) {
            for (int j=0; j < pArray.length-1; j++) {
                if (pArray[j] > pArray[j+1]) {
                    tmp = pArray[j];
                    pArray[j] = pArray[j+1];
                    pArray[j+1] = tmp;
                }
            }
        }
        return pArray;
    }

    public static int sumUpArrayVals(int[] pArray)
    {
        int ret = 0;
        for (int i : pArray) {
            ret += i;
        }
        return ret;
    }



    public static double calcPercent(double pVal, double pBaseVal)
    {
        return pVal/pBaseVal*100;
    }

    public static int getRandomNumber(int pMax, int pMin)
    {
        return (int) ((Math.random() * (pMax - pMin)) + pMin);
    }
}
