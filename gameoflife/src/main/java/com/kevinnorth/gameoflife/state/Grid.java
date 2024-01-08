package com.kevinnorth.gameoflife.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SequencedCollection;

public abstract class Grid {
  protected ArrayList<ArrayList<Cell>> cells;
  protected final int width;
  protected final int height;

  public Grid(int width, int height) {
    this.height = height;
    this.width = width;
    this.cells = new ArrayList<ArrayList<Cell>>(height);

    for(int row = 0; row < height; row++) {
      var rowContents = new ArrayList<Cell>(width);
      for (int col = 0; col < width; col++) {
        rowContents.set(col, new Cell(false));
      }
      cells.set(row, rowContents);
    }
  }

  public Grid(SequencedCollection<SequencedCollection<Cell>> cells) {
    this.height = cells.size();
    this.width = cells.getFirst().size();
    
    cells.forEach((row) -> {
      if(row.size() != width) {
        throw new IllegalArgumentException("All rows in cells must be the same length");
      }
    });

    this.cells = new ArrayList<ArrayList<Cell>>(height);

    for(int row = 0; row < height; row++) {
      var rowContents = new ArrayList<Cell>(width);
      for (int col = 0; col < width; col++) {
        rowContents.set(col, new Cell(false));
      }
      this.cells.set(row, rowContents);
    }
  }

  public abstract Collection<Cell> getNeighbors(int row, int col);
}
