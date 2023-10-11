package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;

public class ExitController {

  @FXML private Button btnExit;
  @FXML private Button btnMainMenu;
  @FXML private ImageView loading;
  @FXML private Text grade;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising ExitController **************");
    RoomBinder.grade = grade;
  }

  // This method is called when the user clicks on the Main Menu button
  @FXML
  private void onMainMenu() throws IOException {
    System.out.println("Go to Main Menu");
    // Set the loading image to visible
    loading.setVisible(true);
    // Disable the buttons for exiting
    btnExit.setDisable(true);
    // Disable the buttons for going to the main menu
    btnMainMenu.setDisable(true);
    // Set the game back to its default state
    GameState.setDefaults();

    // This allows the game to restart in the background
    Task<Void> restartTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            System.out.println("...Restarting...");

            btnExit.setDisable(true);
            btnMainMenu.setDisable(true);
            App.reloadFXML();
            return null;
          }
        };

    restartTask.setOnSucceeded(
        e -> {
          System.out.println("---------------------Succeeded---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
        });

    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
        });

    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }
}
