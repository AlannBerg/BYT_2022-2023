package pl.pja.sem5.qrcepta.services;

import static java.util.Objects.nonNull;

import lombok.extern.slf4j.Slf4j;
import pl.pja.sem5.qrcepta.database.users.UserDao;
import pl.pja.sem5.qrcepta.database.users.UserDaoImpl;
import pl.pja.sem5.qrcepta.model.UserType;

@Slf4j
public class UserServiceImpl implements UserService {
  private UserDao userDao = new UserDaoImpl();

  @Override
  public UserType getUserRole(String login, String password) {
    log.info("Getting  user role, checking if it exist.");
    String userTypeString = userDao.getUserRoleFromDatabase(login, password);
    if (nonNull(userTypeString)) {
      UserType userType = UserType.of(userTypeString);
      log.debug("User {} returned role is {}", login, userType);
      return userType;
    }
    log.error("User {} dont exist", login);
    return UserType.NOT_EXIST;
  }
}
