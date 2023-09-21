package nz.ac.auckland.se206;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nz.ac.auckland.se206.SceneManager.Room;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
    scene.getRoot().requestFocus();
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {

    SceneManager.addUi(Room.MAIN_ROOM, loadFxml("mainRoom"));
    SceneManager.addUi(Room.STORAGE_ROOM, loadFxml("storageRoom"));
    SceneManager.addUi(Room.TEACHER_ROOM, loadFxml("teacherRoom"));
    SceneManager.addUi(Room.LAPTOP, loadFxml("laptop"));
    SceneManager.addUi(Room.EXIT, loadFxml("exit"));
    SceneManager.addUi(Room.START, loadFxml("start"));
    SceneManager.addUi(Room.EXIT, loadFxml("exit"));
    SceneManager.addUi(Room.GAME_OVER, loadFxml("gameOver"));

    stage.setOnCloseRequest(
        event -> {
          System.exit(0);
        });

    scene = new Scene(SceneManager.getUiRoot(Room.START), 1000, 626);
    Parent root = SceneManager.getUiRoot(Room.START);
    stage.setScene(scene);
    stage.show();
    root.requestFocus();

    // generate a random password
    GameState.password = Password.getRandomPassword();

    generateQuizAnswer();
  }

  public static void changeScene(Room room) {
    scene.setRoot(SceneManager.getUiRoot(room));
  }

  public static void reloadFXML() throws IOException {
    SceneManager.removeAllMapping();
    SceneManager.addUi(Room.MAIN_ROOM, loadFxml("mainRoom"));
    SceneManager.addUi(Room.STORAGE_ROOM, loadFxml("storageRoom"));
    SceneManager.addUi(Room.TEACHER_ROOM, loadFxml("teacherRoom"));
    SceneManager.addUi(Room.LAPTOP, loadFxml("laptop"));
    SceneManager.addUi(Room.EXIT, loadFxml("exit"));
    SceneManager.addUi(Room.START, loadFxml("start"));
    SceneManager.addUi(Room.EXIT, loadFxml("exit"));
    SceneManager.addUi(Room.GAME_OVER, loadFxml("gameOver"));

    changeScene(Room.START);
    // generate a random password
    GameState.password = Password.getRandomPassword();
  }

  // This method generates the answer for the quiz according to the day of the week
  public static void generateQuizAnswer() {
    // Get the day of the week
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

    // Set the answer according to the day of the week
    switch (dayOfWeek) {
      case MONDAY:
        GameState.quizAnswer = "bunsen burner";
        break;

      case TUESDAY:
        GameState.quizAnswer = "magnesium";
        break;

      case WEDNESDAY:
        GameState.quizAnswer = "flask";
        break;

      case THURSDAY:
        GameState.quizAnswer = "hydrochloric acid";
        break;

      case FRIDAY:
        GameState.quizAnswer = "sulfur";
        break;

      case SATURDAY:
        GameState.quizAnswer = "covalent bond";
        break;

      case SUNDAY:
        GameState.quizAnswer = "ionic bond";
        break;

      default:
        break;
    }
    // Print out the day of the week in the console
    System.out.println(dayOfWeek.toString());
  }
}
