package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;

public abstract class FrameTemplate extends JFrame {

    public FrameTemplate(String pTitle, Dimension pDimension)
    {
        // Execute parent constructor
        super(pTitle);

        // Set the icon of the window
        this.setIconImage(Utils.getImageIcon().getImage());


        // Set the dimensions of the window
        this.setSize(pDimension);
    }





}
