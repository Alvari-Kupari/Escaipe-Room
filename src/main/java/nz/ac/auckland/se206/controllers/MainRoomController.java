package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
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

  @FXML private Rectangle flask;
  @FXML private Polygon exitDoor;
  @FXML private Polygon teacherDoor;
  @FXML private Polygon storageDoor;
  @FXML private ImageView flask1;
  @FXML private ImageView flask2;
  @FXML private ImageView flask3;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here

    // bind common room elements
    RoomManager.bindRoom(
        Room.MAIN_ROOM,
        chat,
        timer,
        tasks,
        playerInput,
        sendChat,
        toggleChat,
        toggleTasks,
        chemical1Backpack,
        chemical2Backpack,
        thinkingFace);

    // intialize chat and tasks to be open
    GameState.areTasksOpen = true;
    GameState.isChatOpen = true;

    // initialize game master
    gameMaster = new GameMaster(0.5, 0.5);

    // Hide the thinking face when the chat text appears
    chat.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Check if chat text is not empty
              if (!newValue.isEmpty()) {
                thinkingFace.setVisible(false);
                GameState.thinkingFaceVisible = false;
              }
            });

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

    SoundManager.playClick();
    if (GameState.isTask2Completed) {
      return;
    }

    if (GameState.isChemical1Found || GameState.isChemical2Found) {
      if ((GameState.isChemical1Found) && (GameState.isChemical1Added == false)) {
        System.out.println("ADDING CHEMICAL 1");
        hideFlasks();
        flask2.setVisible(true);
        chemical1Backpack.setVisible(false);
        GameState.isChemical1Added = true;

        if (GameState.isChemical1Added && GameState.isChemical2Added) {
          System.out.println("TASK 2 COMPLETED");
          GameState.isTask2Completed = true;
        }
        return;
      }
      if ((GameState.isChemical2Found) && (GameState.isChemical2Added == false)) {
        System.out.println("ADDING CHEMICAL 2");
        hideFlasks();
        flask3.setVisible(true);
        chemical2Backpack.setVisible(false);
        GameState.isChemical2Added = true;

        if (GameState.isChemical1Added && GameState.isChemical2Added) {
          System.out.println("TASK 2 COMPLETED");
          GameState.isTask2Completed = true;
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

  private void hideFlasks() {
    flask2.setVisible(false);
    flask3.setVisible(false);
  }
}
