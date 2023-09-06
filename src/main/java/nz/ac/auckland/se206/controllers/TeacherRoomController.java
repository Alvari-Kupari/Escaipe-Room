package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class TeacherRoomController {

  @FXML private Rectangle mainDoor;
  @FXML private Rectangle professor;

  /** Initializes the Teacher Room view */
  public void initialize() {
    // Initialization code goes here
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
    System.out.println("Main Door clicked");

    Rectangle rectangle = (Rectangle) event.getSource();
    Scene sceneRectangleIsIn = rectangle.getScene();
    sceneRectangleIsIn.setRoot(SceneManager.getUiRoot(AppUi.MAIN_ROOM));
  }

  /**
   * Handles the click event on the professor.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickProfessor(MouseEvent event) throws IOException {
    System.out.println("Professor clicked");

    Rectangle rectangle = (Rectangle) event.getSource();
    Scene sceneRectangleIsIn = rectangle.getScene();
    sceneRectangleIsIn.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
  }
}
