package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class About extends JFrame {
    private JPanel aboutGUI;
    private JLabel image;
    private JButton gitHubButton;
    private JButton licenseButton;
    private JButton closeButton;


    public About()
    {
        super("Wer wird Millionär | Über");
        this.setVisible(true);
        this.add(aboutGUI);
        this.setResizable(false);

        this.setSize(new Dimension(500, 375));
        this.setIconImage(new ImageIcon("common/wwm.png").getImage());

        Utils.consoleLog("INFO", "Showing About window");

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
        image = new JLabel(new ImageIcon("common/wwm_120x120.png"));
    }
}
