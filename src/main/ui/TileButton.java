package main.ui;

import main.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//Represents the tile as an interactive button
public class TileButton extends JButton {

    public static final Font font = new Font("Serif Plain", Font.BOLD, 40);

    public Tile tile;
    public GameWindow gameWindow;

    //EFFECTS: Creates a button that represents a tile
    public TileButton(Tile tile, GameWindow gameWindow) {
        this.tile = tile;
        this.gameWindow = gameWindow;
        setFont(font);
        addMouseListener(new TileListener());
    }

    //MODIFIES: this
    //EFFECTS: Reveals the identity of button
    public void reveal() {
        tile.reveal();
        if(tile.isMine()) {
            setText("M");
            if (tile.isFlagged()) {
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.RED);
            }
        } else {
            setText(Integer.toString(tile.getNearbyMines()));
            setBackground(Color.lightGray);
        }
    }

    public Tile getTile() {
        return tile;
    }

    //Represents the listener for the tile button
    private class TileListener implements MouseListener {

        //MODIFIES: this
        //EFFECTS: Left Click: Reveals tile, if there are no nearby mines, reveals surrounding tiles
        //                     If a surrounding tile has no nearby mines, reveal their surrounding tiles
        //Right Click:         Flags or Unflags tile
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!tile.isRevealed()) {
                if (!tile.isFlagged() && SwingUtilities.isLeftMouseButton(e)) {
                    reveal();
                    if (tile.isMine() || gameWindow.isFinished()) {
                        gameWindow.endGame();
                    } else if (tile.getNearbyMines() == 0) {
                        ArrayList<Integer> tilePosition = gameWindow.findTile(e.getSource());
                        gameWindow.openSurrounding(tilePosition.get(0), tilePosition.get(1));
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (tile.isFlagged()) {
                        tile.unflag();
                        setText("");
                        gameWindow.increaseFlagsLeft();
                    } else {
                        tile.flag();
                        setText("F");
                        gameWindow.decreaseFlagsLeft();
                    }
                }
            }
        }

        //MODIFIES: this
        //EFFECTS: Reveals all surrounding tiles if number of flagged matches number of nearby mines
        //         If a surrounding tile has no nearby mines, reveal their surrounding tiles
        @Override
        public void mousePressed(MouseEvent e) {
            if(tile.isRevealed() && SwingUtilities.isLeftMouseButton(e)) {
                ArrayList<Integer> tilePosition = gameWindow.findTile( e.getSource());
                if(tile.nearbyMines == gameWindow.checkSurrounding(tilePosition.get(0), tilePosition.get(1))) {
                    gameWindow.openSurrounding(tilePosition.get(0), tilePosition.get(1));
                    if(gameWindow.isFinished()) {
                        gameWindow.endGame();
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
