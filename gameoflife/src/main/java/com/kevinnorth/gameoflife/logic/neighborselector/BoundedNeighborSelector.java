package com.kevinnorth.gameoflife.logic.neighborselector;

import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.HashSet;
import java.util.Set;

public class BoundedNeighborSelector implements NeighborSelector {
  private boolean areCoordinatesInBounds(Grid grid, int x, int y) {
    return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
  }

  @Override
  public Set<Cell> selectNeighbors(Grid grid, int x, int y) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for (int adjX = x - 1; adjX <= x + 1; adjX++) {
      for (int adjY = y - 1; adjY <= y + 1; adjY++) {
        if (!(adjX == x && adjY == y) && areCoordinatesInBounds(grid, adjX, adjY)) {
          neighbors.add(grid.getCell(adjX, adjY));
        }
      }
    }

    return neighbors;
  }
}
