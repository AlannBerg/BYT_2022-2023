package pl.pja.qrcepta.controlers;

import static java.util.Objects.isNull;
import static pl.pja.qrcepta.utlis.SceneConstants.ERROR_MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.utlis.SceneConstants.PRESCRIPTION_NOT_FOUND_ERROR_MSG_TEXT;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.DataSingleton;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.implemention.PrescriptionServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class PharmacyController {

  @FXML private Label errorLabel;

  @FXML private Button logOut;

  @FXML private TextField prescriptionID;

  @FXML private Button scanButton;

  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

  private DataSingleton prescriptionData = DataSingleton.getInstance();

  @FXML
  void logOut(ActionEvent event) {
    log.info("Login out");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
  }

  @FXML
  void scanQRcept(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // todo glowna logika
    //    log.info("Geting prescription from QRcept");
    log.info("Geting prescription from QRcept BETA prescription id {}", prescriptionID.getText());
    Prescription prescription = prescriptionService.getPrescription(prescriptionID.getText());
    if (isNull(prescription)) {
      showErrorMsg();
      return;
    }
    log.debug("Found prescription {}", prescription);
    prescriptionData.setPrescription(prescription);
    SceneManager.changeSceneToPharmacyShowPrescription(stage);
  }

  private void showErrorMsg() {
    log.error("Prescription not found");
    prescriptionID.clear();
    errorLabel.setText(PRESCRIPTION_NOT_FOUND_ERROR_MSG_TEXT);
    PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(e -> errorLabel.setText(null));
    pause.play();
  }
}
