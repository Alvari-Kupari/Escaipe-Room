package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GameMaster;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.timer.Timer;

/** Controller class for the room view. */
public class MainRoomController extends RoomController {

  private static GameMaster flaskTalkingGameMaster;
  private static GameMaster tasksDoneTalkingGameMaster;

  @FXML private Polygon flask;
  @FXML private Polygon exitDoor;
  @FXML private Polygon teacherDoor;
  @FXML private Polygon storageDoor;
  @FXML private ImageView flask1;
  @FXML private ImageView flask2;
  @FXML private ImageView flask3;
  @FXML private ImageView key;
  @FXML private ImageView pouch;
  @FXML private ImageView openedPouch;
  @FXML private Slider zipper;

  private double horizontalOffset;
  private double verticalOffset;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
    Timer.bindText(timer);

    // set chat prompt text
    playerInput.setPromptText("Chat here ... ");

    // bind common room elements
    bind();

    // make pressing enter send chat
    setEnterToSendChat();

    // intialize chat to be open
    GameState.isChatOpen = true;

    // initialize game master
    gameMaster = new GameMaster(0.5, 0.5);

    flaskTalkingGameMaster = new GameMaster(0.5, 0.5);
    tasksDoneTalkingGameMaster = new GameMaster(0.5, 0.5);

    flaskTalkingGameMaster.giveContext(GptPromptEngineering.introduceFlask());
    flaskTalkingGameMaster.gettalkFlask();
    System.out.println("msgFlask" + GameState.msgFlask);

    tasksDoneTalkingGameMaster.giveContext(GptPromptEngineering.tasksComplete());
    tasksDoneTalkingGameMaster.gettalkComplete();

