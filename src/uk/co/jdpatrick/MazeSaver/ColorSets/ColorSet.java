package uk.co.jdpatrick.MazeSaver.ColorSets;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jackp on 06/10/2017.
 */
public class ColorSet {
    public static ArrayList<ColorSet> sets = new ArrayList<>();

    static {
        ColorSet greens = new ColorSet("Green", new Color[]{new Color(35, 77, 32),
                new Color(54, 128, 45), new Color(119, 171, 89), new Color(201, 223, 138)});
        sets.add(greens);
    }

    private String name;
    private Color[] colors;

    public ColorSet(String name, Color[] colors) {
        this.name = name;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public Color[] getColors() {
        return colors;
    }

    @Override
    public String toString(){
        return name;
    }

    public static ColorSet fromName(String s) {
        for(ColorSet v : ColorSet.sets.toArray(new ColorSet[0])){
            if(v.getName().equals(s))
                return v;
        }
        return null;
    }
}
