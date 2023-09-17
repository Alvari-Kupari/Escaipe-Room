package nz.ac.auckland.se206;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** This is the entry point of the SoundManager. Where all game sounds are handled. */
public class SoundManager {

  private static MediaPlayer mediaPlayer;
  private static Media soundClick =
      new Media(App.class.getResource("/sounds/click.mp3").toString());
  private static Media soundSelect =
      new Media(App.class.getResource("/sounds/select.mp3").toString());

  /** Plays the click sound */
  public static void playClick() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(soundClick);
    mediaPlayer.play();
  }

  /** Plays the select sound */
  public static void playSelect() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(soundSelect);
    mediaPlayer.play();
  }
}
