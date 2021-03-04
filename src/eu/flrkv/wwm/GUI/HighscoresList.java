package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Game.GameController;
import eu.flrkv.wwm.Highscore.HighscoreController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HighscoresList extends FrameTemplate {
    private JScrollPane tableScrollPane;
    private JLabel logoImage;
    private JButton deleteEntryButton;
    private JLabel entryCountLabel;
    private JButton closeButton;
    private JPanel highscoresList;

    // GUIController
    private GUIController myController;

    // Table
    private DefaultTableModel highscoresTableModel;
    private JTable highscoresTable;

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

    }

    private void setFrameProperties()
    {
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
    }

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
            data[i][3] = Utils.getQuestionMoneyAmount(Integer.parseInt(hm.get("solvedQuestions")));
            data[i][4] = hm.get("usedJokersCount");
        }
        return data;
    }

    private void buildTable()
    {
        Utils.consoleLog("INFO", "Building highscore table...");

        // Bennenung der Spalten
        String[] columnNames = {"ID", "Spielername", "Spielname", "Gewonnener Betrag", "Eingesetzte Joker"};

        // Daten anfordern
        String[][] data = getTableData();

        // Neues Tabellenmodell erstellen & Tabelle anhand des Modells erstellen -> Tabelle zum Scrollpanel hinzufügen
        highscoresTableModel = new DefaultTableModel(data, columnNames);
        highscoresTable = new JTable(highscoresTableModel);
        tableScrollPane = new JScrollPane(highscoresTable);

        // Zeilen mit klick auf Spaltenkopf sortierbar machen.
        highscoresTable.setAutoCreateRowSorter(true);

        // Zeilenhöhe setzen
        highscoresTable.setRowHeight(22);

        // Spaltenbreite
        highscoresTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        highscoresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        highscoresTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        highscoresTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        highscoresTable.getColumnModel().getColumn(4).setPreferredWidth(30);
    }

    public void createUIComponents()
    {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
        buildTable();
    }

    private void setActionListeners()
    {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



}
