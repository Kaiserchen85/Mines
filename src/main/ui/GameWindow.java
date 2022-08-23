package main.ui;

import main.model.Board;

import javax.swing.*;

//Window of the playable minesweeper board
public class GameWindow extends JFrame {

    public JButton[][] buttonBoard;
    public Board board;

    //EFFECTS: Creates window for the game
    public GameWindow(int height, int width, int mines) {
        board = new Board(height, width, mines);
        buttonBoard = new JButton[height][width];
        setupBoard(height, width);
    }

    //MODIFIES: this
    //EFFECTS: Creates the board as an array of buttons to press.
    private void setupBoard(int height, int width) {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                buttonBoard[i][j] = new TileButton(board.getBoardTile()[i][j]);
            }
        }
    }
}
