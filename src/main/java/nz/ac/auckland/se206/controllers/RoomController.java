package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.gpt.GameMaster;

public class RoomController {
  public static GameMaster gameMaster;

  @FXML protected TextArea timer;
  @FXML protected TextArea chat;
  @FXML protected TextArea tasks;
  @FXML protected TextField playerInput;
  @FXML protected Text hintsNumber;
  @FXML protected Button toggleTasks;
  @FXML protected Button toggleChat;
  @FXML protected Button sendChat;
  @FXML protected ImageView chemical1Backpack;
  @FXML protected ImageView chemical2Backpack;
  @FXML protected ImageView thinkingFace;
  @FXML protected ImageView infinity;

  @FXML
  private void initialize() {

    System.out.println();
    System.out.println("************** Initialising RoomController **************");
  }

  @FXML
  protected void onSendText() {
    String input = playerInput.getText();

    gameMaster.recieveplayerMessage(input);
    gameMaster.respond();
  }

  @FXML
  protected void onToggleChat() {
    boolean openChat = !GameState.isChatOpen;
    String buttonText = openChat ? "Close Chat" : "Open Chat";

    sendChat.setVisible(openChat);
    toggleChat.setText(buttonText);
    playerInput.setVisible(openChat);
    chat.setVisible(openChat);

    GameState.isChatOpen = !GameState.isChatOpen;
  }

  @FXML
  protected void onToggleTasks() {
    boolean openTasks = !GameState.areTasksOpen;
    String text = openTasks ? "Close Tasks" : "Open Tasks";

    toggleTasks.setText(text);
    tasks.setVisible(openTasks);

    GameState.areTasksOpen = !GameState.areTasksOpen;
  }

  protected void bind() {
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
  }
}
