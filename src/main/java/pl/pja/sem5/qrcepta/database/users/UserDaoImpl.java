package pl.pja.sem5.qrcepta.database.users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDaoImpl implements UserDao {

  private EntityManager getEntityManager() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("users-persistence-unit");
    return entityManagerFactory.createEntityManager();
  }

  @Override
  public String getUserRoleFromDatabase(String login, String password) {
    log.info("Getting user role form database");

    //    EntityManager entityManager = getEntityManager();
    try {
      // todo baza
      return "DOCTOR";
      //      entityManager.createQuery("select * from duoa")
      //
    } catch (Exception e) {
      return null;
    } finally {
      //      entityManager.close();
    }
  }
}
