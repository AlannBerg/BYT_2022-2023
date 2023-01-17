package pl.pja.qrcepta.dataConnection;

import lombok.Data;
import pl.pja.qrcepta.model.entity.Prescription;

@Data
public class DataSingleton {
  private static final DataSingleton instance = new DataSingleton();
  private Prescription prescription;

  public static DataSingleton getInstance(){
    return instance;
  }
}
