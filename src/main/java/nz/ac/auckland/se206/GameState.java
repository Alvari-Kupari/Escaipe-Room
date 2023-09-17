package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the difficulty has been selected. */
  public static boolean isDifficultySelected = false;

  /** Indicates the difficulty for the round. */
  public static Difficulty levelDifficulty = null;

  /** Indicates whether the time limit has been selected. */
  public static boolean isTimeLimitSelected = false;

  /** Indicates whether the game has been started. */
  public static boolean isGameStarted = false;

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** Indicates whether chemical1 has been found. */
  public static boolean isChemical1Found = false;

  /** Indicates whether chemical2 has been found. */
  public static boolean isChemical2Found = false;

  public static boolean isChemical1Added = false;

  public static boolean isChemical2Added = false;

  public static int taskCompletionTime;

  // are chats and tasks open?
  public static boolean isChatOpen;
  public static boolean areTasksOpen;

  /** Indicates visibility of thinking face */
  public static boolean thinkingFaceVisible = false;

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }
}
