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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Implementation of the Game of Life Kata: http://codingdojo.org/cgi-bin/index.pl?KataGameOfLife
 *
 * This Kata is about calculating the next generation of Conway's game of life, given any starting position. See http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life for background.
 *
 * You start with a two dimensional grid of cells, where each cell is either alive or dead. In this version of the problem, the grid is finite, and no life can exist off the edges. When calcuating the next generation of the grid, follow these rules:
 *
 *   1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
 *   2. Any live cell with more than three live neighbours dies, as if by overcrowding.
 *   3. Any live cell with two or three live neighbours lives on to the next generation.
 *   4. Any dead cell with exactly three live neighbours becomes a live cell.
 * You should write a program that can accept an arbitrary grid of cells, and will output a similar grid showing the next generation.
 *
 * @author brussell
 */
public class GameOfLife {

    public static final String LF = System.lineSeparator();

    private static final String DEFAULT_GAME_GRID_INPUT =
            "4 8" + LF
            + "........" + LF
            + "....*..." + LF
            + "...**..." + LF
            + "........"
            ;
    private static final String DEFAULT_INPUT_FILE_LOCATION = "DefaultInputGrid.txt";

    public static void main(String[] args) {
        String inputFileLocation = DEFAULT_INPUT_FILE_LOCATION;

        if(null != args && args.length > 0) {
            inputFileLocation = args[0];
        }

        String inputGrid = readInputGridFromFileLocation(inputFileLocation);

        runGameOfLife(inputGrid, 2);
    }

    /**
     * Runs the Game of Life by printing out the starting grid and the subsequent grids.
     *   The game will run for the count indicated by "generations"
     *
     * @param gameGridInput String
     * @param generations Integer
     */
    private static void runGameOfLife(String gameGridInput, Integer generations) {
        // instantiate a new GameGrid using the input as string
        GameGrid gameGrid = new GameGrid(gameGridInput);

        // print 1st generation
        printGeneration(gameGrid);

        // loop through the generations requested to print out the resulting grid
        while(gameGrid.getGeneration() < generations) {
            // update the gameGrid for this generation
            gameGrid.incrementGeneration();
            // print generation
            printGeneration(gameGrid);
        }
    }

    /**
     * prints the generation with a header and the grid
     *  e.g.
        Generation 1
        ........
        ....*...
        ...**...
        ........
     *
     * @param gameGrid
     */
    private static void printGeneration(GameGrid gameGrid) {
        // adding a line for easier readability
        System.out.println();
        // print grid header
        System.out.println( "Generation " + gameGrid.getGeneration() );
        // print grid
        System.out.println( gameGrid.asString() );
    }

    /**
     * Given the file location, attempts to read the input grid from text file
     *
     * If there is any issue with the file location or reading the file, returns the default game grid
     *
     * @param inputFileLocation String as file location
     * @return String representation of text in file
     */
    private static String readInputGridFromFileLocation(String inputFileLocation) {
        try {
            return new String(Files.readAllBytes(Paths.get(inputFileLocation)));
        } catch (IOException e) {
            System.out.println("Unable to read the file at the location provided. Using default game grid as input.");
        }
        return DEFAULT_GAME_GRID_INPUT;
    }
}
