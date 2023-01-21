// package pl.pja.qrcepta.model;
//
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.NamedQueries;
// import javax.persistence.NamedQuery;
// import javax.persistence.Table;
// import javax.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.ToString;
//
// @Entity
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @ToString(onlyExplicitlyIncluded = true)
// @NamedQueries({
//  @NamedQuery(
//      name = "Patient.findByPesel",
//      query = "select patient from Patient patient where patient.peselNo = :pesel")
// })
// @Table(name = "patient")
// public class Patient2DB {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "patient_id")
//  private Long id;
//
//  @NotNull
//  @ToString.Include
//  @Column(name = "name")
//  private String name;
//
//  @NotNull
//  @ToString.Include
//  @Column(name = "last_name")
//  private String lastname;
//
//  @NotNull
//  @Column(name = "pesel_number", unique = true)
//  private String peselNo;
// }
