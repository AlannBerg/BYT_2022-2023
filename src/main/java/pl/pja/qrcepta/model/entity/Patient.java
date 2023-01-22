package pl.pja.qrcepta.model.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@NamedQueries({
  @NamedQuery(
      name = "Patient.findByPesel",
      query = "select patient from Patient patient where patient.peselNo = :pesel")
})
@Table(name = "user_patient")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull
  @ToString.Include
  @Column(name = "firstname")
  private String name;

  @NotNull
  @ToString.Include
  @Column(name = "lastname")
  private String lastname;

  @NotNull
  @Column(name = "pesel", unique = true)
  private String peselNo;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password", unique = true)
  private String firstPassword;


  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserType role;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime created_at;
}
