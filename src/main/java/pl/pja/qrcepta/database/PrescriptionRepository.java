package pl.pja.qrcepta.database;

import static pl.pja.qrcepta.database.QrceptaDBConnectionImpl.getEntityManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Prescription;

@Slf4j
public class PrescriptionRepository {

  public Prescription savePrescription(@NotNull Prescription prescription) {
    log.info("Saving prescription {}", prescription);
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(prescription);
      transaction.commit();
      return prescription;
    } catch (Exception e) {
      log.error("Can not save prescription {} {}", prescription, e.getMessage());
      transaction.rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  public Prescription getPrescription(@NotNull Long id) {
    log.info("Geting prescritpion for id {}", id);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Prescription.getByID", Prescription.class)
          .setParameter("id", id)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", id, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public Prescription getPrescription(@NotNull Long patientID, @NotNull String securityCode) {
    log.info("Geting prescritpion for patientID {}", patientID);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Prescription.getByPatientIDandSecurityCode", Prescription.class)
          .setParameter("patientID", patientID)
          .setParameter("securityCode", securityCode)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", patientID, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public List<Prescription> getPrescriptionListForPatient(@NotNull Long patientID) {
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Prescription.getByPatientID", Prescription.class)
          .setParameter("patientID", patientID)
          .getResultList();
    } catch (Exception e) {
      log.error("Can not find prescritpion list  for {} {}", patientID, e.getMessage());
      return Collections.emptyList();
    } finally {
      entityManager.close();
    }
  }
}
