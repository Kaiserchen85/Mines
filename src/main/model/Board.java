package main.model;

import java.util.Random;

//Represents a 2D array of the minesweeper board, with a tile in each board.
public class Board {

    public Tile[][] boardTile;

    //REQUIRES: all integers >0, the number of mines must be less than the dimensions
    //EFFECTS: Creates a randomized board for the mines and tiles based on given dimensions and mine count
    public Board(int height, int width, int mines) {
        boardTile = new Tile[height][width];
        Random random = new Random();
        for(int k=0;k<mines;k++) {
            int column = random.nextInt(height);
            int row = random.nextInt(width);
            if (boardTile[column][row]==null) {
                boardTile[column][row] = new Tile(true, 0);
            } else {
                k--;
            }
        }
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                if(boardTile[i][j] == null) {
                    boardTile[i][j] = new Tile(false, checkSurrounding(i, j, height, width));
                }
            }
        }
    }

    //REQUIRES: integers >=0
    //EFFECTS: checks surroundings for mines and returns the number of mines
    public int checkSurrounding(int column, int row, int height, int width) {
        int mineCount = 0;
        for(int i=column-1;i<column+2;i++) {
            for(int j=row-1;j<row+2;j++) {
                if (i>=0 && j>=0 && i<height && j<width) {
                    if (boardTile[i][j]!=null) {
                        if (boardTile[i][j].isMine()) {
                            mineCount++;
                        }
                    }
                }
            }
        }
        return mineCount;
    }

    public Tile[][] getBoardTile() {
        return boardTile;
    }
}