    System.out.println();
    System.out.println("************** Initialising MainRoomController **************");
  }

  // Allow chemical1Backpack to be draggable with mouse
  @FXML
  public void dragChemical1Backpack(MouseEvent event) {
    // check if chemical1 is found
    if (GameState.isChemical1Found) {
      System.out.println("Dragging chemical 1 backpack");
      // make the chemical1Backpack image opaque
      chemical1Backpack.setOpacity(0.5);
      // get the x and y offset of the mouse
      horizontalOffset = event.getSceneX();
      verticalOffset = event.getSceneY();
      // make the chemical1Backpack image draggable
      chemical1Backpack.setOnMouseDragged(
          e -> {
            // set the chemical1Backpack image location to the mouse location
            chemical1Backpack.setX(e.getSceneX() - horizontalOffset);
            chemical1Backpack.setY(e.getSceneY() - verticalOffset);
            // mkae sure that the chemical1Backpack image does not go out of the screen ALL THE TIME
            // if dragged outside bounds, set the chemical1Backpack image location back to within
            // bounds
            if (chemical1Backpack.getX() < 10) {
              chemical1Backpack.setX(50);
            } else if (chemical1Backpack.getX() > 850) {
              chemical1Backpack.setX(850);
            }
            if (chemical1Backpack.getY() < 10) {
              chemical1Backpack.setY(40);
            } else if (chemical1Backpack.getY() > 450) {
              chemical1Backpack.setY(450);
            }
            // if the chemical1Backpack overlaps with the flask, hide the chemical1Backpack
            if (chemical1Backpack.getBoundsInParent().intersects(flask.getBoundsInParent())) {
              // hide the chemical1Backpack
              chemical1Backpack.setVisible(false);
              // make the chemical1Backpack image not interactive
              chemical1Backpack.setDisable(true);
              hideFlasks();
              // check if chemical2 is added
              if (GameState.isChemical2Added) {
                // set the flask image
                flask3.setVisible(true);
              } else {
                // set the flask image
                flask2.setVisible(true);
              }
              // make flask hover image opaque
              flask.setOpacity(0.5);
              SoundManager.playSplash();
              GameState.isChemical1Added = true;
              if (GameState.isChemical1Added && GameState.isChemical2Added) {
                GameState.isTask3Completed = true;
                GameState.isChecklist3Active = false;
                GameState.isChecklist4Active = true;

                // tick of the task in the checklist
                checklist3.setVisible(false);
                checklist4.setVisible(true);
              }
            }
          });
      // make the chemical1Backpack back to not opaque when not dragged
      chemical1Backpack.setOnMouseReleased(
          e -> {
            chemical1Backpack.setOpacity(1);

            // if the task3 is done, make the AI aware of it
            if (GameState.isTask3Completed) {
              // set all game state variables to reflect task 3 completion
              System.out.println("TASK 3 COMPLETED");
              // make AI aware that task 3 is done
              gameMaster.giveContext(GptPromptEngineering.introduceFourthTask());
            }
          });
    } else {
      return;
    }
  }

  // hover over the chemical1Backpack image
  @FXML
  public void hoverChemical1Backpack(MouseEvent event) {
    // check if chemical1 is found
    if (GameState.isChemical1Found) {
      // make the chemical1Backpack area obaque
      chemical1Backpack.setOpacity(0.5);
      // when not hovered, make the chemical1Backpack area transparent again
      chemical1Backpack.setOnMouseExited(
          e -> {
            chemical1Backpack.setOpacity(1);
          });
    } else {
      return;
    }
  }

  // Allow chemical2Backpack to be draggable with mouse
  @FXML
  public void dragChemical2Backpack(MouseEvent event) {
    // check if chemical2 is found
    if (GameState.isChemical2Found) {
      System.out.println("Dragging chemical 2 backpack");
      // make the chemical2Backpack image opaque
      chemical2Backpack.setOpacity(0.5);
      // get the x and y offset of the mouse
      horizontalOffset = event.getSceneX();
      verticalOffset = event.getSceneY();
      // make the chemical2Backpack image draggable
      chemical2Backpack.setOnMouseDragged(
          e -> {
            // set the chemical2Backpack image location to the mouse location
            chemical2Backpack.setX(e.getSceneX() - horizontalOffset);
            chemical2Backpack.setY(e.getSceneY() - verticalOffset);
            // make sure that the chemical2Backpack image does not go out of the screen
            // if dragged outside bounds, set the chemical2Backpack image location back to within
            // bounds
            if (chemical2Backpack.getX() < 10) {
              chemical2Backpack.setX(50);
            } else if (chemical2Backpack.getX() > 850) {
              chemical2Backpack.setX(850);
            }
            if (chemical2Backpack.getY() < 10) {
              chemical2Backpack.setY(40);
            } else if (chemical2Backpack.getY() > 450) {
              chemical2Backpack.setY(450);
            }
            // if the chemical2Backpack overlaps with the flask, hide the chemical2Backpack
            if (chemical2Backpack.getBoundsInParent().intersects(flask.getBoundsInParent())) {
              // hide the chemical2Backpack
              chemical2Backpack.setVisible(false);
              // make the chemical2Backpack image not interactive
              chemical2Backpack.setDisable(true);
              hideFlasks();
              // check if chemical2 is added
              if (GameState.isChemical1Added) {
                // set the flask image
                flask3.setVisible(true);
              } else {
                // set the flask image
                flask2.setVisible(true);
              }
              // make flask image opaque
              flask.setOpacity(0.5);
              SoundManager.playBubbles();
              GameState.isChemical2Added = true;
              if (GameState.isChemical1Added && GameState.isChemical2Added) {
                GameState.isTask3Completed = true;
                GameState.isChecklist3Active = false;
                GameState.isChecklist4Active = true;
                checklist3.setVisible(false);
                checklist4.setVisible(true);
              }
            }
          });
      // make the chemical2Backpack back to not opaque when not dragged
      chemical2Backpack.setOnMouseReleased(
          e -> {
            chemical2Backpack.setOpacity(1);

            // if the task3 is done, make the AI aware of it
            if (GameState.isTask3Completed) {
              System.out.println("TASK 3 COMPLETED");
              // make AI aware that task 3 is done
              gameMaster.giveContext(GptPromptEngineering.introduceFourthTask());
            }
          });
    } else {
      return;
    }
  }

  // hover over the chemical2Backpack image
  @FXML
  public void hoverChemical2Backpack(MouseEvent event) {
    // check if chemical2 is found
    if (GameState.isChemical2Found) {
      // make the chemical2Backpack area opaque
      chemical2Backpack.setOpacity(0.5);
      // when not hovered, make the chemical2Backpack area transparent again
      chemical2Backpack.setOnMouseExited(
          e -> {
            chemical2Backpack.setOpacity(1);
          });
    } else {
      return;
    }
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

  // when slider is compleded, hide the slider
  @FXML
  public void zipperDragReleased(MouseEvent event) {
    if (zipper.getValue() == zipper.getMax()) {
      // Mouse drag released on the slider at the maximum value
      System.out.println("Mouse drag released on the slider at maximum value.");
      // Hide the zipper
      zipper.setVisible(false);
      // Disable the zipper
      zipper.setDisable(true);
      // Hide the pouch
      pouch.setVisible(false);
      // Show the opened pouch
      openedPouch.setVisible(true);
      // Show the key
      key.setVisible(true);
    }
  }

  // when key is clicked, hide the key and add it to the backpack
  @FXML
  public void clickKey(MouseEvent event) {
    System.out.println("Key clicked");
    // Hide the key
    key.setVisible(false);
    // Make the key unclickable
    key.setDisable(true);
    // Make key appear in backpack
    keyBackpack.setVisible(true);
    // Update the game state
    GameState.isKeyObtained = true;

    if (GameState.isTask1Completed) {
      // make AI aware of the new change
      gameMaster.giveContext(GptPromptEngineering.playerCollectedKey());
      gameMaster.respond();
    } else {
      // make sure the AI knows that task 1 is still to be complete
      gameMaster.giveContext(GptPromptEngineering.playerCollectedKeyBeforeCompletingTask1());
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
    if (!GameState.isTask1Completed) {
      // make AI aware of the task completion
      gameMaster.giveContext(GptPromptEngineering.introduceSecondTask());

      // set all necessary game states to reflect task 2 completion
      GameState.isTask1Completed = true;
      GameState.isChecklist1Active = false;
      GameState.isChecklist2Active = true;

      // make the checklist update to the task completion
      checklist1.setVisible(false);
      checklist2.setVisible(true);
    }
    if (GameState.isTask3Completed) {
      return;
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
    System.out.println("Exit Door clicked");

    if ((!GameState.isTask1Completed)
        || (!GameState.isTask2Completed)
        || (!GameState.isTask3Completed)
        || (!GameState.isTask4Completed)) {
      SoundManager.playError();
      // Code for professor to say you can't leave until you finish all tasks
      return;
    }

    SoundManager.playRoundWon();

    // caculate what grade to show
    calculateGrade();

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

  /**
   * Handles the mouse hover over the flask.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void hoverFlask(MouseEvent event) throws IOException {
    // make the flask area obaque
    flask.setOpacity(0.5);
    // when not hovered, make the flask area transparent again
    flask.setOnMouseExited(
        e -> {
          flask.setOpacity(0);
        });
  }

  private void calculateGrade() {
    // set the task completion time
    int time = Timer.getTimeInSeconds();
    GameState.taskCompletionTime = time;

    // figure out which grade to give
    String grade = "A+";
    if (time < 60) {
      grade = "B";
    }

    if (time < 30) {
      grade = "C";
    }

    if (time < 15) {
      grade = "D";
    }

    RoomBinder.grade.setText(grade);
  }
}
