package pl.pja.qrcepta.utlis;

import static pl.pja.qrcepta.constants.QrceptaConstants.ADMIN_SCENE_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.ADMIN_SCENE_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_SCENE_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_SCENE_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.LOG_IN_SCENE_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.LOG_IN_STAGE_HEIGHT;
import static pl.pja.qrcepta.constants.QrceptaConstants.LOG_IN_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.LOG_IN_STAGE_WIDTH;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_NEW_PATIENT_SCENE;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_NEW_PATIENT_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.PHARMACY_RETURN_PRESCRIPTION_SCENE_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.PHARMACY_SCENE_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.PHARMACY_SCENE_TITLE;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_SAVED_PRESCRIPTION_SCENE;
import static pl.pja.qrcepta.constants.QrceptaConstants.DOCTOR_SAVED_PRESCRIPTION_TITLE;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.QRceptaApplication;

@Slf4j
public class SceneManager {

  @SneakyThrows
  public static void changeSceneTo(Stage stage, String sceneName, String title) {
    log.info("Changing scnece to {}", sceneName);
    FXMLLoader fxmlLoader = new FXMLLoader(QRceptaApplication.class.getResource(sceneName));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle(title);
    stage.setScene(scene);
  }

  public static void changeSceneToFullScreen(Stage stage, String sceneName, String title) {
    changeSceneTo(stage, sceneName, title);
    stage.setMaximized(true);
  }

  @SneakyThrows
  public static void changeSceneTo(
      Stage stage, String sceneName, String title, Double width, Double height) {
    changeSceneTo(stage, sceneName, title);
    stage.setTitle(title);
    stage.show();
  }

  @SneakyThrows
  public static void changeSceneToLoginScene(Stage stage) {
    log.info("Changing scene to Log In scene.");
    changeSceneTo(stage, LOG_IN_SCENE_NAME, LOG_IN_TITLE, LOG_IN_STAGE_WIDTH, LOG_IN_STAGE_HEIGHT);
  }

  public static void changeSceneToNewPatientScene(Stage stage) {
    changeSceneToFullScreen(stage, DOCTOR_NEW_PATIENT_SCENE, DOCTOR_NEW_PATIENT_TITLE);
  }

  public static void changeSceneToSavedPrescription(Stage stage) {
    changeSceneToFullScreen(
        stage, DOCTOR_SAVED_PRESCRIPTION_SCENE, DOCTOR_SAVED_PRESCRIPTION_TITLE);
  }

  public static void changeSceneToDoctorStage(Stage stage) {
    changeSceneTo(stage, DOCTOR_SCENE_NAME, DOCTOR_SCENE_TITLE);
  }

  public static void changeSceneToPharmacyStage(Stage stage) {
    changeSceneTo(stage, PHARMACY_SCENE_NAME, PHARMACY_SCENE_TITLE);
  }

  public static void changeSceneToAdminPanel(Stage stage) {
    changeSceneTo(stage, ADMIN_SCENE_NAME, ADMIN_SCENE_TITLE);
  }

  public static void changeSceneToPharmacyShowPrescription(Stage stage) {
    changeSceneTo(stage, PHARMACY_RETURN_PRESCRIPTION_SCENE_NAME, PHARMACY_SCENE_TITLE);
  }
}
