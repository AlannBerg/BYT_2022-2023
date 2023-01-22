--liquibase formatted sql

--changeset aberg:2023-01-15-ab-1
CREATE TABLE public.users
(
    user_id   serial PRIMARY KEY,
    login     varchar(30)  NOT NULL,
    password  varchar(255) NOT NULL,
    user_role varchar(15)  NOT NULL,
    user_name varchar(30)  NOT NULL,
    constraint unique_login UNIQUE (login)
);
