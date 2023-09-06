package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.timer.Timer;

public class TeacherRoomController {

  @FXML private Rectangle mainDoor;
  @FXML private TextArea timer;

  /** Initializes the Teacher Room view */
  public void initialize() {
    // Initialization code goes here

    // bind timer to timer text
    Timer.bindText(Room.TEACHER_ROOM, timer);

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
