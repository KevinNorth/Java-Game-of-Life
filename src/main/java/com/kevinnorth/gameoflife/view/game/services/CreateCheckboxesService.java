package com.kevinnorth.gameoflife.view.game.services;

import com.kevinnorth.gameoflife.state.Cell;
import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class CreateCheckboxesService extends Service<GridPane> {
  public static interface OnClickHandler {
    public void onClick(int x, int y);
  }

  private ArrayList<ArrayList<Cell>> cells;
  private double cellSize;
  private OnClickHandler onClickHandler;

  public CreateCheckboxesService(
      ArrayList<ArrayList<Cell>> cells, double cellSize, OnClickHandler onClickHandler) {
    this.cells = cells;
    this.cellSize = cellSize;
    this.onClickHandler = onClickHandler;
  }

  @Override
  protected Task<GridPane> createTask() {
    return new Task<GridPane>() {
      @Override
      protected GridPane call() {
        GridPane pane = new GridPane();

        for (int x = 0; x < cells.size(); x++) {
          for (int y = 0; y < cells.get(x).size(); y++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(cells.get(x).get(y).alive());
            checkBox.setPrefSize(cellSize, cellSize);

            final int finalX = x;
            final int finalY = y;
            checkBox.setOnAction((actionEvent) -> onClickHandler.onClick(finalX, finalY));
            pane.add(checkBox, x, y);
          }
        }

        return pane;
      }
    };
  }
}
