package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.isNull;

import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.services.PatientService;
import pl.pja.qrcepta.validator.EntityValidator;

@Slf4j
public class PatientServiceImpl implements PatientService {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();
  private final EntityValidator<Patient> patientEntityValidator = new EntityValidator<>();

  @Override
  public Patient getPatientWithPesel(@NotNull String pesel) {
    log.info("Geting patient with pesel {}",pesel);
    Patient patient = qrceptaDBConnection.getPatiendByPeselNo(pesel);
    if(isNull(patient)){
      log.debug("No patient with pesel {} in database.",pesel);
      return null;
    }
    log.debug("Returned patient {}",patient);
    return patient;
  }

  @Override
  public Patient saveNewPatient(@NotNull Patient patient) {
    if(patientEntityValidator.isValid(patient)){
      return qrceptaDBConnection.savePatient(patient);
    }
    log.error("Can not save patient {}",patient);
    return null;
  }
}
