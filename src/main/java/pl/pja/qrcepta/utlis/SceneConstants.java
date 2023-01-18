package pl.pja.qrcepta.utlis;

import javafx.scene.paint.Paint;

public class SceneConstants {
  public static final String LOG_IN_TITLE = "QRCEPTA LOGOWANIE";
  public static final String LOG_IN_SCENE_NAME = "logowanie.fxml";
  public static final double LOG_IN_STAGE_WIDTH = 750;
  public static final double LOG_IN_STAGE_HEIGHT = 500;

  public static final String DOCTOR_SCENE_NAME = "doctor-stage.fxml";
  public static final String DOCTOR_SCENE_TITLE = "QRCEPTA DOCTOR";
  public static final String DOCTOR_SAVED_PRESCRIPTION_SCENE = "doctor_saved-prescription.fxml";
  public static final String DOCTOR_SAVED_PRESCRIPTION_TITLE = "Utworzona recepta";
  public static final String DOCTOR_NEW_PATIENT_SCENE = "doctor_new-patient.fxml";
  public static final String DOCTOR_NEW_PATIENT_TITLE = "NOWY PACJENT";

  public static final String PHARMACY_SCENE_NAME = "pharmacy-stage.fxml";
  public static final String PHARMACY_SCENE_TITLE = "QRCEPTA PHARMACY";
  public static final String PHARMACY_RETURN_PRESCRIPTION_SCENE_NAME =
      "pharmacy-return-prescription.fxml";

  public static final String ADMIN_SCENE_NAME = "admin-panel.fxml";
  public static final String ADMIN_SCENE_TITLE = "ADMIN PANEL";
  public static final String NEW_USER_SAVED_SUCCESS_MSG = "Zapisano usera poprawnie.";
  public static final String NEW_USER_SAVED_ERROR_MSG = "Błąd podczas zapisywania";
  public static final String DELETE_USER_ERROR_MSG = "Błąd podczas usuwania użytkownika";
  public static final String DELETE_USER_SUCCESS_MSG = "Błąd podczas usuwania użytkownika";
  public static final String FILL_ALL_LABELS_ERROR = "Uzupełnij wszystkie dane.";

  public static final Integer SECURITY_CODE_LENGTH = 254;
  public static final Boolean SECURITY_CODE_USE_LETTERS = true;
  public static final Boolean SECURITY_CODE_USE_NUMBERS = false;
  public static final String SECURITY_TEXT_TO_GENERATE_NEW_ONE = "GENERATE";

  public static final String LOG_IN_ERROR_MSG_TEXT = "Błąd logowania.";
  public static final String PRESCRIPTION_NOT_FOUND_ERROR_MSG_TEXT = "Nie znaleziono recepty.";
  public static final String ERROR_MSG_TEXT_CLEAR = null;
  public static final Integer MSG_SHOW_TIME_SECONDS = 2;
  public static final Paint SUCCES_MSG_COLOR = Paint.valueOf("Green");
  public static final Paint ERROR_MSG_COLOR = Paint.valueOf("Red");
}
