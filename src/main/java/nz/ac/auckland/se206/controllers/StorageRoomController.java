package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

  @FXML private ImageView settingsIcon;
  @FXML private Button btnBack;
  @FXML private Button btnMainMenu;
  @FXML private Button btnExit;

  private RotateTransition rackRotation;
  private RotateTransition rackDoorRotation;
  private double horizontalOffset;
  private double verticalOffset;

  /** Initializes the Storage Room view */
  public void initialize() {
    bind();

    // set chat prompt text
    playerInput.setPromptText("Chat here ... ");

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
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");

    if (event.getCode().equals(KeyCode.ESCAPE)) {
      SoundManager.playSetting();
      System.out.println("Escape pressed");
      if (paneSettings.isVisible()) {
        paneSettings.setVisible(false);
      } else {
        paneSettings.setVisible(true);
      }
    }
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
   * Creates an animation for a node.
   *
   * @param node the node to animate
   * @param angle the angle to rotate the node by
   * @param x the distance to translate the node by
   * @return the animation
   */
  private void animateNode(Node node, int angle, int x) {
    // Create a new rotate transition
    RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), node);
    // Set the number of cycles to indefinite
    rotation.setCycleCount(RotateTransition.INDEFINITE);
    // Rotate the node by the specified angle
    rotation.setByAngle(angle);
    // Set the animation to auto reverse
    rotation.setAutoReverse(true);

    // Create a new translate transition
    TranslateTransition translation = new TranslateTransition(Duration.seconds(0.5), node);
    // Set the number of cycles to indefinite
    translation.setCycleCount(TranslateTransition.INDEFINITE);
    // Translate the node by the specified distance
    translation.setByX(x);
    // Set the animation to auto reverse
    translation.setAutoReverse(true);

    // Play the animations
    rotation.play();
    translation.play();
  }

  // Allow key to be draggable with mouse
  @FXML
  public void dragKey(MouseEvent event) {
    // check if task1 is completed and key is found
    if (GameState.isTask1Completed && GameState.isKeyObtained) {
      System.out.println("Dragging key");
      // make the keyBackpack image opaque
      keyBackpack.setOpacity(0.5);
      // get the x and y offset of the mouse
      horizontalOffset = event.getSceneX();
      verticalOffset = event.getSceneY();
      // make the keyBackpack image draggable
      keyBackpack.setOnMouseDragged(
          e -> {
            // set the keyBackpack image location to the mouse location
            keyBackpack.setX(e.getSceneX() - horizontalOffset);
            keyBackpack.setY(e.getSceneY() - verticalOffset);
            // make sure that the ckeyBackpack image does not go out of the screen ALL THE TIME
            // if dragged outside bounds, set the keyBackpack image location back to in bounds
            if (keyBackpack.getX() < 10) {
              keyBackpack.setX(50);
            } else if (keyBackpack.getX() > 800) {
              keyBackpack.setX(800);
            }
            if (keyBackpack.getY() < 10) {
              keyBackpack.setY(40);
            } else if (keyBackpack.getY() > 470) {
              keyBackpack.setY(470);
            }
            // if the keyBackpack overlaps with the rackDoor, hide the keyBackpack
            if (keyBackpack.getBoundsInParent().intersects(rackDoor.getBoundsInParent())) {
              // Remove key from backpack
              keyBackpack.setVisible(false);
              // disable keyBackpack
              keyBackpack.setDisable(true);
              // Remove the lock from the scene so that items underneath can be clicked
              rackDoor.setVisible(false);
              rackDoor.setDisable(true);
              // make chemicals clickable
              chemical1.setDisable(false);
              chemical2.setDisable(false);
              // make chemicals visible
              chemical1.setOpacity(1);
              chemical2.setOpacity(1);
              // Set game state of door opened
              GameState.doorOpened = true;
            }
          });
      // make the keyBackpack back to not opaque when not dragged
      keyBackpack.setOnMouseReleased(
          e -> {
            keyBackpack.setOpacity(1);
            // if the rackdoor is opened, prompt gpt
            if (GameState.doorOpened) {
              // click sound
              SoundManager.playClick();
              // make AI respond to the storage door opening
              gameMaster.giveContext(GptPromptEngineering.beCarefulInStorageLocker());
              gameMaster.respond();
            }
          });
    } else {
      return;
    }
  }

  // hover over the keyBackpack image
  @FXML
  public void hoverKey(MouseEvent event) {
    // check if task 1 is completed and key is found
    if (GameState.isTask1Completed) {
      // make the keyBackpack area obaque
      keyBackpack.setOpacity(0.5);
      // when not hovered, make the keyBackpack area transparent again
      keyBackpack.setOnMouseExited(
          e -> {
            keyBackpack.setOpacity(1);
          });
    } else {
      return;
    }
  }

  /**
   * Handles the click event on the door of rack.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void clickRackDoor(MouseEvent event) throws IOException {
    // play click sound
    SoundManager.playClick();
    System.out.println("Rack clicked");
    // Check if task 1 is completed and if key is found and give appropriate message
    if (GameState.isTask1Completed == false && GameState.isKeyObtained == false) {
      TextToSpeech.talk(GameState.msgLockedRack);
      return;
    }

    if (GameState.isTask1Completed == true && GameState.isKeyObtained == false) {
      TextToSpeech.talk(GameState.msgNeedKey);
      return;
    }

    if (GameState.isKeyObtained == true && GameState.isTask1Completed == true) {
      TextToSpeech.talk(GameState.msgUseKey);
      return;
    }

    if (GameState.isKeyObtained == true && GameState.isTask1Completed == false) {
      TextToSpeech.talk(GameState.msgUseKeyAndTask1);
      return;
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

  @FXML
  public void clickSettings(MouseEvent event) throws IOException {
    SoundManager.playSetting();
    System.out.println("Settings Icon clicked");
    if (paneSettings.isVisible()) {
      paneSettings.setVisible(false);
    } else {
      paneSettings.setVisible(true);
    }
  }

  @FXML
  public void turnSpeechOff(MouseEvent event) throws IOException {
    SoundManager.playSetting();
    System.out.println("Turning speech off");
    GameState.isAudioOn = false;
    speechOn.setVisible(false);
    speechOff.setVisible(true);
  }

  @FXML
  public void turnSpeechOn(MouseEvent event) throws IOException {
    SoundManager.playSetting();
    System.out.println("Turning speech on");
    GameState.isAudioOn = true;
    speechOn.setVisible(true);
    speechOff.setVisible(false);
  }

  @FXML
  public void goBack() {
    SoundManager.playSetting();
    paneSettings.setVisible(false);
  }

  // This method is called when the user clicks on the Main Menu button
  @FXML
  private void goMainMenu() throws IOException {
    SoundManager.playSetting();
    System.out.println("Go to Main Menu");
    // Set the loading image to visible
    loading.setVisible(true);
    // Disable the buttons for exiting
    btnExit.setDisable(true);
    // Disable the buttons for going to the main menu
    btnMainMenu.setDisable(true);
    btnBack.setDisable(true);
    // Set the game back to its default state
    GameState.setDefaults();

    // This allows the game to restart in the background
    Task<Void> restartTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            System.out.println("...Restarting...");

            btnExit.setDisable(true);
            btnMainMenu.setDisable(true);
            App.reloadFXML();
            return null;
          }
        };

    restartTask.setOnSucceeded(
        e -> {
          System.out.println("---------------------Succeeded---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
          btnBack.setDisable(false);
        });

    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
          btnBack.setDisable(false);
        });

    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }

  @FXML
  private void exit() {
    SoundManager.playSetting();
    System.out.println("Exit");
    System.exit(0);
  }
}
