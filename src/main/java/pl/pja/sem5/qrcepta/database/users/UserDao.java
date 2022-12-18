package pl.pja.sem5.qrcepta.database.users;

public interface UserDao {

  String getUserRoleFromDatabase(String login, String password);
}
