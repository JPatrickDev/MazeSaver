package uk.co.jdpatrick.MazeSaver;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by jackp on 05/10/2017.
 */
public class MazeCanvas extends Canvas implements MouseListener,KeyListener, MouseMotionListener {

    private BufferStrategy bs;
    MazeGenerator g;


    int w, h;

    public MazeCanvas() {

    }

    Random r = new Random();

    public void init(int w, int h) {

        setSize(w, h);
        this.w = w;
        this.h = h;
        try {
            int tX = w / Config.TARGET_TILE_SIZE;
            int tY = h / Config.TARGET_TILE_SIZE;
            System.out.println(tX + ":" + tY);
            if (tX % 2 == 0)
                tX += 1;
            if (tY % 2 == 0)
                tY += 1;
            g = new MazeGenerator(tX, tY);
            // g = new MazeGenerator(51,901);
        } catch (Exception e) {
            e.printStackTrace();
        }

        createBufferStrategy(3);
        bs = getBufferStrategy();

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        requestFocus();
        setCursor( getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null ) );

    }


    public void run() {
        while (true) {
            for (int i = 0; i != Config.STEPS_PER_FRAME; i++)
                g.step();
            draw();
        }
    }

    private void draw() {
        int w = g.getWidth();
        int h = g.getHeight();
        int[][] tiles = g.getMaze();

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int[] pixels = new int[w * h];
        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                if (tiles[x][y] == 0)
                    pixels[x + y * w] = Config.WALL_COLOR.hashCode();
                else {
                    if (Config.CURRENT_COLOR_MODE != Color_Mode.TWO_COLOR) {
                        pixels[x + y * w] = g.colorMap[x][y];
                    } else {
                        pixels[x + y * w] = Config.FLOOR_COLOR.hashCode();
                    }
                }
            }
        }
        Point currentTile = g.getCurrentTile();
        if (Config.SHOW_CURRENT_TILE)
            pixels[currentTile.x + currentTile.y * w] = Color.red.hashCode();
        image.setRGB(0, 0, w, h, pixels, 0, w);
        Image i = image.getScaledInstance(image.getWidth() * Config.TARGET_TILE_SIZE, image.getHeight() * Config.TARGET_TILE_SIZE, 0);
        i = i.getScaledInstance(getWidth(), getHeight(), 0);
        bs.getDrawGraphics().drawImage(
                i, 0, 0, null);
        bs.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.exit(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.exit(0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.exit(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //  System.exit(0);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.exit(0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.exit(0);
    }

    boolean first = true;
    @Override
    public void mouseMoved(MouseEvent e) {
        if(first){
            first = !first;
            return;
        }
        System.exit(0);
    }
}
