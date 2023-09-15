package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GameMaster;

/** Controller class for the room view. */
public class MainRoomController extends RoomController {

  @FXML private Rectangle window;
  @FXML private Rectangle vase;
  @FXML private Polygon exitDoor;
  @FXML private Polygon teacherDoor;
  @FXML private Polygon storageDoor;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here

    // bind common room elements
    RoomManager.bindRoom(
        Room.MAIN_ROOM, chat, timer, tasks, playerInput, sendChat, toggleChat, toggleTasks);

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

    if (!GameState.isKeyFound) {
      showDialog(
          "Info", "Find the key!", "You resolved the riddle, now you know where the key is.");
    } else {
      showDialog("Info", "You Won!", "Good Job!");
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickVase(MouseEvent event) {

    SoundManager.playClick();

    System.out.println("vase clicked");
    if (GameState.isRiddleResolved && !GameState.isKeyFound) {
      showDialog("Info", "Key Found", "You found a key under the vase!");
      GameState.isKeyFound = true;
    }
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickWindow(MouseEvent event) {

    SoundManager.playClick();

    System.out.println("window clicked");
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

    SoundManager.playClick();

    System.out.println("Exit Door clicked");

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
}
