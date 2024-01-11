package com.kevinnorth.gameoflife.view.game.services;

import com.kevinnorth.gameoflife.state.Cell;
import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateDeadCellsService extends Service<ArrayList<ArrayList<Cell>>> {
  private int width;
  private int height;

  public CreateDeadCellsService(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  protected Task<ArrayList<ArrayList<Cell>>> createTask() {
    return new Task<ArrayList<ArrayList<Cell>>>() {
      @Override
      protected ArrayList<ArrayList<Cell>> call() {
        var cells = new ArrayList<ArrayList<Cell>>();

        for (int x = 0; x < width; x++) {
          var column = new ArrayList<Cell>();

          for (int y = 0; y < height; y++) {
            column.add(new Cell(false));
          }

          cells.add(column);
        }

        return cells;
      }
    };
  }
}
