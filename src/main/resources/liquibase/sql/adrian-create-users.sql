--liquibase formatted sql

--changeset aberg:2023-01-15-ab-2
CREATE TABLE public.user_patient
(
    id         bigserial PRIMARY KEY,
    firstname  varchar(50) NOT NULL,
    lastname   varchar(50) NOT NULL,
    role       varchar(50) NOT NULL,
    pesel      varchar(50) NOT NULL,
    updated_at timestamp,
    created_at timestamp,
    password   varchar(255),
    email      varchar(50)
);
