package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the chemistry professor AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
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
        + " lie ahead, all in under 20 words";
  }

  public static String playerGussedRightAnswer() {
    return "The player has correctly guessed the answer to the riddle. Congratulate them, and"
               + " pretend you are disappointed that the user has now hacked into your laptop.";
  }
}
