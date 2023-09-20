package nz.ac.auckland.se206.timer;

import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class Timer {
  private static int seconds, minutes;
  private static Text timerText;

  /**
   * Resets the timer to the specified starting time.
   *
   * @param minutes the number of minutes.
   * @param seconds the number of seconds.
   */
  public static void setTimer(int minutes, int seconds) {
    Timer.seconds = seconds;
    Timer.minutes = minutes;
  }

  /** starts the timer */
  public static void startTimer() {

    Thread thread =
        new Thread(
            () -> {
              while (GameState.isGameStarted) {
                try {

                  // tick the clock and display the new time
                  decrementTime();
                  timerText.setText(getTime());

                  // wait 0.1 seconds.
                  Thread.sleep(1000);

                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            });

    thread.start();
  }

  /** Ticks the timer down by 1 s */
  public static void decrementTime() {

    if (seconds == 0) {

      // check if the timer has ran out
      if (minutes == 0) {
        GameState.isGameStarted = false;

        // change to game over screen
        App.changeScene(Room.GAME_OVER);
      }

      // tick down the minutes
      seconds = 59;
      minutes--;
      return;
    }
    if ((minutes == 0) && (seconds == 31)) {
      TextToSpeech.speech(GameState.msgTime30);
    }
    seconds--;
  }

  /**
   * Gives a string reprenstation of the time.
   *
   * @return a string representing the time of the clock.
   */
  public static String getTime() {
    String secondsString = String.valueOf(seconds);

    if (seconds < 10) {
      secondsString = "0" + secondsString;
    }

    return "0" + minutes + ":" + secondsString;
  }

  public static void stopTimer() {
    GameState.taskCompletionTime = getTimeInSeconds();
  }

  public static int getTimeInSeconds() {
    return 60 * minutes + seconds;
  }

  public static void bindText(Text timerText) {
    timerText.setText(getTime());
    Timer.timerText = timerText;
  }
}
