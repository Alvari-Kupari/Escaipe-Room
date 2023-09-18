package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.gpt.GameMaster;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;

public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private TextField unMaskedPassword;
  @FXML private Button goBackButton;
  @FXML private Button sendPassword;

  private boolean isPasswordHidden;

  private GameMaster laptopGameMaster;

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

    // make a new game master for the laptop mini game
    laptopGameMaster = new GameMaster(0.7, 0.6);

    laptopGameMaster.giveContext(
        GptPromptEngineering.getRiddleWithGivenWord(GameState.password.toString().toLowerCase()));
    chat.setText(laptopGameMaster.getResponse());
    unMaskedPassword.textProperty().bind(passwordField.textProperty());
  }

  @FXML
  private void onMaskPassword() {
    unMaskedPassword.setEditable(isPasswordHidden);
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  @FXML
  private void onSendPassword() {
    if (!passwordField.getText().toUpperCase().equals(GameState.password.toString())) {
      gameMaster.giveContext(GptPromptEngineering.playerGussedRightAnswer());
      chat.setText(gameMaster.getResponse());
      ;

      return;
    }
    System.out.println("password is right");
  }

  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
  }
}
