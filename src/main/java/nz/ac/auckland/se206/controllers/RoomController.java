package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.gpt.GameMaster;
import nz.ac.auckland.se206.timer.Timer;

public class RoomController {

  private static Map<Room, TextArea> timers = new HashMap<>();
  private static Map<Room, TextArea> checkLists = new HashMap<>();
  private static Map<Room, TextArea> chats = new HashMap<>();
  private static Map<Room, GameMaster> gameMasters = new HashMap<>();

  private static TextArea chat;
  private static TextArea checkList;
  public static GameMaster gameMaster;

  public static void bindRoom(
      Room room, TextArea chat, TextArea timer, TextArea checkList, TextField input) {
    chats.put(room, chat);
    timers.put(room, timer);
    checkLists.put(room, checkList);
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
  }
}
