package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/** This is the entry point of the SceneManager, where scenes are added, changed and handled. */
public class SceneManager {

  /** Represents the rooms in the game. */
  public enum Room {
    START,
    MAIN_ROOM,
    STORAGE_ROOM,
    TEACHER_ROOM,
    CHAT,
    GAME_OVER,
    EXIT,
    LAPTOP,
  }

  private static HashMap<Room, Parent> sceneMap = new HashMap<Room, Parent>();

  /**
   * Creates a new scene and adds it to the sceneMap.
   *
   * @param room the room to add
   * @param uiRoot the root of the scene
   * @return void
   */
  public static void addRoom(Room room, Parent uiRoot) {
    sceneMap.put(room, uiRoot);
  }

  public static void removeAllMapping() {
    sceneMap.clear();
  }

  /**
   * Creates a new Parent based of UiRoot.
   *
   * @param room the room to swtich to
   * @return the loaded scene
   */
  public static Parent getRoot(Room room) {
    return sceneMap.get(room);
  }
}
