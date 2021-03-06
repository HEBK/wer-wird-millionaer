package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Exceptions.GameNotFoundException;
import eu.flrkv.wwm.Game.Game;
import eu.flrkv.wwm.Game.GameController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fenster zum Verwalten der Spielst&auml;nde (Spielstandverwaltung)
 */
public class GamesList extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel gamesListPanel;

    /**
     * JLabel für das Logo
     */
    private JLabel logoImage;

    /**
     * Label welches die Informationen über die Anzahl der Elemente in der Tabelle enthält
     */
    private JLabel saveGameCountLabel;

    /**
     * Button zum Entfernen eines Spielstands
     */
    private JButton deleteGameButton;

    /**
     * Button zum Laden eines Spiels
     */
    private JButton loadGameButton;

    /**
     * Button zum schließen des Fensters
     */
    private JButton closeButton;

    /**
     * Tabelle
     */
    private JTable saveGameTable;

    /**
     * TableModel für die Tabelle
     */
    private DefaultTableModel saveGameTableModel;

    /**
     * ScrollPane für die Tabelle.
     * Ermöglicht das scrollen der Tabelle bei vielen Einträgen
     */
    private JScrollPane tableScrollPane;

    /**
     * GUIController für dieses Fenster
     */
    private final GUIController myController;


    /**
     * GamesList Konstruktor
     * @param pController GUIController für diesen Frame
     */
    public GamesList(GUIController pController)
    {
        // Konstruktor von FrameTemplate aufrufen
        super("Wer wird Millionär | Spielstände", new Dimension(900, 600));

        // Controller für dieses Fenster setzen
        myController = pController;

        // Frame-Eigenschaften setzen
        setFrameProperties();

        // Haupt-JPanel zum Frame hinzufügen
        this.add(gamesListPanel);

        // ActionListener für die Buttons setzen
        setActionListeners();

        // Label mit der Anzahl der Spielstände setzen
        setSaveGameCountLabel(saveGameTable.getRowCount());
    }

    /**
     * Setzt die Eigenschaften dieses Fensters
     */
    private void setFrameProperties()
    {
        this.setResizable(false);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Setzt die Anzahl der aktuellen Zeilen der Tabelle im JLabel
     * @param pCount Zeilenanzahl
     */
    private void setSaveGameCountLabel(int pCount)
    {
        this.saveGameCountLabel.setText("Es existieren " + pCount + " Spielstände in der Datenbank.");
    }

    /**
     * Holt die Daten aller Spielstände aus der Datenbank und packt sie in ein JTable kompatibles Array
     * @return JTable Daten-Array mit allen Informationen
     */
    private String[][] getTableData()
    {
        ArrayList<HashMap<String, String>> allSaveGames = GameController.getAllSaveGames();
        String[][] data = new String[allSaveGames.size()][5];

        HashMap<String, String> hm;
        for (int i=0; i< allSaveGames.size(); i++) {
            hm = allSaveGames.get(i);
            data[i][0] = hm.get("gameID");
            data[i][1] = hm.get("gameName");
            data[i][2] = hm.get("gamerTag");
            data[i][3] = Utils.questioNumbertoString(Integer.parseInt(hm.get("questionNumber")));
            data[i][4] = hm.get("lastUpdate");
        }
        return data;
    }

    /**
     * Erstellt/"Baut" die Tabelle und setzt dessen Eigenschaften
     */
    private void buildTable()
    {
        Utils.consoleLog("INFO", "Building game list table...");

        // Bennenung der Spalten
        String[] columnNames = {"Spiel-ID", "Spielname", "Spielername", "Aktuelle Frage", "Letzte Aktualisierung"};

        // Daten anfordern
        String[][] data = getTableData();

        // Neues Tabellenmodell erstellen & Tabelle anhand des Modells erstellen -> Tabelle zum Scrollpanel hinzufügen
        saveGameTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        saveGameTable = new JTable(saveGameTableModel);
        tableScrollPane = new JScrollPane(saveGameTable);

        // Zeilen mit klick auf Spaltenkopf sortierbar machen.
        saveGameTable.setAutoCreateRowSorter(true);

        // Zeilenhöhe setzen
        saveGameTable.setRowHeight(25);

        // Nur eine Zeile makierbar
        saveGameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Spaltenbreite
        saveGameTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        saveGameTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        saveGameTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        saveGameTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        saveGameTable.getColumnModel().getColumn(4).setPreferredWidth(120);
    }

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents()
    {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
        buildTable();
    }

    /**
     * Setzt die ActionListener für die verschiedenen Buttons dieses Fensters
     */
    private void setActionListeners()
    {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prüfen ob eine Zeile gewählt wurde
                if (!saveGameTable.getSelectionModel().isSelectionEmpty()) {
                    // SpielID
                    int saveGameID = Integer.parseInt(saveGameTable.getValueAt(saveGameTable.getSelectedRow(), 0).toString());

                    // Abfrage ob der Spielstand wirklich geladen werden soll
                    if (JOptionPane.showConfirmDialog(null, "Möchten Sie den gewählten Spielstand laden?", "Wer wird Millionär | Spielstand laden", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            myController.loadGame(new Game(saveGameID));
                            dispose();
                        } catch (GameNotFoundException gameNotFoundException) {
                            JOptionPane.showMessageDialog(null, "Beim Laden des Spielstandes trat ein Fehler auf!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Es wurde kein Spielstand ausgewählt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prüfen ob eine Zeile gewählt wurde
                if (!saveGameTable.getSelectionModel().isSelectionEmpty()) {
                    // Zeile & SpielID
                    int selectedRow = saveGameTable.getSelectedRow();
                    int saveGameID = Integer.parseInt(saveGameTable.getValueAt(saveGameTable.getSelectedRow(), 0).toString());

                    // Abfrage ob der Spielstand wirklich geladen werden soll
                    if (JOptionPane.showConfirmDialog(null, "Möchten Sie den gewählten Spielstand löschen?", "Wer wird Millionär | Spielstand löschen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (GameController.deleteGame(saveGameID)) {
                            JOptionPane.showMessageDialog(null, "Der Spielstand wurde erfolgreich gelöscht!", "Wer wird Millionär | Spielstand gelöscht!", JOptionPane.INFORMATION_MESSAGE);
                            saveGameTableModel.removeRow(selectedRow);
                            setSaveGameCountLabel(saveGameTable.getRowCount());
                        } else {
                            JOptionPane.showMessageDialog(null, "Beim Löschen des Spielstandes trat ein Fehler auf!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Es wurde kein Spielstand ausgewählt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
