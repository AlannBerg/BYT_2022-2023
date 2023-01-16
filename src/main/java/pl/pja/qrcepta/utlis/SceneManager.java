package pl.pja.qrcepta.utlis;

import static pl.pja.qrcepta.utlis.SceneConstants.LOG_IN_SCENE_NAME;
import static pl.pja.qrcepta.utlis.SceneConstants.LOG_IN_STAGE_HEIGHT;
import static pl.pja.qrcepta.utlis.SceneConstants.LOG_IN_TITLE;
import static pl.pja.qrcepta.utlis.SceneConstants.LOG_IN_STAGE_WIDTH;
import static pl.pja.qrcepta.utlis.SceneConstants.NEW_PATIENT_SCENE;
import static pl.pja.qrcepta.utlis.SceneConstants.NEW_PATIENT_TITLE;
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
    log.info("Changing scnece to {}",sceneName);
    FXMLLoader fxmlLoader = new FXMLLoader(QRceptaApplication.class.getResource(sceneName));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
  }

  public static void changeSceneToFullScreen(Stage stage, String sceneName, String title){
    changeSceneTo(stage,sceneName,title);
    stage.setMaximized(true);
  }

  @SneakyThrows
  public static void changeSceneTo(
      Stage stage, String sceneName, String title, Double width, Double height){
    changeSceneTo(stage,sceneName,title);
    stage.setTitle(title);
    stage.show();
  }

  @SneakyThrows
  public static void changeSceneToLoginScene(Stage stage) {
    log.info("Changing scene to Log In scene.");
    changeSceneTo(stage, LOG_IN_SCENE_NAME, LOG_IN_TITLE, LOG_IN_STAGE_WIDTH, LOG_IN_STAGE_HEIGHT);
  }

  public static void changeSceneToNewPatientScene(Stage stage) {
    changeSceneToFullScreen(stage,NEW_PATIENT_SCENE,NEW_PATIENT_TITLE);
  }
}
