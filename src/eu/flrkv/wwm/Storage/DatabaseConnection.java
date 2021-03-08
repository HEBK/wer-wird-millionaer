package eu.flrkv.wwm.Storage;

import eu.flrkv.wwm.Utils.Utils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * Klasse zum Herstellen und Pr&uuml;fen der Datenbankverbindung
 */
public class DatabaseConnection {

    /**
     * Pr&uuml;ft die &uuml;bergebenen Daten auf ihre G&uuml;ltigkeit
     * @param pHost Hostname des Datenbank-Servers
     * @param pPort Datenbank-Port
     * @param pUser Datenbank-User
     * @param pPassword Passwort f&uuml;r den Benutzer
     * @param pDatabase Datenbankname
     * @return Gibt true zur&uuml;ck falls die Verbindung erfolgreich war
     */
    public static boolean checkConnection(String pHost, String pPort, String pUser, String pPassword, String pDatabase)
    {
        try {
            Connection sql = DriverManager.getConnection("jdbc:mysql://" + pHost + ":" + pPort + "/" + pDatabase + "?characterEncoding=utf8", pUser, pPassword);
            return true;
        } catch (SQLException throwables) {
            Utils.consoleLog("ERROR", "Failed to connect to the database! '"+pDatabase+"@"+pHost+"'");
        }
        return false;
    }

    /**
     * Gibt das Connection Objekt der gespeicherten Datenbankverbinfung zur&uuml;ck
     * @return Connection Objekt der gespeicherten Datenbankverbinfung
     * @throws SQLException Falls ein Fehler bei der Verbindung auftrat
     */
    public static Connection getConnection() throws SQLException {
        if (!credentialsValid()) {
            Utils.consoleLog("ERROR", "Can not use database connection. Config or credentials are invalid!");
            return null;
        }
        return DriverManager.getConnection("jdbc:mysql://" + DatabaseConfiguration.readConfig("db_host") + ":" + DatabaseConfiguration.readConfig("db_port") + "/" +DatabaseConfiguration.readConfig("db_name"), DatabaseConfiguration.readConfig("db_user"), DatabaseConfiguration.readConfig("db_pass"));
    }

    /**
     * Pr&uuml;ft ob die hinterlegten Daten f&uuml;r die Verbindung g&uuml;ltig sind
     * @return true, falls die Daten g&uuml;ltig sind
     */
    public static boolean credentialsValid()
    {
        if (DatabaseConfiguration.configExists()) {
            return  checkConnection(
                        DatabaseConfiguration.readConfig("db_host"),
                        DatabaseConfiguration.readConfig("db_port"),
                        DatabaseConfiguration.readConfig("db_user"),
                        DatabaseConfiguration.readConfig("db_pass"),
                        DatabaseConfiguration.readConfig("db_name"));
        }
        return false;
    }

    /**
     * Führt den Inhalt einer SQL-Skriptdatei auf dem SQL-Server aus.
     * @param pScriptPath Pfad zur SQL-Skriptdatei
     */
    public static void executeScript(String pScriptPath)
    {
        try {
            Utils.consoleLog("INFO", "Executing SQL-File '"+pScriptPath+"' ...");
            ScriptRunner runner = new ScriptRunner(getConnection());
            runner.runScript(new InputStreamReader(new FileInputStream(pScriptPath), StandardCharsets.UTF_8));
            runner.setStopOnError(false);
            runner.closeConnection();
        } catch (FileNotFoundException e) {
            Utils.consoleLog("ERROR", "The SQL File ('"+pScriptPath+"') was not found!");
        } catch (SQLException exception) {
            Utils.consoleLog("ERROR", "Database connection error or invalid sql file!");
        }
    }



}
