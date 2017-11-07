package uk.co.jdpatrick.MazeSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

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
     //   setExtendedState(JFrame.MAXIMIZED_BOTH);
        Rectangle2D r = getScreenBounds();
        setBounds((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
        setUndecorated(true);
        setVisible(true);
       // device.setFullScreenWindow(this);
        c.init(getWidth(),getHeight());
        c.run();
    }

    private Rectangle2D getScreenBounds(){
        Rectangle2D result = new Rectangle2D.Double();
        GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : localGE.getScreenDevices()) {
            for (GraphicsConfiguration graphicsConfiguration : gd.getConfigurations()) {
                result.union(result, graphicsConfiguration.getBounds(), result);
            }
        }
        return result;
    }
}
