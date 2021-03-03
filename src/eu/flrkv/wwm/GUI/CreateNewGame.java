package eu.flrkv.wwm.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewGame extends FrameTemplate{
    private JPanel newGameWindow;
    private JTextField gameNameInput;
    private JTextField gamertagInput;
    private JLabel logoImage;
    private JButton startGameButton;
    private JLabel responseLabel;

    private GUIController myController;

    public CreateNewGame(GUIController pController) {
        super("Wer wird Millionär | Neues Spiel", new Dimension(400,400));

        myController = pController;

        this.setVisible(true);
        this.add(newGameWindow);

        this.pack();

        this.setResizable(false);

        this.setElements();
        this.setEventListeners();

        this.setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);

        this.startGameButton.setEnabled(false);



        this.startGameButton.setName("CreateNewGame_start");
        this.startGameButton.addActionListener(myController);
    }

    public void updateResponse(String pResponse)
    {
        this.responseLabel.setText(pResponse);
        this.pack();
    }

    private void createUIComponents() {
        logoImage = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }

    public void setElements()
    {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("y-MM-dd_HH-mm");
        this.gameNameInput.setText("WWM_" + s.format(d));
    }

    public void setEventListeners()
    {
        gameNameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gameNameInput.getText().length() >= 20) {
                    e.consume();
                }
                startGameButton.setEnabled(gameNameInput.getText().length() >= 5);
            }
        });

        gamertagInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gamertagInput.getText().length() >= 16) {
                    e.consume();
                }
                startGameButton.setEnabled(gamertagInput.getText().length() >= 5);
            }
        });
    }

    public String getGameName()
    {
        return gameNameInput.getText();
    }

    public String getGamerTag()
    {
        return gamertagInput.getText();
    }


}
