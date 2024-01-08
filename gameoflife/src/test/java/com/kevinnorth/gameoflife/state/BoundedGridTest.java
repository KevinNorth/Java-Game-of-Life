package com.kevinnorth.gameoflife.state;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class BoundedGridTest {
  @Test
  public void IfConstructedWithDimensionsThenCreatesGridOfDeadCells() {
    var grid = new BoundedGrid(3, 4);

    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(false, grid.getCell(row, col).alive());
      }
    }
  }
}
