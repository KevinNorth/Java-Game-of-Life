package com.kevinnorth.gameoflife.view.game;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.view.game.services.CreateCheckboxesService;
import com.kevinnorth.gameoflife.view.game.services.CreateDeadCellsService;
import com.kevinnorth.gameoflife.view.game.services.RunGenerationService;
import com.kevinnorth.gameoflife.view.game.services.ServiceFailedException;
import java.util.ArrayList;
import javafx.scene.layout.Region;

public class Game extends Region {
  private EdgeBehavior edgeBehavior;
  private ArrayList<ArrayList<Cell>> logicalCells;
  private double cellSize;

  public Game(int height, int width, double cellSize, EdgeBehavior edgeBehavior) {
    this.edgeBehavior = edgeBehavior;
    this.cellSize = cellSize;
    this.logicalCells = new ArrayList<>();

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
    service.start();

    service.setOnSucceeded(
        (_event) -> {
          this.logicalCells = service.getValue();
          render();
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    render();
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
    return logicalCells.get(0).size();
  }

  public EdgeBehavior getEdgeBehavior() {
    return this.edgeBehavior;
  }

  public void setEdgeBehavior(EdgeBehavior edgeBehavior) {
    this.edgeBehavior = edgeBehavior;
  }

  public double getCellSize() {
    return this.cellSize;
  }

  public void setCellSize(double cellSize) {
    this.cellSize = cellSize;
    render();
  }

  private void render() throws ServiceFailedException {
    CreateCheckboxesService service =
        new CreateCheckboxesService(logicalCells, cellSize, (x, y) -> onCellClicked(x, y));

    service.setOnSucceeded(
        (_event) -> {
          this.getChildren().removeAll();
          this.getChildren().add(service.getValue());
        });

    service.setOnFailed(
        (event) -> {
          throw new ServiceFailedException(event);
        });

    service.start();
  }

  private void onCellClicked(int x, int y) {
    var existingCell = logicalCells.get(x).get(y);
    logicalCells.get(x).set(y, new Cell(!existingCell.alive()));
    render();
  }
}
