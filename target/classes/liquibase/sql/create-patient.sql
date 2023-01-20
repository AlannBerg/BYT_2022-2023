--liquibase formatted sql

--changeset aberg:2023-01-15-ab-4
CREATE TABLE public.patient
(
    patient_id   bigserial PRIMARY KEY,
    name         varchar(60) NOT NULL,
    last_name    varchar(60) NOT NULL,
    pesel_number varchar(11) NOT NULL,
    login        varchar(30),
    password     varchar(255),
    constraint unique_pesel UNIQUE (pesel_number),
    constraint login_unique UNIQUE (login)
);
