package nz.ac.auckland.se206;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RoomBinder {

  private static TextArea timer;
  private static TextField chatInput;
  private static Button sendChat, toggleTaskButton, toggleChatButton;
  private static ImageView chemical1, chemical2, thinkingFace;
  private static TextArea chat;
  private static TextArea checkList;

  public static void bindRoom(
      TextArea chat,
      TextArea timer,
      TextArea checkList,
      TextField input,
      Button sendInputButton,
      Button toggleChat,
      Button toggleTasks,
      ImageView chemical1Backpack,
      ImageView chemical2Backpack,
      ImageView thinkingFace) {

    if (RoomBinder.chat != null) {
      RoomBinder.timer.textProperty().bindBidirectional(timer.textProperty());

      RoomBinder.chat.textProperty().bindBidirectional(chat.textProperty());
      RoomBinder.chat.visibleProperty().bindBidirectional(chat.visibleProperty());

      RoomBinder.checkList.textProperty().bindBidirectional(checkList.textProperty());
      RoomBinder.checkList.visibleProperty().bindBidirectional(checkList.visibleProperty());

      RoomBinder.chatInput.textProperty().bindBidirectional(input.textProperty());
      RoomBinder.chatInput.visibleProperty().bindBidirectional(input.visibleProperty());

      RoomBinder.sendChat.visibleProperty().bindBidirectional(sendInputButton.visibleProperty());

      RoomBinder.toggleTaskButton.textProperty().bindBidirectional(toggleTasks.textProperty());
      RoomBinder.toggleChatButton.textProperty().bindBidirectional(toggleChat.textProperty());

      RoomBinder.chemical1.imageProperty().bindBidirectional(chemical1Backpack.imageProperty());
      RoomBinder.chemical2.imageProperty().bindBidirectional(chemical2Backpack.imageProperty());
      RoomBinder.thinkingFace.imageProperty().bindBidirectional(thinkingFace.imageProperty());
    }
    RoomBinder.chat = chat;
    RoomBinder.timer = timer;
    RoomBinder.checkList = checkList;
    RoomBinder.chatInput = input;
    RoomBinder.sendChat = sendInputButton;
    RoomBinder.toggleTaskButton = toggleTasks;
    RoomBinder.toggleChatButton = toggleChat;
    RoomBinder.chemical1 = chemical1Backpack;
    RoomBinder.chemical2 = chemical2Backpack;
    RoomBinder.thinkingFace = thinkingFace;
  }
}
