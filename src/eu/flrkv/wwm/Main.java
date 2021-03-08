package eu.flrkv.wwm;

import com.bulenkov.darcula.DarculaLaf;
import eu.flrkv.wwm.GUI.GUIController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.sql.*;

/**
 * Klasse um das Programm zu starrten
 */
public class Main {

    /**
     * Methode, welche bei Programmstart ausgeführt wird. (Startpunkt)
     * @param args Startargumente
     */
    public static void main(String[] args) {
        // Main Info (for debugging)
        Utils.consoleLog("INFO", "System main stage reached.");

        // Load drivers
        registerDrivers();

        // Try to set new look & Feel
        loadLookAndFeel(new DarculaLaf());

        // Create GUIController & Start GUI
        GUIController g = new GUIController();


        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                g.run();
            }
        });
       // g.run();
    }

    /**
     * Registriert alle benötigten Treiber (JDBC -> SQL)
     * Gibt Status/Fehlermeldungen in der Konsole aus.
     */
    private static void registerDrivers()
    {
        try {
            Utils.consoleLog("INFO", "Registering mySQL JDBC driver...");
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            Utils.consoleLog("ERROR", "Failed to load the mySQL JDBC driver!");
        }

    }

    /**
     * Setzt das Design (Look and Feel) für das Swing UI.
     * Gibt Status- und Fehlermeldungen in der Konsole aus.
     * @param pLookAndFeel Das zu verwendende Design/LookAndFeel als Objekt des Datentyps 'LookAndFeel'
     */
    private static void loadLookAndFeel(LookAndFeel pLookAndFeel)
    {
        try {
            UIManager.setLookAndFeel(pLookAndFeel);
            Utils.consoleLog("INFO", "The LookAndFeel was loaded successfully!");
        } catch (UnsupportedLookAndFeelException e) {
            Utils.consoleLog("ERROR", "Failed to load LookAndFeel!");
        }
    }
}
