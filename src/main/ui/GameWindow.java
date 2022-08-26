package main.ui;

import main.model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Window of the playable minesweeper board
public class GameWindow extends JFrame {

    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1400;

    public TileButton[][] buttonBoard;
    public Board board;
    public JPanel flagCountPanel;
    public JPanel boardPanel;
    public int height;
    public int width;
    public int flagsLeft;

    //EFFECTS: Creates window for the game
    public GameWindow(int height, int width, int mines) {
        board = new Board(height, width, mines);
        this.height = height;
        this.width = width;
        flagsLeft = mines;
        buttonBoard = new TileButton[height][width];
        initializeGraphics();
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Makes a panel that tracks the number of "mines" flagged
    private JPanel flagCountPanel() {
        flagCountPanel = new JPanel();
        JLabel flagCountLabel = new JLabel(Integer.toString(flagsLeft));
        Font font = flagCountLabel.getFont();
        font = font.deriveFont(font.getSize() * 10);
        flagCountLabel.setFont(font);
        flagCountPanel.add(flagCountLabel);
        return flagCountPanel;
    }

    //MODIFIES: this
    //EFFECTS: Creates the board as an array of buttons to press.
    private void setupBoard() {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                buttonBoard[i][j] = new TileButton(board.getBoardTile()[i][j], this);
                boardPanel.add(buttonBoard[i][j]);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: decreases flag count and reflects change on the flag count panel
    public void decreaseFlagsLeft() {
        flagsLeft--;
        remove(flagCountPanel);
        add(flagCountPanel(), BorderLayout.NORTH);
        flagCountPanel.revalidate();
        flagCountPanel.repaint();
    }

    //MODIFIES: this
    //EFFECTS: increases flag count and reflects change on the flag count panel
    public void increaseFlagsLeft() {
        flagsLeft++;
        remove(flagCountPanel);
        add(flagCountPanel(), BorderLayout.NORTH);
        flagCountPanel.revalidate();
        flagCountPanel.repaint();
    }

    //EFFECTS: ends game by revealing all the tiles
    public void endGame() {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                buttonBoard[i][j].reveal();
            }
        }
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
             //If it hits a mine, it ends the game
    public void openSurrounding(int row, int column) {
        for(int i=row-1;i<row+2;i++) {
            for(int j=column-1;j<column+2;j++) {
                if (i>=0 && j>=0 && i<height && j<width) {
                    if (!buttonBoard[i][j].getTile().isFlagged()) {
                        if (buttonBoard[i][j].getTile().isMine()) {
                            endGame();
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
}
