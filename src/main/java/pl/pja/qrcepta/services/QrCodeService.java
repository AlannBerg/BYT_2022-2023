package pl.pja.qrcepta.services;

import java.io.File;
import javax.validation.constraints.NotNull;
import pl.pja.qrcepta.model.dto.PrescriptionDTO;
import pl.pja.qrcepta.model.entity.Prescription;

public interface QrCodeService {

  byte[] generateQrPrescription(@NotNull Prescription prescription);

  PrescriptionDTO getPrescriptionDtoFromQRCode(File file);
}
