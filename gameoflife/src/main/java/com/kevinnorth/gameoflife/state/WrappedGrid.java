package com.kevinnorth.gameoflife.state;

import java.util.Collection;
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

  private int wrapRow(int row) {
    if (row < 0) {
      return (row % this.height) + this.height;
    }

    return row % height;
  }

  private int wrapColumn(int column) {
    if (column < 0) {
      return (column % this.width) + this.width;
    }

    return column % width;
  }

  public Collection<Cell> getNeighbors(int row, int col) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for(int adjRow = row - 1; adjRow <= row + 1; adjRow ++) {
      for(int adjCol = col - 1; adjCol <= col + 1; adjCol++) {
        if(adjRow != row && adjCol != col) {
          int wrappedRow = wrapRow(adjRow);
          int wrappedCol = wrapColumn(adjCol);

          neighbors.add(this.cells.get(wrappedRow).get(wrappedCol));
        }
      }        
    }

    return neighbors;
  }
}
