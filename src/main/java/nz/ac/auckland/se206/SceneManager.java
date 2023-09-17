package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/** This is the entry point of the SceneManager, where scenes are added, changed and handled. */
public class SceneManager {

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
   * Creates a new scene and adds it to the scene map.
   *
   * @param Room
   * @param uiRoot
   */
  public static void addUi(Room Room, Parent uiRoot) {
    sceneMap.put(Room, uiRoot);
  }

  /**
   * Creates a new Parent based of UiRoot.
   *
   * @param Room
   * @return
   */
  public static Parent getUiRoot(Room Room) {
    return sceneMap.get(Room);
  }
}
