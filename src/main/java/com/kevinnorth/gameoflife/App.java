package com.kevinnorth.gameoflife;

import com.kevinnorth.gameoflife.logic.generations.EdgeBehavior;
import com.kevinnorth.gameoflife.view.game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) {
    var game = new Game(20, 20, 20, EdgeBehavior.BOUNDED);
    var scrollPane = new ScrollPane(game);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
    scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

    var nextGenerationButton = new Button("Step");
    nextGenerationButton.setOnAction((actionEvent) -> game.runNextGeneration());

    var rootPane = new VBox(scrollPane, nextGenerationButton);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    VBox.setMargin(scrollPane, new Insets(0, 0, 10, 0));

    var scene = new Scene(rootPane, 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
