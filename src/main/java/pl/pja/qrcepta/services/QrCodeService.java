package pl.pja.qrcepta.services;

import java.io.File;
import javax.validation.constraints.NotNull;
import pl.pja.qrcepta.model.dto.PrescriptionDTO;

public interface QrCodeService {

  void generateQrPrescription(@NotNull Long id, @NotNull String securityCode);

  PrescriptionDTO getPrescriptionDtoFromQRCode(File file);
}
