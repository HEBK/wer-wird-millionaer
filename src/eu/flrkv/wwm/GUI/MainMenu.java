package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.GUI.Interfaces.GUIUtils;
import eu.flrkv.wwm.Main;
import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends FrameTemplate implements GUIUtils {
    private JPanel mainMenu;
    private JButton startNewGameButton;
    private JButton spielLadenButton;
    private JButton exitGameButton;
    private JButton aboutButton;
    private JLabel wwmImage;

    public MainMenu()
    {
        // Call of the parent constructor
        super("Wer wird Millionär | Hauptmenü", new Dimension(900, 600));

        // Minimum Size of the window
        this.setMinimumSize(new Dimension(700, 575));

        // Exit program on close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(mainMenu);


        // Exit button Listener -> Exits program if clicked
        exitGameButton.addActionListener(new ActionListener() {

            /**
             * Listener für den Spiel beenden Knopf. Beendet das Programm
             * @param e ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close Program
                Utils.exitProgram(-1);
            }
        });
        aboutButton.addActionListener(e -> {
            JFrame about = new About();
        });

    }

    @Override
    public void showMe(boolean pVisibility)
    {
        // Console logging
        Utils.consoleLog("INFO", "MainMenu window changed its visibility.");
        this.setVisible(pVisibility);
    }

    private void createUIComponents() {
        wwmImage = new JLabel(new ImageIcon("common/wwm_120x120.png"));
    }
}
