package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;

public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private TextField unMaskedPassword;
  @FXML private Button goBackButton;
  @FXML private Button sendPassword;
  @FXML private TextArea quizAnswers;

  private boolean isPasswordHidden;

  @FXML
  private void initialize() {
    // bind common room elements
    RoomBinder.bindRoom(
        chat,
        timer,
        tasks,
        playerInput,
        hintsNumber,
        sendChat,
        toggleChat,
        toggleTasks,
        chemical1Backpack,
        chemical2Backpack,
        thinkingFace,
        infinity);
    // set password to be hidden
    isPasswordHidden = true;

    unMaskedPassword.textProperty().bind(passwordField.textProperty());
  }

  @FXML
  private void onMaskPassword() {
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  @FXML
  private void onSendPassword() {

    if (passwordField.getText().toUpperCase().equals(GameState.password.toString())) {
      System.out.println("password is right");
      quizAnswers.setVisible(true);

      return;
    }
    System.out.println("password is wrong");
  }

  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
  }
}
