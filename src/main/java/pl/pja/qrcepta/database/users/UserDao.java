package pl.pja.qrcepta.database.users;

import pl.pja.qrcepta.model.entity.User;

public interface UserDao {

  //todo
  User getUserFromDatabase(String login, String password);
}
