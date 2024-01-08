package com.kevinnorth.gameoflife.state;

import java.util.Collection;
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

  public Collection<Cell> getNeighbors(int row, int col) {
    Set<Cell> neighbors = new HashSet<Cell>();

    for(int adjRow = row - 1; adjRow <= row + 1; adjRow ++) {
      if (adjRow >= 0 && adjRow < this.height) {
        for(int adjCol = col - 1; adjCol <= col + 1; adjCol++) {
          if (adjCol >= 0 && adjCol < this.width) {
            if(adjRow != row && adjCol != col) {
              neighbors.add(this.cells.get(adjRow).get(adjCol));
            }
          }        
        }
      }
    }

    return neighbors;
  }
}
