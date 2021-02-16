package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Storage.DatabaseConfiguration;
import eu.flrkv.wwm.Storage.DatabaseConnection;

public class GUIController {


    // GUI-Windows
    private MySQLConn sql = new MySQLConn();
    private MainMenu menu = new MainMenu();

    public GUIController()
    {
        System.out.println("[INFO] GUIController was initialized successfully!");
    }

    public void run()
    {
        if (DatabaseConfiguration.configExists() && DatabaseConnection.credentialsValid()) {
            menu.setVisible(true);
        } else {
            sql.setVisible(true);
        }
    }
}
