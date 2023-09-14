package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.RoomManager;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GameMaster;

public class RoomController {
  public static GameMaster gameMaster;

  @FXML protected TextArea timer;
  @FXML protected TextArea chat;
  @FXML protected TextArea tasks;
  @FXML protected TextField playerInput;
  @FXML protected Button toggleTasks;
  @FXML protected Button toggleChat;
  @FXML protected Button sendChat;

  @FXML
  protected void onSendText() {
    String input = playerInput.getText();

    Thread thread =
        new Thread(
            () -> {
              chat.setText(gameMaster.getResponse(input));
            });
    thread.start();
    playerInput.clear();
  }

  @FXML
  protected void onToggleChat() {
    RoomManager.toggleChatVisiblity();
  }

  @FXML
  protected void onToggleTasks() {
    RoomManager.toggleTaskVisiblity();
  }

  protected void appendMessage(ChatMessage msg) {
    chat.appendText(msg.getRole() + ": " + msg.getContent());
  }
}
