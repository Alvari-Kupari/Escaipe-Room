package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.timer.Timer;

/** This is the start screen controller, where difficulty is selected and you can start game. */
public class StartController {

  @FXML private ImageView easy;
  @FXML private ImageView medium;
  @FXML private ImageView hard;
  @FXML private ImageView min6;
  @FXML private ImageView min4;
  @FXML private ImageView min2;

  @FXML private ImageView easySelect;
  @FXML private ImageView mediumSelect;
  @FXML private ImageView hardSelect;
  @FXML private ImageView min6Select;
  @FXML private ImageView min4Select;
  @FXML private ImageView min2Select;

  @FXML private ImageView speechOn;
  @FXML private ImageView speechOff;

  @FXML private Button btnStart;

  /** Loads the fxml for this room. */
  @FXML
  private void initialize() {

    System.out.println();
    System.out.println("************** Initialising StartController **************");
  }

  /** Handles the user selecting the easy option. */
  @FXML
  private void onClickEasy() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("Easy Selected");
    // Set the difficulty to easy
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.EASY;
    // Set game state to infinite hints
    GameState.isHintsInfinite = true;
    GameState.hintsRemaining = -1;
    // Reset the level background
    resetLevelBackground();
    // Set the easy select image to visible
    easySelect.setVisible(true);
    // Set the hints to infinite
    RoomBinder.setHintsInfinite(true);
    RoomBinder.toggleHintsVisibility(false);
    checkButton();
  }

  /** Handles the user selecting the medium option. */
  @FXML
  private void onClickMedium() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("Medium Selected");
    // Set the game state to medium
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.MEDIUM;
    // Set the hints to 5
    GameState.hintsRemaining = 5;
    GameState.isHintsInfinite = false;
    // Reset the level background
    resetLevelBackground();
    // Set the medium select image to visible
    mediumSelect.setVisible(true);
    RoomBinder.toggleHintsVisibility(true);
    RoomBinder.setHintsInfinite(false);
    RoomBinder.setHintsRemaining(5);
    checkButton();
  }

  /** Handles the user selecting the hard option. */
  @FXML
  private void onClickHard() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("Hard Selected");
    // Set the game state to hard
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.HARD;
    GameState.isHintsInfinite = false;
    GameState.hintsRemaining = 0;
    // Reset the level background
    resetLevelBackground();
    // Set the hard select image to visible
    hardSelect.setVisible(true);
    RoomBinder.toggleHintsVisibility(true);
    RoomBinder.setHintsInfinite(false);
    RoomBinder.setHintsRemaining(0);
    checkButton();
  }

  /** This method is called when the user clicks on the 6 Min button. */
  @FXML
  private void onClickMin6() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("6 Min Selected");
    // Set the game state to 6 minutes
    GameState.isTimeLimitSelected = true;
    // Set the timer to 6 minutes
    Timer.setTimer(6, 0);
    // Reset the time background
    resetTimeBackground();
    // Set the 6 min select image to visible
    min6Select.setVisible(true);
    // Check if the start button should be enabled
    checkButton();
  }

  /** This method is called when the user clicks on the 4 Min button. */
  @FXML
  private void onClickMin4() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("4 Min Selected");
    // Set the game state to 4 minutes
    GameState.isTimeLimitSelected = true;
    // Set the timer to 4 minutes
    Timer.setTimer(4, 0);
    // Reset the time background
    resetTimeBackground();
    // Set the 4 min select image to visible
    min4Select.setVisible(true);
    // Check if the start button should be enabled
    checkButton();
  }

  /** This method is called when the user clicks on the 2 Min button. */
  @FXML
  private void onClickMin2() {
    // Play the select sound
    SoundManager.playSelect();
    System.out.println("2 Min Selected");
    // Set the game state to 2 minutes
    GameState.isTimeLimitSelected = true;
    // Set the timer to 2 minutes
    Timer.setTimer(2, 0);
    // Reset the time background
    resetTimeBackground();
    // Set the 2 min select image to visible
    min2Select.setVisible(true);
    checkButton();
  }

  /**
   * Turns the speech off, when the speech off button is clicked.
   *
   * @param event the mouse event.
   * @throws IOException if the room is not loaded properly.
   */
  @FXML
  private void onTurnSpeechOff(MouseEvent event) throws IOException {
    System.out.println("Turning speech off");
    GameState.isAudioOn = false;
    speechOn.setVisible(false);
    speechOff.setVisible(true);
    RoomBinder.showMuteImage(true);
  }

  /**
   * Turns the speech on, when the speech on button is clicked.
   *
   * @param event the mouse event.
   * @throws IOException If the room isn't loaded properly.
   */
  @FXML
  private void onTurnSpeechOn(MouseEvent event) throws IOException {
    System.out.println("Turning speech on");
    GameState.isAudioOn = true;
    speechOn.setVisible(true);
    speechOff.setVisible(false);
    RoomBinder.showMuteImage(false);
  }

  /**
   * Starts the game. Triggered when the user clicks "start game" button.
   *
   * @param event the mouse event
   */
  @FXML
  private void onStartGame(ActionEvent event) {
    SoundManager.playSelect();
    System.out.println("Action Start");
    System.out.println(event.getSource().getClass());
    System.out.println(event.getSource());

    GameState.isGameStarted = true;

    App.changeScene(Room.MAIN_ROOM);

    // start AI storyline
    RoomController.gameMaster.giveContext(GptPromptEngineering.introduceGame());
    RoomController.gameMaster.respond();

    // start timer
    Timer.startTimer();
    TextToSpeech.talk(GameState.msgFlask);
  }

  /** Resets the background for the difficulty. */
  private void resetLevelBackground() {
    easySelect.setVisible(false);
    mediumSelect.setVisible(false);
    hardSelect.setVisible(false);
  }

  /** Resets the time selection screen. */
  private void resetTimeBackground() {
    min6Select.setVisible(false);
    min4Select.setVisible(false);
    min2Select.setVisible(false);
  }

  /** Determines whether to disable the start button. */
  private void checkButton() {
    if (GameState.isDifficultySelected && GameState.isTimeLimitSelected) {
      btnStart.setDisable(false);
    }
  }

  /**
   * Hover effect for the clickable images. Useful for making the GUI look nicer.
   *
   * @param event the mouse event
   */
  @FXML
  private void onHover(MouseEvent event) {
    // Set image to hover
    ((ImageView) event.getSource()).setOpacity(0.5);
    // Set image back to normal when not hovering
    ((ImageView) event.getSource())
        .setOnMouseExited(
            e -> {
              ((ImageView) event.getSource()).setOpacity(1);
            });
  }
}
