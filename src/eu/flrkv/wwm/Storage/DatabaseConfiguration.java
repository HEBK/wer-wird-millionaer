package eu.flrkv.wwm.Storage;

import eu.flrkv.wwm.Utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Properties;

public class DatabaseConfiguration {

    /**
     * Pfad zur Konfigurationsdatei für die Datenbankverbindung
     */
    private static final String file = "config/database.properties";

    /**
     *
     * @return
     */
    public static String getFileDir()
    {
        String[] splittedPath = file.split("/");

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < splittedPath.length-1; i++) {
            path.append(splittedPath[i]);
        }

        return path.toString();
    }

    public static boolean configExists()
    {
        File f = new File(file);
        return f.exists();
    }

    @Nullable
    public static String readConfig(String pKey) {
        Properties p = new Properties();
        try {
            p.load(new FileReader(file));
            return p.getProperty(pKey);
        } catch (IOException e) {
            Utils.consoleLog("ERROR", "Could not read database configuration file!");
        }
        return null;
    }

    private static boolean createConfigFile()
    {
        File f = new File(DatabaseConfiguration.getFileDir());
        try {
            f.mkdirs();
            return new File(file).createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean writeConfig(String pKey, String pData)
    {
        if (!configExists()) {
            if (!createConfigFile()) {
                return false;
            }
        }
        Properties p = new Properties();
        try {
            p.load(new FileReader(file));
            p.setProperty(pKey, pData);
            p.store(new FileWriter(file), "This is the database connection configuration file.");
            return true;
        } catch (IOException e) {
            Utils.consoleLog("ERROR", "Could not read database configuration file!");
            return false;
        }
    }

    public static boolean deleteConfig()
    {
        File f = new File(file);
        return f.delete();
    }

}
