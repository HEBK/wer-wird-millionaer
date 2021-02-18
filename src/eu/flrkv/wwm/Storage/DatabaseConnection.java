package eu.flrkv.wwm.Storage;

import eu.flrkv.wwm.Utils.Utils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DatabaseConnection {


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

    public static Connection getConnection() throws SQLException {
        if (!credentialsValid()) {
            Utils.consoleLog("ERROR", "Can not use database connection. Config or credentials are invalid!");
            return null;
        }
        return DriverManager.getConnection("jdbc:mysql://" + DatabaseConfiguration.readConfig("db_host") + ":" + DatabaseConfiguration.readConfig("db_port") + "/" +DatabaseConfiguration.readConfig("db_name"), DatabaseConfiguration.readConfig("db_user"), DatabaseConfiguration.readConfig("db_pass"));
    }

    public static ResultSet executeQuery(String pSQL)
    {
        if (!credentialsValid()) {
            Utils.consoleLog("ERROR", "Can not use database connection. Config or credentials are invalid!");
            return null;
        }
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                return statement.executeQuery(pSQL);
            }
        } catch (SQLException throwables) {
            Utils.consoleLog("ERROR", "Failed to connect to the database! '"+DatabaseConfiguration.readConfig("db_name")+"@"+DatabaseConfiguration.readConfig("db_host")+"'");
        }
        return null;
    }


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
