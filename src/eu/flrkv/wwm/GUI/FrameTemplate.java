package eu.flrkv.wwm.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class FrameTemplate extends JFrame {

    public FrameTemplate(String pTitle, Dimension pDimension)
    {
        // Execute parent constructor
        super(pTitle);

        // Set the icon of the window
        this.setIconImage(new ImageIcon("common/wwm.png").getImage());


        // Set the dimensions of the window
        this.setSize(pDimension);
    }





}
