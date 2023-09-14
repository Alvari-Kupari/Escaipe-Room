package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;

public class TeacherRoomController extends RoomController {

  @FXML private Rectangle mainDoor;

  /** Initializes the Teacher Room view */
  public void initialize() {
    // Initialization code goes here

    // bind common room elements
    RoomManager.bindRoom(
        Room.TEACHER_ROOM, chat, timer, tasks, playerInput, sendChat, toggleChat, toggleTasks);

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
    App.changeScene(Room.MAIN_ROOM);
    System.out.println("Main Door clicked");
  }
}
