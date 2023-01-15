--liquibase formatted sql

--changeset aberg:2023-01-15-ab-1
CREATE TABLE public.users
(
    id        serial PRIMARY KEY,
    login     varchar(30) not null,
    password  varchar(255) not null,
    user_role varchar(15) not null,
    constraint unique_login UNIQUE (login)
);
