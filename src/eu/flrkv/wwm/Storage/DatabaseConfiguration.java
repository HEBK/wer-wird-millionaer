package eu.flrkv.wwm.Storage;

import eu.flrkv.wwm.Utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Properties;


/**
 * Klasse zur Verwaltung der Konfigurationsdatei f&uuml;r die Datenbank
 */
public class DatabaseConfiguration {

    /**
     * Ordner für die Konfigurationsdatei.
     */
    private static final String dir = System.getProperty("user.home") + "\\.wwm";

    /**
     * Dateiname für die Konfigurationsdatei.
     */
    public static final String filename = "database.properties";


    /**
     * Gibt den Speicherort der Konfigurationsdatei für die Datenbankverbindung zurück.
     * @return Absoluter Speicherort der Konfigurationsdatei als String
     */
    public static String getFilePath()
    {
        return dir + "\\" + filename;
    }

    /**
     * Prüft ob die Konfigurationsdatei bereits angelegt wurde.
     * @return Gibt true zurück falls bereits eine Konfigurationsdatei angelegt wurde.
     */
    public static boolean configExists()
    {
        File f = new File(getFilePath());
        return f.exists();
    }

    /**
     * Liest eine Property (Eigenschaft) aus der Konfigurationsdatei aus.
     * @param pKey Identifier der zu lesenden Property
     * @return Gibt den Wert der Property als String zurück. Falls der Identifier nicht existiert wird ein leerer String zurückgegeben.
     */
    @Nullable
    public static String readConfig(String pKey) {
        Properties p = new Properties();
        try {
            p.load(new FileReader(getFilePath()));
            return p.getProperty(pKey);
        } catch (IOException e) {
            Utils.consoleLog("ERROR", "Could not read database configuration file!");
        }
        return null;
    }

    /**
     * Erstellt die Konfigurationsdatei für die Datenbankverbindung und die benötigte Ordnerstruktur
     * @return Gibt true zurück, wenn die Datei erfolgreich angelegt wurde.
     */
    private static boolean createConfigFile()
    {
        File f = new File(dir);
        try {
            if (f.mkdirs()) {
                Utils.consoleLog("ERROR", "Could not create directory for the database configuration file!");
            }
            return new File(getFilePath()).createNewFile();
        } catch (IOException e) {
            Utils.consoleLog("ERROR", "Database configuration file could not be saved!");
            return false;
        }
    }

    /**
     * Schreibt oder Überschreibt einen Wert für einen Property Identifier.
     * Falls die Konfigurationsdatei noch nicht erstellt wurde, wird diese wenn möglich angelegt.
     * @param pKey Identifier, für den der Wert geschrieben werden soll.
     * @param pData Zu schreibender Wert.
     * @return Gibt true zurück, sollte der Wert erfolgreich geschrieben worden sein.
     */
    public static boolean writeConfig(String pKey, String pData)
    {
        if (!configExists()) {
            if (!createConfigFile()) {
                return false;
            }
        }
        Properties p = new Properties();
        try {
            p.load(new FileReader(getFilePath()));
            p.setProperty(pKey, pData);
            p.store(new FileWriter(getFilePath()), "This is the database connection configuration file.");
            return true;
        } catch (IOException e) {
            Utils.consoleLog("ERROR", "Could not read database configuration file!");
            return false;
        }
    }

    /**
     * Löscht die Konfigurationsdatei.
     * @return Gibt bei erfolgreicher Ausführung true zurück.
     */
    public static boolean deleteConfig()
    {
        File f = new File(getFilePath());
        return f.delete();
    }

}
