package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the difficulty has been selected. */
  public static boolean isDifficultySelected = false;

  /** Indicates the difficulty for the round. */
  public static Difficulty levelDifficulty = null;

  /** Indicates whether the hints are infinite. */
  public static boolean isHintsInfinite = false;

  /** Indicates the number of hints remaining. */
  public static int hintsRemaining = -1;

  /** Indicates whether the time limit has been selected. */
  public static boolean isTimeLimitSelected = false;

  /** Indicates whether the game has been started. */
  public static boolean isGameStarted = false;

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether chemical1 has been found. */
  public static boolean isChemical1Found = false;

  /** Indicates whether chemical2 has been found. */
  public static boolean isChemical2Found = false;

  /** Indicates whether chemical1 has been added. */
  public static boolean isChemical1Added = false;

  /** Indicates whether chemical2 has been added. */
  public static boolean isChemical2Added = false;

  /** Indicates whether task1 is completed. */
  public static boolean isTask1Completed = false;

  /** Indicates whether task2 is completed. */
  public static boolean isTask2Completed = false;

  /** Indicates whether task3 is completed. */
  public static boolean isTask3Completed = false;

  public static int taskCompletionTime = -1;

  /** Indicates how many hints are used */
  public static int hintsUsed = 1;

  // are chats and tasks open?
  public static boolean isChatOpen;
  public static boolean areTasksOpen;

  public static Password password = Password.BANANA;

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  public static void setDefaults() {
    isDifficultySelected = false;
    levelDifficulty = null;
    isHintsInfinite = false;
    hintsRemaining = -1;
    isTimeLimitSelected = false;
    isGameStarted = false;
    isRiddleResolved = false;
    isChemical1Found = false;
    isChemical2Found = false;
    isChemical1Added = false;
    isChemical2Added = false;
    isTask1Completed = false;
    isTask2Completed = false;
    isTask3Completed = false;
    taskCompletionTime = -1;
    isChatOpen = false;
    areTasksOpen = false;
    password = Password.BANANA;
  }
}
