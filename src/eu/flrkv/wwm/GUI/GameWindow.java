package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends FrameTemplate {


    private JPanel panel1;
    private JLabel logo;
    private JButton buttonAnswerA;
    private JButton buttonAnswerB;
    private JButton buttonAnswerC;
    private JButton buttonAnswerD;
    private JLabel fiftyFiftyJoker;
    private JLabel phoneJoker;
    private JLabel audienceJoker;
    private JButton hauptmenüButton;


    public GameWindow(GUIController pController) {
        super("Wer wird Millionär | InGame - @", new Dimension(950, 650));

        Utils.consoleLog("INFO", "Game window initialized!");
        setWindowProperties();

        // this.setContentPane(new JLabel(new ImageIcon("common/wwm_game_bg.png")));
        this.add(panel1);
        autoResizeFrame();


        audienceJoker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JOptionPane.showConfirmDialog(null, "Publikumsjoker gedrückt");
            }
        });
    }

    private void setWindowProperties()
    {
        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);

    }

    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));

        audienceJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerAudience_h64.png"));
        fiftyFiftyJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerFiftyFifty_h64.png"));
        phoneJoker = new JLabel(new ImageIcon("common/icons/jokers/jokerPhone_h64.png"));
    }



    private void autoResizeFrame()
    {
        this.pack();
        this.setMinimumSize(new Rectangle(getBounds()).getSize());
    }
}
