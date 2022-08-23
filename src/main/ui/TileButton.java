package main.ui;

import main.model.Tile;

import javax.swing.*;

//Represents the tile as an interactive button
public class TileButton extends JButton {

    public Tile tile;

    public TileButton(Tile tile) {
        this.tile = tile;
    }
}
