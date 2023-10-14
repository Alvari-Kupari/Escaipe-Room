package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;

/**
 * This is a parent class for any room which has a settings menu. This class is here to avoid
 * duplicate code, By delegating all the settings logic to this class.
 */
public class SettingsController {
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
    // Go to the loading screen
    App.changeScene(Room.LOADING);
    // Set the game back to its default state
    GameState.setDefaults();

    // This allows the game to restart in the background
    Task<Void> restartTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            System.out.println("...Restarting...");

            // reload all the rooms
            App.reload();
            return null;
          }
        };

    // handle the restart being successfully executed
    restartTask.setOnSucceeded(
        e -> {
          System.out.println("---------------------Succeeded---------------------");
        });

    // handle the restart failing
    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
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
