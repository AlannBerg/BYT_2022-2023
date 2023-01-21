package pl.pja.qrcepta.database;

import static pl.pja.qrcepta.database.QrceptaDBConnectionImpl.getEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Patient;

@Slf4j
public class PatientRepository {

  public Patient getPatiendByPeselNo(@NotNull String pesel) {
    log.info("Geting patient with pesel {} from database", pesel);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Patient.findByPesel", Patient.class)
          .setParameter("pesel", pesel)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not get patient from database {}", e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public Patient savePatient(@NotNull Patient patient) {
    log.info("Saving patient {}", patient);
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(patient);
      transaction.commit();
      return patient;
    } catch (Exception e) {
      log.error("Can not save patient {} . {}", patient, e.getMessage());
      transaction.rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }
}
