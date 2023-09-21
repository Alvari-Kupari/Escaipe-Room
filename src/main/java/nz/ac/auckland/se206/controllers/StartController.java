package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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

  @FXML private Button btnStart;

  @FXML
  private void initialize() {

    System.out.println();
    System.out.println("************** Initialising StartController **************");
  }

  // This method is called when the user clicks on the Easy button
  @FXML
  private void clickEasy() {
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

  // This method is called when the user clicks on the Medium button
  @FXML
  private void clickMedium() {
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

  // This method is called when the user clicks on the Hard button
  @FXML
  private void clickHard() {
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

  // This method is called when the user clicks on the 6 Min button
  @FXML
  private void clickMin6() {
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

  // This method is called when the user clicks on the 4 Min button
  @FXML
  private void clickMin4() {
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

  // This method is called when the user clicks on the 2 Min button
  @FXML
  private void clickMin2() {
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

  @FXML
  private void startGame(ActionEvent event) {
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
    TextToSpeech.speech(GameState.msgFlask);
  }

  private void resetLevelBackground() {
    easySelect.setVisible(false);
    mediumSelect.setVisible(false);
    hardSelect.setVisible(false);
  }

  private void resetTimeBackground() {
    min6Select.setVisible(false);
    min4Select.setVisible(false);
    min2Select.setVisible(false);
  }

  private void checkButton() {
    if (GameState.isDifficultySelected && GameState.isTimeLimitSelected) {
      btnStart.setDisable(false);
    }
  }
}
