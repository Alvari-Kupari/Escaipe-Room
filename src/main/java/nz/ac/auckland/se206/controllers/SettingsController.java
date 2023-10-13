package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SoundManager;

/**
 * This is a parent class for any room which has a settings menu. This class is here to avoid
 * duplicate code, By delegating all the settings logic to this class.
 */
public class SettingsController {
  @FXML protected ImageView loading;
  @FXML protected Pane room;

  /**
   * Handles the user clicking the main menu button. The user is sent to the start screen, and the
   * game is reloaded.
   *
   * @throws IOException when the FXML is not loaded properly.
   */
  @FXML
  private void onGoMainMenu() throws IOException {
    System.out.println("Go to Main Menu");
    // Set the loading image to visible
    loading.setVisible(true);
    // Disable the room
    room.setDisable(true);
    // Set the game back to its default state
    GameState.setDefaults();

    // This allows the game to restart in the background
    Task<Void> restartTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            System.out.println("...Restarting...");

            // disable all the buttons while loading
            room.setDisable(true);

            // reload all the rooms
            App.reload();
            return null;
          }
        };

    // handle the restart being successfully executed
    restartTask.setOnSucceeded(
        e -> {
          System.out.println("---------------------Succeeded---------------------");
          room.setDisable(false);
        });

    // handle the restart failing
    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          room.setDisable(false);
        });

    // begin the restart
    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }

  /** Handles the user exiting the game, on the settings menu. */
  @FXML
  private void onExit() {
    SoundManager.playSetting();
    System.out.println("Exit");
    System.exit(0);
  }
}
