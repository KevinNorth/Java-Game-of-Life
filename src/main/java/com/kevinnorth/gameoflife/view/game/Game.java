package com.kevinnorth.gameoflife.view.game;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.logic.generations.GenerationController;
import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class Game extends Region {
  private EdgeBehavior edgeBehavior;
  private ArrayList<ArrayList<Cell>> logicalCells;
  private double cellSize;

  private static ArrayList<ArrayList<Cell>> extractCellsFromGrid(Grid grid) {
    var cells = new ArrayList<ArrayList<Cell>>();

    for (int x = 0; x < grid.getWidth(); x++) {
      var column = new ArrayList<Cell>();

      for (int y = 0; y < grid.getHeight(); y++) {
        column.add(grid.getCell(x, y));
      }

      cells.add(column);
    }

    return cells;
  }

  private static ArrayList<ArrayList<Cell>> createDeadCells(int width, int height) {
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

  public Game(int height, int width, double cellSize, EdgeBehavior edgeBehavior) {
    this.logicalCells = createDeadCells(width, height);
    this.edgeBehavior = edgeBehavior;
    this.cellSize = cellSize;

    render();
  }

  public void resetGrid() {
    this.logicalCells = createDeadCells(getLogicalWidth(), getLogicalHeight());
    render();
  }

  public void runNextGeneration() {
    var grid = new Grid(logicalCells);
    var nextGeneration = GenerationController.determineNextGeneration(grid, this.edgeBehavior);
    this.logicalCells = extractCellsFromGrid(nextGeneration);

    render();
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

  private void render() {
    GridPane pane = new GridPane();

    for (int x = 0; x < getLogicalWidth(); x++) {
      for (int y = 0; y < getLogicalHeight(); y++) {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(logicalCells.get(x).get(y).alive());
        checkBox.setPrefSize(cellSize, cellSize);

        final int finalX = x;
        final int finalY = y;
        checkBox.setOnAction((actionEvent) -> onCellClicked(finalX, finalY));
        pane.add(checkBox, x, y);
      }
    }

    this.getChildren().removeAll();
    this.getChildren().add(pane);
  }

  private void onCellClicked(int x, int y) {
    var existingCell = logicalCells.get(x).get(y);
    logicalCells.get(x).set(y, new Cell(!existingCell.alive()));
    render();
  }
}
