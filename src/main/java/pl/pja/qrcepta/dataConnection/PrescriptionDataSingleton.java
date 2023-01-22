package pl.pja.qrcepta.dataConnection;

import lombok.Data;
import pl.pja.qrcepta.model.entity.Prescription;

@Data
public class PrescriptionDataSingleton {
  private static final PrescriptionDataSingleton instance = new PrescriptionDataSingleton();
  private Prescription prescription;

  public static PrescriptionDataSingleton getInstance() {
    return instance;
  }
}
