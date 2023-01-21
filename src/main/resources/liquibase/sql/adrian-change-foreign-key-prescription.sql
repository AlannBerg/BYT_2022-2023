--liquibase formatted sql

--changeset aberg:2023-01-15-ab-6

-- DROP TABLE patient;
-- ALTER TABLE prescriptions DROP CONSTRAINT patient_fk;
-- ALTER TABLE prescriptions ADD CONSTRAINT new_patient_fk_adrian_user FOREIGN KEY (patient_id) REFERENCES user(id);