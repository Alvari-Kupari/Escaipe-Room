package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.timer.Timer;

/** This is the start screen controller, where difficulty is selected and you can start game. */
public class StartController {

  @FXML private Button btnStart;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising StartController **************");
  }

  @FXML
  private void startGame(ActionEvent event) {
    System.out.println("Action Start");
    System.out.println(event.getSource().getClass());
    System.out.println(event.getSource());

    GameState.isGameStarted = true;

    // start timer
    Timer.startTimer();

    App.changeScene(Room.MAIN_ROOM);
  }
}
