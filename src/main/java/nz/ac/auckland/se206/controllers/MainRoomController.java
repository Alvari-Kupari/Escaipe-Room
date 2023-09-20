package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GameMaster;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.timer.Timer;

/** Controller class for the room view. */
public class MainRoomController extends RoomController {

  @FXML private Polygon flask;
  @FXML private Polygon exitDoor;
  @FXML private Polygon teacherDoor;
  @FXML private Polygon storageDoor;
  @FXML private ImageView flask1;
  @FXML private ImageView flask2;
  @FXML private ImageView flask3;
  @FXML private ImageView key;
  @FXML private ImageView pouch;
  @FXML private ImageView openedPouch;
  @FXML private Slider zipper;

  private static GameMaster flaskTalkingGameMaster;
  private static GameMaster tasksDoneTalkingGameMaster;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
    Timer.bindText(timer);

    // bind common room elements
    bind();

    // make pressing enter send chat
    setEnterToSendChat();

    // intialize chat and tasks to be open
    GameState.areTasksOpen = true;
    GameState.isChatOpen = true;

    // initialize game master
    gameMaster = new GameMaster(0.5, 0.5);

    flaskTalkingGameMaster = new GameMaster(0.5, 0.5);
    tasksDoneTalkingGameMaster = new GameMaster(0.5, 0.5);

    flaskTalkingGameMaster.giveContext(GptPromptEngineering.introduceFlask());
    flaskTalkingGameMaster.gettalkFlask();
    System.out.println("msgFlask" + GameState.msgFlask);

    tasksDoneTalkingGameMaster.giveContext(GptPromptEngineering.tasksComplete());
    tasksDoneTalkingGameMaster.gettalkComplete();

    System.out.println();
    System.out.println("************** Initialising MainRoomController **************");
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  // when slider is compleded, hide the slider
  @FXML
  public void zipperDragReleased(MouseEvent event) {
    if (zipper.getValue() == zipper.getMax()) {
      // Mouse drag released on the slider at the maximum value
      // Handle accordingly
      System.out.println("Mouse drag released on the slider at maximum value.");
      // hide the zipper
      zipper.setVisible(false);
      // Disable the zipper
      zipper.setDisable(true);
      // Hide the pouch
      pouch.setVisible(false);
      // Show the opened pouch
      openedPouch.setVisible(true);
      // Show the key
      key.setVisible(true);
    }
  }

  // when key is clicked, hide the key
  @FXML
  public void clickKey(MouseEvent event) {
    System.out.println("Key clicked");
    // Hide the key
    key.setVisible(false);
    // Make the key unclickable
    key.setDisable(true);
    // Make key appear in backpack
    keyBackpack.setVisible(true);
    // Update the game state
    GameState.isKeyObtained = true;

    // make AI aware of the new change
    gameMaster.giveContext(GptPromptEngineering.playerCollectedKey());
    gameMaster.respond();
  }

  /**
   * Handles the click event on the exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {

    SoundManager.playClick();

    System.out.println("exitDoor clicked");

    if (!GameState.isRiddleResolved) {
      showDialog("Info", "Riddle", "You need to resolve the riddle!");

      App.changeScene(Room.CHAT);

      return;
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickFlask(MouseEvent event) {

    SoundManager.playClick();

    System.out.println("flask clicked");

    if (!GameState.isTask1Completed) {
      // make AI aware of the task completion
      gameMaster.giveContext(GptPromptEngineering.introduceSecondTask());

      // set all necessary game states to reflect task 2 completion
      GameState.isTask1Completed = true;
      GameState.isChecklist1Active = false;
      GameState.isChecklist2Active = true;

      // make the checklist update to the task completion
      checklist1.setVisible(false);
      checklist2.setVisible(true);
    }
    if (GameState.isTask3Completed) {
      return;
    }

    if (GameState.isChemical1Found || GameState.isChemical2Found) {
      if ((GameState.isChemical1Found) && (GameState.isChemical1Added == false)) {
        System.out.println("ADDING CHEMICAL 1");
        hideFlasks();
        SoundManager.playSplash();
        flask2.setVisible(true);
        chemical1Backpack.setVisible(false);
        GameState.isChemical1Added = true;

        if (GameState.isChemical1Added && GameState.isChemical2Added) {

          // set all game state variables to reflect task 3 completion
          System.out.println("TASK 3 COMPLETED");
          GameState.isTask3Completed = true;
          GameState.isChecklist3Active = false;
          GameState.isChecklist4Active = true;

          // tick of the task in the checklist
          checklist3.setVisible(false);
          checklist4.setVisible(true);

          // make AI aware that task 3 is done
          gameMaster.giveContext(GptPromptEngineering.introduceFourthTask());
        }
        return;
      }
      if ((GameState.isChemical2Found) && (GameState.isChemical2Added == false)) {
        System.out.println("ADDING CHEMICAL 2");
        hideFlasks();
        SoundManager.playBubbles();
        flask3.setVisible(true);
        chemical2Backpack.setVisible(false);
        GameState.isChemical2Added = true;

        if (GameState.isChemical1Added && GameState.isChemical2Added) {
          System.out.println("TASK 3 COMPLETED");
          GameState.isTask3Completed = true;
          GameState.isChecklist3Active = false;
          GameState.isChecklist4Active = true;
          checklist3.setVisible(false);
          checklist4.setVisible(true);

          // make AI aware that task 3 is done
          gameMaster.giveContext(GptPromptEngineering.introduceFourthTask());
        }
        return;
      }
    }
  }

  /**
   * Handles the click event on the teacher exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Teacher Room
   */
  @FXML
  public void clickTeacherDoor(MouseEvent event) throws IOException {

    SoundManager.playClick();

    System.out.println("Teacher Door clicked");
    App.changeScene(Room.TEACHER_ROOM);
  }

