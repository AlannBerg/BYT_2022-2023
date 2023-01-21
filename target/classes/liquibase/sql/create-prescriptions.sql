--liquibase formatted sql

--changeset aberg:2023-01-15-ab-3
CREATE TABLE public.prescriptions
(
    prescription_id bigserial PRIMARY KEY,
    patient_id      bigint       NOT NULL,
    recommendation  varchar(255) NOT NULL,
    medicines       varchar(255) NOT NULL,
    security_code   varchar(255) NOT NULL,
    status          varchar(30)  NOT NULL,
    qr_code_img     bytea        NOT NULL,
    CONSTRAINT patient_fk FOREIGN KEY (patient_id) REFERENCES user_patient (id),
    CONSTRAINT security_code_prescriptions_id_unique UNIQUE (prescription_id, security_code)
);
