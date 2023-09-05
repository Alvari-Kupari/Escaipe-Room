package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/** This is the entry point of the SceneManager, where scenes are added, changed and handled. */
public class SceneManager {

  public enum AppUi {
    START,
    MAIN_ROOM,
    STORAGE_ROOM,
    TEACHER_ROOM,
    CHAT,
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  /**
   * Creates a new scene and adds it to the scene map.
   *
   * @param appUi
   * @param uiRoot
   */
  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  /**
   * Creates a new Parent based of UiRoot.
   *
   * @param appUi
   * @return
   */
  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }
}
