package pl.pja.qrcepta.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Prescription {

  @Id
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "diagosis")
  private String diagnosis;

  @NotNull
  @Column(name = "medicines")
  private String medicines;

  @NotNull
  @Column(name = "patient_id")
  private Long patientID;

  @OneToOne()
  private Patient patient;
}