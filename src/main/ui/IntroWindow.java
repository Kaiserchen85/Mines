package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Window for customizing the board.
public class IntroWindow extends JFrame implements ActionListener {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    public static final int TEXT_FIELD_COLUMN = 5;
    public static final Font font = new Font("Serif Plain", Font.BOLD, 30);

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
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        height = new JTextField(TEXT_FIELD_COLUMN);
        height.setFont(font);
        width = new JTextField(TEXT_FIELD_COLUMN);
        width.setFont(font);
        mines = new JTextField(TEXT_FIELD_COLUMN);
        mines.setFont(font);
        add(boardSetupPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    //EFFECTS: Creates the panel that allows users to pick a board size
    public JPanel boardSetupPanel() {
        JPanel boardSetup = new JPanel();
        boardSetup.setLayout(null);
        JLabel heightText = new JLabel("Board Height: ");
        heightText.setFont(font);
        heightText.setBounds(10,20,250, 50);
        height.setBounds(250, 20, 200, 50);
        JLabel widthText = new JLabel("Board Width: ");
        widthText.setFont(font);
        widthText.setBounds(10,80,250, 50);
        width.setBounds(250, 80, 200, 50);
        JLabel minesText = new JLabel("# of Mines: ");
        minesText.setFont(font);
        minesText.setBounds(10,140,250, 50);
        mines.setBounds(250, 140, 200, 50);
        boardSetup.add(heightText);
        boardSetup.add(height);
        boardSetup.add(widthText);
        boardSetup.add(width);
        boardSetup.add(minesText);
        boardSetup.add(mines);
        JButton confirmation = new JButton("Ok");
        confirmation.setBounds(75, 200, 100, 50);
        confirmation.setFont(font);
        confirmation.addActionListener(this);
        boardSetup.add(confirmation);
        return boardSetup;
    }

    //EFFECTS: Closes window and opens window with the game itself
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int height = Integer.parseInt(this.height.getText());
            int width = Integer.parseInt(this.width.getText());
            int mines = Integer.parseInt(this.mines.getText());
            if (height*width>mines) {
                new GameWindow(height, width, mines);
            }
        }
        catch(Exception exception){
            //nothing happens
        }
    }
}
