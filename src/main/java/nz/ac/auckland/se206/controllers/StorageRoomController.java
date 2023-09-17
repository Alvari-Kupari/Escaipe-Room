package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;

public class StorageRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private ImageView chemical1;
  @FXML private ImageView chemical2;
  @FXML private ImageView rack;
  @FXML private Polygon chemical1Select;
  @FXML private Polygon chemical2Select;

  private RotateTransition rackRotation;

  /** Initializes the Storage Room view */
  public void initialize() {

    rackRotation = new RotateTransition(Duration.seconds(1), rack);
    rackRotation.setCycleCount(RotateTransition.INDEFINITE);
    rackRotation.setByAngle(10);
    rackRotation.setAutoReverse(true);
    rackRotation.play();

    animateNode(chemical1, 10, 25);
    animateNode(chemical1Select, 10, 25);
    animateNode(chemical2, 10, 15);
    animateNode(chemical2Select, 10, 15);

    // bind common room elements
    RoomManager.bindRoom(
        Room.STORAGE_ROOM,
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

    // Hide the thinking face when the chat text appears
    chat.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Check if chat text is not empty
              if (!newValue.isEmpty()) {
                thinkingFace.setVisible(false);
              }
            });

    System.out.println();
    System.out.println("************** Initialising StorageRoomController **************");
  }

  private void animateNode(Node node, int angle, int x) {
    RotateTransition rotation = new RotateTransition(Duration.seconds(1), node);
    rotation.setCycleCount(RotateTransition.INDEFINITE);
    rotation.setByAngle(angle);
    rotation.setAutoReverse(true);

    TranslateTransition translation = new TranslateTransition(Duration.seconds(1), node);
    translation.setCycleCount(TranslateTransition.INDEFINITE);
    translation.setByX(x);
    translation.setAutoReverse(true);

    rotation.play();
    translation.play();
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

  /**
   * Handles the click event on the chemical 1.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickChemical1(MouseEvent event) throws IOException {
    SoundManager.playClick();
    System.out.println("Chemical 1 clicked");
    // turn image to transparent
    chemical1.setOpacity(0);
    // make it unclickable
    chemical1.setDisable(true);
    chemical1Select.setDisable(true);
    // add chemical to backpack
    chemical1Backpack.setOpacity(1);
    // change the state of the chemical
    GameState.isChemical1Found = true;
  }

  /**
   * Handles the click event on the chemical 2.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickChemical2(MouseEvent event) throws IOException {
    SoundManager.playClick();
    System.out.println("Chemical 2 clicked");
    // turn image to transparent
    chemical2.setOpacity(0);
    // make it unclickable
    chemical2.setDisable(true);
    chemical2Select.setDisable(true);
    // add chemical to backpack
    chemical2Backpack.setOpacity(1);
    // change the state of the chemical
    GameState.isChemical2Found = true;
  }

  /**
   * Handles the mouse hover over the chemical 1.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Bookshelf
   */
  @FXML
  public void hoverChemical1(MouseEvent event) throws IOException {
    // make the door area obaque
    chemical1Select.setOpacity(0.5);
    // when not hovered, make the door area transparent again
    chemical1Select.setOnMouseExited(
        e -> {
          chemical1Select.setOpacity(0);
        });
  }

  /**
   * Handles the mouse hover over the chemical 2.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Bookshelf
   */
  @FXML
  public void hoverChemical2(MouseEvent event) throws IOException {
    // make the door area obaque
    chemical2Select.setOpacity(0.5);
    // when not hovered, make the door area transparent again
    chemical2Select.setOnMouseExited(
        e -> {
          chemical2Select.setOpacity(0);
        });
  }
}
