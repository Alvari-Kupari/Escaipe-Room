package nz.ac.auckland.se206.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;

public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private TextField unMaskedPassword;
  @FXML private Button goBackButton;
  @FXML private ImageView quizAnswers;

  private boolean isPasswordHidden;

  @FXML
  private void initialize() {
    // bind common room elements
    bind();

    // make pressing enter send chat
    setEnterToSendChat();

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
              String guess = passwordField.getText();

              System.out.println(guess);

              if (guess.toUpperCase().equals(GameState.password.toString())) {
                System.out.println("password is right");

                SoundManager.playCorrect();
                quizAnswers.setVisible(true);
                GameState.isTask4Completed = true;
                GameState.isChecklist4Active = false;
                GameState.isChecklist5Active = true;
                checklist4.setVisible(false);
                checklist5.setVisible(true);

                return;
              }
              System.out.println("password is wrong");
              SoundManager.playError();
            }
          }
        };
    // set both fields to pick up the enter key being pressed
    passwordField.setOnKeyPressed(handler);
    unMaskedPassword.setOnKeyPressed(handler);
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
}
