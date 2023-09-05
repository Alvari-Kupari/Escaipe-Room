package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** This is the start screen controller, where difficulty is selected and you can start game. */
public class StartController {

  @FXML private Button btnStart;

  @FXML
  private void initialize() {
    System.out.println("************** Initialising StartController **************");
  }

  @FXML
  private void startGame(ActionEvent event) {
    System.out.println("Action Start");
    System.out.println(event.getSource().getClass());
    System.out.println(event.getSource());

    GameState.isGameStarted = true;
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManager.getUiRoot(AppUi.MAIN_ROOM));
  }
}
