package eu.flrkv.wwm.GUI;

import eu.flrkv.wwm.Utils.Utils;

import javax.swing.*;
import java.awt.*;


/**
 * Template Klasse zur Vererbung für Frames
 */
public abstract class FrameTemplate extends JFrame {

    /**
     * Konstruktor der Klasse FrameTemplate
     * Setzt den Frame Titel und die Frame Größe
     * @param pTitle Titel des Frames
     * @param pDimension Größe des Fensters
     */
    public FrameTemplate(String pTitle, Dimension pDimension)
    {
        // Execute parent constructor
        super(pTitle);

        // Set the icon of the window
        this.setIconImage(new ImageIcon("common/logos/wwm.png").getImage());

        // Set the dimensions of the window
        this.setSize(pDimension);
    }
}