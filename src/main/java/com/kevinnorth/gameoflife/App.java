package com.kevinnorth.gameoflife;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.view.game.Game;
import java.util.function.UnaryOperator;
import javafx.application.Application;
import javafx.geometry.Insets;
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
    var game = new Game(20, 20, 20, EdgeBehavior.BOUNDED);

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

    var nextGenerationButton = new Button("Step");
    nextGenerationButton.setOnAction((actionEvent) -> game.runNextGeneration());

    var rootPane = new VBox(scrollPane, nextGenerationButton);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    VBox.setMargin(scrollPane, new Insets(0, 0, 10, 0));

    var tab = new Tab("Simulation", rootPane);
    tab.setClosable(false);
    return tab;
  }

  private Tab prepareSettingsTab(Game game) {
    var wrapEdgesLabel = new Label("Wrap Edges");
    var wrapEdgesCheckbox = new CheckBox();
    var wrapEdgesPane = new HBox(wrapEdgesLabel, wrapEdgesCheckbox);

    var gridSizeLabel = new Label("Grid Size");
    var gridWidthLabel = new Label("Width");
    var gridWidthInput = createNumberField(20);
    var gridWidthPane = new HBox(gridWidthLabel, gridWidthInput);
    var gridHeightLabel = new Label("Height");
    var gridHeightInput = createNumberField(20);
    var gridHeightPane = new HBox(gridHeightLabel, gridHeightInput);
    var gridSizePane = new HBox(gridSizeLabel, gridWidthPane, gridHeightPane);

    var cellSizeLabel = new Label("Cell size");
    var cellSizeInput = createNumberField(20);
    var cellSizeUnits = new Label("pixels");
    var cellSizePane = new HBox(cellSizeLabel, cellSizeInput, cellSizeUnits);

    var saveButton = new Button("Save");

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
}
