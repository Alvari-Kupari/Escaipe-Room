package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {
  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
  }

  /** Indicates the difficulty for the round. */
  public static Difficulty levelDifficulty = null;

  /** Indicates whether the difficulty has been selected. */
  public static boolean isDifficultySelected = false;

  /** Indicates whether the hints are infinite. */
  public static boolean isHintsInfinite = false;

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

  /** Indicates whether task4 is completed. */
  public static boolean isTask4Completed = false;

  public static boolean isChecklist1Active = true;
  public static boolean isChecklist2Active = false;
  public static boolean isChecklist3Active = false;
  public static boolean isChecklist4Active = false;
  public static boolean isChecklist5Active = false;
  public static boolean isChatOpen = false;
  public static boolean areTasksOpen = false;
  public static boolean isKeyObtained = false;

  /** Indicates the number of hints remaining. */
  public static int hintsRemaining = -1;

  public static int taskCompletionTime = -1;

  /** Indicates how many hints are used */
  public static int hintsUsed = 0;

  public static String msgFlask = null;
  public static String msgComplete = null;
  public static String msgLockedRack = "Complete the first task before opening the rack.";
  public static String msgTime30 = "Hurry Up, 30 seconds left!";
  public static String msgNeedKey = "You need a key to open the shelf.";
  public static String msgUseKey = "You need to use the key.";
  public static String msgUseKeyAndTask1 = "You need to use the key and complete the first task.";

  public static boolean doorOpened = false;
  // the laptop password
  public static Password password;

  public static String quizAnswer;

  // This sets all the variables to their default values
  public static void setDefaults() {
    // The level difficulty
    levelDifficulty = null;
    // Indicates difficulty has not been selected
    isDifficultySelected = false;
    // Indicates hints are not infinite
    isHintsInfinite = false;
    // Indicates that the time limit has not been selected
    isTimeLimitSelected = false;
    // Indicates that the game has not been started
    isGameStarted = false;
    // Indicates that the riddle has not been resolved
    isRiddleResolved = false;
    // Indicates that chemical1 has not been found
    isChemical1Found = false;
    // Indicates that chemical2 has not been found
    isChemical2Found = false;
    // Indicates that chemical1 has not been added
    isChemical1Added = false;
    // Indicates that chemical2 has not been added
    isChemical2Added = false;
    // Indicates that task1 has not been completed
    isTask1Completed = false;
    // Indicates that task2 has not been completed
    isTask2Completed = false;
    // Indicates that task3 has not been completed
    isTask3Completed = false;
    // Indicates that task4 has not been completed
    isTask4Completed = false;
    // Indicates that checklist1 is active
    isChecklist1Active = true;
    // Indicates that checklist2 is not active
    isChecklist2Active = false;
    // Indicates that checklist3 is not active
    isChecklist3Active = false;
    // Indicates that checklist4 is not active
    isChecklist4Active = false;
    // Indicates that checklist5 is not active
    isChecklist5Active = false;
    // Indicates that the chat is not open
    isChatOpen = false;
    // Indicates that the tasks are not open
    areTasksOpen = false;
    // Indicates that the key has not been obtained
    isKeyObtained = false;
    // The number of hints remaining
    hintsRemaining = -1;
    // The time limit
    taskCompletionTime = -1;
    // The number of hints used
    hintsUsed = 0;
    // Flask message has not been completed
    msgFlask = null;
    // Message has not been completed
    msgComplete = null;
    // Door opened
    doorOpened = false;
    // the laptop password
    password = Password.getRandomPassword();
  }
}
