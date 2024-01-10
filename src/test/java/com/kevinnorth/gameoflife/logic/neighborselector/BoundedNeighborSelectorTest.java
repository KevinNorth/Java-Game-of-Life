package com.kevinnorth.gameoflife.logic.neighborselector;

import static org.junit.jupiter.api.Assertions.*;

import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import com.kevinnorth.gameoflife.testutil.BooleanArrayGenerator;
import java.util.ArrayList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

// I'll admit this isn't the prettiest code, and it probably would be just as good to write a handful of clear, specific test cases.
// But I was excited to see that JUnit 5 includes parameterized tests and gosh darn it, I wanted to give them a try!
public class BoundedNeighborSelectorTest {
  /**
   * Generates all possible boolean arrays of length 8. Useful for testing the neighbors of a Cell
   * in the middle of a Grid.
   *
   * @return All possible boolean arrays of length 8.
   */
  public static boolean[][] generateBooleanArraysOfLength8() {
    return BooleanArrayGenerator.generateBooleanArrays(8);
  }

  /**
   * Generates all possible boolean arrays of length 5. Useful for testing the neighbors of a Cell
   * along the edge of a Grid.
   *
   * @return All possible boolean arrays of length 5.
   */
  public static boolean[][] generateBooleanArraysOfLength5() {
    return BooleanArrayGenerator.generateBooleanArrays(5);
  }

  /**
   * Generates all possible boolean arrays of length 3. Useful for testing the neighbors of a Cell
   * on the corner of a Grid.
   *
   * @return All possible boolean arrays of length 3.
   */
  public static boolean[][] generateBooleanArraysOfLength3() {
    return BooleanArrayGenerator.generateBooleanArrays(3);
  }

  private static Grid createGridForInsideOfGridTests(boolean[] neighborStates) {
    var originalCells = new ArrayList<ArrayList<Cell>>(3);
    var column1 = new ArrayList<Cell>(3);
    column1.add(new Cell(neighborStates[0]));
    column1.add(new Cell(neighborStates[1]));
    column1.add(new Cell(neighborStates[2]));
    var column2 = new ArrayList<Cell>(3);
    column2.add(new Cell(neighborStates[3]));
    column2.add(
        new Cell(true)); // The cell whose neighbors we are checking - its value shouldn't matter.
    column2.add(new Cell(neighborStates[4]));
    var column3 = new ArrayList<Cell>(3);
    column3.add(new Cell(neighborStates[5]));
    column3.add(new Cell(neighborStates[6]));
    column3.add(new Cell(neighborStates[7]));
    originalCells.add(column1);
    originalCells.add(column2);
    originalCells.add(column3);

    return new Grid(originalCells);
  }

  /**
   * Creates grids that look like this:
   *
   * <pre>
   * . . 4 X 0 . .<br/>
   * . . 3 2 1 . .<br/>
   * 0 1 . . . 3 4<br/>
   * X 2 . . . 2 X<br/>
   * 4 3 . . . 1 0</br>
   * . . 1 2 3 . .<br/>
   * . . 0 X 4 . .
   * </pre>
   *
   * <p>This way, all four edges can be tested at once in the same test case.
   */
  private static Grid createGridForAlongEdgeOfGridTests(boolean[] neighborStates) {
    var originalCells = new ArrayList<ArrayList<Cell>>(7);

    var column1 = new ArrayList<Cell>(7);
    column1.add(new Cell(false));
    column1.add(new Cell(false));
    column1.add(new Cell(neighborStates[0]));
    column1.add(new Cell(true));
    column1.add(new Cell(neighborStates[4]));
    column1.add(new Cell(false));
    column1.add(new Cell(false));

    var column2 = new ArrayList<Cell>(7);
    column2.add(new Cell(false));
    column2.add(new Cell(false));
    column2.add(new Cell(neighborStates[1]));
    column2.add(new Cell(neighborStates[2]));
    column2.add(new Cell(neighborStates[3]));
    column2.add(new Cell(false));
    column2.add(new Cell(false));

    var column3 = new ArrayList<Cell>(7);
    column3.add(new Cell(neighborStates[4]));
    column3.add(new Cell(neighborStates[3]));
    column3.add(new Cell(false));
    column3.add(new Cell(false));
    column3.add(new Cell(false));
    column3.add(new Cell(neighborStates[1]));
    column3.add(new Cell(neighborStates[0]));

    var column4 = new ArrayList<Cell>(7);
    column4.add(new Cell(true));
    column4.add(new Cell(neighborStates[2]));
    column4.add(new Cell(false));
    column4.add(new Cell(false));
    column4.add(new Cell(false));
    column4.add(new Cell(neighborStates[2]));
    column4.add(new Cell(true));

    var column5 = new ArrayList<Cell>(7);
    column5.add(new Cell(neighborStates[0]));
    column5.add(new Cell(neighborStates[1]));
    column5.add(new Cell(false));
    column5.add(new Cell(false));
    column5.add(new Cell(false));
    column5.add(new Cell(neighborStates[3]));
    column5.add(new Cell(neighborStates[4]));

    var column6 = new ArrayList<Cell>(7);
    column6.add(new Cell(false));
    column6.add(new Cell(false));
    column6.add(new Cell(neighborStates[3]));
    column6.add(new Cell(neighborStates[2]));
    column6.add(new Cell(neighborStates[1]));
    column6.add(new Cell(false));
    column6.add(new Cell(false));

    var column7 = new ArrayList<Cell>(7);
    column7.add(new Cell(false));
    column7.add(new Cell(false));
    column7.add(new Cell(neighborStates[4]));
    column7.add(new Cell(true));
    column7.add(new Cell(neighborStates[0]));
    column7.add(new Cell(false));
    column7.add(new Cell(false));

    originalCells.add(column1);
    originalCells.add(column2);
    originalCells.add(column3);
    originalCells.add(column4);
    originalCells.add(column5);
    originalCells.add(column6);
    originalCells.add(column7);

    return new Grid(originalCells);
  }

