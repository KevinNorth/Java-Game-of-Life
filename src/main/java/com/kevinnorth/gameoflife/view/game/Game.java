package com.kevinnorth.gameoflife.view.game;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.view.game.services.CreateCheckboxesService;
import com.kevinnorth.gameoflife.view.game.services.CreateDeadCellsService;
import com.kevinnorth.gameoflife.view.game.services.RunGenerationService;
import com.kevinnorth.gameoflife.view.game.services.ServiceFailedException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.geometry.Pos;
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
    this.cachedCheckBoxes = null;

    CreateDeadCellsService service = new CreateDeadCellsService(width, height);

    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render(true);
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
          render(false);
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
          render(false);
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
          render(true);
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  private void render(boolean recreateCheckboxes) throws ServiceFailedException {
    if (recreateCheckboxes || cachedCheckBoxes == null) {
      CreateCheckboxesService service =
          new CreateCheckboxesService(logicalCells, (x, y) -> onCellClicked(x, y));

      service.setOnSucceeded(
          (_event) -> {
            File tempStylesheet;
            try {
              tempStylesheet = generateStylesheetForCheckBoxSize();
            } catch (IOException err) {
              throw new IllegalStateException(err);
            }

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.getStylesheets().add(tempStylesheet.toURI().toString());

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

  private File generateStylesheetForCheckBoxSize() throws IOException {
    // Borrowed from https://stackoverflow.com/a/44409349/473792
    // It seems that this is truly the best way to programmatically create full CSS stylesheets at
    // runtime
    File tempStylesheet = File.createTempFile("gameoflife-checkboxsize", ".css");
    tempStylesheet.deleteOnExit();

    try (PrintWriter printWriter = new PrintWriter(tempStylesheet)) {
      printWriter.println(".check-box > .box {");
      printWriter.println("  -fx-padding: " + (cellSize / 2.5) + "px;");
      printWriter.println("}");
      printWriter.println(".check-box > .box >.mark {");
      printWriter.println("  -fx-padding: " + cellSize + "px;");
      printWriter.println("}");
    }

    return tempStylesheet;
  }

  private void onCellClicked(int x, int y) {
    var existingCell = logicalCells.get(x).get(y);
    logicalCells.get(x).set(y, new Cell(!existingCell.alive()));
    cachedCheckBoxes.get(x).get(y).setSelected(!existingCell.alive());
  }
}
