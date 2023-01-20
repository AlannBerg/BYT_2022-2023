package pl.pja.qrcepta.utlis;

import static pl.pja.qrcepta.constants.QrceptaConstants.QR_CODE_DATA_JOINER;

import javax.validation.constraints.NotNull;
import pl.pja.qrcepta.model.entity.Prescription;

public class PrescriptionUtils {

  public static String prescriptionToQrCodeData(@NotNull Prescription prescription) {
    String patientID = prescription.getPatient().getId().toString();
    String securityCode = prescription.getSecurityCode();
    return new StringBuilder()
        .append(patientID)
        .append(QR_CODE_DATA_JOINER)
        .append(securityCode)
        .toString();
  }
}
