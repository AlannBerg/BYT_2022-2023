package pl.pja.qrcepta.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoctorController {
  @FXML private Button LogOutButton;

  @FXML private Button NewPatientButton;

  @FXML
  void LogOutAction(ActionEvent event) {
    log.info("LOGING OUT");
  }

  @FXML
  void NewPatientAction(ActionEvent event) {}
}
