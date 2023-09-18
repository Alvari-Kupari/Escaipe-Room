package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;

public class GameMaster {
  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Gets a one off message from a separate AI instance. Useful if you wish to save tokens, and you
   * dont require. the AI to know any previous context.
   *
   * @param context the context of the room / game
   * @return the message the AI sent back
   */
  public static String getOneOffMessage(String context, double temp, double topP) {

    try {
      ChatCompletionRequest request =
          new ChatCompletionRequest()
              .setN(1)
              .setTemperature(temp)
              .setTopP(topP)
              .setMaxTokens(100)
              .setN(1);
      request.addMessage("system", context);
      ChatCompletionResult result = request.execute();
      return result.getChoice(0).getChatMessage().getContent();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates a new game master instance.
   *
   * @param temp the temperature of the AI
   * @param topP the top P value of the AI
   */
  public GameMaster(double temp, double topP) {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(temp).setTopP(topP).setMaxTokens(200);
  }

  /**
   * gives context to the AI from the system / game and gets a message from the AI.
   *
   * @param context a string representing the context of what has happened.
   * @return the response from the AI.
   */
  public void giveContext(String context) {
    chatCompletionRequest.addMessage("system", context);
  }

  /**
   * gets the response from the AI after recieving input from the player.
   *
   * @param text the text the user typed
   * @return the response from the AI
   */
  public String getResponse(String text) {
    return runGpt(new ChatMessage("user", text));
  }

  /**
   * gets the message from the AI based on the current chat log.
   *
   * @return the AI response
   */
  public String getResponse() {
    return runGpt();
  }

  /**
   * runs the gpt, returns the AI's response.
   *
   * @param msg the chatmessage from the game.
   * @return the AI response.
   */
  private String runGpt(ChatMessage msg) {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      ChatMessage result = chatCompletionResult.getChoices().iterator().next().getChatMessage();
      chatCompletionRequest.addMessage(result);
      printLogs();
      return result.getContent();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * runs the gpt, returns the AI's response.
   *
   * @return the AI response.
   */
  private String runGpt() {
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      ChatMessage result = chatCompletionResult.getChoices().iterator().next().getChatMessage();
      chatCompletionRequest.addMessage(result);
      printLogs();
      return result.getContent();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void printLogs() {
    for (ChatMessage msg : chatCompletionRequest.getMessages()) {
      System.out.println(msg.getRole() + ": " + msg.getContent());
    }
  }
}
