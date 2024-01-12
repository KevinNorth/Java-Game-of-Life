package com.kevinnorth.gameoflife;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.view.game.Game;
import java.util.function.UnaryOperator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/** JavaFX App */
public class App extends Application {
  // Borrowed from https://stackoverflow.com/a/40472822/473792
  private static UnaryOperator<Change> numberFilter =
      (change) -> {
        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?")) {
          return change;
        }
        return null;
      };

  @Override
  public void start(Stage stage) {
    var game = new Game(12, 24, 8, EdgeBehavior.BOUNDED);

    var tabPane = new TabPane();
    tabPane.getTabs().add(prepareSimiulationTab(game));
    tabPane.getTabs().add(prepareSettingsTab(game));

    var rootPane = new VBox(tabPane);
    VBox.setVgrow(tabPane, Priority.ALWAYS);

    var scene = new Scene(rootPane, 640, 480);
    stage.setScene(scene);
    stage.setTitle("Game of Life");
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  private Tab prepareSimiulationTab(Game game) {
    var scrollPane = new ScrollPane(game);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setMinSize(0, 0);

    var nextGenerationButton = new Button("Step");
    nextGenerationButton.setOnAction((actionEvent) -> game.runNextGeneration());
    nextGenerationButton.setPrefSize(140, 100);

    var rootPane = new VBox(scrollPane, nextGenerationButton);
    rootPane.setAlignment(Pos.CENTER);
    rootPane.setPadding(new Insets(15));

    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    VBox.setMargin(scrollPane, new Insets(0, 0, 10, 0));
    VBox.setMargin(nextGenerationButton, new Insets(5, 5, 5, 5));

    var tab = new Tab("Simulation", rootPane);
    tab.setClosable(false);
    return tab;
  }

  private Tab prepareSettingsTab(Game game) {
    final var wrapEdgesLabel = new Label("Wrap Edges");
    final var wrapEdgesCheckbox = new CheckBox();
    final var wrapEdgesPane = new HBox(wrapEdgesLabel, wrapEdgesCheckbox);
    wrapEdgesPane.setAlignment(Pos.BASELINE_LEFT);
    HBox.setMargin(wrapEdgesLabel, new Insets(0, 10, 0, 0));

    final var gridSizeLabel = new Label("Grid Size");
    HBox.setMargin(gridSizeLabel, new Insets(0, 20, 0, 0));

    final var gridWidthLabel = new Label("Width");
    final var gridWidthInput = createNumberField(24);
    final var gridWidthPane = new HBox(gridWidthLabel, gridWidthInput);
    gridWidthPane.setAlignment(Pos.BASELINE_LEFT);
    HBox.setMargin(gridWidthLabel, new Insets(0, 10, 0, 0));
    HBox.setMargin(gridWidthPane, new Insets(0, 20, 0, 0));

    final var gridHeightLabel = new Label("Height");
    final var gridHeightInput = createNumberField(12);
    final var gridHeightPane = new HBox(gridHeightLabel, gridHeightInput);
    gridHeightPane.setAlignment(Pos.BASELINE_LEFT);
    HBox.setMargin(gridHeightLabel, new Insets(0, 10, 0, 0));
    HBox.setMargin(gridHeightPane, new Insets(0, 20, 0, 0));

    final var gridSizePane = new HBox(gridSizeLabel, gridWidthPane, gridHeightPane);
    gridSizePane.setAlignment(Pos.BASELINE_LEFT);

    final var cellSizeLabel = new Label("Cell size");
    final var cellSizeInput = createNumberField(8);
    final var cellSizeUnits = new Label("pixels");
    final var cellSizePane = new HBox(cellSizeLabel, cellSizeInput, cellSizeUnits);
    cellSizePane.setAlignment(Pos.BASELINE_LEFT);
    HBox.setMargin(cellSizeLabel, new Insets(0, 10, 0, 0));
    HBox.setMargin(cellSizeInput, new Insets(0, 3, 0, 0));

    final var saveButton = new Button("Save");
    saveButton.setPrefSize(60, 20);
    saveButton.setOnAction(
        (_event) -> {
          var converter = new IntegerStringConverter();
          boolean wrapEdges = wrapEdgesCheckbox.isSelected();
          int gridWidth = converter.fromString(gridWidthInput.getText());
          int gridHeight = converter.fromString(gridHeightInput.getText());
          double cellSize = (double) converter.fromString(cellSizeInput.getText());

          onSaveClicked(game, wrapEdges, gridWidth, gridHeight, cellSize);
        });

    var rootPane = new VBox(wrapEdgesPane, gridSizePane, cellSizePane, saveButton);
    rootPane.setAlignment(Pos.TOP_LEFT);
    rootPane.setSpacing(20);
    rootPane.setPadding(new Insets(15));

    var scrollPane = new ScrollPane(rootPane);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    var tab = new Tab("Settings", scrollPane);
    tab.setClosable(false);
    return tab;
  }

  private TextField createNumberField(int initialValue) {
    var textField = new TextField();
    textField.setTextFormatter(
        new TextFormatter<Integer>(new IntegerStringConverter(), 0, numberFilter));
    textField.setText(Integer.toString(initialValue));
    return textField;
  }

  private void onSaveClicked(
      Game game, boolean wrapEdges, int gridWidth, int gridHeight, double cellSize) {
    var edgeBehavior = wrapEdges ? EdgeBehavior.WRAPPED : EdgeBehavior.BOUNDED;
    game.setProperties(gridWidth, gridHeight, edgeBehavior, cellSize);
  }
}
