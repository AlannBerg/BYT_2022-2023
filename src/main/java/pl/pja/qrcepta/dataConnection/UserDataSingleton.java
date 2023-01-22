package pl.pja.qrcepta.dataConnection;

import lombok.Data;
import pl.pja.qrcepta.model.entity.User;

@Data
public class UserDataSingleton {
  private static final UserDataSingleton instance = new UserDataSingleton();
  private User user;

  public static UserDataSingleton getInstance() {
    return instance;
  }
}
