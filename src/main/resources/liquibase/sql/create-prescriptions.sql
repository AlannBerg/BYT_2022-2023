--liquibase formatted sql

--changeset aberg:2023-01-15-ab-3
CREATE TABLE public.prescriptions
(
    id         bigserial PRIMARY KEY,
    patient_id bigint       NOT NULL,
    diagnosis  varchar(255) NOT NULL,
    medicines  varchar(255) NOT NULL,
    constraint patient_fk foreign key (patient_id) REFERENCES patient (id)
);
