package nz.ac.auckland.se206.gpt;

import javafx.concurrent.Task;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;

public class GameMaster {

  private ChatCompletionRequest chatCompletionRequest;

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
   * Gives context to the AI. The context is automatically assumed to be from the system.
   *
   * @param context the context to be given to the AI
   */
  public void giveContext(String context) {
    chatCompletionRequest.addMessage("system", context);
  }

  /**
   * Recives a message from the player.
   *
   * @param playerMessage the message the player wants to send the AI.
   */
  public void recieveplayerMessage(String playerMessage) {
    chatCompletionRequest.addMessage("user", playerMessage);
  }

  /**
   * Makes the AI respond to whatever is currently in the chat. This method automatically updates
   * the text for you, by using a thread. To avoid freezing the game.
   */
  public void respond() {
    Task<Void> respondTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            RoomBinder.professorThinking.setVisible(true);
            RoomBinder.professorAngry1.setVisible(false);
            RoomBinder.professorAngry2.setVisible(false);
            RoomBinder.professorAngry3.setVisible(false);
            RoomBinder.professorAngry4.setVisible(false);
            RoomBinder.professorResting.setVisible(false);
            RoomBinder.professorTalking.setVisible(false);

            // run the AI
            String response = runGpt();

            // set the chat text
            RoomBinder.chat.setText(response);

            return null;
          }
        };

    // make the face disappear after loading is finished
    respondTask.setOnSucceeded(
        e -> {

          // check if a hint was given
          String response = getLastResponse();

          if (response.substring(0, 10).toLowerCase().contains("hint")) {
            // update the hints used
            GameState.hintsUsed++;
            // print hint used
            System.out.println("Hint used");

            if (GameState.hintsRemaining != 0) {
              GameState.hintsRemaining--;
              RoomBinder.hintsNumber.setText(String.valueOf(GameState.hintsRemaining));
            }

            // stop giving hints, if there arent any hints left
            if (GameState.hintsRemaining == 0) {
              giveContext(GptPromptEngineering.stopGivingHints());
            }
          }
          RoomBinder.professorThinking.setVisible(false);
          if (GameState.hintsUsed == 0) {
            RoomBinder.professorResting.setVisible(true);
          } else if (GameState.hintsUsed == 1) {
            RoomBinder.professorAngry1.setVisible(true);
          } else if (GameState.hintsUsed == 2) {
            RoomBinder.professorAngry2.setVisible(true);
          } else if (GameState.hintsUsed == 3) {
            RoomBinder.professorAngry3.setVisible(true);
          } else if (GameState.hintsUsed >= 4) {
            RoomBinder.professorAngry4.setVisible(true);
          }
        });

    Thread thread = new Thread(respondTask);
    thread.start();
  }

  public void gettalkFlask() {
    Task<Void> talkTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {

            // run the AI
            String speech = runGpt();
            GameState.msgFlask = speech;

            return null;
          }
        };

    Thread threadTalk = new Thread(talkTask);
    threadTalk.start();
  }

  public void gettalkComplete() {
    Task<Void> takTask2 =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {

            // run the AI
            String speech = runGpt();
            GameState.msgComplete = speech;

            return null;
          }
        };

    Thread threadTalk2 = new Thread(takTask2);
    threadTalk2.start();
  }

  /**
   * Get the last reponse from the AI.
   *
   * @return the last AI response.
   */
  public String getLastResponse() {
    return chatCompletionRequest
        .getMessages()
        .get(chatCompletionRequest.getMessages().size() - 1)
        .getContent();
  }

  /**
   * This method runs the gpt and returns the AI's response.
   *
   * @return the AI response.
   */
  private String runGpt() {
    try {
      // execute the AI response
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      // get the AI response
      ChatMessage result = chatCompletionResult.getChoices().iterator().next().getChatMessage();
      // add the AI response to the chat
      chatCompletionRequest.addMessage(result);
      // print the logs
      printLogs();
      // return the AI response
      return result.getContent();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Helper method for the devs of the team, to debug and make sure the. chat completion request is
   * not putting on extra messages.
   */
  private void printLogs() {
    for (ChatMessage msg : chatCompletionRequest.getMessages()) {
      System.out.println(msg.getRole() + ": " + msg.getContent() + "\n");
    }
  }

  public void setRiddleText(Text riddleText) {
    Thread thread =
        new Thread(
            () -> {
              String riddle = runGpt();

              riddleText.setText(riddle);
            });

    thread.start();
  }
}
