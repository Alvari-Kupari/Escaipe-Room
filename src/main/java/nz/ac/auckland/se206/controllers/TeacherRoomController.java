package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;

public class TeacherRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private Polygon laptop;

  /** Initializes the Teacher Room view */
  public void initialize() {
    // Initialization code goes here

    // bind common room elements
    RoomManager.bindRoom(
        Room.TEACHER_ROOM,
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

    // Hide the thinking face if chat is visable
    chat.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Check if chat text is not empty
              if (!newValue.isEmpty()) {
                thinkingFace.setVisible(false);
              }
            });

    System.out.println();
    System.out.println("************** Initialising TeacherRoomController **************");
  }

  /**
   * Handles the click event on the main door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickMainDoor(MouseEvent event) throws IOException {

    SoundManager.playClick();

    App.changeScene(Room.MAIN_ROOM);
    System.out.println("Main Door clicked");
  }

  /**
   * Handles the mouse hover over the main door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Bookshelf
   */
  @FXML
  public void hoverMainDoor(MouseEvent event) throws IOException {
    // make the door area obaque
    mainDoor.setOpacity(0.5);
    // when not hovered, make the door area transparent again
    mainDoor.setOnMouseExited(
        e -> {
          mainDoor.setOpacity(0);
        });
  }

  @FXML
  private void onOpenLaptop() {
    App.changeScene(Room.LAPTOP);
  }

  @FXML
  private void onHoverLaptop() {
    laptop.setOpacity(0.5);

    laptop.setOnMouseExited(
        e -> {
          laptop.setOpacity(0);
        });
  }
}
