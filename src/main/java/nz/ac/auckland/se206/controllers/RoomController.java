package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GameMaster;

public class RoomController {
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
  @FXML protected ImageView loading;
  @FXML protected Button btnExit;
  @FXML protected Button btnMainMenu;
  @FXML protected Button btnBack;
  @FXML private ImageView settingsIcon;

  // This method is called when the user clicks on the Toggle Chat button
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
        keyBackpack,
        chalkBoard,
        paneSettings,
        speechOn,
        speechOff,
        loading);
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

  @FXML
  private void onExit() {
    SoundManager.playSetting();
    System.out.println("Exit");
    System.exit(0);
  }

  @FXML
  private void onGoBackToRoom() {
    SoundManager.playSetting();
    paneSettings.setVisible(false);
  }

  // This method is called when the user clicks on the Main Menu button
  @FXML
  private void onGoMainMenu() throws IOException {
    SoundManager.playSetting();
    System.out.println("Go to Main Menu");
    // Set the loading image to visible
    loading.setVisible(true);
    // Disable the buttons for exiting
    btnExit.setDisable(true);
    // Disable the buttons for going to the main menu
    btnMainMenu.setDisable(true);
    btnBack.setDisable(true);
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
          btnBack.setDisable(false);
        });

    restartTask.setOnFailed(
        e -> {
          System.out.println("---------------------Failed---------------------");
          btnExit.setDisable(false);
          btnMainMenu.setDisable(false);
          btnBack.setDisable(false);
        });

    Thread restartThread = new Thread(restartTask);
    restartThread.start();
  }

  @FXML
  private void onClickSettings(MouseEvent event) throws IOException {
    SoundManager.playSetting();
    System.out.println("Settings Icon clicked");
    if (paneSettings.isVisible()) {
      paneSettings.setVisible(false);
    } else {
      paneSettings.setVisible(true);
    }
  }

  @FXML
  private void onSpeechOff(MouseEvent event) throws IOException {
    SoundManager.playSetting();
    System.out.println("Turning speech off");
    GameState.isAudioOn = false;
    speechOn.setVisible(false);
    speechOff.setVisible(true);
  }

  @FXML
  private void onSpeechOn(MouseEvent event) throws IOException {
    SoundManager.playSetting();
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

    if (event.getCode().equals(KeyCode.ESCAPE)) {
      SoundManager.playSetting();
      System.out.println("Escape pressed");
      if (paneSettings.isVisible()) {
        paneSettings.setVisible(false);
      } else {
        paneSettings.setVisible(true);
      }
    }
  }
}
