package com.kevinnorth.gameoflife.state;

import java.util.HashSet;
import java.util.SequencedCollection;
import java.util.Set;

public class BoundedGrid extends Grid {
  public BoundedGrid(int width, int height) {
    super(width, height);
  }

  public BoundedGrid(SequencedCollection<SequencedCollection<Cell>> cells) {
    super(cells);
  }

  private boolean areCoordinatesInBounds(int x, int y) {
    return x >= 0 && x < this.width && y >= 0 && y < this.height;
  }

  public Set<Cell> getNeighbors(int x, int y) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for (int adjX = x - 1; adjX <= x + 1; adjX++) {
      for (int adjY = y - 1; adjY <= y + 1; adjY++) {
        if (adjX != x && adjY != y && areCoordinatesInBounds(x, y)) {
          neighbors.add(this.cells.get(adjX).get(adjY));
        }
      }
    }

    return neighbors;
  }
}
