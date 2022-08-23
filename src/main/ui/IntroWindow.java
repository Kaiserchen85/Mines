package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

//Window for customizing the board.
public class IntroWindow extends JFrame implements ActionListener {

    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1400;
    public static final int TEXT_FIELD_COLUMN = 5;

    public JTextField height;
    public JTextField width;
    public JTextField mines;

    //EFFECTS: Creates a window that allows users to customize their game board
    public IntroWindow() {
        super("Set Up Board");
        initializeGraphics();
    }

    //MODIFIES: this
    //EFFECTS: Creates the graphics for this window
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        height = new JTextField(TEXT_FIELD_COLUMN);
        width = new JTextField(TEXT_FIELD_COLUMN);
        mines = new JTextField(TEXT_FIELD_COLUMN);
        add(boardSetupPanel(), BorderLayout.NORTH);
        add(new JButton("Ok"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //EFFECTS: Creates the panel that allows users to pick a board size
    public JPanel boardSetupPanel() {
        JPanel boardSetup = new JPanel();
        JLabel heightText = new JLabel("Board Height: ");
        boardSetup.add(heightText);
        boardSetup.add(height);
        JLabel widthText = new JLabel("Board Width: ");
        boardSetup.add(widthText);
        boardSetup.add(width);
        JLabel mineText = new JLabel("Board Mines: ");
        boardSetup.add(mineText);
        boardSetup.add(mines);
        return boardSetup;
    }

    //EFFECTS: Closes window and opens window with the game itself
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new GameWindow(Integer.parseInt(height.getText()),
                Integer.parseInt(width.getText()),
                Integer.parseInt(mines.getText()));
    }
}
