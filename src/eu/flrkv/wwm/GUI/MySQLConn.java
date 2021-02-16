package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.GUI.Interfaces.GUIUtils;
import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;
import eu.flrkv.wwm.Utils.Utils;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySQLConn extends FrameTemplate implements GUIUtils {


    private JPanel dbConnPanel;
    private JTextField db_host;
    private JTextField db_port;
    private JTextField db_user;
    private JPasswordField db_password;
    private JTextField db_name;
    private JButton checkSave;
    private JLabel response;

    public MySQLConn() {
        super("Wer wird Millionär | Datenbankverbindung", new Dimension(600, 335));

        this.setDefaultCloseOperation(FrameTemplate.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setDefaultValues();

        this.add(dbConnPanel);

        checkSave.addActionListener(e -> {
            if (!DatabaseConnection.checkConnection(db_host.getText(), db_port.getText(), db_user.getText(), db_password.getText(), db_name.getText())) {
                response.setText("Die Verbindung konnte nicht hergestellt werden!");
            } else {
                // Write config

                DatabaseConfiguration.writeConfig("db_host", db_host.getText());
                DatabaseConfiguration.writeConfig("db_port", db_port.getText());
                DatabaseConfiguration.writeConfig("db_user", db_user.getText());
                DatabaseConfiguration.writeConfig("db_pass", db_password.getText());
                DatabaseConfiguration.writeConfig("db_name", db_name.getText());

                checkSave.setText("Einrichtung abschließen");
            }
        });

    }

    @Override
    public void showMe(boolean pVisibility)
    {
        Utils.consoleLog("INFO", "MySQLConn window changed its visibility.");
        this.setVisible(pVisibility);
    }

    public void setDefaultValues()
    {
        db_host.setText("127.0.0.1");
        db_port.setText("3306");
        db_name.setText("wwm");
        db_user.setText("wwmUser");
    }
}
