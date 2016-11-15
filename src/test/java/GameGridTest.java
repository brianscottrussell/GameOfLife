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

    @Test
    public void testIncrementGeneration() {
        GameGrid gameGrid = new GameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD);
        gameGrid.incrementGeneration();
        Assert.assertEquals(2, gameGrid.getGeneration());
    }

    @Test
    public void testInvalidGameGridHeaderInput() {
        testGameGrid(TEST_INVALID_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD, "");
    }

    @Test
    public void testInvalidGameGridBodyInput() {
        testGameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_INVALID_GAME_GRID, TEST_3x3_GAME_GRID_ALL_DEAD);
    }

    @Test
    public void testAllDead() {
        testGameGrid(TEST_3X3_GAME_GRID_HEADER + TEST_3x3_GAME_GRID_ALL_DEAD, TEST_3x3_GAME_GRID_ALL_DEAD);
    }

    /**
     * Rule 1 Tests
     *   1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
     */
    @Test
    public void liveCellFewerThanTwoLiveNeighbors1() {
        final String inputGrid =
                "..." + GameOfLife.LF
              + ".*." + GameOfLife.LF
              + "..."
              ;
        final String expectedResult = TEST_3x3_GAME_GRID_ALL_DEAD;

        testGameGrid(TEST_3X3_GAME_GRID_HEADER + inputGrid, expectedResult);
    }
    @Test
    public void liveCellFewerThanTwoLiveNeighbors2() {
        final String inputGrid =
                "**." + GameOfLife.LF
              + "..." + GameOfLife.LF
              + "..."
              ;
        final String expectedResult = TEST_3x3_GAME_GRID_ALL_DEAD;

        testGameGrid(TEST_3X3_GAME_GRID_HEADER + inputGrid, expectedResult);
    }

    /**
     * Rule 2 Tests
     *   2. Any live cell with more than three live neighbours dies, as if by overcrowding.
     */
    @Test
    public void liveCellMoreThanThreeLiveNeighbors() {
        final String inputGrid =
                "***" + GameOfLife.LF
              + "***" + GameOfLife.LF
              + "***"
              ;

        final String expectedResult =
                "*.*" + GameOfLife.LF
              + "..." + GameOfLife.LF
              + "*.*"
              ;

        testGameGrid(TEST_3X3_GAME_GRID_HEADER + inputGrid, expectedResult);
    }

    /**
     * Rule 3 Tests
     *   3. Any live cell with two or three live neighbours lives on to the next generation.
     */
    @Test
    public void liveCellTwoOrThreeLiveNeighbors() {
        final String inputGrid =
                "***" + GameOfLife.LF
              + "*.*" + GameOfLife.LF
              + "***"
              ;

        final String expectedResult =
                "*.*" + GameOfLife.LF
              + "..." + GameOfLife.LF
              + "*.*"
              ;

        testGameGrid(TEST_3X3_GAME_GRID_HEADER + inputGrid, expectedResult);
    }

    /**
     * Rule 4 Tests
     *   4. Any dead cell with exactly three live neighbours becomes a live cell.
     */
    @Test
    public void deadCellExactlyThreeLiveNeighbors() {
        final String inputGrid =
                ".*." + GameOfLife.LF
              + "*.*" + GameOfLife.LF
              + "..."
              ;

        final String expectedResult =
                ".*." + GameOfLife.LF
              + ".*." + GameOfLife.LF
              + "..."
              ;

        testGameGrid(TEST_3X3_GAME_GRID_HEADER + inputGrid, expectedResult);
    }

    /**
     * Basic test of a grid input against the expected & actual result after incrementing the generation once.
     *  Note: the input should include the header indicating row & column counts
     *    e.g.
          3 3
          ...
          .*.
          ...
     *
     * Note: the expectedResult should only be the grid body, not including header
     *    e.g.
          ...
          ...
          ...
     *
     * @param input String representing the full game grid to be tested, including header
     * @param expectedResult String representing the game grid as it should look after a generation, grid only
     */
    private void testGameGrid(String input, String expectedResult) {
        GameGrid gameGrid = new GameGrid(input);
        gameGrid.incrementGeneration();
        Assert.assertEquals(expectedResult, gameGrid.asString());
    }
}
