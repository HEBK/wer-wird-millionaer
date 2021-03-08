package eu.flrkv.wwm.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Fenster zum erstellen/starten eines neuen Spiels
 */
public class CreateNewGame extends FrameTemplate{

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel newGameWindow;

    /**
     * JLabel für das Logo
     */
    private JLabel logoImage;

    /**
     * Textfeld um den Spielnamen einzugeben
     */
    private JTextField gameNameInput;

    /**
     * Textfeld um den Spielernamen einzugeben
     */
    private JTextField gamertagInput;

    /**
     * Button um das Spiel zu erstellen/starten
     */
    private JButton startGameButton;

    /**
     * Objekt des GUIControllers für dieses Fenster
     */
    private final GUIController myController;


    /**
     * Konstruktor der Klasse CreateNewGame
     * @param pController Objekt des GUIControllers für dieses Fenster
     */
    public CreateNewGame(GUIController pController) {
        // Konstruktor der Oberklasse aufrufen
        super("Wer wird Millionär | Neues Spiel", new Dimension(400,400));

        // GUIController für dieses Fenster setzen
        this.myController = pController;

        // Eigenschaften für dieses Fenster setzen
        setFrameProperties();

        // Haupt-JPanel zum Frame hinzufügen
        this.add(newGameWindow);

        // Größe des Fensters an die Elemente anpassen
        pack();

        // Standardwerte setzen
        this.setDefaultValues();

        // EventListener/ActionListener setzen
        this.setEventListeners();
    }

    /**
     * Setzt die Eigenschaften dieses Fensters
     */
    private void setFrameProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     * Setzt die Standardwerte für die Eingabefelder/-elemente
     */
    private void setDefaultValues()
    {
        // Wert des GameName Textfelds auf 'WWM_YYYY-mm-dd_HH-ii' setzen
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("y-MM-dd_HH-mm");
        this.gameNameInput.setText("WWM_" + s.format(d));

        // Start-Button standardmäßig erstmal deaktivieren
        this.startGameButton.setEnabled(false);
    }

    /**
     * Setzt die EventListener/ActionListener für die Buttons
     */
    private void setEventListeners()
    {
        // Überprüfen der Länge vom Spielnamen Textfeld
        gameNameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gameNameInput.getText().length() >= 20) {
                    e.consume();
                }
                startGameButton.setEnabled(gameNameInput.getText().length() >= 5);
            }
        });

        // Überprüfen der Länge vom Spielernamen Textfeld
        gamertagInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gamertagInput.getText().length() >= 16) {
                    e.consume();
                }
                startGameButton.setEnabled(gamertagInput.getText().length() >= 5);
            }
        });

        // Spiel starten button
        this.startGameButton.setName("CreateNewGame_start");
        this.startGameButton.addActionListener(myController);
    }

    /**
     * Gibt den aktuellen Wert des Spielname Textfeldes zurück
     * @return Spielname
     */
    public String getGameName()
    {
        return gameNameInput.getText();
    }

    /**
     * Gibt den aktuellen Wert des Spielername Textfeldes zurück
     * @return Spielername
     */
    public String getGamerTag()
    {
        return gamertagInput.getText();
    }

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
