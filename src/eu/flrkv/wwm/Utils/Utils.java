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



}
