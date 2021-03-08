package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Highscore.HighscoreController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Bestenliste Fenster.
 * Zeigt alle Highscores in einer Tabelle an.
 */
public class HighscoresList extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel highscoresList;

    /**
     * JLabel für das Logo
     */
    private JLabel logoImage;

    /**
     * Label welches die Informationen über die Anzahl der Elemente in der Tabelle enthält
     */
    private JLabel entryCountLabel;

    /**
     * Button zum entfernen eines Datensatzes (Highscore)
     */
    private JButton deleteEntryButton;

    /**
     * Button zum schließen des Fensters
     */
    private JButton closeButton;

    /**
     * ScrollPane für die Tabelle.
     * Ermöglicht das scrollen der Tabelle bei vielen Einträgen
     */
    private JScrollPane tableScrollPane;

    /**
     * TableModel für die Tabelle
     */
    private DefaultTableModel highscoresTableModel;

    /**
     * Tabelle
     */
    private JTable highscoresTable;

    /**
     * GUIController für dieses Fenster
     */
    private GUIController myController;


    /**
     * Konstruktor der Klasse HighscoresList
     * @param pController GUIController für dieses Fenster
     */
    public HighscoresList(GUIController pController)
    {
        super("Wer wird Millionär | Bestenliste", new Dimension(900, 600));

        // Controller für dieses Fenster setzen
        myController = pController;

        // Frame-Eigenschaften setzen
        setFrameProperties();

        // ActionListener für die Buttons setzen
        setActionListeners();

        // Haupt-JPanel zum Frame hinzufügen
        this.add(highscoresList);

        // Label mit der Anzahl der Spielstände setzen
        setEntryCountLabel(highscoresTable.getRowCount());
    }

    /**
     * Setzt die Anzahl der aktuellen Zeilen der Tabelle im JLabel
     * @param pCount Zeilenanzahl
     */
    private void setEntryCountLabel(int pCount)
    {
        this.entryCountLabel.setText("Es wurden " + pCount + " Einträge gefunden.");
    }

    /**
     * Setzt die Fenster-Eigenschaften für dieses JFrame
     */
    private void setFrameProperties()
    {
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
    }

    /**
     * Holt die Daten aller Spielstände aus der Datenbank und packt sie in ein JTable kompatibles Array
     * @return JTable Daten-Array mit allen Informationen
     */
    private String[][] getTableData()
    {
        ArrayList<HashMap<String, String>> allHighscores = HighscoreController.getAllHighscores();
        String[][] data = new String[allHighscores.size()][5];

        HashMap<String, String> hm;
        for (int i=0; i< allHighscores.size(); i++) {
            hm = allHighscores.get(i);
            data[i][0] = hm.get("ID");
            data[i][1] = hm.get("gamerTag");
            data[i][2] = hm.get("gameName");
            data[i][3] = Utils.questioNumbertoString(Integer.parseInt(hm.get("solvedQuestions")));
            data[i][4] = hm.get("usedJokersCount");
        }
        return data;
    }

    /**
     * Erstellt/"Baut" die Tabelle und setzt dessen Eigenschaften
     */
    private void buildTable()
    {
        Utils.consoleLog("INFO", "Building highscore table...");

        // Bennenung der Spalten
        String[] columnNames = {"ID", "Spielername", "Spielname", "Gewonnener Betrag", "Eingesetzte Joker"};

        // Daten anfordern
        String[][] data = getTableData();

        // Neues Tabellenmodell erstellen & Tabelle anhand des Modells erstellen -> Tabelle zum Scrollpanel hinzufügen
        highscoresTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        highscoresTable = new JTable(highscoresTableModel);
        tableScrollPane = new JScrollPane(highscoresTable);

        // Zeilen mit klick auf Spaltenkopf sortierbar machen.
        highscoresTable.setAutoCreateRowSorter(true);

        // Nur eine Zeile makierbar
        highscoresTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Zeilenhöhe setzen
        highscoresTable.setRowHeight(22);

        // Spaltenbreite
        highscoresTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        highscoresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        highscoresTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        highscoresTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        highscoresTable.getColumnModel().getColumn(4).setPreferredWidth(30);
    }

    /**
     * IntelliJ Frame Builder method
     */
    public void createUIComponents()
    {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
        buildTable();
    }

    /**
     * Setzt die ActionListener für die verschiedenen Buttons dieses Fensters
     */
    private void setActionListeners()
    {
        // Close Button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Knopf zum Löschen von Einträgen
        deleteEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prüfen ob eine Zeile gewählt wurde
                if (!highscoresTable.getSelectionModel().isSelectionEmpty()) {
                    // Zeile & SpielID
                    int selectedRow = highscoresTable.getSelectedRow();
                    int entryID = Integer.parseInt(highscoresTable.getValueAt(highscoresTable.getSelectedRow(), 0).toString());

                    // Abfrage ob der der Eintrag wirklich gelöscht werden soll
                    if (JOptionPane.showConfirmDialog(null, "Möchten Sie den gewählten Eintrag löschen?", "Wer wird Millionär | Eintrag löschen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        // Prüfen ob Löschung erfolgreich
                        if (HighscoreController.deleteHighscore(entryID)) {
                            JOptionPane.showMessageDialog(null, "Der Eintrag wurde erfolgreich gelöscht!", "Wer wird Millionär | Eintrag gelöscht!", JOptionPane.INFORMATION_MESSAGE);
                            highscoresTableModel.removeRow(selectedRow);
                            setEntryCountLabel(highscoresTable.getRowCount());
                        } else {
                            JOptionPane.showMessageDialog(null, "Beim Löschen des Eintrags trat ein Fehler auf!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // Keine Zeile gewählt
                    JOptionPane.showMessageDialog(null, "Es wurde kein Eintrag ausgewählt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}