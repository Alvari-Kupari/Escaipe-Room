package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Used to manage the teacher room. */
public class TeacherRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private Polygon laptop;
  @FXML private TextField userAnswer;

  private boolean hasLaptopBeenOpened;

  /** Initializes the Teacher Room, by loading all the necessary stuff. */
  @FXML
  private void initialize() {
    // Initialization code goes here

    // make pressing enter send chat
    setEnterToSendChat();

    // set chat prompt text
    playerInput.setPromptText("Chat here ... ");

    // bind common room elements
    bind();

    hasLaptopBeenOpened = false;

    // make a new handler to pick up the enter key being pressed
    EventHandler<KeyEvent> handler =
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent e) {
            if (e.getCode().equals(KeyCode.ENTER)) {
              System.out.println("Enter pressed from password");
              String userGuess = userAnswer.getText();

              System.out.println(userGuess);

              if (userGuess.toUpperCase().equals(GameState.quizAnswer.toString().toUpperCase())) {
                System.out.println("quiz answer is right");
                SoundManager.playCorrect();
                GameState.isTask4Completed = true;
                GameState.isChecklist4Active = false;
                GameState.isChecklist5Active = true;
                checklist4.setVisible(false);
                checklist5.setVisible(true);
                TextToSpeech.talk(GameState.msgComplete);

                // make AI aware that all tasks are done
                gameMaster.giveContext(GptPromptEngineering.allTasksComplete());
                return;
              }
              System.out.println("quiz answer is wrong");
              SoundManager.playError();
              userAnswer.clear();
            }
          }
        };
    // set field to pick up the enter key being pressed
    userAnswer.setOnKeyPressed(handler);

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
  private void onClickMainDoor(MouseEvent event) throws IOException {

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
  private void onHoverMainDoor(MouseEvent event) throws IOException {
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
    SoundManager.playClick();
    if (!GameState.isTask3Completed) {
      return;
    }
    if (!hasLaptopBeenOpened) {
      // begin the riddle for the laptop
      gameMaster.giveContext(
          GptPromptEngineering.getRiddleWithGivenWord(GameState.password.toString().toLowerCase()));

      // make the AI respond
      gameMaster.setRiddleText();
    }

    hasLaptopBeenOpened = true;
    App.changeScene(Room.LAPTOP);
  }

  /**
   * Handles the mouse hover on the laptop.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  private void onHoverLaptop(MouseEvent event) throws IOException {
    // make the laptop area obaque
    laptop.setOpacity(0.5);
    // when not hovered, make the laptop area transparent again
    laptop.setOnMouseExited(
        e -> {
          laptop.setOpacity(0);
        });
  }
}
