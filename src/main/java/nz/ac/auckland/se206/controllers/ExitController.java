package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;

public class ExitController {

  @FXML private Button btnExit;
  @FXML private Button btnMainMenu;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising ExitController **************");
  }

  @FXML
  private void goMainMenu() {
    System.out.println("Go to Main Menu");
    GameState.setDefaults();
    App.changeScene(Room.START);
  }

  @FXML
  private void exit() {
    System.out.println("Exit");
    System.exit(0);
  }
}
