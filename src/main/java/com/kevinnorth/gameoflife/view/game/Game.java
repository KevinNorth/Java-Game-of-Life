package com.kevinnorth.gameoflife.view.game;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.view.game.services.CreateCheckboxesService;
import com.kevinnorth.gameoflife.view.game.services.CreateDeadCellsService;
import com.kevinnorth.gameoflife.view.game.services.RunGenerationService;
import com.kevinnorth.gameoflife.view.game.services.ServiceFailedException;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class Game extends Region {
  private EdgeBehavior edgeBehavior;
  private ArrayList<ArrayList<Cell>> logicalCells;
  private double cellSize;
  private ArrayList<ArrayList<CheckBox>> cachedCheckBoxes;

  public Game(int height, int width, double cellSize, EdgeBehavior edgeBehavior) {
    this.edgeBehavior = edgeBehavior;
    this.cellSize = cellSize;
    this.logicalCells = new ArrayList<ArrayList<Cell>>();
    this.cachedCheckBoxes = new ArrayList<ArrayList<CheckBox>>();

    CreateDeadCellsService service = new CreateDeadCellsService(width, height);

    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render();
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  public void resetGrid() {
    CreateDeadCellsService service =
        new CreateDeadCellsService(getLogicalWidth(), getLogicalHeight());

    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render();
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  public void runNextGeneration() {
    var service =
        new RunGenerationService<ArrayList<ArrayList<Cell>>>(this.logicalCells, this.edgeBehavior);
    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render();
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  public int getLogicalWidth() {
    return logicalCells.size();
  }

  public int getLogicalHeight() {
    if (logicalCells.size() == 0) {
      return 0;
    }

    return logicalCells.get(0).size();
  }

  public EdgeBehavior getEdgeBehavior() {
    return this.edgeBehavior;
  }

  public double getCellSize() {
    return this.cellSize;
  }

  public void setProperties(
      int gridWidth, int gridHeight, EdgeBehavior edgeBehavior, double cellSize) {
    this.edgeBehavior = edgeBehavior;
    this.cellSize = cellSize;

    CreateDeadCellsService service = new CreateDeadCellsService(gridWidth, gridHeight);

    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render();
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  private void render() throws ServiceFailedException {
    boolean hasWidthChanged = getLogicalWidth() != cachedCheckBoxes.size();
    int previousHeight = cachedCheckBoxes.size() == 0 ? 0 : cachedCheckBoxes.get(0).size();
    boolean hasHeightChanged = getLogicalHeight() != previousHeight;

    if (hasWidthChanged || hasHeightChanged) {
      CreateCheckboxesService service =
          new CreateCheckboxesService(logicalCells, cellSize, (x, y) -> onCellClicked(x, y));

      service.setOnSucceeded(
          (_event) -> {
            GridPane gridPane = new GridPane();
            var newCheckBoxes = service.getValue();

            for (int x = 0; x < newCheckBoxes.size(); x++) {
              for (int y = 0; y < newCheckBoxes.get(x).size(); y++) {
                gridPane.add(newCheckBoxes.get(x).get(y), x, y);
              }
            }

            this.cachedCheckBoxes = newCheckBoxes;

            this.getChildren().clear();
            this.getChildren().add(gridPane);
          });

      service.setOnFailed(
          (event) -> {
            throw new ServiceFailedException(event);
          });

      service.start();
    } else {
      for (int x = 0; x < getLogicalWidth(); x++) {
        for (int y = 0; y < getLogicalHeight(); y++) {
          var checkBox = cachedCheckBoxes.get(x).get(y);
          var cell = logicalCells.get(x).get(y);
          if (checkBox.isSelected() != cell.alive()) {
            checkBox.setSelected(cell.alive());
          }
        }
      }
    }
  }

  private void onCellClicked(int x, int y) {
    var existingCell = logicalCells.get(x).get(y);
    logicalCells.get(x).set(y, new Cell(!existingCell.alive()));
    cachedCheckBoxes.get(x).get(y).setSelected(!existingCell.alive());
  }
}
