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
    SoundManager.playSelect();
    System.out.println("Easy Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.EASY;
    GameState.isHintsInfinite = true;
    GameState.hintsRemaining = -1;
    resetLevelBackground();
    easySelect.setVisible(true);
    RoomBinder.setHintsInfinite(true);
    RoomBinder.toggleHintsVisibility(false);
    checkButton();
  }

  // This method is called when the user clicks on the Medium button
  @FXML
  private void clickMedium() {
    SoundManager.playSelect();
    System.out.println("Medium Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.MEDIUM;
    GameState.hintsRemaining = 5;
    GameState.isHintsInfinite = false;
    resetLevelBackground();
    mediumSelect.setVisible(true);
    RoomBinder.toggleHintsVisibility(true);
    RoomBinder.setHintsInfinite(false);
    RoomBinder.setHintsRemaining(5);
    checkButton();
  }

  // This method is called when the user clicks on the Hard button
  @FXML
  private void clickHard() {
    SoundManager.playSelect();
    System.out.println("Hard Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.HARD;
    GameState.isHintsInfinite = false;
    GameState.hintsRemaining = 0;
    resetLevelBackground();
    hardSelect.setVisible(true);
    RoomBinder.toggleHintsVisibility(true);
    RoomBinder.setHintsInfinite(false);
    RoomBinder.setHintsRemaining(0);
    checkButton();
  }

  // This method is called when the user clicks on the 6 Min button
  @FXML
  private void clickMin6() {
    SoundManager.playSelect();
    System.out.println("6 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(6, 0);
    resetTimeBackground();
    min6Select.setVisible(true);
    checkButton();
  }

  // This method is called when the user clicks on the 4 Min button
  @FXML
  private void clickMin4() {
    SoundManager.playSelect();
    System.out.println("4 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(4, 0);
    resetTimeBackground();
    min4Select.setVisible(true);
    checkButton();
  }

  // This method is called when the user clicks on the 2 Min button
  @FXML
  private void clickMin2() {
    SoundManager.playSelect();
    System.out.println("2 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(2, 0);
    resetTimeBackground();
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

    // make AI aware of first task
    RoomController.gameMaster.giveContext(GptPromptEngineering.introduceFirstTask());

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
