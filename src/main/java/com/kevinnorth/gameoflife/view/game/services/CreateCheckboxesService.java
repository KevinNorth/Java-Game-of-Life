package com.kevinnorth.gameoflife.view.game.services;

import com.kevinnorth.gameoflife.state.Cell;
import java.util.ArrayList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;

public class CreateCheckboxesService extends Service<ArrayList<ArrayList<CheckBox>>> {
  public static interface OnClickHandler {
    public void onClick(int x, int y);
  }

  private ArrayList<ArrayList<Cell>> cells;
  private OnClickHandler onClickHandler;

  public CreateCheckboxesService(ArrayList<ArrayList<Cell>> cells, OnClickHandler onClickHandler) {
    this.cells = cells;
    this.onClickHandler = onClickHandler;
  }

  @Override
  protected Task<ArrayList<ArrayList<CheckBox>>> createTask() {
    return new Task<ArrayList<ArrayList<CheckBox>>>() {
      @Override
      protected ArrayList<ArrayList<CheckBox>> call() {
        var checkBoxes = new ArrayList<ArrayList<CheckBox>>();
        for (int x = 0; x < cells.size(); x++) {
          var column = new ArrayList<CheckBox>();

          for (int y = 0; y < cells.get(x).size(); y++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(cells.get(x).get(y).alive());

            final int finalX = x;
            final int finalY = y;
            checkBox.setOnAction((actionEvent) -> onClickHandler.onClick(finalX, finalY));
            column.add(checkBox);
          }

          checkBoxes.add(column);
        }

        return checkBoxes;
      }
    };
  }
}
