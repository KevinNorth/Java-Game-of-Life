package com.kevinnorth.gameoflife.testutil;

import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.ArrayList;

/**
 * Allows creating Grids based off of boolean[][] arrays, making it easier to create Grids in tests
 * in a way that is convenient to read.
 */
public final class GridFactory {
  /**
   * Creates a Grid based on the initalState array.
   *
   * @param initialState An array representing the states of each Cell in the desired Grid.
   * @return A Grid where grid.getCell(x, y) == initialState[x][y].
   */
  public static Grid generateGridFromArray(boolean[][] initialState) {
    var cells = new ArrayList<ArrayList<Cell>>();

    for (int x = 0; x < initialState[0].length; x++) {
      var column = new ArrayList<Cell>();

      for (int y = 0; y < initialState.length; y++) {
        // x and y are deliberately flipped here because
        // initialState is rows on the outside, columns on the inside,
        // but the cells ArrayList is visa versa.
        column.add(new Cell(initialState[y][x]));
      }

      cells.add(column);
    }

    return new Grid(cells);
  }
}
