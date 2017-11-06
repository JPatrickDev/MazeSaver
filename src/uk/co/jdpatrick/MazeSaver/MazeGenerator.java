package uk.co.jdpatrick.MazeSaver;

import uk.co.jdpatrick.MazeSaver.ColorSets.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by jackp on 05/10/2017.
 */
public class MazeGenerator {

    private int[][] tiles;
    private int w, h;

    private int i = 0;
    private int height;
    private int width;

    public int[][] colorMap;


    public MazeGenerator(int w, int h) throws Exception {
        if (w % 2 == 0 || h % 2 == 0) {
            throw new Exception("Both width and height must be odd numbers.");
        }
        this.w = w;
        this.h = h;
        this.tiles = new int[w][h];
    }

    public int[][] getMaze() {
        return tiles;
    }

    private Stack<Point> mazeStack = new Stack<Point>();
    private Point currentTile;
    private final Random rand = new Random();

    public void step() {
        if (mazeStack.isEmpty()) {
            startMaze();
        }

        ArrayList<Point> neighbours = getNeighbours(currentTile);
        if (neighbours.isEmpty()) {
            currentTile = mazeStack.pop();
            if(Config.INSTANT_BACKUP)
                step();
        } else {
            Point nextTile = neighbours.get(rand.nextInt(neighbours.size()));
            carve(currentTile, nextTile);
            mazeStack.push(currentTile);
            currentTile = nextTile;
        }
    }

    private void carve(Point from, Point to) {
        if (to.x > from.x && to.y == from.y) {
            tiles[to.x][to.y] = 1;
            tiles[to.x - 1][to.y] = 1;
        }
        if (to.x < from.x && to.y == from.y) {
            tiles[to.x][to.y] = 1;
            tiles[to.x + 1][to.y] = 1;
        }

        if (to.y > from.y && to.x == from.x) {
            tiles[to.x][to.y] = 1;
            tiles[to.x][to.y - 1] = 1;
        }
        if (to.y < from.y && to.x == from.x) {
            tiles[to.x][to.y] = 1;
            tiles[to.x][to.y + 1] = 1;
        }
    }

    private ArrayList<Point> getNeighbours(Point p) {
        Point up = new Point(p.x, p.y - 2);
        Point down = new Point(p.x, p.y + 2);
        Point left = new Point(p.x - 2, p.y);
        Point right = new Point(p.x + 2, p.y);
        ArrayList<Point> neighbours = new ArrayList<Point>();
        if (isValid(up))
            neighbours.add(up);
        if (isValid(down))
            neighbours.add(down);
        if (isValid(left))
            neighbours.add(left);
        if (isValid(right))
            neighbours.add(right);
        return neighbours;
    }

    private boolean isValid(Point p) {
        if (p.x < 0 || p.y < 0)
            return false;
        if (p.x >= w || p.y >= h)
            return false;
        if (tiles[p.x][p.y] != 0)
            return false;
        return true;
    }

    public int randOdd(int b) {
        return (rand.nextInt((b - 1) / 2) * 2) + 1;
    }

    public void startMaze() {
        tiles = new int[w][h];
        mazeStack.clear();
        i = 0;
        currentTile = new Point(randOdd(w), randOdd(h));

        colorMap = new int[w][h];
        if(Config.CURRENT_COLOR_MODE == Color_Mode.RAINBOW) {
            for (int x = 0; x != w; x++) {
                for (int y = 0; y != h; y++) {
                    colorMap[x][y] = rand.nextInt();
                }
            }
        }else if(Config.CURRENT_COLOR_MODE == Color_Mode.COLOR_SET){
            ColorSet i =Config.CURRENT_SET;
            for (int x = 0; x != w; x++) {
                for (int y = 0; y != h; y++) {
                    colorMap[x][y] = i.getColors()[rand.nextInt(i.getColors().length)].hashCode();
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            new SettingsFrame();
        }else{
            if(args[0].startsWith("/c")){
                new SettingsFrame();
            }else if(args[0].equalsIgnoreCase("/s")){
                Config.load();
                MazeFrame f = new MazeFrame();
            }
        }
    }

    public int getHeight() {
        return h;
    }

    public int getWidth() {
        return w;
    }

    public Point getCurrentTile() {
        return currentTile;
    }
}
