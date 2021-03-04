package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class About extends FrameTemplate {
    private JPanel aboutGUI;
    private JLabel image;
    private JButton gitHubButton;
    private JButton licenseButton;
    private JButton closeButton;
    private JLabel titleVersionLabel;

    private GUIController myController;


    public About(GUIController pController)
    {
        super("Wer wird Millionär | Über", new Dimension(500, 375));

        this.myController = pController;

        this.setVisible(true);
        this.add(aboutGUI);
        this.setResizable(false);

        setEventListeners();

        Utils.consoleLog("INFO", "Showing About window");
    }

    public void setEventListeners()
    {
        gitHubButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/HEBK/wer-wird-millionaer"));
                Utils.consoleLog("INFO", "Opened web page 'https://github.com/HEBK/wer-wird-millionaer' in a web browser.");
            } catch (IOException | URISyntaxException ioException) {
                Utils.consoleLog("ERROR", "Unable to open web page 'https://github.com/HEBK/wer-wird-millionaer' in a web browser.");
                ioException.printStackTrace();
            }
        });
        closeButton.addActionListener(e -> dispose());
        licenseButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("licenses/LICENSE.rtf"));
            } catch (IOException ioException) {
                Utils.consoleLog("ERROR", "LICENSE.rtf file could not be found!");
                ioException.printStackTrace();
            }
        });
    }

    private void createUIComponents() {
        image = new JLabel(new ImageIcon("common/logos/wwm_120x120.png"));
    }
}
