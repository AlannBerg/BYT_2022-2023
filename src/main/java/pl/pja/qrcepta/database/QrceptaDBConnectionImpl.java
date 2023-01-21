package pl.pja.qrcepta.database;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Prescription;

@Slf4j
public class QrceptaDBConnectionImpl implements QrceptaDBConnection {

  public static EntityManager getEntityManager() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("users-persistence-unit");
    return entityManagerFactory.createEntityManager();
  }

  @Override
  public boolean securityCodeExistInDB(@NotNull String securityCode) {
    log.info("Cheching if prescription security code is unique {}", securityCode);
    EntityManager entityManager = getEntityManager();
    try {
      Optional<Prescription> optionalPrescription =
          entityManager
              .createNamedQuery("Prescription.checkIfSecurityCodeIsValid", Prescription.class)
              .setParameter("securityCode", securityCode)
              .getResultList()
              .stream()
              .findFirst();
      Boolean securityCodeExist = optionalPrescription.isPresent();
      log.debug("Prescription security code exist {}", securityCodeExist);
      return securityCodeExist;
    } catch (Exception e) {
      log.error("Can not check if security code is unique {}", e.getMessage());
      throw new RuntimeException("Can not check if security code is unique ");
    } finally {
      entityManager.close();
    }
  }
}
