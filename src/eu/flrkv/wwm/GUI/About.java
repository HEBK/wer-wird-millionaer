package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Über/Spielinformationen Fenster.
 */
public class About extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel aboutGUI;

    /**
     * JLabel für das Logo
     */
    private JLabel image;

    /**
     * Button um die Projektseite des Spiels auf GitHub aufzurufen
     */
    private JButton gitHubButton;

    /**
     * Button um die Lizenzdatei zu öffnen
     */
    private JButton licenseButton;

    /**
     * Button um das Fenster zu schließen
     */
    private JButton closeButton;

    /**
     * Objekt des GUIControllers für dieses Fenster
     */
    private GUIController myController;


    /**
     * Konstruktor der Klasse About
     * @param pController GUIController für dieses Fenster
     */
    public About(GUIController pController)
    {
        super("Wer wird Millionär | Über", new Dimension(500, 375));

        // Controller setzen
        this.myController = pController;

        // Haupt-JPanel zum Frame hinzufügen
        this.add(aboutGUI);

        // EventListener hinzufügen
        setEventListeners();

        // Fenstereigenschaften setzen
        setFrameProperties();
    }

    /**
     * Setzt die Eigenschaften dieses Fensters
     */
    private void setFrameProperties()
    {
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Setzt die EventListener/ActionListener für die Buttons
     */
    public void setEventListeners()
    {
        // GitHub-Button -> Öffnet die Projektseite auf Github
        gitHubButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/HEBK/wer-wird-millionaer"));
                Utils.consoleLog("INFO", "Opened web page 'https://github.com/HEBK/wer-wird-millionaer' in a web browser.");
            } catch (IOException | URISyntaxException ioException) {
                Utils.consoleLog("ERROR", "Unable to open web page 'https://github.com/HEBK/wer-wird-millionaer' in a web browser.");
                ioException.printStackTrace();
            }
        });

        // Button zum Schließen des Fensters
        closeButton.addActionListener(e -> dispose());

        // Button zum Öffnen der Lizenzdatei
        licenseButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("licenses/LICENSE.rtf"));
            } catch (IOException ioException) {
                Utils.consoleLog("ERROR", "LICENSE.rtf file could not be found!");
                ioException.printStackTrace();
            }
        });
    }

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        image = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
