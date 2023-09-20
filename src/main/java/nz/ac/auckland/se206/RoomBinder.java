package nz.ac.auckland.se206;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RoomBinder {

  private static Text timer;
  public static Text grade;
  private static TextField chatInput;
  private static Button toggleTaskButton, toggleChatButton;
  private static ImageView chemical1, chemical2, infinity;
  public static ImageView thinkingFace,
      professorResting,
      professorThinking,
      professorAngry1,
      professorAngry2,
      professorAngry3,
      professorAngry4,
      checklist1,
      checklist2,
      checklist3,
      checklist4,
      checklist5;
  public static TextArea chat;
  public static Text hintsNumber;

  public static void bindRoom(
      TextArea chat,
      Text timer,
      TextField input,
      Text hintsNumber,
      Button toggleChat,
      Button toggleTasks,
      ImageView chemical1Backpack,
      ImageView chemical2Backpack,
      ImageView infinity,
      ImageView professorResting,
      ImageView professorThinking,
      ImageView professorAngry1,
      ImageView professorAngry2,
      ImageView professorAngry3,
      ImageView professorAngry4,
      ImageView checklist1,
      ImageView checklist2,
      ImageView checklist3,
      ImageView checklist4,
      ImageView checklist5) {

    if (RoomBinder.chat != null) {
      RoomBinder.timer.textProperty().bindBidirectional(timer.textProperty());

      RoomBinder.chat.textProperty().bindBidirectional(chat.textProperty());
      RoomBinder.chat.visibleProperty().bindBidirectional(chat.visibleProperty());

      RoomBinder.hintsNumber.textProperty().bindBidirectional(hintsNumber.textProperty());
      RoomBinder.hintsNumber.visibleProperty().bindBidirectional(hintsNumber.visibleProperty());

      RoomBinder.chatInput.textProperty().bindBidirectional(input.textProperty());
      RoomBinder.chatInput.visibleProperty().bindBidirectional(input.visibleProperty());

      RoomBinder.toggleTaskButton.textProperty().bindBidirectional(toggleTasks.textProperty());
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

      RoomBinder.infinity.visibleProperty().bindBidirectional(infinity.visibleProperty());

      RoomBinder.checklist1.visibleProperty().bindBidirectional(checklist1.visibleProperty());
      RoomBinder.checklist2.visibleProperty().bindBidirectional(checklist2.visibleProperty());
      RoomBinder.checklist3.visibleProperty().bindBidirectional(checklist3.visibleProperty());
      RoomBinder.checklist4.visibleProperty().bindBidirectional(checklist4.visibleProperty());
      RoomBinder.checklist5.visibleProperty().bindBidirectional(checklist5.visibleProperty());
    }
    RoomBinder.chat = chat;
    RoomBinder.timer = timer;
    RoomBinder.chatInput = input;
    RoomBinder.toggleTaskButton = toggleTasks;
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
    RoomBinder.checklist1 = checklist1;
    RoomBinder.checklist2 = checklist2;
    RoomBinder.checklist3 = checklist3;
    RoomBinder.checklist4 = checklist4;
    RoomBinder.checklist5 = checklist5;
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
