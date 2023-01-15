package pl.pja.qrcepta.services;

import static java.util.Objects.nonNull;

import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.users.UserDao;
import pl.pja.qrcepta.database.users.UserDaoImpl;
import pl.pja.qrcepta.model.UserType;
import pl.pja.qrcepta.model.entity.User;

@Slf4j
public class UserServiceImpl implements UserService {
  private UserDao userDao = new UserDaoImpl();

  @Override
  public UserType getUserRole(String login, String hashedPassword) {
    log.info("Getting  user role, checking if it exist.");
    User user = userDao.getUserFromDatabase(login, hashedPassword);
    if (nonNull(user)) {
//      UserType userType = user.getUserType();
//      log.debug("User {} returned role is {}", login, userType);
//      return userType;
      return null;
    }
    log.error("User {} dont exist", login);
    return UserType.NOT_EXIST;
  }
}
