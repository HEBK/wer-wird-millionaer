package eu.flrkv.wwm.GUI;

import java.awt.*;

public class AddNewQuestion extends FrameTemplate {

    private final GUIController myController;

    public AddNewQuestion(GUIController pController) {
        super("Wer wird Millionär | Neue Frage hinzufügen", new Dimension(500, 900));
        this.myController = pController;
        setVisible(true);

        setDefaultCloseOperation(FrameTemplate.DISPOSE_ON_CLOSE);
    }
}
