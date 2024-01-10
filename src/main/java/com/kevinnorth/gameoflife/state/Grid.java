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

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }

    if (other == this) {
      return true;
    }

    if (!(other instanceof Grid)) {
      return false;
    }

    Grid otherGrid = (Grid) other;

    if (this.height != otherGrid.height) {
      return false;
    }

    if (this.width != otherGrid.width) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (this.getCell(x, y).alive() != otherGrid.getCell(x, y).alive()) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = this.height * 11;
    hash += this.width * 13;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < width; y++) {
        if (getCell(x, y).alive()) {
          hash += 43 * x + 57 * y;
        }
      }
    }

    return hash;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (getCell(x, y).alive()) {
          str.append('O');
        } else {
          str.append('.');
        }
      }
      str.append('\n');
    }

    return str.toString().trim();
  }
}
