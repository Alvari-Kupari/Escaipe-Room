package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.gpt.GameMaster;

public class RoomController {
  public static GameMaster gameMaster;

  @FXML protected Text timer;
  @FXML protected TextArea chat;
  @FXML protected TextField playerInput;
  @FXML protected Text hintsNumber;
  @FXML protected Button toggleTasks;
  @FXML protected Button toggleChat;
  @FXML protected ImageView chemical1Backpack;
  @FXML protected ImageView chemical2Backpack;
  @FXML protected ImageView infinity;
  @FXML protected ImageView professorResting;
  @FXML protected ImageView professorThinking;
  @FXML protected ImageView professorAngry1;
  @FXML protected ImageView professorAngry2;
  @FXML protected ImageView professorAngry3;
  @FXML protected ImageView professorAngry4;
  @FXML protected ImageView professorTalking;
  @FXML protected ImageView checklist1;
  @FXML protected ImageView checklist2;
  @FXML protected ImageView checklist3;
  @FXML protected ImageView checklist4;
  @FXML protected ImageView checklist5;

  @FXML
  private void initialize() {

    System.out.println();
    System.out.println("************** Initialising RoomController **************");
  }

  @FXML
  protected void onToggleChat() {
    boolean openChat = !GameState.isChatOpen;
    String buttonText = openChat ? "Close Chat" : "Open Chat";

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

    if (GameState.isChecklist1Active) {
      checklist1.setVisible(openTasks);
    } else if (GameState.isChecklist2Active) {
      checklist2.setVisible(openTasks);
    } else if (GameState.isChecklist3Active) {
      checklist3.setVisible(openTasks);
    } else if (GameState.isChecklist4Active) {
      checklist4.setVisible(openTasks);
    } else if (GameState.isChecklist5Active) {
      checklist5.setVisible(openTasks);
    }
    GameState.areTasksOpen = !GameState.areTasksOpen;
  }

  protected void bind() {
    // bind common room elements
    RoomBinder.bindRoom(
        chat,
        timer,
        playerInput,
        hintsNumber,
        toggleChat,
        toggleTasks,
        chemical1Backpack,
        chemical2Backpack,
        infinity,
        professorResting,
        professorThinking,
        professorAngry1,
        professorAngry2,
        professorAngry3,
        professorAngry4,
        professorTalking,
        checklist1,
        checklist2,
        checklist3,
        checklist4,
        checklist5);
  }

  protected void setEnterToSendChat() {
    playerInput.setPromptText("Chat here...");
    playerInput.setOnKeyPressed(
        (e) -> {
          if (e.getCode().equals(KeyCode.ENTER)) {
            System.out.println("Enter pressed from chat");

            // get the players message
            String input = playerInput.getText();

            // clear the input text
            playerInput.clear();

            // make the AI respond
            gameMaster.recieveplayerMessage(input);
            gameMaster.respond();
          }
        });
  }
}
