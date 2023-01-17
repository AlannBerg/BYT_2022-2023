package pl.pja.qrcepta.utlis;

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

  public static final String ADMIN_SCENE_NAME = "admin-stage.fxml";
  public static final String ADMIN_SCENE_TITLE = "QRCEPTA ADMIN";

  public static final Integer SECURITY_CODE_LENGTH = 254;
  public static final Boolean SECURITY_CODE_USE_LETTERS = true;
  public static final Boolean SECURITY_CODE_USE_NUMBERS = false;
  public static final String SECURITY_TEXT_TO_GENERATE_NEW_ONE = "GENERATE";

  public static final String LOG_IN_ERROR_MSG_TEXT = "Błąd logowania.";
  public static final String PRESCRIPTION_NOT_FOUND_ERROR_MSG_TEXT = "Nie znaleziono recepty.";
  public static final String ERROR_MSG_TEXT_CLEAR = null;
  public static final Integer ERROR_MSG_SHOW_TIME_SECONDS = 2;
}
