--liquibase formatted sql

--changeset aberg:2023-01-15-ab-6
ALTER TABLE public.prescriptions
    ADD COLUMN creation_date timestamp;