  /**
   * Handles the mouse hover over the teacher exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Teacher Room
   */
  @FXML
  public void hoverTeacherDoor(MouseEvent event) throws IOException {
    // make the exitDoor area obaque
    teacherDoor.setOpacity(0.5);
    // when not hovered, make the exitDoor area transparent again
    teacherDoor.setOnMouseExited(
        e -> {
          teacherDoor.setOpacity(0);
        });
  }

  /**
   * Handles the click event on the storage exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Storage Room
   */
  @FXML
  public void clickStorageDoor(MouseEvent event) throws IOException {

    SoundManager.playClick();

    System.out.println("Storage Door clicked");

    App.changeScene(Room.STORAGE_ROOM);
  }

  /**
   * Handles the mouse hover over the storage exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Teacher Room
   */
  @FXML
  public void hoverStorageDoor(MouseEvent event) throws IOException {
    // make the exitDoor area obaque
    storageDoor.setOpacity(0.5);
    // when not hovered, make the exitDoor area transparent again
    storageDoor.setOnMouseExited(
        e -> {
          storageDoor.setOpacity(0);
        });
  }

  /**
   * Handles the click event on the exit exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Storage Room
   */
  @FXML
  public void clickExitDoor(MouseEvent event) throws IOException {
    System.out.println("Exit Door clicked");

    if ((!GameState.isTask1Completed)
        || (!GameState.isTask2Completed)
        || (!GameState.isTask3Completed)
        || (!GameState.isTask4Completed)) {
      SoundManager.playError();
      // Code for professor to say you can't leave until you finish all tasks
      return;
    }

    SoundManager.playClick();

    // caculate what grade to show
    calculateGrade();

    App.changeScene(Room.EXIT);
  }

  /**
   * Handles the mouse hover over the exit exitDoor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Teacher Room
   */
  @FXML
  public void hoverExitDoor(MouseEvent event) throws IOException {
    // make the exitDoor area obaque
    exitDoor.setOpacity(0.5);
    // when not hovered, make the exitDoor area transparent again
    exitDoor.setOnMouseExited(
        e -> {
          exitDoor.setOpacity(0);
        });
  }

  private void hideFlasks() {
    flask2.setVisible(false);
    flask3.setVisible(false);
  }

  /**
   * Handles the mouse hover over the flask.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void hoverFlask(MouseEvent event) throws IOException {
    // make the flask area obaque
    flask.setOpacity(0.5);
    // when not hovered, make the flask area transparent again
    flask.setOnMouseExited(
        e -> {
          flask.setOpacity(0);
        });
  }

  private void calculateGrade() {
    // set the task completion time
    int time = Timer.getTimeInSeconds();
    GameState.taskCompletionTime = time;

    // figure out which grade to give
    String grade = "A+";
    if (time < 60) {
      grade = "B";
    }

    if (time < 30) {
      grade = "C";
    }

    if (time < 15) {
      grade = "D";
    }

    RoomBinder.grade.setText(grade);
  }
}
