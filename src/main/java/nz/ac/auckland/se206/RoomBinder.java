package nz.ac.auckland.se206;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RoomBinder {

  private static Text timer;
  public static Text grade;
  public static Text hintsNumber;
  private static TextField chatInput;
  public static TextArea chat, riddleText;
  private static Button toggleChatButton;
  public static ImageView chemical1;
  public static ImageView chemical2;
  public static ImageView infinity;
  public static ImageView keyBackpack;
  public static ImageView thinkingFace;
  public static ImageView professorResting;
  public static ImageView professorThinking;
  public static ImageView professorAngry1;
  public static ImageView professorAngry2;
  public static ImageView professorAngry3;
  public static ImageView professorAngry4;
  public static ImageView professorTalking;
  public static ImageView checklist1;
  public static ImageView checklist2;
  public static ImageView checklist3;
  public static ImageView checklist4;
  public static ImageView checklist5;

  // This method is used to bind the room elements to the room controller
  public static void bindRoom(
      // Chat area for the user to see the chat with the professor
      TextArea chat,
      // The timer for the game
      Text timer,
      // The input for the user to type in the chat to the professor
      TextField input,
      // The amount of hints remaining
      Text hintsNumber,
      // The button to toggle the chat to the professor
      Button toggleChat,
      // Image of the first chemical
      ImageView chemical1Backpack,
      // Image of the second chemical
      ImageView chemical2Backpack,
      // Image of the infinity symbol
      ImageView infinity,
      // Image of the professor resting
      ImageView professorResting,
      // Image of the professor thinking
      ImageView professorThinking,
      // Image of the professor angry
      ImageView professorAngry1,
      // Image of the professor angrier
      ImageView professorAngry2,
      // Image of the professor very angry
      ImageView professorAngry3,
      // Image of the professor extremely angry
      ImageView professorAngry4,
      // Image of the professor talking
      ImageView professorTalking,
      // Image of the first checklist
      ImageView checklist1,
      // Image of the second checklist
      ImageView checklist2,
      // Image of the third checklist
      ImageView checklist3,
      // Image of the fourth checklist
      ImageView checklist4,
      // Image of the fifth checklist
      ImageView checklist5,
      // Image of the key in the inventory
      ImageView keyBackpack) {

    if (RoomBinder.chat != null) {
      RoomBinder.timer.textProperty().bindBidirectional(timer.textProperty());

      RoomBinder.chat.textProperty().bindBidirectional(chat.textProperty());
      RoomBinder.chat.visibleProperty().bindBidirectional(chat.visibleProperty());

      RoomBinder.hintsNumber.textProperty().bindBidirectional(hintsNumber.textProperty());
      RoomBinder.hintsNumber.visibleProperty().bindBidirectional(hintsNumber.visibleProperty());

      RoomBinder.chatInput.textProperty().bindBidirectional(input.textProperty());
      RoomBinder.chatInput.visibleProperty().bindBidirectional(input.visibleProperty());

      RoomBinder.toggleChatButton.textProperty().bindBidirectional(toggleChat.textProperty());

      RoomBinder.chemical1.opacityProperty().bindBidirectional(chemical1Backpack.opacityProperty());
      RoomBinder.chemical2.opacityProperty().bindBidirectional(chemical2Backpack.opacityProperty());

      RoomBinder.chemical1.visibleProperty().bindBidirectional(chemical1Backpack.visibleProperty());
      RoomBinder.chemical2.visibleProperty().bindBidirectional(chemical2Backpack.visibleProperty());

      RoomBinder.professorResting
          .visibleProperty()
          .bindBidirectional(professorResting.visibleProperty());
      RoomBinder.professorThinking
          .visibleProperty()
          .bindBidirectional(professorThinking.visibleProperty());
      RoomBinder.professorAngry1
          .visibleProperty()
          .bindBidirectional(professorAngry1.visibleProperty());
      RoomBinder.professorAngry2
          .visibleProperty()
          .bindBidirectional(professorAngry2.visibleProperty());
      RoomBinder.professorAngry3
          .visibleProperty()
          .bindBidirectional(professorAngry3.visibleProperty());
      RoomBinder.professorAngry4
          .visibleProperty()
          .bindBidirectional(professorAngry4.visibleProperty());
      RoomBinder.professorTalking
          .visibleProperty()
          .bindBidirectional(professorTalking.visibleProperty());

      RoomBinder.infinity.visibleProperty().bindBidirectional(infinity.visibleProperty());

      RoomBinder.checklist1.visibleProperty().bindBidirectional(checklist1.visibleProperty());
      RoomBinder.checklist2.visibleProperty().bindBidirectional(checklist2.visibleProperty());
      RoomBinder.checklist3.visibleProperty().bindBidirectional(checklist3.visibleProperty());
      RoomBinder.checklist4.visibleProperty().bindBidirectional(checklist4.visibleProperty());
      RoomBinder.checklist5.visibleProperty().bindBidirectional(checklist5.visibleProperty());
      RoomBinder.keyBackpack.visibleProperty().bindBidirectional(keyBackpack.visibleProperty());
    }
    RoomBinder.chat = chat;
    RoomBinder.timer = timer;
    RoomBinder.chatInput = input;
    RoomBinder.toggleChatButton = toggleChat;
    RoomBinder.chemical1 = chemical1Backpack;
    RoomBinder.chemical2 = chemical2Backpack;
    RoomBinder.infinity = infinity;
    RoomBinder.hintsNumber = hintsNumber;
    RoomBinder.professorResting = professorResting;
    RoomBinder.professorThinking = professorThinking;
    RoomBinder.professorAngry1 = professorAngry1;
    RoomBinder.professorAngry2 = professorAngry2;
    RoomBinder.professorAngry3 = professorAngry3;
    RoomBinder.professorAngry4 = professorAngry4;
    RoomBinder.professorTalking = professorTalking;
    RoomBinder.checklist1 = checklist1;
    RoomBinder.checklist2 = checklist2;
    RoomBinder.checklist3 = checklist3;
    RoomBinder.checklist4 = checklist4;
    RoomBinder.checklist5 = checklist5;
    RoomBinder.keyBackpack = keyBackpack;
  }

  public static void setHintsInfinite(boolean isInfinite) {
    infinity.setVisible(isInfinite);
  }

  public static void toggleHintsVisibility(boolean isVisible) {
    hintsNumber.setVisible(isVisible);
  }

  public static void setHintsRemaining(int hints) {
    hintsNumber.setText(String.valueOf(hints));
  }
}
