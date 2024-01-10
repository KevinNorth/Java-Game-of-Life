package com.kevinnorth.gameoflife.state;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.*;

public class GridTest {
  @Test
  public void IfConstructedWithDimensionsThenCreatesGridOfDeadCells() {
    var grid = new Grid(3, 4);

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 4; y++) {
        assertEquals(false, grid.getCell(x, y).alive());
      }
    }
  }

  @Test
  public void IfConstructedWithCollectionOfCellsThenCreatesGridOfMatchingCells() {
    boolean[][] initialState = {
      {false, true, false},
      {false, false, true},
      {true, true, true}
    };
    var originalCells = new ArrayList<ArrayList<Cell>>(initialState.length);

    for (int x = 0; x < initialState.length; x++) {
      var column = new ArrayList<Cell>(initialState[x].length);

      for (int y = 0; y < initialState[x].length; y++) {
        column.add(new Cell(initialState[x][y]));
      }

      originalCells.add(column);
    }

    var grid = new Grid(originalCells);

    for (int x = 0; x < initialState.length; x++) {
      for (int y = 0; y < initialState[x].length; y++) {
        assertEquals(initialState[x][y], grid.getCell(x, y).alive());
      }
    }
  }

  @Test
  public void GetHieghtAndGetWidth() {
    int height = 21;
    int width = 43;

    var grid = new Grid(width, height);

    assertEquals(height, grid.getHeight());
    assertEquals(width, grid.getWidth());
  }
}
