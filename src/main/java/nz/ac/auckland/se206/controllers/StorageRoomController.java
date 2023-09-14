package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.Room;

public class StorageRoomController {

  @FXML private Rectangle mainDoor;
  @FXML private TextArea timer;
  @FXML private TextArea chat;
  @FXML private TextArea tasks;
  @FXML private TextField input;
  @FXML private Button toggleTasks;
  @FXML private Button toggleChat;
  @FXML private Button sendChat;

  /** Initializes the Storage Room view */
  public void initialize() {
    // Initialization code goes here

    // bind common room elements
    RoomController.bindRoom(Room.STORAGE_ROOM, chat, timer, tasks, input);

    System.out.println();
    System.out.println("************** Initialising StorageRoomController **************");
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
