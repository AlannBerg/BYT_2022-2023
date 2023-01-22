package pl.pja.qrcepta.dataConnection;

import lombok.Data;
import pl.pja.qrcepta.model.entity.Patient;

@Data
public class PatientDataSingleton {
  private static final PatientDataSingleton instance = new PatientDataSingleton();
  private Patient patient;

  public static PatientDataSingleton getInstance() {
    return instance;
  }
}
