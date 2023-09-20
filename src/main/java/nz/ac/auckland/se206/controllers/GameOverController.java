package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
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

  // This method is called when the user clicks on the Main Menu button
  @FXML
  private void goMainMenu() throws IOException {
    System.out.println("Go to Main Menu");
    loading.setVisible(true);
    btnExit.setDisable(true);
    btnMainMenu.setDisable(true);
    GameState.setDefaults();

    Task<Void> restartTask2 =
        new Task<Void>() {
          @Override
          // This allows the game to restart in the background
          protected Void call() throws Exception {
            System.out.println("...Restarting...");

            btnExit.setDisable(true);
            btnMainMenu.setDisable(true);
            App.reloadFXML();
            return null;
          }
        };

    restartTask2.setOnSucceeded(
        e -> {
          System.out.println("---------------------Succeeded---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
        });

    restartTask2.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
        });

    Thread restartThread2 = new Thread(restartTask2);
    restartThread2.start();
  }

  @FXML
  private void exit() {
    System.out.println("Exit");
    System.exit(0);
  }
}
