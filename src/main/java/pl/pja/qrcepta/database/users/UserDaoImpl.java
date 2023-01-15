package pl.pja.qrcepta.database.users;

import static java.util.Objects.isNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.User;

@Slf4j
public class UserDaoImpl implements UserDao {

//  @PersistenceUnit(name = "users-persistence-unit")
//  private EntityManagerFactory entityManagerFactory;

  private EntityManager getEntityManager() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("users-persistence-unit");
    return entityManagerFactory.createEntityManager();
  }

//    private EntityManager getEntityManager() {
//    return entityManagerFactory.createEntityManager();
//  }

  @Override
  public User getUserFromDatabase(String login, String hashedPassword) {
    log.info("Getting user role form database for {}", login);
    EntityManager entityManager = getEntityManager();
    try {
      User user =
          entityManager
              .createQuery(
                  "select user from User user where user.login = :login and user.hashedPassword = : password",
                  User.class)
              .setParameter(login, "login")
              .setParameter(hashedPassword, "password")
              .getSingleResult();
      return user;
    } catch (Exception e) {
      log.error("Can not get user {} from database {}",login,e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }
}
