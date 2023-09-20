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
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class StorageRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private ImageView chemical1;
  @FXML private ImageView chemical2;
  @FXML private ImageView rack;
  @FXML private ImageView rackDoor;

  private RotateTransition rackRotation;
  private RotateTransition rackDoorRotation;

  /** Initializes the Storage Room view */
  public void initialize() {
    bind();

    // make pressing enter send chat
    setEnterToSendChat();

    // Create a random number generator
    Random random = new Random();

    // Define the Y coordinates
    double[] verticalCoordinates = {120, 234, 336};

    // Randomly select Y coordinates for chemical1 and chemical2
    double verticalCoordinate1 = verticalCoordinates[random.nextInt(verticalCoordinates.length)];
    double verticalCoordinate2;

    // Make sure that verticalCoordinate2 is different from verticalCoordinate1
    do {
      verticalCoordinate2 = verticalCoordinates[random.nextInt(verticalCoordinates.length)];
    } while (verticalCoordinate2 == verticalCoordinate1);

    // Loop through each chemical
    for (ImageView chemical : new ImageView[] {chemical1, chemical2}) {
      double verticalCoordinate;
      if (chemical == chemical1) {
        verticalCoordinate = verticalCoordinate1;
      } else {
        verticalCoordinate = verticalCoordinate2;
      }

      // Calculate a random X coordinate within the bounds
      double minHorizontal = 495; // Minimum X coordinate
      double maxHorizontal = 606; // Maximum X coordinate
      double horizontalCoordinate =
          minHorizontal + random.nextDouble() * (maxHorizontal - minHorizontal);

      // Set the position of the chemical
      chemical.setLayoutX(horizontalCoordinate);
      chemical.setLayoutY(verticalCoordinate);
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
    rackDoorRotation = new RotateTransition(Duration.seconds(0.5), rackDoor);
    rackRotation.setCycleCount(RotateTransition.INDEFINITE);
    rackDoorRotation.setCycleCount(RotateTransition.INDEFINITE);
    rackRotation.setByAngle(rotationAngle);
    rackDoorRotation.setByAngle(rotationAngle);
    rackRotation.setAutoReverse(true);
    rackDoorRotation.setAutoReverse(true);
    rackRotation.play();
    rackDoorRotation.play();

    if (rotateRight) {
      animateNode(chemical1, rotationAngle, 45);
      animateNode(chemical2, rotationAngle, 45);
    } else {
      animateNode(chemical1, rotationAngle, -45);
      animateNode(chemical2, rotationAngle, -45);
    }

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
   * Handles the click event on the door of rack.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickRackDoor(MouseEvent event) throws IOException {
    SoundManager.playClick();
    System.out.println("Rack clicked");
    if (GameState.isTask1Completed == false || GameState.isKeyObtained == false) {
      TextToSpeech.speech(GameState.msgLockedRack);
      return;
    } else {
      // Remove key from backpack
      keyBackpack.setVisible(false);
      // Remove the lock from the scene so that items underneath can be clicked
      rackDoor.setVisible(false);
      rackDoor.setDisable(true);
      // make chemicals clickable
      chemical1.setDisable(false);
      chemical2.setDisable(false);
      // make chemicals visible
      chemical1.setOpacity(1);
      chemical2.setOpacity(1);

      // make AI respond to the storage door opening
      gameMaster.giveContext(GptPromptEngineering.beCarefulInStorageLocker());
      gameMaster.respond();
    }
  }

  /**
   * Handles the mouse hover over the door of rack.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Bookshelf
   */
  @FXML
  public void hoverRackDoor(MouseEvent event) throws IOException {
    // make rack area obaque
    rackDoor.setOpacity(0.5);
    // when not hovered, make rack area transparent again
    rackDoor.setOnMouseExited(
        e -> {
          rackDoor.setOpacity(1);
        });
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
    if (!GameState.isTask1Completed) {
      return;
    }

    // turn image to transparent
    chemical1.setOpacity(0);
    // make it unclickable
    chemical1.setDisable(true);
    // add chemical to backpack
    chemical1Backpack.setOpacity(1);
    // change the state of the chemical
    GameState.isChemical1Found = true;

    if ((GameState.isChemical1Found) && (GameState.isChemical2Found)) {
      // make AI aware that task 2 is complete
      gameMaster.giveContext(GptPromptEngineering.introduceThirdTask());

      // set task 2 to be complete
      GameState.isTask2Completed = true;
      GameState.isChecklist2Active = false;
      GameState.isChecklist3Active = true;

      // make the user aware they have done task 2
      checklist2.setVisible(false);
      checklist3.setVisible(true);
    }
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
    if (!GameState.isTask1Completed) {
      return;
    }

    // turn image to transparent
    chemical2.setOpacity(0);
    // make it unclickable
    chemical2.setDisable(true);
    // add chemical to backpack
    chemical2Backpack.setOpacity(1);
    // change the state of the chemical
    GameState.isChemical2Found = true;

    if ((GameState.isChemical1Found) && (GameState.isChemical2Found)) {
      // make AI aware that task 2 is complete
      gameMaster.giveContext(GptPromptEngineering.introduceThirdTask());

      GameState.isTask2Completed = true;
      GameState.isChecklist2Active = false;
      GameState.isChecklist3Active = true;
      checklist2.setVisible(false);
      checklist3.setVisible(true);
    }
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
