package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.timer.Timer;

/** This is the start screen controller, where difficulty is selected and you can start game. */
public class StartController {

  @FXML private ImageView easy;
  @FXML private ImageView medium;
  @FXML private ImageView hard;
  @FXML private ImageView min6;
  @FXML private ImageView min4;
  @FXML private ImageView min2;

  @FXML private ImageView backEasy;
  @FXML private ImageView backMedium;
  @FXML private ImageView backHard;
  @FXML private ImageView back6;
  @FXML private ImageView back4;
  @FXML private ImageView back2;

  @FXML private Button btnStart;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising StartController **************");
  }

  @FXML
  private void clickEasy() {
    System.out.println("Easy Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.EASY;
    resetLevelBackground();
    backEasy.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickMedium() {
    System.out.println("Medium Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.MEDIUM;
    resetLevelBackground();
    backMedium.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickHard() {
    System.out.println("Hard Selected");
    GameState.isDifficultySelected = true;
    GameState.levelDifficulty = GameState.Difficulty.HARD;
    resetLevelBackground();
    backHard.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickMin6() {
    System.out.println("6 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(6, 0);
    resetTimeBackground();
    back6.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickMin4() {
    System.out.println("4 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(4, 0);
    resetTimeBackground();
    back4.setVisible(true);
    checkButton();
  }

  @FXML
  private void clickMin2() {
    System.out.println("2 Min Selected");
    GameState.isTimeLimitSelected = true;
    Timer.setTimer(2, 0);
    resetTimeBackground();
    back2.setVisible(true);
    checkButton();
  }

  @FXML
  private void startGame(ActionEvent event) {
    System.out.println("Action Start");
    System.out.println(event.getSource().getClass());
    System.out.println(event.getSource());

    GameState.isGameStarted = true;

    // start timer
    Timer.startTimer();

    App.changeScene(Room.MAIN_ROOM);
  }

  private void resetLevelBackground() {
    backEasy.setVisible(false);
    backMedium.setVisible(false);
    backHard.setVisible(false);
  }

  private void resetTimeBackground() {
    back6.setVisible(false);
    back4.setVisible(false);
    back2.setVisible(false);
  }

  private void checkButton() {
    if (GameState.isDifficultySelected && GameState.isTimeLimitSelected) {
      btnStart.setDisable(false);
    }
  }
}
