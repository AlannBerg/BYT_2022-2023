--liquibase formatted sql

--changeset aberg:2023-01-15-ab-5
CREATE TABLE public.user_patient
(
    id         serial primary key,
    firstname  varchar(50) NOT NULL,
    lastname   varchar(50) NOT NULL,
    email      varchar(50), -- do usuniecia not null u Adriana
    password   varchar(255),-- do usuniecia not null u Adriana
    updated_at timestamp   NOT NULL,
    role       varchar(50) NOT NULL,
    pesel      varchar(50) NOT NULL,
    created_at timestamp   NOT NULL
);