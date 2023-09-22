package nz.ac.auckland.se206;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** This is the entry point of the SoundManager. Where all game sounds are handled. */
public class SoundManager {

  // Create a media player for each sound
  private static MediaPlayer mediaPlayer;
  // Get the URL for each sound
  private static URL soundClick = App.class.getResource("/sounds/click.mp3");
  private static URL soundSelect = App.class.getResource("/sounds/select.mp3");
  private static URL soundError = App.class.getResource("/sounds/error.mp3");
  private static URL soundSplash = App.class.getResource("/sounds/splash.mp3");
  private static URL soundBubbles = App.class.getResource("/sounds/bubbles.mp3");
  private static URL soundCorrect = App.class.getResource("/sounds/correct.mp3");
  private static URL soundRoundWon = App.class.getResource("/sounds/roundWon.mp3");
  private static URL soundRoundLost = App.class.getResource("/sounds/roundLost.mp3");

  /** Plays the click sound */
  public static void playClick() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundClick.toString()));
    mediaPlayer.play();
  }

  /** Plays the select sound */
  public static void playSelect() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundSelect.toString()));
    mediaPlayer.play();
  }

  /** Plays the error sound */
  public static void playError() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundError.toString()));
    mediaPlayer.play();
  }

  /** Plays the splash sound */
  public static void playSplash() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundSplash.toString()));
    mediaPlayer.play();
  }

  /** Plays the bubbles sound */
  public static void playBubbles() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundBubbles.toString()));
    mediaPlayer.play();
  }

  public static void playCorrect() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundCorrect.toString()));
    mediaPlayer.play();
  }

  public static void playRoundWon() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundRoundWon.toString()));
    mediaPlayer.play();
  }

  public static void playRoundLost() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(new Media(soundRoundLost.toString()));
    mediaPlayer.play();
  }
}
