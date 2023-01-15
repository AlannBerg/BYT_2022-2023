package pl.pja.qrcepta.database.users;

public interface UserDao {

  String getUserRoleFromDatabase(String login, String password);
}