  /**
   * Creates grids that look like this:
   *
   * <pre>
   * X 0 2 X<br/>
   * 2 1 1 0<br/>
   * 0 1 1 2<br/>
   * X 2 0 X
   * </pre>
   *
   * <p>This way, all four corners can be tested at once in the same test case.
   */
  private static Grid createGridForInCornerOfGridTests(boolean[] neighborStates) {
    var originalCells = new ArrayList<ArrayList<Cell>>(4);

    var column1 = new ArrayList<Cell>(4);
    column1.add(new Cell(true));
    column1.add(new Cell(neighborStates[0]));
    column1.add(new Cell(neighborStates[2]));
    column1.add(new Cell(true));

    var column2 = new ArrayList<Cell>(4);
    column2.add(new Cell(neighborStates[2]));
    column2.add(new Cell(neighborStates[1]));
    column2.add(new Cell(neighborStates[1]));
    column2.add(new Cell(neighborStates[0]));

    var column3 = new ArrayList<Cell>(4);
    column3.add(new Cell(neighborStates[0]));
    column3.add(new Cell(neighborStates[1]));
    column3.add(new Cell(neighborStates[1]));
    column3.add(new Cell(neighborStates[2]));

    var column4 = new ArrayList<Cell>(4);
    column4.add(new Cell(true));
    column4.add(new Cell(neighborStates[2]));
    column4.add(new Cell(neighborStates[0]));
    column4.add(new Cell(true));

    originalCells.add(column1);
    originalCells.add(column2);
    originalCells.add(column3);
    originalCells.add(column4);

    return new Grid(originalCells);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength8")
  public void IfCellIsInTheMiddleOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    long expectedNumAliveCells = 0;
    for (int i = 0; i < 8; i++) {
      expectedNumAliveCells += neighborStates[i] ? 1 : 0;
    }
    long expectedNumDeadCells = 8 - expectedNumAliveCells;

    Grid grid = createGridForInsideOfGridTests(neighborStates);
    var result = new BoundedNeighborSelector().selectNeighbors(grid, 1, 1);

    long actualNumAliveCells = result.stream().filter((cell) -> cell.alive()).count();
    long actualNumDeadCells = result.stream().filter((cell) -> !cell.alive()).count();

    assertEquals(expectedNumAliveCells, actualNumAliveCells);
    assertEquals(expectedNumDeadCells, actualNumDeadCells);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength5")
  public void IfCellIsAlongEdgeOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    long expectedNumAliveCells = 0;
    for (int i = 0; i < 5; i++) {
      expectedNumAliveCells += neighborStates[i] ? 1 : 0;
    }
    long expectedNumDeadCells = 5 - expectedNumAliveCells;

    Grid grid = createGridForAlongEdgeOfGridTests(neighborStates);
    var topResult = new BoundedNeighborSelector().selectNeighbors(grid, 3, 0);
    var leftResult = new BoundedNeighborSelector().selectNeighbors(grid, 0, 3);
    var rightResult = new BoundedNeighborSelector().selectNeighbors(grid, 6, 3);
    var bottomResult = new BoundedNeighborSelector().selectNeighbors(grid, 3, 6);

    long topActualNumAliveCells = topResult.stream().filter((cell) -> cell.alive()).count();
    long topActualNumDeadCells = topResult.stream().filter((cell) -> !cell.alive()).count();
    long leftActualNumAliveCells = leftResult.stream().filter((cell) -> cell.alive()).count();
    long leftActualNumDeadCells = leftResult.stream().filter((cell) -> !cell.alive()).count();
    long rightActualNumAliveCells = rightResult.stream().filter((cell) -> cell.alive()).count();
    long rightActualNumDeadCells = rightResult.stream().filter((cell) -> !cell.alive()).count();
    long bottomActualNumAliveCells = bottomResult.stream().filter((cell) -> cell.alive()).count();
    long bottomActualNumDeadCells = bottomResult.stream().filter((cell) -> !cell.alive()).count();

    assertEquals(expectedNumAliveCells, topActualNumAliveCells);
    assertEquals(expectedNumDeadCells, topActualNumDeadCells);
    assertEquals(expectedNumAliveCells, leftActualNumAliveCells);
    assertEquals(expectedNumDeadCells, leftActualNumDeadCells);
    assertEquals(expectedNumAliveCells, rightActualNumAliveCells);
    assertEquals(expectedNumDeadCells, rightActualNumDeadCells);
    assertEquals(expectedNumAliveCells, bottomActualNumAliveCells);
    assertEquals(expectedNumDeadCells, bottomActualNumDeadCells);
  }

