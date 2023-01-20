--liquibase formatted sql

--changeset aberg:2023-01-15-ab-2
insert into public.users(login, password, user_role, user_name)
values ('doctor', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'DOCTOR', 'admin-doctor'),
       ('farma', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'PHARMACIST', 'admin-pharmacist'),
       ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'ADMIN', 'admin');