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
  @FXML protected ImageView keyBackpack;

  @FXML
  private void initialize() {

    System.out.println();
    System.out.println("************** Initialising RoomController **************");
  }

  // This method is called when the user clicks on the Toggle Chat button
  @FXML
  protected void onToggleChat() {
    // Get the opposite of the current toggle state of the chat
    boolean openChat = !GameState.isChatOpen;
    // Set the button text to the opposite of what it was
    String buttonText = openChat ? "Close Chat" : "Open Chat";
    toggleChat.setText(buttonText);
    // Set the player input and chat visibility to the opposite of what it was
    playerInput.setVisible(openChat);
    chat.setVisible(openChat);
    // Set the game state of the chat toggle to the opposite of what it was
    GameState.isChatOpen = !GameState.isChatOpen;
  }

  // This method is used to bind the common room elements that all rooms share
  protected void bind() {
    RoomBinder.bindRoom(
        // Chat area for the user to see the chat with the professor
        chat,
        // The timer for the game
        timer,
        // The input for the user to type in the chat to the professor
        playerInput,
        // The amount of hints remaining
        hintsNumber,
        // The button to toggle the chat to the professor
        toggleChat,
        // Image of the chemicals
        chemical1Backpack,
        chemical2Backpack,
        // Image of the infinity symbol
        infinity,
        // Image of the professor resting
        professorResting,
        // Image of the professor thinking
        professorThinking,
        // Images of the professor getting angrier
        professorAngry1,
        professorAngry2,
        professorAngry3,
        professorAngry4,
        // Image of the professor talking
        professorTalking,
        // Images of the checklists
        checklist1,
        checklist2,
        checklist3,
        checklist4,
        checklist5,
        // Image of the key in the inventory
        keyBackpack);
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
