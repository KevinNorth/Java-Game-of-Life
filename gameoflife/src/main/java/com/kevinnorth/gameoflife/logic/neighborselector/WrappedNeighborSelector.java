package com.kevinnorth.gameoflife.logic.neighborselector;

import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.HashSet;
import java.util.Set;

public class WrappedNeighborSelector implements NeighborSelector {
  private int wrapX(int x, int width) {
    if (x < 0) {
      return (x % width) + width;
    }

    return x % width;
  }

  private int wrapY(int y, int height) {
    if (y < 0) {
      return (y % height) + height;
    }

    return y % height;
  }

  @Override
  public Set<Cell> selectNeighbors(Grid grid, int x, int y) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for (int adjX = x - 1; adjX <= x + 1; adjX++) {
      for (int adjY = y - 1; adjY <= y + 1; adjY++) {
        if (!(adjX == x && adjY == y)) {
          int wrappedX = wrapX(adjX, grid.getWidth());
          int wrappedY = wrapY(adjY, grid.getHeight());

          neighbors.add(grid.getCell(wrappedX, wrappedY));
        }
      }
    }

    return neighbors;
  }
}
