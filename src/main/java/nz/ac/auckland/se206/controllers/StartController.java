package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
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

  @FXML
  private void clickEasy() {
    SoundManager.playSelect();
    System.out.println("Easy Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.EASY;
    GameState.isHintsInfinite = true;
    resetLevelBackground();
    easySelect.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickMedium() {
    SoundManager.playSelect();
    System.out.println("Medium Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.MEDIUM;
    GameState.hintsRemaining = 5;
    resetLevelBackground();
    mediumSelect.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickHard() {
    SoundManager.playSelect();
    System.out.println("Hard Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.HARD;
    GameState.hintsRemaining = 0;
    resetLevelBackground();
    hardSelect.setVisible(true);
    checkButton();
  }

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

    // start timer
    Timer.startTimer();
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
