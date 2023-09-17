package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;

public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private Text unMaskedPassword;
  @FXML private Button goBackButton;

  private boolean isPasswordHidden;

  @FXML
  private void initialize() {
    // bind common room elements
    RoomManager.bindRoom(
        Room.LAPTOP,
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
    // set password to be hidden
    isPasswordHidden = true;

    passwordField.setOnKeyPressed(
        (e) -> {
          if (e.getCode().equals(KeyCode.ENTER)) {}
        });

    unMaskedPassword.textProperty().bind(passwordField.textProperty());
  }

  // @FXML
  // private void onPressKey(KeyEvent e) {
  //   // check if enter pressed
  //   if (e.getCode().equals(KeyCode.ENTER)) {
  //     System.out.println("enter pressed");

  //     // now check if password was right
  //     if (passwordField.getText().equals("fish")) {
  //       System.out.println("correct");
  //     }
  //   }
  // }

  @FXML
  private void onKeyPressed(KeyEvent e) {
    // check if enter pressed
    if (e.getCode().toString().equals(KeyCode.ENTER)) {
      System.out.println("enter pressed");

      // now check if password was right
      if (passwordField.getText().equals("fishy")) {
        System.out.println("correct");
      }
    }
  }

  @FXML
  private void onMaskPassword() {
    // if (isPasswordHidden) {
    //   unMaskedPassword.setText(passwordField.getText());
    // }
    // else {
    //   passwordField.setText(unMaskedPassword.getText());
    // }
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
  }
}
