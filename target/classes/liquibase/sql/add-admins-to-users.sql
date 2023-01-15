--liquibase formatted sql

--changeset aberg:2023-01-15-ab-3
insert into public.users (login, password, user_role) values
                                                          ('doctor','d033e22ae348aeb5660fc2140aec35850c4da997','DOCTOR'),
                                                          ('pharmacist','d033e22ae348aeb5660fc2140aec35850c4da997','PHARMACIST'),
                                                          ('admin','d033e22ae348aeb5660fc2140aec35850c4da997','ADMIN');