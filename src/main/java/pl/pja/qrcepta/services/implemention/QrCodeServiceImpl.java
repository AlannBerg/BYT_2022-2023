package pl.pja.qrcepta.services.implemention;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.dto.PrescriptionDTO;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.QrCodeService;
import javax.imageio.ImageIO;
import pl.pja.qrcepta.utlis.PrescriptionUtils;

@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

  public byte[] generateQrPrescription(@NotNull Prescription prescription) {
    log.info("Generating QR code for prescription {}", prescription);
    try {
      String data = PrescriptionUtils.prescriptionToQrCodeData(prescription);
      int height = 500;
      int width = 500;
      String charset = "UTF-8";
      //      String path = "C:/Users/Alan/Desktop/Projekt studia/Wygenerowane recepty/";
      //      String filename =
      //          id.toString().concat(String.valueOf(LocalTime.now().getMinute())).concat(".jpg");
      //      String pathWithName = path.concat(filename);

      BitMatrix matrix =
          new MultiFormatWriter()
              .encode(
                  new String(data.getBytes(charset), charset),
                  BarcodeFormat.QR_CODE,
                  width,
                  height);
      ByteArrayOutputStream qrCodeByteArr = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(matrix, "jpg", qrCodeByteArr);
      return qrCodeByteArr.toByteArray();
    } catch (Exception e) {
      log.error("Can not generate QR code {}", e.getMessage());
      return null;
    }
  }

  @Override
  public PrescriptionDTO getPrescriptionDtoFromQRCode(@NotNull File file) {
    try {

      Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
      hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      BinaryBitmap binaryBitmap =
          new BinaryBitmap(
              new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
      Result result = new MultiFormatReader().decode(binaryBitmap);
      String data = result.getText();

      Long prescriptionID = Long.valueOf(data.substring(0, data.indexOf("=")));
      String securityCode = data.substring(data.indexOf("=") + 1);
      log.debug("Data from QR CODE id {}, security code {}", prescriptionID, securityCode);
      return PrescriptionDTO.builder().patientID(prescriptionID).securityCode(securityCode).build();
    } catch (Exception e) {
      log.error("Can not read Qr code {}", e.getMessage());
      return null;
    }
  }
}
