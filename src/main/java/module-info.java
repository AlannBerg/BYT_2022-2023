module pl.pja.qrcepta {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.persistence;
  requires org.slf4j;
  requires lombok;
  requires org.apache.commons.codec;

  opens pl.pja.qrcepta to
      javafx.fxml;

  exports pl.pja.qrcepta;
  exports pl.pja.qrcepta.controlers;

  opens pl.pja.qrcepta.controlers to
      javafx.fxml;

  exports pl.pja.qrcepta.utlis;

  opens pl.pja.qrcepta.utlis to
      javafx.fxml;
}
