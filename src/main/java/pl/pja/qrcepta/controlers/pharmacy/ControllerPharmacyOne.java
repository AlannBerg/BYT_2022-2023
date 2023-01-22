package pl.pja.qrcepta.controlers.pharmacy;

import static java.util.Objects.isNull;
import static pl.pja.qrcepta.constants.QrceptaConstants.CHOOSE_QR_FILE_WINDOW_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.ERROR_MSG_COLOR;
import static pl.pja.qrcepta.constants.QrceptaConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SUCCESS_MSG_COLOR;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.PrescriptionDataSingleton;
import pl.pja.qrcepta.dataConnection.UserDataSingleton;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.PatientService;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.implemention.PatientServiceImpl;
import pl.pja.qrcepta.services.implemention.PrescriptionServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;
import pl.pja.qrcepta.validator.PeselValidator;

@Slf4j
public class ControllerPharmacyOne implements Initializable {

  @FXML private Button LogOutButton;

  @FXML private Button cancelWithdrawingPrescriptionButton;

  @FXML private Label foundPatientLastName;

  @FXML private Label foundPatientName;

  @FXML private Pane patientPane;

  @FXML private TextField peselTextFIeld;

  @FXML private Label prescriptionContent;

  @FXML private Label prescriptionCreationDateLabel;

  @FXML private Pane prescriptionDetailsPane;

  @FXML private TextField prescriptionIDTextField;

  @FXML private Label prescriptionInfoLabel;

  @FXML private Pane prescriptionPane;

  @FXML private Button scanButton;

  @FXML private Pane searchPatientPane;

  @FXML private Button searchPrescriptionButton;

  @FXML private Button searchpatientButton;

  @FXML private Button showPatientSerchButton;

  @FXML private Label usersName;

  @FXML private Button withDrawMedicinesButton;

  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();
  private PatientService patientService = new PatientServiceImpl();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = UserDataSingleton.getInstance().getUser();
    usersName.setText(user.getUserName());
  }

  @FXML
  void logOutAction(ActionEvent event) {
    log.info("LOGING OUT");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
  }

  @FXML
  void cancelWithdrawingPrescription(ActionEvent event) {
    cancellAll();
  }

  @FXML
  void scanQRcept(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    log.info("Scaning qr code and getting prescription.");
    File qrCodeFile = getQrCodeFromFileChooser(stage);
    Prescription prescription = prescriptionService.getPrescriptionFromQrCode(qrCodeFile);
    if (isNull(prescription)) {
      showInfo("Nie znaleziono recepty.", ERROR_MSG_COLOR);
      return;
    }
    log.debug("Found prescription {}", prescription);
    showPrescription(prescription);
  }

  @FXML
  void searchPatient(ActionEvent event) {
    if (peselTextFIeld.getText().isEmpty()) {
      showInfo("Uzupełnij pesel", ERROR_MSG_COLOR);
      return;
    }
    if (!PeselValidator.isPeselValid(peselTextFIeld.getText())) {
      showInfo("Pesel niepoprawny", ERROR_MSG_COLOR);
    }

    Patient foundPatient = patientService.getPatientWithPesel(peselTextFIeld.getText());
    if (isNull(foundPatient)) {
      showInfo("Nie ma takiego pacjenta.", ERROR_MSG_COLOR);
      searchPatientPane.setVisible(false);
      return;
    }
    showPatientInfo(foundPatient);
    searchPatientPane.setVisible(false);
    prescriptionPane.setVisible(true);
  }

  @FXML
  void searchPrescription(ActionEvent event) {
    if (prescriptionIDTextField.getText().isEmpty()) {
      showInfo("Uzupełnij numer recepty", ERROR_MSG_COLOR);
      return;
    }
    Prescription prescription =
        prescriptionService.getPrescription(prescriptionIDTextField.getText());
    if (isNull(prescription)) {
      showInfo("Nie znaleziono recepty", ERROR_MSG_COLOR);
      return;
    }
    prescriptionPane.setVisible(false);
    showPrescription(prescription);
  }

  @FXML
  void showPatientSerch(ActionEvent event) {
    searchPatientPane.setVisible(true);
  }

  @FXML
  void withDrawMedicines(ActionEvent event) {
    prescriptionDetailsPane.setVisible(false);
    Prescription prescription = PrescriptionDataSingleton.getInstance().getPrescription();
    Prescription savedPrescritpion = prescriptionService.setStatusAsRelased(prescription);
    if (isNull(savedPrescritpion)) {
      showInfo("Blad podczas wydawania recepty.", ERROR_MSG_COLOR);
      return;
    }
    showInfo("Wydano receptę poprawnie.", SUCCESS_MSG_COLOR);
    PrescriptionDataSingleton.getInstance().setPrescription(null);
    patientPane.setVisible(false);
    cancellAllWithoutPatient();
  }

  private File getQrCodeFromFileChooser(Stage stage) {
    try {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle(CHOOSE_QR_FILE_WINDOW_TITLE);
      return fileChooser.showOpenDialog(stage);
    } catch (Exception e) {
      log.error("Blad podczas wyvierania koduy {}", e.getMessage());
      return null;
    }
  }

  private void showInfo(String info, Paint color) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          prescriptionInfoLabel.setText(info);
          prescriptionInfoLabel.setTextFill(color);
        });
    pause.play();
  }

  private void showPatientInfo(Patient foundPatient) {
    foundPatientName.setText(foundPatient.getName());
    foundPatientLastName.setText(foundPatient.getLastname());
    patientPane.setVisible(true);
  }

  private void showPrescription(Prescription prescription) {
    PrescriptionDataSingleton.getInstance().setPrescription(prescription);
    prescriptionDetailsPane.setVisible(true);
    prescriptionContent.setText(prescription.getMedicines());
    prescriptionCreationDateLabel.setText(prescription.getCreated_at().toString());
  }

  private void cancellAll() {
    prescriptionDetailsPane.setVisible(false);
    foundPatientName.setText(null);
    foundPatientLastName.setText(null);
    prescriptionIDTextField.clear();
    peselTextFIeld.clear();
    prescriptionPane.setVisible(false);
    patientPane.setVisible(false);
  }

  private void cancellAllWithoutPatient() {
    prescriptionDetailsPane.setVisible(false);
    prescriptionIDTextField.clear();
    peselTextFIeld.clear();
    prescriptionPane.setVisible(false);
  }
}
