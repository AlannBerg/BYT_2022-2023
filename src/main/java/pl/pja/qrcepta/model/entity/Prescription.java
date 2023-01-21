package pl.pja.qrcepta.model.entity;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Data
@Entity
@Builder
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
  @NamedQuery(
      name = "Prescription.checkIfSecurityCodeIsValid",
      query =
          "select prescription from Prescription prescription where prescription.securityCode = :securityCode"),
  @NamedQuery(
      name = "Prescription.getByID",
      query = "select prescription from Prescription prescription where prescription.id = :id"),
  @NamedQuery(
      name = "Prescription.getByPatientIDandSecurityCode",
      query =
          "select prescription from Prescription prescription where prescription.patient.id = :patientID and prescription.securityCode = :securityCode")
})
@Table(name = "prescriptions")
public class Prescription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prescription_id")
  private Long id;

  @NotNull
  @ToString.Include
  @Column(name = "recommendation")
  private String recommendation;

  @NotNull
  @ToString.Include
  @Column(name = "medicines")
  private String medicines;

  @NotNull
  @Column(name = "security_code")
  private String securityCode;

  @Lob
  //  @NotNull
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "qr_code_img")
  private byte[] qr_code;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PrescriptionStatus status;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "patient_id")
  private Patient patient;

  //  @ManyToOne(fetch = FetchType.EAGER)
  //  @JoinColumn(name = "patient_id")
  //  private Patient patient;
}
