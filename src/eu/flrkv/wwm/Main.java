package eu.flrkv.wwm;

import com.bulenkov.darcula.DarculaLaf;
import eu.flrkv.wwm.GUI.GUIController;
import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;


import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // Main Info (for debugging)
        System.out.println("[INFO] System main stage reached.");

        // Load drivers
        registerDrivers();

        // Try to set new look & Feel
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
            Utils.consoleLog("INFO", "Loaded theme 'Darcula' from bulenkov (https://github.com/bulenkov/Darcula).");
        } catch (UnsupportedLookAndFeelException e) {
            Utils.consoleLog("ERROR", "Failed to load theme");
        }

        // Start GUI
        GUIController g = new GUIController();
        g.run();

    }


    private static void registerDrivers()
    {
        try {
            Utils.consoleLog("INFO", "Registering mySQL JDBC driver...");
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Failed to load the mySQL JDBC driver!");
        }

    }

}