  @ParameterizedTest
  @MethodSource("generateBooleanArraysOfLength3")
  public void IfCellInCornerOfAGridThenReturnsItsNeighbors(boolean[] neighborStates) {
    long expectedNumAliveCells = 0;
    for (int i = 0; i < 3; i++) {
      expectedNumAliveCells += neighborStates[i] ? 1 : 0;
    }
    long expectedNumDeadCells = 3 - expectedNumAliveCells;

    Grid grid = createGridForInCornerOfGridTests(neighborStates);
    var topLeftResult = new BoundedNeighborSelector().selectNeighbors(grid, 0, 0);
    var topRightResult = new BoundedNeighborSelector().selectNeighbors(grid, 3, 0);
    var bottomLeftResult = new BoundedNeighborSelector().selectNeighbors(grid, 0, 3);
    var bottomRightResult = new BoundedNeighborSelector().selectNeighbors(grid, 3, 3);

    long topLeftActualNumAliveCells = topLeftResult.stream().filter((cell) -> cell.alive()).count();
    long topLeftActualNumDeadCells = topLeftResult.stream().filter((cell) -> !cell.alive()).count();
    long topRightActualNumAliveCells =
        topRightResult.stream().filter((cell) -> cell.alive()).count();
    long topRightActualNumDeadCells =
        topRightResult.stream().filter((cell) -> !cell.alive()).count();
    long bottomLeftActualNumAliveCells =
        bottomLeftResult.stream().filter((cell) -> cell.alive()).count();
    long bottomLeftActualNumDeadCells =
        bottomLeftResult.stream().filter((cell) -> !cell.alive()).count();
    long bottomRightActualNumAliveCells =
        bottomRightResult.stream().filter((cell) -> cell.alive()).count();
    long bottomRightActualNumDeadCells =
        bottomRightResult.stream().filter((cell) -> !cell.alive()).count();

    assertEquals(expectedNumAliveCells, topLeftActualNumAliveCells);
    assertEquals(expectedNumDeadCells, topLeftActualNumDeadCells);
    assertEquals(expectedNumAliveCells, topRightActualNumAliveCells);
    assertEquals(expectedNumDeadCells, topRightActualNumDeadCells);
    assertEquals(expectedNumAliveCells, bottomLeftActualNumAliveCells);
    assertEquals(expectedNumDeadCells, bottomLeftActualNumDeadCells);
    assertEquals(expectedNumAliveCells, bottomRightActualNumAliveCells);
    assertEquals(expectedNumDeadCells, bottomRightActualNumDeadCells);
  }
}
