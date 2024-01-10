package com.kevinnorth.gameoflife.state;

// This is a class instead of a record because we want
// instances to be hashed and compared by object identity,
// not whether their states are identical.
public class Cell {
  private boolean alive;

  public Cell(boolean alive) {
    this.alive = alive;
  }

  public boolean alive() {
    return alive;
  }
}
