package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends FrameTemplate {



    public GameWindow(GUIController pController) {
        super("Wer wird Millionär | InGame - @", new Dimension(700, 500));

        Utils.consoleLog("INFO", "Game window initialized!");
        setWindowProperties();

        this.setContentPane(new JLabel(new ImageIcon("common/wwm_game_bg.png")));

    }

    private void setWindowProperties()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);

    }

}
