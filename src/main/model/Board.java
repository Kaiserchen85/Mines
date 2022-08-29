package main.model;

import java.util.Random;

//Represents a 2D array of the minesweeper board, with a tile in each board.
public class Board {

    public Tile[][] boardTile;

    //REQUIRES: all integers >0, the number of mines must be less than the dimensions,
    //          the number of mines cannot be greater or equal to the total number of tiles
    //EFFECTS: Creates a randomized board for the mines and tiles based on given dimensions and mine count
    public Board(int height, int width, int mines) {
        boardTile = new Tile[height][width];
        Random random = new Random();
        for(int k=0;k<mines;k++) {
            int row = random.nextInt(height);
            int column = random.nextInt(width);
            if (boardTile[row][column]==null) {
                boardTile[row][column] = new Tile(true, 0);
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
    public int checkSurrounding(int row, int column, int height, int width) {
        int mineCount = 0;
        for(int i=row-1;i<row+2;i++) {
            for(int j=column-1;j<column+2;j++) {
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
