package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.controllers.RoomController;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.timer.Timer;

public class RoomManager {

  private static Map<Room, TextArea> timers = new HashMap<>();
  private static Map<Room, TextArea> checkLists = new HashMap<>();
  private static Map<Room, TextArea> chats = new HashMap<>();
  private static Map<Room, TextField> playerInputs = new HashMap<>();
  private static Map<Room, Button> sendInputButtons = new HashMap<>();
  private static Map<Room, Button> toggleTaskButtons = new HashMap<>();
  private static Map<Room, Button> toggleChatButtons = new HashMap<>();
  private static Map<Room, ImageView> backpack1 = new HashMap<>();
  private static Map<Room, ImageView> backpack2 = new HashMap<>();
  private static Map<Room, ImageView> thinking = new HashMap<>();

  private static TextArea chat;
  private static TextArea checkList;

  public static void bindRoom(
      Room room,
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
    chats.put(room, chat);
    timers.put(room, timer);
    checkLists.put(room, checkList);
    playerInputs.put(room, input);
    sendInputButtons.put(room, sendInputButton);
    toggleTaskButtons.put(room, toggleTasks);
    toggleChatButtons.put(room, toggleChat);
    backpack1.put(room, chemical1Backpack);
    backpack2.put(room, chemical2Backpack);
    thinking.put(room, thinkingFace);
  }

  public static void changeRoom(Room room) {
    // check if the room is an actual room in the gane
    if (room != Room.MAIN_ROOM && room != Room.STORAGE_ROOM && room != Room.TEACHER_ROOM) {
      return;
    }

    Timer.setRoom(timers.get(room));

    TextArea newChat = chats.get(room);

    if (chat != null) {
      newChat.setText(chat.getText());
    }
    chat = newChat;

    TextArea newCheckList = checkLists.get(room);

    if (checkList != null) {
      newCheckList.setText(checkList.getText());
    }

    checkList = newCheckList;

    // If the user has obtained chemical1, make it visible in the backpack
    if (GameState.isChemical1Found) {
      backpack1.get(room).setOpacity(1);
    }

    // If the user has obtained chemical2, make it visible in the backpack
    if (GameState.isChemical2Found) {
      backpack2.get(room).setOpacity(1);
    }
  }

  public static void beginStoryLine() {
    Thread thread =
        new Thread(
            () -> {
              RoomController.gameMaster.giveContext(GptPromptEngineering.introduceGame());
              chats.get(Room.MAIN_ROOM).setText(RoomController.gameMaster.getResponse());
            });

    thread.start();
  }

  public static void toggleChatVisiblity() {
    boolean openChat = !GameState.isChatOpen;
    String buttonText = openChat ? "Close Chat" : "Open Chat";

    for (Button sendInputButton : sendInputButtons.values()) {
      sendInputButton.setVisible(openChat);
    }

    for (Button toggleChatButton : toggleChatButtons.values()) {
      toggleChatButton.setText(buttonText);
    }

    for (TextField input : playerInputs.values()) {
      input.setVisible(openChat);
    }

    for (TextArea chat : chats.values()) {
      chat.setVisible(openChat);
    }

    for (ImageView thinkingFace : thinking.values()) {
      thinkingFace.setVisible(openChat);
      if (openChat) {
        // check game state of thinking face
        if (GameState.thinkingFaceVisible) {
          thinkingFace.setVisible(true);
        } else {
          thinkingFace.setVisible(false);
        }
      }
    }

    GameState.isChatOpen = !GameState.isChatOpen;
  }

  public static void toggleTaskVisiblity() {
    boolean openTasks = !GameState.areTasksOpen;
    String text = openTasks ? "Close Tasks" : "Open Tasks";

    for (Button toggleTaskButton : toggleTaskButtons.values()) {
      toggleTaskButton.setText(text);
    }

    for (TextArea tasks : checkLists.values()) {
      tasks.setVisible(openTasks);
    }

    GameState.areTasksOpen = !GameState.areTasksOpen;
  }
}
