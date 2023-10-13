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

  /**
   * Launches the game.
   *
   * @param args optional args for running the game
   */
  public static void main(final String[] args) {
    launch();
  }

  /**
   * Changes which screen is shown to the user.
   *
   * @param fxml the string representing the filename to be loaded.
   * @throws IOException if the FXML file could not be loaded.
   */
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

    // load fonts
    Font.loadFont(getClass().getResourceAsStream("/fonts/linowrite.ttf"), 20);

    loadRooms();

    // set exit button to close all threads
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

  /**
   * Switches which room is shown. Used by the controllers to switch scenes.
   *
   * @param room the room to change to.
   */
  public static void changeScene(Room room) {
    scene.setRoot(SceneManager.getRoot(room));
  }

  /**
   * Reloads all the fxml files in the game. Used when restarting the game.
   *
   * @throws IOException if the files couldnt be loaded properly.
   */
  public static void reload() throws IOException {
    SceneManager.removeAllMapping();
    loadRooms();

    changeScene(Room.START);
    // generate a random password
    GameState.password = Password.getRandomPassword();
  }

  /**
   * Loads all the rooms in the game.
   *
   * @throws IOException if the files couldnt be loaded properly.
   */
  private static void loadRooms() throws IOException {
    // add the 4 primary rooms
    SceneManager.addRoom(Room.MAIN_ROOM, loadFxml("mainRoom"));
    SceneManager.addRoom(Room.STORAGE_ROOM, loadFxml("storageRoom"));
    SceneManager.addRoom(Room.TEACHER_ROOM, loadFxml("teacherRoom"));
    SceneManager.addRoom(Room.LAPTOP, loadFxml("laptop"));

    // add the exit room
    SceneManager.addRoom(Room.EXIT, loadFxml("exit"));
    SceneManager.addRoom(Room.START, loadFxml("start"));

    // add the game over room
    SceneManager.addRoom(Room.GAME_OVER, loadFxml("gameOver"));
  }
}
