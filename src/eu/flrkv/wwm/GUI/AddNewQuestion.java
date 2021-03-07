package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Question.QuestionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenster zum Hinzufügen einer neuen Frage zum Spiel
 */
public class AddNewQuestion extends FrameTemplate {

    /**
     * JPanel welches alle weiteren Elemente für dieses Fenster enthält
     */
    private JPanel addQuestionPanel;

    /**
     * JLabel für das Logo
     */
    private JLabel logo;

    /**
     * Button um die Frage zu Speichern
     */
    private JButton addQuestionButton;

    /**
     * Button um das Formular zu leeren
     */
    private JButton resetFormButton;

    /**
     * Textfeld um die Fragestellung einzugeben
     */
    private JTextField questionInput;

    /**
     * ComboBox/Select/Auswahlliste um die Schwierigkeit der Frage zu wählen
     */
    private JComboBox<String> questionDifficulty;

    /**
     * Textfeld um die erste falsche Antwortmöglichkeit einzugeben.
     */
    private JTextField answer1Input;

    /**
     * Textfeld um die zweite falsche Antwortmöglichkeit einzugeben.
     */
    private JTextField answer2Input;

    /**
     * Textfeld um die dritte falsche Antwortmöglichkeit einzugeben.
     */
    private JTextField answer3Input;

    /**
     * Textfeld um die richtige Antwortmöglichkeit einzugeben.
     */
    private JTextField rightAnswerInput;

    /**
     * Objekt des GUIControllers für dieses Fenster
     */
    private final GUIController myController;

    /**
     * Konstruktor der Klasse AddNewQuestion
     * @param pController Objekt des GUIControllers für dieses Fenster
     */
    public AddNewQuestion(GUIController pController) {
        // Konstruktor der Oberklasse aufrufen
        super("Wer wird Millionär | Neue Frage hinzufügen", new Dimension(500, 900));

        // GUIController für dieses Fenster setzen
        this.myController = pController;

        // Eigenschaften für dieses Fenster setzen
        setFrameProperties();

        // Haupt-JPanel zum Frame hinzufügen
        this.add(addQuestionPanel);

        // Größe des Fensters an die Elemente anpassen
        pack();

        // EventListener/ActionListener setzen
        setEventListeners();
    }

    /**
     * Setzt die Eigenschaften dieses Fensters
     */
    private void setFrameProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
    }

    /**
     * Leert das Formular.
     * Setzt den Cursor zurück in das Eingabefeld für die Fragestellung
     */
    public void resetForm()
    {
        questionInput.setText("");
        questionDifficulty.setSelectedIndex(0);
        answer1Input.setText("");
        answer2Input.setText("");
        answer3Input.setText("");
        rightAnswerInput.setText("");
        questionInput.requestFocus();
    }

    /**
     * Setzt die EventListener/ActionListener für die Buttons
     */
    private void setEventListeners()
    {
        // 'Frage hinzufügen' Button
        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkQuestionValidity(true)) {
                    if (QuestionController.addQuestion(questionDifficulty.getSelectedIndex()+1, questionInput.getText(), answer1Input.getText(), answer2Input.getText(), answer3Input.getText(), rightAnswerInput.getText())) {
                        JOptionPane.showMessageDialog(null, "Die Frage wurde erfolgreich in der Datenbank abgespeichert!" , "Wer wird Millionär | Frage hinzugefügt", JOptionPane.INFORMATION_MESSAGE);
                        resetForm();
                    } else {
                        JOptionPane.showMessageDialog(null, "Die Frage konnte nicht abgespeichert werden!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 'Zurücksetzen' Button
        resetFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }

    /**
     * Derzeitige Eingaben überprüfen
     * @param pShowPrompts Wenn true, wird ein Dialogfenster mit der entsprechended Fehlermeldung angezeigt
     * @return Gibt true zurück wenn die Prüfung erfolgreich war. Andernfalls wird false zurückgegeben
     */
    private boolean checkQuestionValidity(boolean pShowPrompts)
    {
        if (questionInput.getText().length() > 120) {
            if (pShowPrompts) JOptionPane.showMessageDialog(null, "Die eingegebene Frage darf nicht länger als 120 Zeichen sein!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (answer1Input.getText().length() > 30 || answer2Input.getText().length() > 30 || answer3Input.getText().length() > 30 || rightAnswerInput.getText().length() > 30) {
            if (pShowPrompts) JOptionPane.showMessageDialog(null, "Die eingegebenen Anworten dürfen die Länge von 30 Zeichen nicht überschreiten!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (questionInput.getText().isEmpty() || answer1Input.getText().isEmpty() || answer2Input.getText().isEmpty() || answer3Input.getText().isEmpty() || rightAnswerInput.getText().isEmpty()) {
            if (pShowPrompts) JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus!", "Wer wird Millionär | Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * IntelliJ Frame Builder method
     */
    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
