package uk.co.jdpatrick.MazeSaver;

import uk.co.jdpatrick.MazeSaver.ColorSets.ColorSet;
import uk.co.jdpatrick.MazeSaver.Util.ColorChooserButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Created by jackp on 05/11/2017.
 */
public class SettingsFrame extends JFrame {

    public SettingsFrame() {
        try {
            Config.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        setLayout(layout);

        //Steps per frame
        JLabel stepsPerFrame_label = new JLabel("Steps Per Frame:");
        JTextPane stepsPerFrame_input = new JTextPane();
        stepsPerFrame_input.setText(Config.STEPS_PER_FRAME + "");
        c.gridx = 0;
        c.gridy = 0;
        add(stepsPerFrame_label, c);
        c.gridx = 1;
        add(stepsPerFrame_input, c);

        c.gridx = 0;
        //Color Mode dropdown
        JLabel colorMode_label = new JLabel("Colour Mode:");
        JComboBox<Color_Mode> colorMode_input = new JComboBox<Color_Mode>(Color_Mode.values());
        colorMode_input.setSelectedItem(Config.CURRENT_COLOR_MODE);
        c.gridy = 1;
        add(colorMode_label, c);
        c.gridx = 1;
        add(colorMode_input, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        final JPanel[] currentPanel = {setupColorArea()};
        add(currentPanel[0], c);

        colorMode_input.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Config.CURRENT_COLOR_MODE = (Color_Mode) e.getItem();
                    remove(currentPanel[0]);
                    currentPanel[0] = setupColorArea();
                    c.gridx = 0;
                    c.gridy = 2;
                    c.gridwidth = 2;
                    add(currentPanel[0], c);
                    revalidate();
                    repaint();
                    pack();
                }
            }
        });

        JButton saveButton = new JButton("Save Config");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Config.STEPS_PER_FRAME = Integer.parseInt(stepsPerFrame_input.getText());
                    Config.save();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        c.gridy = 3;
        add(saveButton, c);
        pack();
        setVisible(true);
    }

    private JPanel setupColorArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        switch (Config.CURRENT_COLOR_MODE) {
            case TWO_COLOR:
                JLabel wallColor_label = new JLabel("Wall Colour:");
                ColorChooserButton wallColor_inputButton = new ColorChooserButton(Config.WALL_COLOR);
                panel.add(wallColor_label);
                panel.add(wallColor_inputButton);
                wallColor_inputButton.addColorChangedListener(new ColorChooserButton.ColorChangedListener() {
                    @Override
                    public void colorChanged(Color newColor) {
                        Config.WALL_COLOR = newColor;
                    }
                });

                JLabel floorColor_label = new JLabel("Floor Colour:");
                ColorChooserButton floorColor_inputButton = new ColorChooserButton(Config.FLOOR_COLOR);
                panel.add(floorColor_label);
                panel.add(floorColor_inputButton);
                floorColor_inputButton.addColorChangedListener(new ColorChooserButton.ColorChangedListener() {
                    @Override
                    public void colorChanged(Color newColor) {
                        Config.FLOOR_COLOR = newColor;
                    }
                });

                break;
            case RAINBOW:
                break;
            case COLOR_SET:
                JLabel colorMode_label = new JLabel("Colour Set:");
                JComboBox<ColorSet> colorMode_input = new JComboBox<ColorSet>(ColorSet.sets.toArray(new ColorSet[0]));
                panel.add(colorMode_label);
                panel.add(colorMode_input);
                break;
        }
        return panel;
    }

    public static void main(String[] args) {
        new SettingsFrame();
    }
}
