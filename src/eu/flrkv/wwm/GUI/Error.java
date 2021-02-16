package eu.flrkv.wwm.GUI;

import javax.swing.*;
import java.awt.*;

public class Error extends FrameTemplate {
    private JPanel errorPanel;
    private JLabel errorIcon;
    private JLabel errorLabel;

    public Error(String pErrorText, String pTitle) {
        super(pTitle, new Dimension(500, 500));

        this.add(errorPanel);
        this.errorLabel.setText(pErrorText);
        this.pack();

        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
    }

    private void createUIComponents() {
        errorIcon = new JLabel(new ImageIcon("common/warning_exclamation_64x64.png"));
    }
}
