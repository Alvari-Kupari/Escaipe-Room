package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;

public class StorageRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private ImageView chemical1;
  @FXML private ImageView chemical2;
  @FXML private ImageView rack;

  private RotateTransition rackRotation;

  /** Initializes the Storage Room view */
  public void initialize() {
    bind();

    // Create a random number generator
    Random random = new Random();

    // Define the Y coordinates
    double[] yCoordinates = {120, 234, 336};

    // Randomly select Y coordinates for chemical1 and chemical2
    double yCoordinate1 = yCoordinates[random.nextInt(yCoordinates.length)];
    double yCoordinate2;

    // Make sure that yCoordinate2 is different from yCoordinate1
    do {
      yCoordinate2 = yCoordinates[random.nextInt(yCoordinates.length)];
    } while (yCoordinate2 == yCoordinate1);

    // Loop through each chemical
    for (ImageView chemical : new ImageView[] {chemical1, chemical2}) {
      double yCoordinate;
      if (chemical == chemical1) {
        yCoordinate = yCoordinate1;
      } else {
        yCoordinate = yCoordinate2;
      }

      // Calculate a random X coordinate within the bounds
      double minX = 495; // Minimum X coordinate
      double maxX = 606; // Maximum X coordinate
      double xCoordinate = minX + random.nextDouble() * (maxX - minX);

      // Set the position of the chemical
      chemical.setLayoutX(xCoordinate);
      chemical.setLayoutY(yCoordinate);
    }

    // Randomly determine the rotation direction
    boolean rotateRight = random.nextBoolean();
    int rotationAngle;
    if (rotateRight) {
      rotationAngle = 8;
    } else {
      rotationAngle = -8;
    }

    rackRotation = new RotateTransition(Duration.seconds(0.5), rack);
    rackRotation.setCycleCount(RotateTransition.INDEFINITE);
    rackRotation.setByAngle(rotationAngle);
    rackRotation.setAutoReverse(true);
    rackRotation.play();

    if (rotateRight) {
      animateNode(chemical1, rotationAngle, 45);
      animateNode(chemical2, rotationAngle, 45);
    } else {
      animateNode(chemical1, rotationAngle, -45);
      animateNode(chemical2, rotationAngle, -45);
    }

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

  /**
   * Creates an animation for a node.
   *
   * @param node the node to animate
   * @param angle the angle to rotate the node by
   * @param x the distance to translate the node by
   * @return the animation
   */
  private void animateNode(Node node, int angle, int x) {
    RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), node);
    rotation.setCycleCount(RotateTransition.INDEFINITE);
    rotation.setByAngle(angle);
    rotation.setAutoReverse(true);

    TranslateTransition translation = new TranslateTransition(Duration.seconds(0.5), node);
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
    // make chemical area obaque
    chemical1.setOpacity(0.5);
    // when not hovered, make chemical area transparent again
    chemical1.setOnMouseExited(
        e -> {
          if (!GameState.isChemical1Found) {
            chemical1.setOpacity(1);
          }
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
    // make chemical area obaque
    chemical2.setOpacity(0.5);
    // when not hovered, make chemical area transparent again
    chemical2.setOnMouseExited(
        e -> {
          if (!GameState.isChemical2Found) {
            chemical2.setOpacity(1);
          }
        });
  }
}
