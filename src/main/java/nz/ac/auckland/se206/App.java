package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
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

    Font.loadFont(getClass().getResourceAsStream("/fonts/linowrite.ttf"), 20);

    loadRooms();

    stage.setOnCloseRequest(
        event -> {
          System.exit(0);
        });

    scene = new Scene(SceneManager.getRoot(Room.START), 1000, 626);
    Parent root = SceneManager.getRoot(Room.START);
    // Set css style
    String css = this.getClass().getResource("/css/style.css").toExternalForm();
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
    root.requestFocus();

    // generate a random password
    GameState.password = Password.getRandomPassword();
  }

  public static void changeScene(Room room) {
    scene.setRoot(SceneManager.getRoot(room));
  }

  public static void reloadFXML() throws IOException {
    SceneManager.removeAllMapping();
    loadRooms();

    changeScene(Room.START);
    // generate a random password
    GameState.password = Password.getRandomPassword();
  }

  private static void loadRooms() throws IOException {
    SceneManager.addRoom(Room.MAIN_ROOM, loadFxml("mainRoom"));
    SceneManager.addRoom(Room.STORAGE_ROOM, loadFxml("storageRoom"));
    SceneManager.addRoom(Room.TEACHER_ROOM, loadFxml("teacherRoom"));
    SceneManager.addRoom(Room.LAPTOP, loadFxml("laptop"));
    SceneManager.addRoom(Room.EXIT, loadFxml("exit"));
    SceneManager.addRoom(Room.START, loadFxml("start"));
    SceneManager.addRoom(Room.EXIT, loadFxml("exit"));
    SceneManager.addRoom(Room.GAME_OVER, loadFxml("gameOver"));
  }
}
