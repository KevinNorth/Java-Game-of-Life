package com.kevinnorth.gameoflife.state;

import java.util.ArrayList;
import java.util.SequencedCollection;

public class Grid {
  protected ArrayList<ArrayList<Cell>> cells;
  protected final int width;
  protected final int height;

  public Grid(int width, int height) {
    this.width = width;
    this.height = height;
    this.cells = new ArrayList<ArrayList<Cell>>(width);

    for (int x = 0; x < width; x++) {
      var colContents = new ArrayList<Cell>(height);
      for (int y = 0; y < height; y++) {
        colContents.add(new Cell(false));
      }
      cells.add(colContents);
    }
  }

  public <T extends SequencedCollection<Cell>, S extends SequencedCollection<T>> Grid(S cells) {
    this.width = cells.size();
    this.height = cells.getFirst().size();

    cells.forEach(
        (column) -> {
          if (column.size() != height) {
            throw new IllegalArgumentException("All rows in cells must be the same length");
          }
        });

    this.cells = new ArrayList<ArrayList<Cell>>(width);

    for (SequencedCollection<Cell> column : cells) {
      var colummContents = new ArrayList<Cell>(height);
      for (Cell cell : column) {
        colummContents.add(cell);
      }
      this.cells.add(colummContents);
    }
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public Cell getCell(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("Cell coordinates must be within the grid's dimensions");
    }

    return this.cells.get(x).get(y);
  }
}
