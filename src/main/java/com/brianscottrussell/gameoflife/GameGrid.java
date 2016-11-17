package com.brianscottrussell.gameoflife;

/*
 * Copyright (c) 2016 Brian Scott Russell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * The 2-dimensional grid of Cells used by the game
 *
 * @author brussell
 */
public class GameGrid {

    private static final char DEAD_SYMBOL = '.';
    private static final char ALIVE_SYMBOL = '*';

    private CellStatus[][] grid = new CellStatus[0][0];
    private int rowCount = 0;
    private int colCount = 0;
    private int generation = 1;

    /**
     * Constructor for a grid of size (rowCount x colCount) where all cells are Dead
     *
     * @param rowCount int as # of rows in the grid
     * @param colCount int as # of cols in the grid
     */
    public GameGrid(int rowCount, int colCount) {
        // initialize the grid with the given row & column counts
        initializeGrid(rowCount, colCount);
        this.generation = 1;
    }

    /**
     * Constructor which builds a GameGrid object from the given String
     *
     * if there is an invalid header format then this will initialize the grid as 0x0, empty grid
     * if there is an invalid body format then this will initialize the grid as the header size, all Dead cells
     *
     * @param gridAsString String
     */
    public GameGrid(String gridAsString) {
        // initialize empty grid
        initializeGrid(0, 0);

        if(StringUtils.isNotBlank(gridAsString)) {
            // default the row & col counts to 0
            int rowCount = 0;
            int colCount = 0;

            // get row/col counts from input
            try {
                rowCount = parseRowCountFromInputString(gridAsString);
            } catch (InvalidGameGridInputException e) {
                // TODO: handle error?
                // System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            try {
                // get the character(s), before the 1st carriage return as the rowCount
                colCount = parseColumnCountFromInputString(gridAsString);
            } catch (InvalidGameGridInputException e) {
                // TODO: handle error?
                // System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            // only need to move forward if we retrieved valid row & col counts
            if(rowCount > 0 && colCount > 0) {
                // initialize grid
                initializeGrid(rowCount, colCount);

                // split the input string so that we're getting all lines of the string as rows, after the first line.
                //   this is the input Grid that will give us the starting point of each cell as Alive or Dead
                String[] gridRows = StringUtils.split(StringUtils.substringAfter(gridAsString, GameOfLife.LF), GameOfLife.LF);
                // iterate over the rows of the grid
                // todo: improve from O(n^2)
                int rowIndex = 0;
                for(String row: gridRows) {
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

        // start the generation at 1
        this.generation = 1;
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
     * given the grid input String, parses the row count if this is a valid input String
     *
     * Sample of expected format of input String where rowCount = 4:
     *
        4 8
        ......../n
        ....*.../n
        ...**.../n
        ........
     *
     * @param input String
     * @return int as the row count
     * @throws InvalidGameGridInputException
     */
    private int parseRowCountFromInputString(String input) throws InvalidGameGridInputException {
        try {
            // get the character(s), before the 1st space as the rowCount
            return Integer.valueOf(StringUtils.substring(input, 0, StringUtils.indexOf(input, ' ')));
        } catch (NumberFormatException e) {
            throw new InvalidGameGridInputException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    /**
     * given the grid input String, parses the column count if this is a valid input String
     *
     * Sample of expected format of input String where columnCount = 8:
     *
        4 8
        ......../n
        ....*.../n
        ...**.../n
        ........
     *
     * @param input String
     * @return int as the column count
     * @throws InvalidGameGridInputException
     */
    private int parseColumnCountFromInputString(String input) throws InvalidGameGridInputException {
        try {
            // get the character(s), before the 1st carriage return as the rowCount
            return Integer.valueOf(StringUtils.substring(input, StringUtils.indexOf(input,  ' ') + 1, StringUtils.indexOf(input, GameOfLife.LF)));
        } catch (NumberFormatException e) {
            throw new InvalidGameGridInputException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
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
                    // if this is not the 1st row, and is the 1st column of the row, add return carriage
                    if (row != 0 && col == 0) {
                        output.append(GameOfLife.LF);
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
