/*
 * Copyright (c) 2016 Brian Scott Russell  All Rights Reserved.
 * The source code is owned by Brian Scott Russell and is protected by copyright
 * laws and international copyright treaties, as well as other intellectual
 * property laws and treaties.
 */

import com.brianscottrussell.gameoflife.GameGrid;
import com.brianscottrussell.gameoflife.GameOfLife;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author brussell
 */
public class GameGridTest {

    private static final String TEST_INVALID_GAME_GRID_HEADER = "G 4" + GameOfLife.LF;
    private static final String TEST_INVALID_GAME_GRID =
              ".*" + GameOfLife.LF
            + ".&@" + GameOfLife.LF
            + ".*"
            ;

    private static final String TEST_3X3_GAME_GRID_HEADER = "3 3" + GameOfLife.LF;
    private static final String TEST_3x3_GAME_GRID_ALL_DEAD =
              "..." + GameOfLife.LF
            + "..." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_1_ALIVE =
              "*.." + GameOfLife.LF
            + "..." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_2_ALIVE =
              "**." + GameOfLife.LF
            + "..." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_3_ALIVE =
              "***" + GameOfLife.LF
            + "..." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_4_ALIVE =
              "***" + GameOfLife.LF
            + "*.." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_5_ALIVE =
              "***" + GameOfLife.LF
            + "**." + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_6_ALIVE =
              "***" + GameOfLife.LF
            + "***" + GameOfLife.LF
            + "..."
            ;
    private static final String TEST_3x3_GAME_GRID_7_ALIVE =
              "***" + GameOfLife.LF
            + "***" + GameOfLife.LF
            + "*.."
            ;
    private static final String TEST_3x3_GAME_GRID_8_ALIVE =
              "***" + GameOfLife.LF
            + "***" + GameOfLife.LF
            + "**."
            ;
    private static final String TEST_3x3_GAME_GRID_9_ALIVE =
              "***" + GameOfLife.LF
            + "***" + GameOfLife.LF
            + "***"
            ;

    @Test
    public void testInvalidGameGridHeaderInput() {
        GameGrid gameGrid = new GameGrid(TEST_INVALID_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD);
        gameGrid.incrementGeneration();
        Assert.assertEquals("", gameGrid.asString());
    }

    @Test
    public void testInvalidGameGridInput() {
        GameGrid gameGrid = new GameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_INVALID_GAME_GRID);
        gameGrid.incrementGeneration();
        Assert.assertEquals(TEST_3x3_GAME_GRID_ALL_DEAD, gameGrid.asString());
    }

    @Test
    public void testIncrementGeneration() {
        GameGrid gameGrid = new GameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD);
        gameGrid.incrementGeneration();
        Assert.assertEquals(2, gameGrid.getGeneration());
    }

    @Test
    public void testAllDead() {
        GameGrid gameGrid = new GameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD);
        gameGrid.incrementGeneration();
        Assert.assertEquals(TEST_3x3_GAME_GRID_ALL_DEAD, gameGrid.asString());
    }
}
