package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class GameOverController {
  @FXML private Button btnExit;
  @FXML private Button btnMainMenu;
  @FXML private ImageView loading;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising GameOverController **************");
  }

  @FXML
  private void goMainMenu() throws IOException {
    System.out.println("Go to Main Menu");
    loading.setVisible(true);
    btnExit.setDisable(true);
    btnMainMenu.setDisable(true);
    GameState.setDefaults();
    App.reloadFXML();
  }

  @FXML
  private void exit() {
    System.out.println("Exit");
    System.exit(0);
  }
}
