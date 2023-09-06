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

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }
}
