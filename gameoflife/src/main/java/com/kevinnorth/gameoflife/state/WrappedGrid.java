package com.kevinnorth.gameoflife.state;

import java.util.HashSet;
import java.util.SequencedCollection;
import java.util.Set;

public class WrappedGrid extends Grid {
  public WrappedGrid(int width, int height) {
    super(width, height);
  }

  public WrappedGrid(SequencedCollection<SequencedCollection<Cell>> cells) {
    super(cells);
  }

  private int wrapX(int x) {
    if (x < 0) {
      return (x % this.width) + this.width;
    }

    return x % this.width;
  }

  private int wrapY(int y) {
    if (y < 0) {
      return (y % this.height) + this.height;
    }

    return y % this.height;
  }

  public Set<Cell> getNeighbors(int x, int y) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for (int adjX = x - 1; adjX <= x + 1; adjX++) {
      for (int adjY = y - 1; adjY <= y + 1; adjY++) {
        if (adjX != x && adjY != y) {
          int wrappedX = wrapX(adjX);
          int wrappedY = wrapY(adjY);

          neighbors.add(this.cells.get(wrappedX).get(wrappedY));
        }
      }
    }

    return neighbors;
  }
}
