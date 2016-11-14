/*
 * Copyright (c) 2016 Brian Scott Russell  All Rights Reserved.
 * The source code is owned by Brian Scott Russell and is protected by copyright
 * laws and international copyright treaties, as well as other intellectual
 * property laws and treaties.
 */

import com.brianscottrussell.gameoflife.GameGrid;
import org.junit.Test;

/**
 * @author brussell
 */
public class GameGridTest {

    private static final String LF = System.lineSeparator();

    private static final String TEST_3x3_GAME_GRID_ALL_DEAD =
            "3 3" + LF
            + "..." + LF
            + "..." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_1_ALIVE =
            "3 3" + LF
            + "*.." + LF
            + "..." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_2_ALIVE =
            "3 3" + LF
            + "**." + LF
            + "..." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_3_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "..." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_4_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "*.." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_5_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "**." + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_6_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "***" + LF
            + "..." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_7_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "***" + LF
            + "*.." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_8_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "***" + LF
            + "**." + LF
            ;
    private static final String TEST_3x3_GAME_GRID_9_ALIVE =
            "3 3" + LF
            + "***" + LF
            + "***" + LF
            + "***" + LF
            ;

    public void testAllDead() {
        GameGrid gameGrid = new GameGrid(TEST_3x3_GAME_GRID_ALL_DEAD, 1);
        gameGrid.incrementGeneration();

    }
}
