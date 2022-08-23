package main.test;

import main.model.Board;
import main.model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    public Tile tile;
    public Tile mine;

    @BeforeEach
    public void setup() {
        tile = new Tile(false,4);
        mine = new Tile(true,0);
    }

    @Test
    public void constructorTest() {
        assertFalse(tile.isRevealed());
        assertFalse(mine.isRevealed());
        assertFalse(tile.isFlagged());
        assertFalse(mine.isFlagged());
        assertFalse(tile.isMine());
        assertTrue(mine.isMine());
        assertEquals(4, tile.getNearbyMines());
        assertEquals(0, mine.getNearbyMines());
    }

    @Test
    public void flagTest() {
        tile.flag();
        assertTrue(tile.isFlagged());
    }

    @Test
    public void unflagTest() {
        tile.flag();
        assertTrue(tile.isFlagged());
        tile.unflag();
        assertFalse(tile.isFlagged());
    }

    @Test
    public void revealTest() {
        tile.reveal();
        assertTrue(tile.isRevealed());
    }
}
