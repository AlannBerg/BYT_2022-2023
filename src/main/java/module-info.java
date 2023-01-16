module pl.pja.qrcepta {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.persistence;
  requires org.slf4j;
  requires lombok;
  requires org.apache.commons.codec;
  requires java.sql;
  requires org.hibernate.orm.core;
  requires java.validation;

  opens pl.pja.qrcepta to
      javafx.fxml;

  exports pl.pja.qrcepta;
  exports pl.pja.qrcepta.controlers;
  exports pl.pja.qrcepta.model.entity;

  opens pl.pja.qrcepta.controlers to
      javafx.fxml;

  exports pl.pja.qrcepta.utlis;

  opens pl.pja.qrcepta.utlis to
      javafx.fxml;

  opens pl.pja.qrcepta.model.entity to org.hibernate.orm.core;
}
