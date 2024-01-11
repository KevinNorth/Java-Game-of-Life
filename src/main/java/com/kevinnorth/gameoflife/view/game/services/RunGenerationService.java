package com.kevinnorth.gameoflife.view.game.services;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.logic.generations.GenerationController;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.ArrayList;
import java.util.SequencedCollection;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RunGenerationService<
        T extends SequencedCollection<? extends SequencedCollection<Cell>>>
    extends Service<ArrayList<ArrayList<Cell>>> {
  private T cells;
  private EdgeBehavior edgeBehavior;

  public RunGenerationService(T cells, EdgeBehavior edgeBehavior) {
    this.cells = cells;
    this.edgeBehavior = edgeBehavior;
  }

  @Override
  protected Task<ArrayList<ArrayList<Cell>>> createTask() {
    return new Task<ArrayList<ArrayList<Cell>>>() {
      @Override
      protected ArrayList<ArrayList<Cell>> call() {
        var grid = new Grid(cells);
        var nextGen = GenerationController.determineNextGeneration(grid, edgeBehavior);
        return nextGen.getCopyOfCells();
      }
    };
  }
}
