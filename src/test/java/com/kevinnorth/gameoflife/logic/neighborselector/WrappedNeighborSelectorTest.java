package com.kevinnorth.gameoflife.logic.neighborselector;

import static org.junit.jupiter.api.Assertions.*;

import com.kevinnorth.gameoflife.state.Grid;
import com.kevinnorth.gameoflife.testutil.BooleanArrayGenerator;
import com.kevinnorth.gameoflife.testutil.GridFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

// I'll admit this isn't the prettiest code, and it probably would be just as good to write a
// handful of clear, specific test cases.
// But I was excited to see that JUnit 5 includes parameterized tests and gosh darn it, I wanted to
// give them a try!
public class WrappedNeighborSelectorTest {
  /**
   * Generates all possible boolean arrays of length 8. Useful for testing the neighbors of a Cell.
   *
   * @return All possible boolean arrays of length 8.
   */
  public static boolean[][] generateBooleanArraysOfLength8() {
    return BooleanArrayGenerator.generateBooleanArrays(8);
  }

  private static void assertNeighborSelectorProducesCorrectCells(
      Grid grid, boolean[] neighborStates, int x, int y) {
    long expectedNumAliveCells = 0;
    if (neighborStates[0]) expectedNumAliveCells++;
    if (neighborStates[1]) expectedNumAliveCells++;
    if (neighborStates[2]) expectedNumAliveCells++;
    if (neighborStates[3]) expectedNumAliveCells++;
    if (neighborStates[4]) expectedNumAliveCells++;
    if (neighborStates[5]) expectedNumAliveCells++;
    if (neighborStates[6]) expectedNumAliveCells++;
    if (neighborStates[7]) expectedNumAliveCells++;
    long expectedNumDeadCells = 8 - expectedNumAliveCells;

    var result = new WrappedNeighborSelector().selectNeighbors(grid, x, y);

    long actualNumAliveCells = result.stream().filter((cell) -> cell.alive()).count();
    long actualNumDeadCells = result.stream().filter((cell) -> !cell.alive()).count();

    assertEquals(expectedNumAliveCells, actualNumAliveCells);
    assertEquals(expectedNumDeadCells, actualNumDeadCells);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInTheMiddleOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {neighborStates[3], true, neighborStates[4]},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 1, 1);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsOnTopEdgeOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], true, neighborStates[1]},
      {neighborStates[2], neighborStates[3], neighborStates[4]},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 1, 0);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsOnLeftEdgeOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {true, neighborStates[3], neighborStates[4]},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 0, 1);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsOnRightEdgeOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {neighborStates[3], neighborStates[4], true},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 2, 1);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsOnBottomEdgeOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {neighborStates[3], neighborStates[4], neighborStates[5]},
      {neighborStates[6], true, neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 1, 2);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInTopLeftCornerOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {true, neighborStates[0], neighborStates[1]},
      {neighborStates[2], neighborStates[3], neighborStates[4]},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 0, 0);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInTopRightCornerOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], true},
      {neighborStates[2], neighborStates[3], neighborStates[4]},
      {neighborStates[5], neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 2, 0);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInBottomLeftCornerOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {neighborStates[3], neighborStates[4], neighborStates[5]},
      {true, neighborStates[6], neighborStates[7]}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 0, 2);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInBottomRightCornerOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    boolean[][] initialState = {
      {neighborStates[0], neighborStates[1], neighborStates[2]},
      {neighborStates[3], neighborStates[4], neighborStates[5]},
      {neighborStates[6], neighborStates[7], true}
    };

    Grid grid = GridFactory.generateGridFromArray(initialState);

    assertNeighborSelectorProducesCorrectCells(grid, neighborStates, 2, 2);
  }
}
