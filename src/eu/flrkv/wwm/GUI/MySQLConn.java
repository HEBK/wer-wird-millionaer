package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySQLConn extends FrameTemplate {


    private JPanel dbConnPanel;
    private JTextField db_host;
    private JTextField db_port;
    private JTextField db_user;
    private JPasswordField db_password;
    private JTextField db_name;
    private JButton checkSave;
    private JLabel response;

    private final GUIController myController;

    public MySQLConn(GUIController pController) {
        // Call the parent constructor
        super("Wer wird Millionär | Datenbankverbindung", new Dimension(600, 335));

        // Set the GUIController for this window
        myController = pController;

        this.setDefaultCloseOperation(FrameTemplate.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setDefaultValues();

        this.setVisible(true);


        this.checkSave.setName("SQL_checkSave");

        this.add(dbConnPanel);

        // Set ActionListeners
        this.setActionListeners();
    }

    /**
     * Setzt die Stamdardwerte für Eingabefelder dieses Fensters
     */
    public void setDefaultValues()
    {
        db_host.setText("127.0.0.1");
        db_port.setText("3306");
        db_name.setText("wwm");
        db_user.setText("wwmUser");
    }

    /**
     * Setzt die ActionListener für Elemente dieses Fensters
     */
    private void setActionListeners()
    {
        // Set Action Listener for save & check conn button
        ActionListener saveDBConn = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!DatabaseConfiguration.configExists()) {
                    if (!DatabaseConnection.checkConnection(db_host.getText(), db_port.getText(), db_user.getText(), db_password.getText(), db_name.getText())) {
                        response.setText("Die Verbindung konnte nicht hergestellt werden!");
                        JOptionPane.showMessageDialog(null, "Die Verbindung konnte nicht hergestellt werden!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Write config

                        DatabaseConfiguration.writeConfig("db_host", db_host.getText());
                        DatabaseConfiguration.writeConfig("db_port", db_port.getText());
                        DatabaseConfiguration.writeConfig("db_user", db_user.getText());
                        DatabaseConfiguration.writeConfig("db_pass", db_password.getText());
                        DatabaseConfiguration.writeConfig("db_name", db_name.getText());

                        DatabaseConnection.executeScript("common/initSQL.sql");
                        response.setText("Die Verbindung zur Datenbank wurde erfolgreich hergestellt!");
                        JOptionPane.showMessageDialog(null, "Die Verbindung zur Datenbank wurde erfolgreich hergestellt!", "Wer wird Millionär | Datenbankverbindung", JOptionPane.INFORMATION_MESSAGE);

                        checkSave.setText("Zum Hauptmenü");
                        checkSave.addActionListener(myController);
                    }
                }
            }
        };
        checkSave.addActionListener(saveDBConn);
    }


}
