package pl.pja.qrcepta.utlis;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.QRceptaApplication;

@Slf4j
public class SceneManager {

  public static void changeSceneTo(Stage stage, String sceneName, String title) {}

  public static void changeSceneTo(
      Stage stage, String sceneName, String title, Double width, Double height) throws IOException {
    log.info("Changing scnece to {}",sceneName);
    FXMLLoader fxmlLoader = new FXMLLoader(QRceptaApplication.class.getResource(sceneName));
    Scene scene = new Scene(fxmlLoader.load(), width, height);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}
