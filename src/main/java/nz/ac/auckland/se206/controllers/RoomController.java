package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GameMaster;

public class RoomController extends SettingsController {
  public static GameMaster gameMaster;

  @FXML protected Text timer;
  @FXML protected Text chat;
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
  @FXML protected ImageView chalkBoard;
  @FXML protected Pane paneSettings;
  @FXML protected ImageView speechOn;
  @FXML protected ImageView speechOff;
  @FXML protected Button btnBack;
  @FXML protected ImageView settingsIcon;

  /**
   * Handles the chat being toggled. This will toggle the visibility of chat and all its components.
   */
  @FXML
  private void onToggleChat() {
    // Get the opposite of the current toggle state of the chat
    boolean openChat = !GameState.isChatOpen;
    // Set the button text to the opposite of what it was
    String buttonText = openChat ? "Close Chat" : "Open Chat";
    toggleChat.setText(buttonText);
    // Set the player input and chat visibility to the opposite of what it was
    playerInput.setVisible(openChat);
    chat.setVisible(openChat);

    // make chalkboard not visible
    chalkBoard.setVisible(openChat);

    // Set the game state of the chat toggle to the opposite of what it was
    GameState.isChatOpen = !GameState.isChatOpen;
  }

  /** This binds all common room elements together. */
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
        keyBackpack,
        chalkBoard,
        paneSettings,
        speechOn,
        speechOff,
        loading);
  }

  /** Allows user to press the enter key to send a chat message. */
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

  /** Handles the user going back to the room. */
  @FXML
  private void onGoBackToRoom() {
    if (GameState.isAudioOn) {
      SoundManager.playSetting();
    }
    paneSettings.setVisible(false);
  }

  /**
   * Handles the settings menu being clicked. Loads the settings menu.
   *
   * @param event the mouse event.
   * @throws IOException If the room not loaded properly.
   */
  @FXML
  private void onClickSettings(MouseEvent event) throws IOException {

    // play the setting sound if audio is on
    if (GameState.isAudioOn) {
      SoundManager.playSetting();
    }
    System.out.println("Settings Icon clicked");

    // toggle visibility
    paneSettings.setVisible(!paneSettings.isVisible());
  }

  /**
   * Turns the speech off, when the speech off button is clicked.
   *
   * @param event the mouse event.
   * @throws IOException if the room is not loaded properly.
   */
  @FXML
  private void onTurnSpeechOff(MouseEvent event) throws IOException {
    System.out.println("Turning speech off");
    GameState.isAudioOn = false;
    speechOn.setVisible(false);
    speechOff.setVisible(true);
  }

  /**
   * Turns the speech on, when the speech on button is clicked.
   *
   * @param event the mouse event.
   * @throws IOException If the room isn't loaded properly.
   */
  @FXML
  private void onTurnSpeechOn(MouseEvent event) throws IOException {
    System.out.println("Turning speech on");
    GameState.isAudioOn = true;
    speechOn.setVisible(true);
    speechOff.setVisible(false);
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");

    // check if enter key pressed
    if (event.getCode().equals(KeyCode.ESCAPE)) {

      // play setting sound
      SoundManager.playSetting();
      System.out.println("Escape pressed");

      // toggle the visibility
      paneSettings.setVisible(!paneSettings.isVisible());
    }
  }
}
