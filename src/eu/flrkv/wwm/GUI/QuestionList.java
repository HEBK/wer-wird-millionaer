package eu.flrkv.wwm.GUI;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionList extends FrameTemplate {
    private JPanel questionListPanel;
    private JButton addQuestionButton;
    private JLabel logoImage;
    private JButton deleteQuestionButton;
    private JScrollPane tableScrollPane;
    private JButton closeButton;
    private JTable questionTable;


    public QuestionList()
    {
        super("Wer wird Millionär | Fragenliste", new Dimension(900, 600));


        this.add(questionListPanel);

        this.setFrameProperties();
        this.setEventListeners();
    }

    private void setEventListeners()
    {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void setFrameProperties()
    {
        this.setResizable(false);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void buildTable()
    {
        // Bennenung der Spalten
        String[] columnNames = {"Fragen-ID", "Schwierigkeitsgrad", "Fragestellung", "Richtige Antwort"};
        String[][] data = {
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Leicht", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Mittel", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},
                {"4512", "Schwer", "Wer ist der/die derzeitige Vorsitzende der Christlich Demokratischen Union?", "Annegret Kramp-Karrenbauer"},

        };

        questionTable = new JTable(data, columnNames);
        tableScrollPane = new JScrollPane(questionTable);
        questionTable.setFillsViewportHeight(true);
        questionTable.setAutoCreateRowSorter(true);

        TableColumn col = null;
        questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        questionTable.getColumnModel().getColumn(1).setPreferredWidth(35);
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        questionTable.getColumnModel().getColumn(3).setPreferredWidth(275);
    }


    private void createUIComponents() {
        logoImage = new JLabel(new ImageIcon("common/wwm_120x120.png"));
        buildTable();
    }
}
