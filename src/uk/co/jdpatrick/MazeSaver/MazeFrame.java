package uk.co.jdpatrick.MazeSaver;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jackp on 05/10/2017.
 */
public class MazeFrame extends JFrame {

    private MazeCanvas c;
    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];

    public MazeFrame() {

        c = new MazeCanvas();
        add(c);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        device.setFullScreenWindow(this);
        c.init(getWidth(),getHeight());
        c.run();
    }
}
