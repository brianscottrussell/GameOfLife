package com.brianscottrussell.gameoflife;

/*
 * Copyright (c) 2016 Brian Scott Russell  All Rights Reserved.
 * The source code is owned by Brian Scott Russell and is protected by copyright
 * laws and international copyright treaties, as well as other intellectual
 * property laws and treaties.
 */

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

    public static void main(String[] args) {
        runGameOfLife(GameGrid.DEFAULT_GAME_GRID_INPUT, 2);
    }

    private static void runGameOfLife(String gameGridInput, Integer generations) {
        // debug: print input
        System.out.println("Game Grid Input");
        System.out.println(gameGridInput);

        // instantiate a new GameGrid using the input as string
        GameGrid gameGrid = new GameGrid(gameGridInput);

        // start at generation 1, which is the initial grid
        int generation = 1;
        // print grid
        gameGrid.printGrid(generation);

        while(generation < generations) {
            // iterate the generation
            generation++;
            // TODO: update the gameGrid for this generation
            // print grid
            gameGrid.printGrid(generation);
        }
    }


}
