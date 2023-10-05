package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;

public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private TextField unMaskedPassword;
  @FXML private Button goBackButton;
  @FXML private ImageView quizAnswers;
  @FXML private TextArea riddle;

  @FXML private ImageView settingsIcon;
  @FXML private Button btnBack;
  @FXML private Button btnMainMenu;
  @FXML private Button btnExit;

  private boolean isPasswordHidden;

  @FXML
  private void initialize() {
    // bind common room elements
    bind();

    // set chat prompt text
    playerInput.setPromptText("Chat here ... ");

    // make pressing enter send chat
    setEnterToSendChat();

    // set riddle text to be accessible
    RoomBinder.riddleText = riddle;

    // set password to be hidden
    isPasswordHidden = true;

    // bind the masked password to the unmasked one
    unMaskedPassword.textProperty().bindBidirectional(passwordField.textProperty());

    // make a new handler to pick up the enter key being pressed
    EventHandler<KeyEvent> handler =
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent e) {
            if (e.getCode().equals(KeyCode.ENTER)) {
              System.out.println("Enter pressed from password");
              // get the password
              String guess = passwordField.getText();

              System.out.println(guess);

              // check if it is correct
              if (guess.toUpperCase().equals(GameState.password.toString())) {
                System.out.println("password is right");
                quizAnswers.setVisible(true);

                SoundManager.playCorrect();

                gameMaster.giveContext(GptPromptEngineering.answerQuestionOnWhiteBoard());

                return;
              }
              System.out.println("password is wrong");
              SoundManager.playError();
              passwordField.clear();
            }
          }
        };
    // set both fields to pick up the enter key being pressed
    passwordField.setOnKeyPressed(handler);
    unMaskedPassword.setOnKeyPressed(handler);
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

  @FXML
  private void onMaskPassword() {
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
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
