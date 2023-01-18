package pl.pja.qrcepta.controlers.admin;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.pja.qrcepta.utlis.SceneConstants.DELETE_USER_ERROR_MSG;
import static pl.pja.qrcepta.utlis.SceneConstants.DELETE_USER_SUCCESS_MSG;
import static pl.pja.qrcepta.utlis.SceneConstants.ERROR_MSG_COLOR;
import static pl.pja.qrcepta.utlis.SceneConstants.FILL_ALL_LABELS_ERROR;
import static pl.pja.qrcepta.utlis.SceneConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.utlis.SceneConstants.NEW_USER_SAVED_ERROR_MSG;
import static pl.pja.qrcepta.utlis.SceneConstants.NEW_USER_SAVED_SUCCESS_MSG;
import static pl.pja.qrcepta.utlis.SceneConstants.SUCCES_MSG_COLOR;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.UserType;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.UserService;
import pl.pja.qrcepta.services.implemention.UserServiceImpl;
import pl.pja.qrcepta.utlis.PasswordUtils;

@Slf4j
public class AdminPanelController implements Initializable {

  @FXML private Button addUserButton;

  @FXML private Label addUserMsgLabel;

  @FXML private Pane addUserPane;

  @FXML private Button deleteFindButton;

  @FXML private Button deleteUserButton;

  @FXML private TextField deleteUserLogin;

  @FXML private TextField deleteUserName;

  @FXML private Pane deleteUserPane;

  @FXML private Label deletingUserMsgLabel;

  @FXML private Button findUserToDeleteButton;

  @FXML private TextField newUserLogin;

  @FXML private TextField newUserPassword;

  @FXML private ComboBox<UserType> newUserRole;

  @FXML private TextArea newUserToShowData;

  @FXML private TextField newUserUserName;

  @FXML private Button saveUserButton;

  @FXML private Button submitNewUser;

  @FXML private TextArea userToDeleteData;

  private final UserService userService = new UserServiceImpl();
  private User newUser = null;
  private User userToDelete = null;

  @FXML
  void addNewUser(ActionEvent event) {
    deleteUserPane.setVisible(false);
    addUserPane.setVisible(true);
  }

  @FXML
  void deleteUser(ActionEvent event) {
    addUserPane.setVisible(false);
    deleteUserPane.setVisible(true);
  }

  @FXML
  void deleteFoundUser(ActionEvent event) {
    log.info("Usuwanie usera {}", userToDelete);
    Boolean deleteSuccessful = userService.deleteUser(userToDelete);
    clearDeleteInputData();
    if (deleteSuccessful) {
      deleteShowSuccessInfo(DELETE_USER_SUCCESS_MSG);
      deleteUserPane.setVisible(false);
    }
    deleteShowErrorInfo(DELETE_USER_ERROR_MSG);
  }

  @FXML
  void findUserToDelete(ActionEvent event) {

    if (userToDeleteDataIsNotSet()) {
      deleteShowErrorInfo(FILL_ALL_LABELS_ERROR);
      return;
    }
    userToDelete =
        userService.getByLoginAndName(deleteUserLogin.getText(), deleteUserName.getText());
    if (nonNull(userToDelete)) {
      clearDeleteInputData();
      userToDeleteData.setDisable(false);
      userToDeleteData.setText(userToDelete.toString());
      findUserToDeleteButton.setDisable(false);
      return;
    }
    clearDeleteInputData();
    deleteShowErrorInfo(DELETE_USER_ERROR_MSG);
  }

  @FXML
  void submitNewUser(ActionEvent event) {
    if (newUserDataIsNotReady()) {
      addUsershowErrorInfo(FILL_ALL_LABELS_ERROR);
      return;
    }
    String encryptedPassword = PasswordUtils.encryptPassword(newUserPassword.getText());
    newUser =
        User.builder()
            .userName(newUserUserName.getText())
            .login(newUserLogin.getText())
            .hashedPassword(encryptedPassword)
            .userType(newUserRole.getValue())
            .build();
    newUserToShowData.setText(newUser.toString());
    newUserToShowData.setDisable(false);
    saveUserButton.setDisable(false);
  }

  @FXML
  void saveUser(ActionEvent event) {
    clearNewUserInputLabels();
    Boolean saveWasSuccessful = userService.saveNewUser(newUser);
    if (saveWasSuccessful) {
      newUserShowSuccessInfo(NEW_USER_SAVED_SUCCESS_MSG);
      addUserPane.setVisible(false);
      return;
    }
    addUsershowErrorInfo(NEW_USER_SAVED_ERROR_MSG);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    newUserRole.setItems(
        FXCollections.observableArrayList(UserType.ADMIN, UserType.DOCTOR, UserType.PHARMACIST));
  }

  private boolean newUserDataIsNotReady() {
    return newUserLogin.getText().isEmpty()
        || newUserPassword.getText().isEmpty()
        || newUserUserName.getText().isEmpty()
        || isNull(newUserRole.getValue());
  }

  private void clearNewUserInputLabels() {
    newUserLogin.clear();
    newUserPassword.clear();
    newUserUserName.clear();
    newUserToShowData.clear();
    newUserRole.setValue(null);
  }

  private void newUserShowSuccessInfo(String msg) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          addUserMsgLabel.setText(msg);
          addUserMsgLabel.setTextFill(SUCCES_MSG_COLOR);
        });
    pause.play();
  }

  private void addUsershowErrorInfo(String errorMSG) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          addUserMsgLabel.setText(errorMSG);
          addUserMsgLabel.setTextFill(ERROR_MSG_COLOR);
        });
    pause.play();
  }

  private void deleteShowSuccessInfo(String msg) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          deletingUserMsgLabel.setText(msg);
          deletingUserMsgLabel.setTextFill(SUCCES_MSG_COLOR);
        });
    pause.play();
  }

  private void deleteShowErrorInfo(String errorMSG) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          deletingUserMsgLabel.setText(errorMSG);
          deletingUserMsgLabel.setTextFill(ERROR_MSG_COLOR);
        });
    pause.play();
  }

  private boolean userToDeleteDataIsNotSet() {
    return deleteUserLogin.getText().isEmpty() || deleteUserLogin.getText().isEmpty();
  }

  private void clearDeleteInputData() {
    deleteUserName.clear();
    deleteUserLogin.clear();
    userToDeleteData.clear();
  }
}
