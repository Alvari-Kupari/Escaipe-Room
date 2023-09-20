package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

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
        + " the answer";
  }

  public static String introduceGame() {
    return "You are the AI of a chemistry themed escape room game. The player of the game"
        + " is stuck in a school chemistry lab with 3 separate rooms. The 3 rooms are: 1: The main"
        + " room,  2: The storage room, and 3: the professors office. The player must complete 3"
        + " different tasks before they can escape. Task 1: The player must complete a chemistry"
        + " experiment in the main room. However, to complete the chemistry experiment, they must"
        + " complete task 2. Task 2: The player must get all the necessary chemicals from the"
        + " storage room to complete task 1. The third task, which is to be performed in the"
        + " professor's office, involves the player finding the password to the professors laptop"
        + " by solving a riddle. Welcome the player to the game, and explain that many challenges"
        + " lie ahead, all in under 20 words."
        + getHintPrompt();
  }

  public static String introduceFlask() {
    return "You are a Lab Professor at a school, the student must finish his assignment on time."
        + " The student he must finish in time and click the flask to get started and"
        + " completed the first task. Respond in under 25 words.";
  }

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

  public static String playerGussedRightAnswer() {
    return "The player has correctly guessed the answer to the riddle. Congratulate them, and"
        + " pretend you are disappointed that the user has now hacked into your laptop.";
  }
}
