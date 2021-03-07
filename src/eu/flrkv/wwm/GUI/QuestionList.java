package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Question.Question;
import eu.flrkv.wwm.Question.QuestionController;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Fragen-Liste Fenster.
 * Zeigt alle Fragen in einer Tabelle an und erlaubt die Verwaltung dieser.
 */
public class QuestionList extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel questionListPanel;

    /**
     * JLabel für das Logo
     */
    private JLabel logoImage;

    /**
     * Fenster schließen Button
     */
    private JButton closeButton;

    /**
     * Frage löschen Button
     */
    private JButton deleteQuestionButton;

    /**
     * Frage hinzufügen Button
     */
    private JButton addQuestionButton;

    /**
     * Tabelle aktualisieren Button
     */
    private JButton refreshButton;


    /**
     * Label welches die Informationen über die Anzahl der Elemente in der Tabelle enthält
     */
    private JLabel questionCountLabel;


    /**
     * TableModel für die Tabelle
     */
    private DefaultTableModel questionTableModel;

    /**
     * Tabelle
     */
    private JTable questionTable;

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
     * Konstruktor der QuestionList Klasse
     * @param pController GUIController für dieses Fenster
     */
    public QuestionList(GUIController pController)
    {
        super("Wer wird Millionär | Fragenliste", new Dimension(900, 600));
        this.myController = pController;

        this.add(questionListPanel);

        this.setFrameProperties();
        this.setButtonProperties();

        this.setQuestionCountLabel(questionTable.getRowCount());

        refreshButton.setEnabled(false);
    }

    /**
     * Setzt die Anzahl der aktuellen Zeilen der Tabelle im JLabel
     * @param pCount Zeilenanzahl
     */
    private void setQuestionCountLabel(int pCount)
    {
        this.questionCountLabel.setText("Derzeit befinden sich " + pCount + " Fragen in der Datenbank.");
    }

    /**
     * Setzt die Eigenschaften der Buttons dieses Fensters
     */
    private void setButtonProperties()
    {
        // Fenster schließen button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fenster schließen
                dispose();
            }
        });

        // Frage löschen button
        deleteQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!questionTable.getSelectionModel().isSelectionEmpty()) {
                    int selectedRow = questionTable.getSelectedRow();
                    int questionID = Integer.parseInt(questionTable.getValueAt(questionTable.getSelectedRow(), 0).toString());

                    if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie diese Frage löschen möchten?", "Wer wird Millionär | Frage entfernen", JOptionPane.YES_NO_OPTION) == 0) {
                        if (QuestionController.deleteQuestion(questionID)) {
                            setQuestionCountLabel(QuestionController.getQuestionCount());
                            questionTableModel.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Frage erfolgreich entfernt!", "Wer wird Millionär | Frage gelöscht", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Beim löschen der Frage trat ein Fehler auf!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Es wurde keine Frage ausgewählt!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener für den 'Frage hinzufügen' Button setzen & Namen zuweisen
        addQuestionButton.addActionListener(myController);
        addQuestionButton.setName("QuestionList_addQuestion");


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildTable();
                setQuestionCountLabel(questionTable.getRowCount());
                questionTable.setModel(questionTableModel);
            }
        });
    }

    /**
     * Erstellt/"Baut" die Tabelle und setzt dessen Eigenschaften
     */
    private void buildTable()
    {
        Utils.consoleLog("INFO", "Building question table...");

        // Bennenung der Spalten
        String[] columnNames = {"Fragen-ID", "Schwierigkeitsgrad", "Fragestellung", "Richtige Antwort"};

        // Daten anfordern
        String[][] data = getTableData();

        // Neues Tabellenmodell erstellen & Tabelle anhand des Modells erstellen -> Tabelle zum Scrollpanel hinzufügen
        questionTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        questionTable = new JTable(questionTableModel);
        tableScrollPane = new JScrollPane(questionTable);

        // Zeilen mit klick auf Spaltenkopf sortierbar machen.
        questionTable.setAutoCreateRowSorter(true);

        // Nur eine Zeile makierbar
        questionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Zeilenhöhe setzen
        questionTable.setRowHeight(20);

        // Spaltenbreite festlegen
        questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        questionTable.getColumnModel().getColumn(1).setPreferredWidth(35);
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        questionTable.getColumnModel().getColumn(3).setPreferredWidth(275);
    }

    /**
     * Kreeirt das Datenarray für die Fragentabelle
     * @return Zweidimensionales Array vom Datentyp String
     */
    private String[][] getTableData()
    {
        ArrayList<Question> allQuestions = QuestionController.getAllQuestions();
        String[][] data = new String[allQuestions.size()][4];

        Question q;
        for (int i=0; i< allQuestions.size(); i++) {
            q = allQuestions.get(i);
            data[i][0] = String.valueOf(q.getId());
            data[i][1] = q.getDifficultyString();
            data[i][2] = q.getQuestion();
            data[i][3] = q.getRightAnswer();
        }
        return data;
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
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
        buildTable();
    }
}
