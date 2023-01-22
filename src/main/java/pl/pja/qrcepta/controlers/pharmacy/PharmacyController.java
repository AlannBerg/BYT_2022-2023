package pl.pja.qrcepta.controlers.pharmacy;

import static java.util.Objects.isNull;
import static pl.pja.qrcepta.constants.QrceptaConstants.CHOOSE_QR_FILE_WINDOW_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.constants.QrceptaConstants.PRESCRIPTION_NOT_FOUND_ERROR_MSG_TEXT;

import java.io.File;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.PrescriptionDataSingleton;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.implemention.PrescriptionServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class PharmacyController {

  @FXML private Label errorLabel;

  @FXML private Button findByIDButton;

  @FXML private Button logOut;

  @FXML private TextField prescriptionID;

  @FXML private Button scanButton;

  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

  private PrescriptionDataSingleton prescriptionData = PrescriptionDataSingleton.getInstance();

  @FXML
  void logOut(ActionEvent event) {
    log.info("Login out");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
  }

  @FXML
  void scanQRcept(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    log.info("Scaning qr code and getting prescription.");
    File qrCodeFile = getQrCodeFromFileChooser(stage);
    Prescription prescription = prescriptionService.getPrescriptionFromQrCode(qrCodeFile);
    if (isNull(prescription)) {
      showErrorMsg();
      return;
    }
    log.debug("Found prescription {}", prescription);
    prescriptionData.setPrescription(prescription);
    SceneManager.changeSceneToPharmacyShowPrescription(stage);
  }

  @FXML
  void findByID(ActionEvent event) {
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
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(e -> errorLabel.setText(null));
    pause.play();
  }

  private File getQrCodeFromFileChooser(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(CHOOSE_QR_FILE_WINDOW_TITLE);
    return fileChooser.showOpenDialog(stage);
  }
}
