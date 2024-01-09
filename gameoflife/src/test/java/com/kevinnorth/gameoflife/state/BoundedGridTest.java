package com.kevinnorth.gameoflife.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class BoundedGridTest {
  @Test
  public void IfConstructedWithDimensionsThenCreatesGridOfDeadCells() {
    var grid = new BoundedGrid(3, 4);

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 4; y++) {
        assertEquals(false, grid.getCell(x, y).alive());
      }
    }
  }
}
