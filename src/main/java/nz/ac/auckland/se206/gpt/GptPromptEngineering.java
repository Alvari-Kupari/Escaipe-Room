package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.timer.Timer;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "Tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer. Begin your response by getting straight into the riddle. Do note the answer"
        + " is "
        + wordToGuess;
  }

  /**
   * Introduces the game background to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceGame() {
    return "You are the professor of a school chemistry lab themed escape room game. The player of"
        + " the game is a student stuck in a school chemistry lab with 3 separate rooms. The"
        + " player must complete 4 different tasks, in order before they can escape. The"
        + " player has a time limit of "
        + Timer.getTime()
        + " to complete all the tasks, or they fail the assignment. Throughout the game, pretend"
        + " you are a professor who is angry at the student for procastinating his lab tasks."
        + " Introduce yourself to the player in under 30 words."
        + getHintPrompt();
  }

  /**
   * Introduces the 1st task to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceFirstTask() {
    return "The player must complete task 1 before they can progress. To complete the task 1, the"
        + " player must inspect the flask at the centre of the main room.";
  }

  /**
   * Introduces the 2nd task to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceSecondTask() {
    return "The player has now completed task 1, and now must complete task 2 before they can"
        + " progress. To complete task 2, the player must open the storage locker, which is"
        + " located in the storage room. In order to open the storage locker, the student"
        + " must collect a key from the main room to open the lock to the locker. Once the"
        + " chemical locker is open, the player must collect the 2 chemicals located"
        + " inside.";
  }

  /**
   * Introduces the 3rd task to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceThirdTask() {
    return "The player has now completed task 2, and must complete task 3 before they can progress."
        + " To complete task 3, the player must use the chemicals they have collected from"
        + " the storage room, to perform a chemical reaction in the flask, which is in the"
        + " main room.";
  }

  /**
   * Tells the AI to stop giving hints to the player.
   *
   * @return the prompt to the AI
   */
  public static String introduceFourthTask() {
    return "The player must complete task 4 before they can progress. To complete task 4, the"
        + " player must correctly answer a quiz question on a whiteboard. The whiteboard is"
        + " located in your office. To find the answer to the question, the player must hack"
        + " into your laptop by guessing the password. The password will be the answer to a"
        + " riddle. The answer to the quiz question on the whiteboard will then be open in"
        + " the laptop once it has been hacked into.";
  }

  /**
   * Tells the AI that the player has completed all the tasks.
   *
   * @return the prompt to the AI
   */
  public static String allTasksComplete() {
    return "The player has completed all tasks and can now exit the game by going through the exit"
        + " door. Pretend you are angry at the student for completing the tasks close to the"
        + " deadline, but happy at the same time that they got all the tasks done.";
  }

  /**
   * Tells the AI that the player has collected the key.
   *
   * @return the prompt to the AI
   */
  public static String playerCollectedKey() {
    return "The player has collected a key, which they will need to complete task 2. If the player"
        + " has completed task 1, your hints (if you are allowed to give them) should now"
        + " focus on using the key to open the storage locker. Pretend like you are"
        + " wondering what the key does. Respond to the player in under 10 words.";
  }

  /**
   * Introduces the user to the game Used for TTS only.
   *
   * @return the prompt to the AI
   */
  public static String introduceFlask() {
    return "You are a Lab Professor at a school, the student must finish his assignment on time."
        + " The student he must finish in time and click the flask to get started and"
        + " completed the first task. Respond in under 25 words.";
  }

  /**
   * Tells the AI that the user has done all the tasks.
   *
   * @return the prompt to the AI
   */
  public static String tasksComplete() {
    return "The player has completed all the tasks. Congratulate them, and tell them they can leave"
        + " in under 15 words.";
  }

  /**
   * Tells the AI to stop giving hints to the player.
   *
   * @return the prompt to the AI
   */
  public static String stopGivingHints() {
    return "The player has now reached 5 hints used. You must no longer give any hints to the"
        + " player under any circumstances, even if they ask for them.";
  }

  /**
   * Figures out what to prompt the AI regarding how many hints to give out.
   *
   * @return a prompt, telling the AI how many hints to give out
   */
  private static String getHintPrompt() {
    switch (GameState.levelDifficulty) {
      case EASY:
        return " Help the player out, and give them hints if they ask for them. The player can ask"
            + " as many hints as they want. If the player asks for a hint, begin your reponse with"
            + " \"Hint: \".";

      case MEDIUM:
        return " The player has 5 hints they can use throughout the whole game. After those 5 hints"
            + " are used up, you must not give the player any more hints under any"
            + " circumstances. If the player asks for a hint, begin your reponse with"
            + " \"Hint: \".";

      case HARD:
        return " You must not give any hints to the player under any circumstances, even if they"
            + " ask for them.";

      default:
        System.out.println("ERROR - A DIFFICULTY HAS NOT BEEN CHOSEN!!!");
        return null;
    }
  }
}
