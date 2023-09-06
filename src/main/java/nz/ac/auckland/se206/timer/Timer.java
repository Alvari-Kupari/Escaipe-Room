package nz.ac.auckland.se206.timer;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.Room;

public class Timer {
  private static int seconds, minutes, miliSeconds;
  private static TextArea timerText;

  private static Map<Room, TextArea> timerTexts = new HashMap<>();

  /**
   * Binds the timer text from a room to the Timer class.
   *
   * @param room the room the text came from.
   * @param text the text area for the timer.
   */
  public static void bindText(Room room, TextArea text) {
    timerTexts.put(room, text);
  }

  /**
   * Resets the timer to the specified starting time.
   *
   * @param minutes the number of minutes.
   * @param seconds the number of seconds.
   */
  public static void setTimer(int minutes, int seconds) {
    Timer.seconds = seconds;
    Timer.minutes = minutes;
    Timer.miliSeconds = 0;
  }

  /**
   * Switches which text to update.
   *
   * @param room the room to switch to.
   */
  public static void changeRoom(Room room) {
    if (room == Room.MAIN_ROOM || room == Room.STORAGE_ROOM || room == Room.TEACHER_ROOM) {
      timerText = timerTexts.get(room);
    }
  }

  /** starts the timer */
  public static void startTimer() {

    Thread thread =
        new Thread(
            () -> {
              while (GameState.isGameStarted) {
                try {

                  // tick the clock and display the new time
                  timerText.setText(getTime());
                  decrementTime();

                  // wait 0.1 seconds.
                  Thread.sleep(100);

                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }

              // Timer must have ran out, if the while loop was executed
              App.changeScene(Room.GAME_OVER);
            });
    thread.setDaemon(true);
    thread.start();
  }

  /** Ticks the timer down by 0.1 s */
  public static void decrementTime() {
    if (miliSeconds == 0) {

      if (seconds == 0) {

        // check if the timer has ran out
        if (minutes == 0) {
          GameState.isGameStarted = false;
        }

        // tick down the minutes
        seconds = 60;
        minutes--;
      }
      // decrement the seconds
      seconds--;
      miliSeconds = 9;
      return;
    }
    // decrement miliseconds
    miliSeconds -= 1;
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

    return "0" + minutes + ":" + secondsString + "." + miliSeconds;
  }
}
