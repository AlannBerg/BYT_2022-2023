package pl.pja.qrcepta.controlers.doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class DoctorController {
  @FXML private Button LogOutButton;

  @FXML private Button NewPatientButton;

  @FXML
  void LogOutAction(ActionEvent event) {
    log.info("LOGING OUT");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
  }

  @FXML
  void NewPatientAction(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToNewPatientScene(stage);
  }
}
