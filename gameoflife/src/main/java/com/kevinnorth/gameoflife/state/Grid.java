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
        rowContents.add(new Cell(false));
      }
      cells.add(rowContents);
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

    for(SequencedCollection<Cell> row : cells) {
      var rowContents = new ArrayList<Cell>(width);
      for (Cell col : row) {
        rowContents.add(col);
      }
      this.cells.add(rowContents);
    }
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public Cell getCell(int row, int col) {
    if (row < 0 || row >= this.height || col < 0 || col >= this.width) {
      throw new IllegalArgumentException("Cell coordinates must be within the grid's dimensions");
    }

    return this.cells.get(row).get(col);
  }

  public abstract Collection<Cell> getNeighbors(int row, int col);
}
