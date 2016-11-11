package com.brianscottrussell.gameoflife;

/*
 * Copyright (c) 2016 Brian Scott Russell  All Rights Reserved.
 * The source code is owned by Brian Scott Russell and is protected by copyright
 * laws and international copyright treaties, as well as other intellectual
 * property laws and treaties.
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * The 2-dimensional grid of Cells used by the game
 *
 * @author brussell
 */
public class GameGrid {

    private static final String LF = System.lineSeparator();

    public static final String DEFAULT_GAME_GRID_INPUT =
            "4 8" + LF
            + "........" + LF
            + "....*..." + LF
            + "...**..." + LF
            + "........"
            ;

    private CellStatus[][] grid = new CellStatus[0][0];
    private int rowCount = 0;
    private int colCount = 0;

    /**
     * Constructor for a grid of size (rowCount x colCount) where all cells are Dead
     *
     * @param rowCount int as # of rows in the grid
     * @param colCount int as # of cols in the grid
     */
    public GameGrid(int rowCount, int colCount) {
        // initialize the grid with the given row & column counts
        initializeGrid(rowCount, colCount);
    }

    /**
     * Constructor which builds a GameGrid object from the given String
     *
     * @param gridAsString String
     */
    public GameGrid(String gridAsString) {
        // validate input string
        if(isGridInputStringValid(gridAsString)) {
            // Grid Input is valid
            int rowCount = 0;
            int colCount = 0;

            // get row/col counts from input
            try {
                // TODO: get the character(s), before the 1st space as the rowCount
                rowCount = Integer.valueOf(StringUtils.substring(gridAsString, 0, StringUtils.indexOf(gridAsString, ' ')));
            } catch (NumberFormatException e) {
                // TODO: handle error more gracefully
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            try {
                // TODO: get the character(s), before the 1st carriage return as the rowCount
                colCount = Integer.valueOf(StringUtils.substring(gridAsString, StringUtils.indexOf(gridAsString,  ' ') + 1, StringUtils.indexOf(gridAsString, LF)));
            } catch (NumberFormatException e) {
                // TODO: handle error more gracefully
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            // initialize grid
            initializeGrid(rowCount, colCount);

            if(rowCount > 0 && colCount > 0) {
                // split the input string so that we're getting all lines of the string as rows, after the first line.
                //   this is the input Grid that will give us the starting point of each cell as Alive or Dead
                String[] gridRows = StringUtils.split(StringUtils.substringAfter(gridAsString, LF), LF);
                // iterate over the rows of the grid
                for (String row : gridRows) {
                    // TODO: update live cells using input
                }
            }


        }
        else {
            // Grid Input is invalid
            // initialize empty grid
            initializeGrid(0, 0);
        }

    }

    private enum CellStatus {
        Alive,
        Dead;

        public char getPrintSymbol() {
            switch(this) {
                case Alive:
                    return '*';
                case Dead:
                    return '.';
                default:
                    return Dead.getPrintSymbol();
            }
        }
    }


    /**
     * Sets up the grid as the given size (rowCount * colCount) with all Dead cells
     *
     * @param rowCount int as number of rows in the grid
     * @param colCount int as nunber of columns in the grid
     */
    private void initializeGrid(int rowCount, int colCount) {
        // initialize the grid with the given row & column counts
        this.rowCount = rowCount > 0 ? rowCount : 0;
        this.colCount = colCount > 0 ? colCount : 0;
        // create grid using rowCount & colCount
        this.grid = new CellStatus[this.rowCount][this.colCount];
        // start with a Dead Grid
        killGrid();
    }

    /**
     *  Sets all cells in this GameGrid's grid to the CellStatus Dead
     */
    private void killGrid(){
        if(null != this.grid && grid.length > 0 && this.rowCount > 0 && this.colCount > 0) {
            // iterate through the rows and set each cell to Dead
            for (int y = 0; y < this.rowCount; y++) {
                Arrays.fill(this.grid[y], CellStatus.Dead);
            }
        }
    }

    /**
     * Given the String input, initializes this GameGrid object.
     *
     * Expected format of String (very strict):
     *
        r c/n
        ......../n
        ....*.../n
        ...**.../n
        ........
     *
     *  where r = an integer value indicating the number of rows in the grid
     *  where c = an integer value indicating the number of columns in the grid
     *
     * @param gridInput String as input String
     * @return boolean as true if input String is valid
     */
    private boolean isGridInputStringValid(String gridInput) {
        if(StringUtils.isNotBlank(gridInput)) {
            return true;
        }

        return false;
    }

    /**
     * Retrieves the Alive/Dead status of the cell indicated by the coordinates.
     *  if the coordinates are not found in the grid, Dead is returned
     *
     * @param row int
     * @param col int
     * @return CellStatus
     */
    private CellStatus getCellStatus(int row, int col) {
        return isValidCell(row, col) ? grid[row][col] : CellStatus.Dead;
    }

    /**
     * Give the grid coordinates indicated by row & col:
     *  return true if the coordinates exist in this grid
     *  return false if the coordinates do not exist in this grid
     *
     * @param row int
     * @param col int
     * @return boolean
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && col >= 0 && row < this.rowCount && col < this.colCount;
    }



    /**
     * Prints the Grid without printing the current Generation as header
     */
    public void printGrid() {
        printGrid(0);
    }

    /**
     * Builds a string representation of the GameGrid and prints to System.out
     *  if given a non null generation greater than 0, adds a header
     *    "Generation X" above the grid
     */
    public void printGrid(Integer generation) {
        StringBuilder output = new StringBuilder();

        if(null != generation && generation > 0) {
            output.append("Generation ").append(generation);
        }

        for (int row = 0; row < this.rowCount; row++) {
            for(int col = 0; col < this.colCount; col++) {
                // if this is the 1st column of the row, add return carriage
                if(col == 0) {
                    output.append(LF);
                }
                output.append(isValidCell(row, col) ? this.grid[row][col].getPrintSymbol() : '_');
            }
        }

        System.out.println(output.toString());
    }

    @Override
    public String toString() {
        return "GameGrid{" +
                "grid=" + Arrays.deepToString(grid) +
                ", rowCount=" + rowCount +
                ", colCount=" + colCount +
                '}';
    }
}
