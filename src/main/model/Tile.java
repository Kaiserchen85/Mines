package main.model;

//Represents a single tile in the minesweeper game.
public class Tile {
    public boolean flagged;
    public boolean revealed;
    public boolean mine;
    public int nearbyMines;

    //REQUIRES: integer >0
    //EFFECTS: Creates an unrevealed and unflagged tile and determines:
    //         (1) Whether it is a mine
    //         (2) How many mines are surrounding the perimeter (0 if the tile is a mine itself)
    public Tile(boolean isMine, int nearbyMines) {
        flagged = false;
        revealed = false;
        mine = isMine;
        this.nearbyMines = nearbyMines;
    }

    //MODIFIES: this
    //EFFECTS: Flags tile
    public void flag() {
        flagged = true;
    }

    //MODIFIES: this
    //EFFECTS: Unflags tile
    public void unflag() {
        flagged = false;
    }

    //MODIFIES: this
    //EFFECTS: Reveals tile
    public void reveal() {
        revealed = true;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isMine() {
        return mine;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }
}
