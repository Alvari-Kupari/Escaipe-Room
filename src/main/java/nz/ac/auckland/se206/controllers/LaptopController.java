package nz.ac.auckland.se206.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.QuizAnswer;
import nz.ac.auckland.se206.RoomBinder;
import nz.ac.auckland.se206.SceneManager.Room;
import nz.ac.auckland.se206.SoundManager;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;

/** Used to manage the laptop mini game. */
public class LaptopController extends RoomController {
  @FXML private PasswordField passwordField;
  @FXML private Button maskPasswordButton;
  @FXML private TextField unMaskedPassword;
  @FXML private Button goBackButton;
  @FXML private TextArea riddle;

  @FXML private Pane tabs;
  @FXML private TextArea diary;
  @FXML private Button leftTab;
  @FXML private Button rightTab;

  @FXML private ImageView rightTabImage;
  @FXML private ImageView leftTabImage;

  @FXML private Text quizText;
  @FXML private Pane quizAnswers;
  @FXML private Text days;

  private boolean isPasswordHidden;

  /**
   * Initializes the fxml for this room. For the laptop room, this involves. Binding the riddle text
   * to another class, so that the riddle can be loaded, as. well as handling the enter key being
   * pressed.
   */
  @FXML
  private void initialize() {
    // bind common room elements
    bind();

    // set chat prompt text
    playerInput.setPromptText("Chat here ... ");

    // make pressing enter send chat
    setEnterToSendChat();

    // set riddle text to be accessible
    RoomBinder.riddleText = riddle;

    // set password to be hidden
    isPasswordHidden = true;

    // bind the masked password to the unmasked one
    unMaskedPassword.textProperty().bindBidirectional(passwordField.textProperty());

    // make a new handler to pick up the enter key being pressed
    EventHandler<KeyEvent> handler =
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent e) {
            if (e.getCode().equals(KeyCode.ENTER)) {
              System.out.println("Enter pressed from password");
              // get the password
              String guess = passwordField.getText();

              System.out.println(guess);

              // check if it is correct
              if (guess.toUpperCase().equals(GameState.password.toString())) {
                System.out.println("password is right");

                openComputer();

                SoundManager.playCorrect();

                gameMaster.giveContext(GptPromptEngineering.answerQuestionOnWhiteBoard());

                return;
              }
              System.out.println("password is wrong");
              SoundManager.playError();
              passwordField.clear();
            }
          }
        };
    // set both fields to pick up the enter key being pressed
    passwordField.setOnKeyPressed(handler);
    unMaskedPassword.setOnKeyPressed(handler);

    setQuizAnswers();
  }

  /** Switches between masking and unmasking the password field text. */
  @FXML
  private void onMaskPassword() {
    // update the password's visiblity
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  /** Handles the user exiting the laptop view. This returns them back to the teach room. */
  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
  }

  /**
   * Handles the computer being opened for the first time. This method is only invoked once, and
   * makes the laptop visible.
   */
  private void openComputer() {
    // open the computer
    tabs.setVisible(true);
    diary.setVisible(true);

    // set the right button to be translucent
    rightTabImage.setOpacity(0.5);

    // activate the left tab button
    leftTab.setOnAction(
        (e) -> {
          // update opacities of both tab buttons
          leftTabImage.setOpacity(0.5);
          rightTabImage.setOpacity(1);

          // update visiblity
          diary.setVisible(false);
          quizAnswers.setVisible(true);
        });

    // active the right tab button
    rightTab.setOnAction(
        (e) -> {
          // update opacities of both tab buttons
          leftTabImage.setOpacity(1);
          rightTabImage.setOpacity(0.5);

          // update visiblity
          diary.setVisible(true);
          quizAnswers.setVisible(false);
        });
  }

  /** Sets the quiz answers randomly for this game. */
  private void setQuizAnswers() {
    StringBuilder sb = new StringBuilder();

    // get a selection of random quiz answers
    for (String answer : QuizAnswer.getRandomAnswers()) {
      sb.append(answer + "\n");
    }

    // set the quiz answers
    quizText.setText(sb.toString());

    // set all the days of the week
    days.setText("Monday: \nTuesday:\nWednesday:\nThursday:\nFriday:\nSaturday:\nSunday:\n");
  }
}
