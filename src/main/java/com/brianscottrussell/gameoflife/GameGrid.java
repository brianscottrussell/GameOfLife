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

    private static final char DEAD_SYMBOL = '.';
    private static final char ALIVE_SYMBOL = '*';

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
    private int generation = 1;

    /**
     * Constructor for a grid of size (rowCount x colCount) where all cells are Dead
     *
     * @param rowCount int as # of rows in the grid
     * @param colCount int as # of cols in the grid
     * @param generation int as the starting generation #
     */
    public GameGrid(int rowCount, int colCount, int generation) {
        // initialize the grid with the given row & column counts
        initializeGrid(rowCount, colCount);
        this.generation = generation;
    }

    /**
     * Constructor which builds a GameGrid object from the given String
     *
     * @param gridAsString String
     * @param generation int as the starting generation #
     */
    public GameGrid(String gridAsString, int generation) {
        // validate input string
        if(!isGridInputStringValid(gridAsString)) {
            // Grid Input is invalid
            // initialize empty grid
            initializeGrid(0, 0);
        }
        else {
            // Grid Input is valid
            int rowCount = 0;
            int colCount = 0;

            // get row/col counts from input
            try {
                // get the character(s), before the 1st space as the rowCount
                rowCount = Integer.valueOf(StringUtils.substring(gridAsString, 0, StringUtils.indexOf(gridAsString, ' ')));
            } catch (NumberFormatException e) {
                // TODO: handle error more gracefully
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            try {
                // get the character(s), before the 1st carriage return as the rowCount
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
                // todo: improve from O(n^2)
                int rowIndex = 0;
                for (String row: gridRows) {
                    // iterate over the chars of the row String
                    int colIndex = 0;
                    for(char symbol: row.toCharArray()) {
                        // check if a valid CellStatus symbol, otherwise skip it.
                        if(CellStatus.isValidCellStatusSymbol(symbol)) {
                            // if the cell is "alive", make it Alive in the CellStatus grid
                            this.grid[rowIndex][colIndex] = CellStatus.getCellStatusBySymbol(symbol);
                            colIndex++;
                        }
                    }
                    rowIndex++;
                }
            }
        }

        this.generation = generation;
    }

    public int getGeneration() {
        return generation;
    }

    private enum CellStatus {
        Dead(DEAD_SYMBOL),
        Alive(ALIVE_SYMBOL)
        ;

        private char symbol;

        CellStatus(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        /**
         * Simply checks the symbol to see if it matches the Alive symbol.
         *   if so, returns Alive
         *   otherwise returns Dead
         *
         * @param symbol as the symbol to test
         * @return CellStatus as Dead by default
         */
        public static CellStatus getCellStatusBySymbol(char symbol) {
            return symbol == Alive.getSymbol() ? Alive : Dead;
        }

        /**
         * Helper method to test if the symbol is one for the Dead enum
         *
         * @param symbol char as the symbol to test
         * @return boolean true if is the symbol for Dead
         */
        public static boolean isDeadSymbol(char symbol) {
            return symbol == CellStatus.Dead.getSymbol();
        }

        /**
         * Helper method to test if the symbol is one for the Alive enum
         *
         * @param symbol char as the symbol to test
         * @return boolean true if is the symbol for Alive
         */
        public static boolean isAliveSymbol(char symbol) {
            return symbol == CellStatus.Alive.getSymbol();
        }

        /**
         * Helper method to test if the symbol is valid as either a Dead or Alive symbol
         *
         * @param symbol char as the symbol to test
         * @return boolean true if is the symbol for Dead or Alive
         */
        public static boolean isValidCellStatusSymbol(char symbol) {
            return isDeadSymbol(symbol) || isAliveSymbol(symbol);
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
        // TODO: check string is valid
        if(StringUtils.isNotBlank(gridInput)) {
            return true;
        }

        return false;
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  return true if the coordinates exist in this grid
     *  return false if the coordinates do not exist in this grid
     *
     * @param row int
     * @param col int
     * @return boolean
     */
    private boolean isCellInGrid(int row, int col) {
        return (row >= 0 && col >= 0) && (row < this.rowCount && col < this.colCount);
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
        return isCellInGrid(row, col) ? grid[row][col] : CellStatus.Dead;
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  return true if the current cell is Dead
     *
     * @param row int
     * @param col int
     * @return boolean true if this cell is Dead
     */
    private boolean isCellDead(int row, int col) {
        return getCellStatus(row, col).equals(CellStatus.Dead);
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  return true if the current cell is Alive
     *
     * @param row int
     * @param col int
     * @return boolean true if this cell is Alive
     */
    private boolean isCellAlive(int row, int col) {
        return getCellStatus(row, col).equals(CellStatus.Alive);
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  checks the adjacent cells to count how many are alive
     *  this takes into account that the given coordinate might be at the edge of the grid
     *
     * @param row int
     * @param col int
     * @return int as count of living neighbors
     */
    private int countLivingNeighbors(int row, int col) {

        int livingNeighborCount = 0;

        /**
         * Checks if the upper left cell is alive (O = current cell, # = cell to check)
             #xx
             xOx
             xxx
        */
        if(isCellInGrid(row - 1, col - 1) && isCellAlive(row - 1, col - 1)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the upper cell is alive (O = current cell, # = cell to check)
             x#x
             xOx
             xxx
        */
        if(isCellInGrid(row - 1, col) && isCellAlive(row - 1, col)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the upper right cell is alive (O = current cell, # = cell to check)
             xx#
             xOx
             xxx
        */
        if(isCellInGrid(row - 1, col + 1) && isCellAlive(row - 1, col + 1)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the left cell is alive (O = current cell, # = cell to check)
             xxx
             #Ox
             xxx
        */
        if(isCellInGrid(row, col - 1) && isCellAlive(row, col - 1)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the right cell is alive (O = current cell, # = cell to check)
             xxx
             xO#
             xxx
        */
        if(isCellInGrid(row, col + 1) && isCellAlive(row, col + 1)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the lower left cell is alive (O = current cell, # = cell to check)
             xxx
             xOx
             #xx
        */
        if(isCellInGrid(row + 1, col - 1) && isCellAlive(row + 1, col - 1)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the lower cell is alive (O = current cell, # = cell to check)
             xxx
             xOx
             x#x
        */
        if(isCellInGrid(row + 1, col) && isCellAlive(row + 1, col)) {
            livingNeighborCount++;
        }
        /**
         * Checks if the lower right cell is alive (O = current cell, # = cell to check)
             xxx
             xOx
             xx#
        */
        if(isCellInGrid(row + 1, col + 1) && isCellAlive(row + 1, col + 1)) {
            livingNeighborCount++;
        }

        return livingNeighborCount;
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  run the 1st rule outlined in the game on the cell during a generation change
     *
     *   1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
     *
     * @param row int
     * @param col int
     */
    private boolean matchesRuleOne(int row, int col) {
        return isCellAlive(row, col) && countLivingNeighbors(row, col) < 2;
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  run the 2nd rule outlined in the game on the cell during a generation change
     *
     *   2. Any live cell with more than three live neighbours dies, as if by overcrowding.
     *
     * @param row int
     * @param col int
     */
    private boolean matchesRuleTwo(int row, int col) {
        return isCellAlive(row, col) && countLivingNeighbors(row, col) > 3;
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  run the 3rd rule outlined in the game on the cell during a generation change
     *
     *   3. Any live cell with two or three live neighbours lives on to the next generation.
     *
     * @param row int
     * @param col int
     */
    private boolean matchesRuleThree(int row, int col) {
        int livingNeighbors = countLivingNeighbors(row, col);
        return isCellAlive(row, col) && (livingNeighbors == 2 || livingNeighbors == 3);
    }

    /**
     * Given the grid coordinates indicated by row & col:
     *  run the 4th rule outlined in the game on the cell during a generation change
     *
     *   4. Any dead cell with exactly three live neighbours becomes a live cell.
     *
     * @param row int
     * @param col int
     */
    private boolean matchesRuleFour(int row, int col) {
        return isCellDead(row, col) && countLivingNeighbors(row, col) == 3;
    }

    @Override
    public String toString() {
        return "GameGrid{" +
                "grid=" + Arrays.deepToString(grid) +
                ", rowCount=" + rowCount +
                ", colCount=" + colCount +
                '}';
    }

    /**
     * Builds a String representation of the GameGrid
     */
    public String asString() {
        StringBuilder output = new StringBuilder();

        if(null != this.grid) {
            // TODO: can do better than O(n^2)?
            for (int row = 0; row < this.rowCount; row++) {
                for (int col = 0; col < this.colCount; col++) {
                    // if this is the 1st column of the row, add return carriage
                    if (col == 0) {
                        output.append(LF);
                    }
                    output.append(isCellInGrid(row, col) ? this.grid[row][col].getSymbol() : '_');
                }
            }
        }

        return output.toString();
    }

    /**
     * runs the rules on the grid to move to the next generation
     *  the result is an updated grid and an incremented generation
     */
    public void incrementGeneration() {
        CellStatus[][] nextGenerationGrid = new CellStatus[rowCount][colCount];
        // TODO: can do better than O(n^2)?
        for (int row = 0; row < this.rowCount; row++) {
            for(int col = 0; col < this.colCount; col++) {
                // enforce rules on this cell
                if(isCellInGrid(row, col)) {
                    //   1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
                    if(matchesRuleOne(row, col)) {
                        // kill cell
                        nextGenerationGrid[row][col] = CellStatus.Dead;
                    }
                    //   2. Any live cell with more than three live neighbours dies, as if by overcrowding.
                    else if(matchesRuleTwo(row, col)) {
                        // kill cell
                        nextGenerationGrid[row][col] = CellStatus.Dead;
                    }
                    //   3. Any live cell with two or three live neighbours lives on to the next generation.
                    else if(matchesRuleThree(row, col)) {
                        // resurrect cell
                        nextGenerationGrid[row][col] = CellStatus.Alive;
                    }
                    //   4. Any dead cell with exactly three live neighbours becomes a live cell.
                    else if(matchesRuleFour(row, col)) {
                        // resurrect cell
                        nextGenerationGrid[row][col] = CellStatus.Alive;
                    }
                    else {
                        // just copy cell
                        nextGenerationGrid[row][col] = this.grid[row][col];
                    }
                }
            }
        }

        this.grid = nextGenerationGrid;
        this.generation++;
    }
}
