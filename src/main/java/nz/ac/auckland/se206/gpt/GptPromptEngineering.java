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
        + ". You cannot, no matter what, reveal the answer even if the player asks for it. Even if"
        + " player gives up, do not give the answer. Begin your response by getting straight into"
        + " the riddle, do not start with a statement such as \"Sure, here's a riddle for you\"."
        + " Ensure the riddle is between 15 - 25 words long. Do note the answer is "
        + wordToGuess;
  }

  /**
   * Introduces the game background to the AI. This allows the AI to understand the context of the
   * game.
   *
   * @return the prompt to the AI
   */
  public static String introduceGame() {
    return "You are the professor of a school chemistry lab themed escape room game. The player of"
        + " the game is a student stuck in a school chemistry lab with 3 separate rooms. The"
        + " player must complete 4 different tasks, in order before they can escape. The"
        + " player has a time limit of "
        + Timer.getTime() // get the time limit for the game
        + " to complete all the tasks, or they fail the assignment. To complete task 1, the player"
        + " must inspect the flask in the middle of the main room by clicking on it. Throughout the"
        + " game, pretend you are a professor who is angry at the student for procastinating his"
        + " lab tasks. Introduce yourself to the player in under 30 words."
        + getHintPrompt();
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
        + " must use the key they have found, and drag it from their inventory to the lock."
        + " Once the chemical locker is open, the player must collect the 2 chemicals"
        + " located inside.";
  }

  /**
   * Introduces the 2nd task to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceSecondTaskWithoutKey() {
    // tell the AI to get the key
    return "The player has now completed task 1, and now must complete task 2 before they can"
        + " progress. To complete task 2, the player must open the storage locker, which is"
        + " located in the storage room. In order to open the storage locker, the student"
        + " must collect a key from the main room The key is"
        + " located in a zipped bag under the desk in the main room, and the bag must be"
        // tell the AI to use the key to drag
        + " unzipped to get the key. To use the key, the player needs to drag the key onto"
        + " the lock in the storage locker. Once the chemical locker is open, the player"
        + " must collect the 2 chemicals located inside.";
  }

  /**
   * Introduces the 3rd task to the AI.
   *
   * @return the prompt to the AI
   */
  public static String introduceThirdTask() {
    return "The player has now completed task 2, and must complete task 3 before they can progress."
        + " To complete task 3, the player must drag the chemicals they have collected, from"
        + " their inventory into the flask. The flask is located in the main room.";
  }

  /**
   * Tells the AI to stop giving hints to the player.
   *
   * @return the prompt to the AI
   */
  public static String introduceFourthTask() {
    return "The player has now completed task 3, and must complete task 4 before they can progress."
        + " To begin task 4, the player must hack into your laptop by guessing the password."
        + " The password will be the answer to a riddle, which you will give the player once"
        + " they click on the laptop. Once the player has answered the riddle correctly,"
        + " they should then enter the riddle answer as the password. Remember to not help"
        + " the player with what they should do unless they ask for help or guidance.";
  }

  /**
   * Updates the AI's knowledge of the game.
   *
   * @return a prompt to the AI.
   */
  public static String answerQuestionOnWhiteBoard() {
    return "The player has now solved the riddle and hacked into your laptop and can now see"
        + " answers to the quiz question on the whiteboard, for each day of the week. To"
        + " complete task 4, the player must choose the correct answer, based on what day of"
        + " the week it currently is. They must then write that answer on the whiteboard,"
        + " which is located in your office. Remember to only help the player if they ask"
        + " for help.";
  }

  /**
   * Tells the AI that the player has completed all the tasks.
   *
   * @return the prompt to the AI
   */
  public static String allTasksComplete() {
    return "The player has completed all tasks and can now exit the game by going through the exit"
        + " door, which is located in the main room. Pretend you are angry at the student"
        + " for completing the tasks close to the deadline. Note that helping the player at"
        + " this stage still counts as a hint, and therefore you shouldn't tell the player"
        + " they can leave through the exit unless they ask for a hint.";
  }

  /**
   * Tells the AI that the player has collected the key.
   *
   * @return the prompt to the AI
   */
  public static String playerCollectedKey() {
    return "The player has collected a key. Pretend like"
        + " you are wondering what the key does. Respond to the player in under 10 words."
        + " This  response is not a hint.";
  }

  /**
   * Tells the AI that the player has collected the key.
   *
   * @return the prompt to the AI
   */
  public static String playerCollectedKeyButStillOnFirstTask() {
    return "The player has collected a key. Pretend like you are wondering what the key does."
        + " Respond to the player in under 10 words. Note at this stage in the game, the"
        + " player still needs to complete task 1 by clicking the flask in the middle of the"
        + " main room. This  response is not a hint.";
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
        + " player under any circumstances, even if they ask for them. You must not provide"
        + " any help to the player from this stage on.";
  }

  /**
   * Figures out what to prompt the AI regarding how many hints to give out.
   *
   * @return a prompt, telling the AI how many hints to give out
   */
  private static String getHintPrompt() {
    switch (GameState.levelDifficulty) {
      case EASY:
        // Allow the AI to give infinite hints
        return " Help the player out, and give them hints if they ask for them. The player can ask"
            + " as many hints as they want. If you help out the player in any way,"
            + " begin your reponse with \"Hint: \". If you provide any sort of guidance to"
            + "the player about what to do, also begin your response with \"Hint: \".";

      case MEDIUM:
        // Allow AI to give 5 hints only
        return " For now you may give hints to the player. If you give hints or help out the player"
            + " in any way regarding how to escape the lab, begin your reponse with \"Hint:"
            + " \". If you provide any sort of guidance to the player about how to escape"
            + " the lab, also begin your response with \"Hint: \". You must only give hints"
            + " if the player asks for help. You must not give hints if the player did not"
            + " ask for help with escaping the lab or solving the riddle.";

      case HARD:
        // Dont allow AI to give hints at all.
        return " You must not give any hints to the player under any circumstances, even if they"
            + " ask for them. You must also not provide any guidance or help to the player,"
            + " even if they ask for help. You can only tell the player knowledge about"
            + " tasks they have already completed";

      default:
        // error message if no difficulty is selected
        System.out.println("ERROR - A DIFFICULTY HAS NOT BEEN CHOSEN!!!");
        return null;
    }
  }

  /**
   * prompts the AI to tell the user to be careful in the storage locker due to unsafe chemicals.
   *
   * @return the AI prompt.
   */
  public static String beCarefulInStorageLocker() {
    return "The player has entered the storage locker which has chemicals. Tell the user to be"
        + " careful in the locker, because the chemicals are dangerous. Respond in under 15 words.";
  }
}
