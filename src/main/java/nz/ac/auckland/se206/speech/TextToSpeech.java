package nz.ac.auckland.se206.speech;

import javafx.concurrent.Task;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.RoomBinder;

/** Text-to-speech API using the JavaX speech library. */
public class TextToSpeech {

  private static TextToSpeech textToSpeech = new TextToSpeech();

  /** Custom unchecked exception for Text-to-speech issues. */
  static class TextToSpeechException extends RuntimeException {
    public TextToSpeechException(final String message) {
      super(message);
    }
  }

  /**
   * Main function to speak the given list of sentences.
   *
   * @param args A sequence of strings to speak.
   */
  public static void main(final String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException(
          "You are not providing any arguments. You need to provide one or more sentences.");
    }

    final TextToSpeech textToSpeech = new TextToSpeech();

    textToSpeech.speak(args);
    textToSpeech.terminate();
  }

  private final Synthesizer synthesizer;

  /**
   * Constructs the TextToSpeech object creating and allocating the speech synthesizer. English
   * voice: com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory
   */
  public TextToSpeech() {
    try {
      System.setProperty(
          "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
      Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

      synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(java.util.Locale.ENGLISH));

      synthesizer.allocate();
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  /**
   * Speaks the given list of sentences.
   *
   * @param sentences A sequence of strings to speak.
   */
  public void speak(final String... sentences) {
    boolean isFirst = true;

    for (final String sentence : sentences) {
      if (isFirst) {
        isFirst = false;
      } else {
        // Add a pause between sentences.
        sleep();
      }

      speak(sentence);
    }
  }

  /**
   * Speaks the given sentence in input.
   *
   * @param sentence A string to speak.
   */
  public void speak(final String sentence) {
    if (sentence == null) {
      throw new IllegalArgumentException("Text cannot be null.");
    }

    try {
      synthesizer.resume();
      synthesizer.speakPlainText(sentence, null);
      synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    } catch (final AudioException | InterruptedException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  /** Sleeps a while to add some pause between sentences. */
  private void sleep() {
    try {
      Thread.sleep(100);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * It deallocates the speech synthesizer. If you are experiencing an IllegalThreadStateException,
   * avoid using this method and run the speak method without terminating.
   */
  public void terminate() {
    try {
      synthesizer.deallocate();
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  // This method is used for the professor to speak
  public static void speech(String text) {
    // Multi-Threading
    Task<Void> speechTask =
        new Task<Void>() {
          @Override
          // This allows the text to speech to run in the background
          protected Void call() throws Exception {
            System.out.println("Task.call() method: " + Thread.currentThread().getName());
            RoomBinder.professorTalking.setVisible(true);
            RoomBinder.professorThinking.setVisible(false);
            RoomBinder.professorAngry1.setVisible(false);
            RoomBinder.professorAngry2.setVisible(false);
            RoomBinder.professorAngry3.setVisible(false);
            RoomBinder.professorAngry4.setVisible(false);
            RoomBinder.professorResting.setVisible(false);

            textToSpeech.speak(text);
            return null;
          }
        };

    speechTask.setOnSucceeded(
        e -> {
          RoomBinder.professorTalking.setVisible(false);
          if (GameState.hintsUsed == 1) {
            RoomBinder.professorResting.setVisible(true);
          } else if (GameState.hintsUsed == 2) {
            RoomBinder.professorAngry1.setVisible(true);
          } else if (GameState.hintsUsed == 3) {
            RoomBinder.professorAngry2.setVisible(true);
          } else if (GameState.hintsUsed == 4) {
            RoomBinder.professorAngry3.setVisible(true);
          } else if (GameState.hintsUsed >= 5) {
            RoomBinder.professorAngry4.setVisible(true);
          }
        });

    Thread speechThread = new Thread(speechTask);
    speechThread.start();
  }
}
