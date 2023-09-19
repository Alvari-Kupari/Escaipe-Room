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
  private static Media soundError =
      new Media(App.class.getResource("/sounds/error.mp3").toString());
  private static Media soundSplash =
      new Media(App.class.getResource("/sounds/splash.mp3").toString());
  private static Media soundBubbles =
      new Media(App.class.getResource("/sounds/bubbles.mp3").toString());

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

  /** Plays the error sound */
  public static void playError() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(soundError);
    mediaPlayer.play();
  }

  /** Plays the splash sound */
  public static void playSplash() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(soundSplash);
    mediaPlayer.play();
  }

  /** Plays the bubbles sound */
  public static void playBubbles() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(soundBubbles);
    mediaPlayer.play();
  }
}
