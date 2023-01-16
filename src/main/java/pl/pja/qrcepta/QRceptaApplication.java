package pl.pja.qrcepta;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.pja.qrcepta.utlis.SceneManager;

public class QRceptaApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    SceneManager.changeSceneToLoginScene(stage);
  }

  public static void main(String[] args) {
    launch();
  }
}
