package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.RoomBinder;

public class ExitController extends SettingsController {

  @FXML private Text grade;

  @FXML
  private void initialize() {
    System.out.println();
    System.out.println("************** Initialising ExitController **************");

    // set the grade to be bound to the RoomBinder class
    RoomBinder.grade = grade;
  }
}
