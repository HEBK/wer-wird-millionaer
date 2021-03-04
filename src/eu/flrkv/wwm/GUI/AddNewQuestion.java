package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Question.QuestionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewQuestion extends FrameTemplate {

    private final GUIController myController;
    private JPanel addQuestionPanel;
    private JLabel logo;
    private JTextField questionInput;
    private JComboBox<String> questionDifficulty;
    private JTextField answer1Input;
    private JButton addQuestionButton;
    private JButton resetFormButton;
    private JTextField answer2Input;
    private JTextField answer3Input;
    private JTextField rightAnswerInput;

    public AddNewQuestion(GUIController pController) {
        super("Wer wird Millionär | Neue Frage hinzufügen", new Dimension(500, 900));
        this.myController = pController;
        setVisible(true);

        setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);


        this.add(addQuestionPanel);

        pack();

        setEventListeners();

    }

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

    private void setEventListeners()
    {
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
        resetFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }

    private int convertDifficulty(String pDifficulty)
    {
        return switch (pDifficulty) {
            case "Einfach" -> 1;
            case "Mittel" -> 2;
            case "Schwer" -> 3;
            default -> 0;
        };
    }

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


    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
