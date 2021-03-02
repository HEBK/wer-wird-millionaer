package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends FrameTemplate {


    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextPane aTextPane;
    private JTextPane bTextPane;
    private JTextPane dTextPane;
    private JTextPane cTextPane;
    private JLabel fiftyFiftyJoker;

    public GameWindow(GUIController pController) {
        super("Wer wird Millionär | InGame - @", new Dimension(700, 500));

        Utils.consoleLog("INFO", "Game window initialized!");
        setWindowProperties();

        // this.setContentPane(new JLabel(new ImageIcon("common/wwm_game_bg.png")));
        this.add(panel1);
    }

    private void setWindowProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);

    }

}
