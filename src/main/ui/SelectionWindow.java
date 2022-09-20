package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Window for customizing the board.
public class SelectionWindow extends JFrame implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final Font font = new Font("Serif Plain", Font.BOLD, 30);

    JRadioButton beginner;
    JRadioButton intermediate;
    JRadioButton advanced;
    JButton confirmation;

    //EFFECTS: Creates a window that allows users to customize their game board
    public SelectionWindow() {
        super("Set Up Board");
        initializeGraphics();
    }

    //MODIFIES: this
    //EFFECTS: Creates the graphics for this window
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        add(boardSetupPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    //EFFECTS: Creates the panel that allows users to pick a board size from three options
    public JPanel boardSetupPanel() {
        JPanel boardSetup = new JPanel();
        boardSetup.setLayout(null);

        beginner = new JRadioButton("Beginner: 9x9, 10 mines");
        beginner.setFont(font);
        beginner.setBounds(10,20,400, 50);
        beginner.addActionListener(new RadioListener());
        boardSetup.add(beginner);

        intermediate = new JRadioButton("Intermediate: 16x16, 40 mines");
        intermediate.setFont(font);
        intermediate.setBounds(10,80,450, 50);
        intermediate.addActionListener(new RadioListener());
        boardSetup.add(intermediate);

        advanced = new JRadioButton("Advanced: 30x16, 100 mines");
        advanced.setFont(font);
        advanced.setBounds(10,140,450, 50);
        advanced.addActionListener(new RadioListener());
        boardSetup.add(advanced);

        confirmation = new JButton("Continue");
        confirmation.setBounds(WIDTH/2-100, 200, 200, 50);
        confirmation.setFont(font);
        confirmation.setVisible(false);
        confirmation.addActionListener(this);
        boardSetup.add(confirmation);
        return boardSetup;
    }

    //EFFECTS: Closes window and opens window with the game itself
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(beginner.isSelected()){
                new GameWindow(9, 9, 10);
            }
            else if(intermediate.isSelected()) {
                new GameWindow(16, 16, 40);
            } else {
                new GameWindow(30, 16, 100);
            }
        }
        catch(Exception exception){
            return;
        }
    }

    private class RadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            confirmation.setVisible(true);
        }
    }
}