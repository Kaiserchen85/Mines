package main.ui;

import main.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

//Window of the playable minesweeper board
public class GameWindow extends JFrame {

    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1400;
    public static final Font font = new Font("Serif Plain", Font.BOLD, 30);

    public TileButton[][] buttonBoard;
    public Board board;
    public JPanel flagCountPanel;
    public JPanel boardPanel;
    public Timer timer;
    public JLabel timerDisplay;
    public int height;
    public int width;
    public int flagsLeft;
    public int second;
    public int minute;
    public String decimalFormatSecond;
    public String decimalFormatMinute;
    DecimalFormat decimalFormat;

    //EFFECTS: Creates window for the game
    public GameWindow(int height, int width, int mines) {
        board = new Board(height, width, mines);
        this.height = height;
        this.width = width;
        flagsLeft = mines;
        timer = new Timer(1000, new TimerListener());
        second = 0;
        minute = 0;
        decimalFormat = new DecimalFormat("00");
        timerDisplay = new JLabel();
        timerDisplay.setText("00:00");
        buttonBoard = new TileButton[height][width];
        initializeGraphics();
        timer.start();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(height, width));
        setupBoard();
        add(boardPanel);
    }

    //EFFECTS: Creates the graphics for this window
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        add(flagCountPanel(), BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Makes a panel that tracks the number of "mines" flagged and timer
    private JPanel flagCountPanel() {
        flagCountPanel = new JPanel();
        flagCountPanel.setLayout(new GridLayout(1, 2));
        JLabel label = new JLabel("Flags Left: " + flagsLeft);
        label.setFont(font);
        flagCountPanel.add(label);
        makeTimer();
        return flagCountPanel;
    }

    //EFFECTS: Creates timer display
    private void makeTimer() {
        timerDisplay.setFont(font);
        flagCountPanel.add(timerDisplay);
    }

    //MODIFIES: this
    //EFFECTS: Creates the board as an array of buttons to press
    private void setupBoard() {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                buttonBoard[i][j] = new TileButton(board.getBoardTile()[i][j], this);
                boardPanel.add(buttonBoard[i][j]);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Decreases flag count and reflects change on the flag count panel
    public void decreaseFlagsLeft() {
        flagsLeft--;
        remove(flagCountPanel);
        add(flagCountPanel(), BorderLayout.NORTH);
        flagCountPanel.revalidate();
        flagCountPanel.repaint();
    }

    //MODIFIES: this
    //EFFECTS: Increases flag count and reflects change on the flag count panel
    public void increaseFlagsLeft() {
        flagsLeft++;
        remove(flagCountPanel);
        add(flagCountPanel(), BorderLayout.NORTH);
        flagCountPanel.revalidate();
        flagCountPanel.repaint();
    }

    //EFFECTS: Returns true if all non-mine tiles are revealed
    public boolean isFinished() {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                if(!board.getBoardTile()[i][j].isMine() && !board.getBoardTile()[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    //EFFECTS: Ends game by revealing all the tiles
    public void endGame(boolean winner) {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                buttonBoard[i][j].reveal();
            }
        }
        timer.stop();
        remove(flagCountPanel);
        flagCountPanel = new JPanel();
        flagCountPanel.setLayout(new GridLayout(1, 3));
        JLabel label = new JLabel("Flags Left: " + flagsLeft);
        label.setFont(font);
        flagCountPanel.add(label);
        JLabel text = new JLabel();
        text.setFont(font);
        if(winner) {
            text.setText("You Win!");
        } else {
            text.setText("Oops! You hit a mine!");
        }
        flagCountPanel.add(text);
        makeTimer();
        add(flagCountPanel, BorderLayout.NORTH);
        flagCountPanel.revalidate();
        flagCountPanel.repaint();

    }

    //EFFECTS: Finds button that was pressed in the 2D button array and returns the row and column
    public ArrayList<Integer> findTile(Object source) {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                if(source.equals(buttonBoard[i][j])) {
                    ArrayList<Integer> tilePosition = new ArrayList<>();
                    tilePosition.add(i);
                    tilePosition.add(j);
                    return tilePosition;
                }
            }
        }
        return null;
    }

    //EFFECTS: Checks the number of flags place around a certain button/tile
    public int checkSurrounding(int row, int column) {
        int flagCount = 0;
        for(int i=row-1;i<row+2;i++) {
            for(int j=column-1;j<column+2;j++) {
                if (i>=0 && j>=0 && i<height && j<width) {
                    if (board.getBoardTile()[i][j]!=null) {
                        if (board.getBoardTile()[i][j].isFlagged()) {
                            flagCount++;
                        }
                    }
                }
            }
        }
        return flagCount;
    }

    //EFFECTS: Reveals the surrounding tiles of a fixed tile. Does so recursively if there are no nearby mines
    //         If it hits a mine, it ends the game
    public void openSurrounding(int row, int column) {
        for(int i=row-1;i<row+2;i++) {
            for(int j=column-1;j<column+2;j++) {
                if (i>=0 && j>=0 && i<height && j<width) {
                    if (!buttonBoard[i][j].getTile().isFlagged()) {
                        if (buttonBoard[i][j].getTile().isMine()) {
                            endGame(false);
                        } else if (buttonBoard[i][j].getTile().getNearbyMines() == 0
                                && !buttonBoard[i][j].getTile().isRevealed()) {
                            buttonBoard[i][j].reveal();
                            openSurrounding(i, j);
                        } else {
                            buttonBoard[i][j].reveal();
                        }
                    }
                }
            }
        }
    }

    private class TimerListener implements ActionListener {
        //EFFECTS: Increments timer
        @Override
        public void actionPerformed(ActionEvent e) {
            if(minute == 99 && second == 59) {
                timer.stop();
            }
            second++;
            if(second == 60) {
                second = 0;
                minute++;
            }
            decimalFormatMinute = decimalFormat.format(minute);
            decimalFormatSecond = decimalFormat.format(second);
            timerDisplay.setText(decimalFormatMinute + ":" + decimalFormatSecond);
        }
    }
}