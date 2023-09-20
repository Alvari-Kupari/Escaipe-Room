package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
      GameState.isTask1Completed = true;
      GameState.isChecklist1Active = false;
      GameState.isChecklist2Active = true;
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
          System.out.println("TASK 3 COMPLETED");
          GameState.isTask3Completed = true;
          GameState.isChecklist3Active = false;
          GameState.isChecklist4Active = true;
          checklist3.setVisible(false);
          checklist4.setVisible(true);
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
