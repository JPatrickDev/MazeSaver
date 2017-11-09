package uk.co.jdpatrick.MazeSaver;

import uk.co.jdpatrick.MazeSaver.ColorSets.ColorSet;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by jackp on 05/10/2017.
 */
public class Config {

    public static int STEPS_PER_FRAME = 1;
    public static int TARGET_TILE_SIZE = 2;
    public static boolean SHOW_CURRENT_TILE = true;
    public static boolean INSTANT_BACKUP = true;

    //Color settings
    public static ColorSet CURRENT_SET = ColorSet.sets.get(0);
    public static Color WALL_COLOR = Color.black;
    public static Color FLOOR_COLOR = Color.white;
    public static Color_Mode CURRENT_COLOR_MODE = Color_Mode.COLOR_SET;


    public static void save() throws IOException {
        File folder = new File(getUserDataDirectory());
        folder.mkdirs();
        File storageFile = new File(folder, "config");
        BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile));

        writer.write(STEPS_PER_FRAME + "");
        writer.newLine();
        writer.write(TARGET_TILE_SIZE + "");
        writer.newLine();
        writer.write(SHOW_CURRENT_TILE + "");
        writer.newLine();
        writer.write(INSTANT_BACKUP + "");
        writer.newLine();
        writer.write(CURRENT_SET.getName() + "");
        writer.newLine();
        writer.write(rgb2Hex(WALL_COLOR) + "");
        writer.newLine();
        writer.write(rgb2Hex(FLOOR_COLOR) + "");
        writer.newLine();
        writer.write(CURRENT_COLOR_MODE + "");

        writer.flush();
        writer.close();
    }


    public static void load() throws FileNotFoundException {
        File folder = new File(getUserDataDirectory());
        folder.mkdirs();
        File storageFile = new File(folder, "config");
        if (storageFile.exists()) {
            Scanner scanner = new Scanner(storageFile);

            Config.STEPS_PER_FRAME = Integer.parseInt(scanner.nextLine());
            Config.TARGET_TILE_SIZE = Integer.parseInt(scanner.nextLine());
            Config.SHOW_CURRENT_TILE = Boolean.valueOf(scanner.nextLine());
            Config.INSTANT_BACKUP = Boolean.valueOf(scanner.nextLine());

            Config.CURRENT_SET = ColorSet.fromName(scanner.nextLine());
            Config.WALL_COLOR = hex2Rgb(scanner.nextLine());
            Config.FLOOR_COLOR = hex2Rgb(scanner.nextLine());
            Config.CURRENT_COLOR_MODE = Color_Mode.fromName(scanner.nextLine());
        }
    }

    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".MazeSaver" + File.separator;
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public static String rgb2Hex(Color c) {
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }
}


enum Color_Mode {
    TWO_COLOR("Two Colour"), RAINBOW("Rainbow"), COLOR_SET("Colour Set"),BRANCH("Branch");

    private String name;

    Color_Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Color_Mode fromName(String s) {
        for (Color_Mode m : values()) {
            if (m.getName().equalsIgnoreCase(s))
                return m;
        }
        return null;
    }
}