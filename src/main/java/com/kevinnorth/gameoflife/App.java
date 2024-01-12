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
    var game = new Game(14, 28, 8, EdgeBehavior.BOUNDED);

    var tabPane = new TabPane();
    tabPane.getTabs().add(prepareSimiulationTab(game));
    tabPane.getTabs().add(prepareSettingsTab(game));

    var rootPane = new VBox(tabPane);
    VBox.setVgrow(tabPane, Priority.ALWAYS);

    var scene = new Scene(rootPane, 640, 480);
    stage.setScene(scene);
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

    final var gridSizeLabel = new Label("Grid Size");
    final var gridWidthLabel = new Label("Width");
    final var gridWidthInput = createNumberField(14);
    final var gridWidthPane = new HBox(gridWidthLabel, gridWidthInput);
    final var gridHeightLabel = new Label("Height");
    final var gridHeightInput = createNumberField(28);
    final var gridHeightPane = new HBox(gridHeightLabel, gridHeightInput);
    final var gridSizePane = new HBox(gridSizeLabel, gridWidthPane, gridHeightPane);

    final var cellSizeLabel = new Label("Cell size");
    final var cellSizeInput = createNumberField(8);
    final var cellSizeUnits = new Label("pixels");
    final var cellSizePane = new HBox(cellSizeLabel, cellSizeInput, cellSizeUnits);

    final var saveButton = new Button("Save");
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
