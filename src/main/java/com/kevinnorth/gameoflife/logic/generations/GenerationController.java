package com.kevinnorth.gameoflife.logic.generations;

import com.kevinnorth.gameoflife.logic.neighborselector.BoundedNeighborSelector;
import com.kevinnorth.gameoflife.logic.neighborselector.NeighborSelector;
import com.kevinnorth.gameoflife.logic.neighborselector.WrappedNeighborSelector;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.ArrayList;
import java.util.Set;

/**
 * Updates a Game of Life grid by one generation.
 *
 * <p>("Generation" isn't a vague, unhelpful verb. In this context, it's the specific noun from
 * Conway's Game of Life.)
 */
public final class GenerationController {
  private static Cell determineNextCellState(Cell cell, Set<Cell> neighbors) {
    long numNeighborsAlive = neighbors.stream().filter((neighbor) -> neighbor.alive()).count();

    if (cell.alive()) {
      if (numNeighborsAlive == 2 || numNeighborsAlive == 3) {
        // Survival
        return new Cell(true);
      }
      // Death
      return new Cell(false);
    }

    if (numNeighborsAlive == 3) {
      // Birth
      return new Cell(true);
    }
    // Dead cell remains dead
    return new Cell(false);
  }

  private static NeighborSelector getNeighborSelector(EdgeBehavior edgeBehavior) {
    switch (edgeBehavior) {
      case BOUNDED:
        return new BoundedNeighborSelector();
      case WRAPPED:
        return new WrappedNeighborSelector();
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * Produces the next generation of the Game of Life based on an existing Grid.
   *
   * @param grid The current Game of Life state to advance.
   * @param edgeBehavior How to treat cells adjacent to the edge of the grid:
   *     <ul>
   *       <li><code>BOUNDED</code>: Cells outside the boundaries of the grid do not exist / are
   *           always dead.
   *       <li><code>WRAPPED</code>: The top and bottom edges wrap to each other, as do the left and
   *           right edges.
   *     </ul>
   *
   * @return A Grid containing the state of the next generation of the Game of Life.
   */
  public static Grid determineNextGeneration(Grid grid, EdgeBehavior edgeBehavior) {
    var neighborSelector = getNeighborSelector(edgeBehavior);

    var newCells = new ArrayList<ArrayList<Cell>>();

    for (int x = 0; x < grid.getWidth(); x++) {
      var newColumn = new ArrayList<Cell>();
      for (int y = 0; y < grid.getHeight(); y++) {
        var neighbors = neighborSelector.selectNeighbors(grid, x, y);
        var newCell = determineNextCellState(grid.getCell(x, y), neighbors);
        newColumn.add(newCell);
      }
      newCells.add(newColumn);
    }

    return new Grid(newCells);
  }
}
