package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

public class TeacherRoomController extends RoomController {

  @FXML private Rectangle mainDoor;
  @FXML private Polygon laptop;
  @FXML private TextField userAnswer;

  @FXML private ImageView settingsIcon;
  @FXML private Button btnBack;
  @FXML private Button btnMainMenu;
  @FXML private Button btnExit;

  private boolean hasLaptopBeenOpened;

  /** Initializes the Teacher Room view */
  public void initialize() {
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
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");

    if (event.getCode().equals(KeyCode.ESCAPE)) {
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

  @FXML
  private void onHoverLaptop() {
    laptop.setOpacity(0.5);

    laptop.setOnMouseExited(
        e -> {
          laptop.setOpacity(0);
        });
  }

  /**
   * Handles the mouse hover on the laptop.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the Main Room
   */
  @FXML
  public void hoverLaptop(MouseEvent event) throws IOException {
    // make the laptop area obaque
    laptop.setOpacity(0.5);
    // when not hovered, make the laptop area transparent again
    laptop.setOnMouseExited(
        e -> {
          laptop.setOpacity(0);
        });
  }

  @FXML
  public void clickSettings(MouseEvent event) throws IOException {
    System.out.println("Settings Icon clicked");
    if (paneSettings.isVisible()) {
      paneSettings.setVisible(false);
    } else {
      paneSettings.setVisible(true);
    }
  }

  @FXML
  public void turnSpeechOff(MouseEvent event) throws IOException {
    System.out.println("Turning speech off");
    GameState.isTextToSpeechOn = false;
    speechOn.setVisible(false);
    speechOff.setVisible(true);
  }

  @FXML
  public void turnSpeechOn(MouseEvent event) throws IOException {
    System.out.println("Turning speech on");
    GameState.isTextToSpeechOn = true;
    speechOn.setVisible(true);
    speechOff.setVisible(false);
  }

  @FXML
  public void goBack() {
    paneSettings.setVisible(false);
  }

  // This method is called when the user clicks on the Main Menu button
  @FXML
  private void goMainMenu() throws IOException {
    System.out.println("Go to Main Menu");
    // Set the loading image to visible
    loading.setVisible(true);
    // Disable the buttons for exiting
    btnExit.setDisable(true);
    // Disable the buttons for going to the main menu
    btnMainMenu.setDisable(true);
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
        });

    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
        });

    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }

  @FXML
  private void exit() {
    System.out.println("Exit");
    System.exit(0);
  }
}
