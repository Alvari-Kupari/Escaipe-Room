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

  @FXML
  private void onMaskPassword() {
    unMaskedPassword.setVisible(isPasswordHidden);
    passwordField.setVisible(isPasswordHidden = !isPasswordHidden);
  }

  @FXML
  private void onGoBack() {
    App.changeScene(Room.TEACHER_ROOM);
  }

  private void openComputer() {
    tabs.setVisible(true);

    diary.setVisible(true);

    rightTabImage.setOpacity(0.5);

    leftTab.setOnAction(
        (e) -> {
          leftTabImage.setOpacity(0.5);
          rightTabImage.setOpacity(1);

          diary.setVisible(false);
          quizAnswers.setVisible(true);
        });

    rightTab.setOnAction(
        (e) -> {
          leftTabImage.setOpacity(1);
          rightTabImage.setOpacity(0.5);

          diary.setVisible(true);
          quizAnswers.setVisible(false);
        });
  }

  private void setQuizAnswers() {
    // get a selection of random quiz answers

    StringBuilder sb = new StringBuilder();

    for (String answer : QuizAnswer.getRandomAnswers()) {
      sb.append(answer + "\n");
    }
    quizText.setText(sb.toString());

    days.setText("Monday: \nTuesday:\nWednesday:\nThursday:\nFriday:\nSaturday:\nSunday:\n");
  }
}
