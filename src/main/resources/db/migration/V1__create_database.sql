create table clients
(
    id      bigserial not null
        constraint clients_pkey
            primary key,
    born_at timestamp,
    user_id bigint
        constraint fkuwca24wcd1tg6pjex8lmc0y7
            references users
);

alter table clients
    owner to root;




create table doctors
(
    id             bigserial not null
        constraint doctors_pkey
            primary key,
    specialization varchar(255),
    user_id        bigint
        constraint fke9pf5qtxxkdyrwibaevo9frtk
            references users
);

alter table doctors
    owner to root;



create table treatments
(
    id   bigserial not null
        constraint treatments_pkey
            primary key,
    cost varchar(255),
    name varchar(255)
);

alter table treatments
    owner to root;



create table users
(
    id           bigserial not null
        constraint users_pk
            primary key,
    email        varchar(50),
    password     varchar(200),
    first_name   varchar(30),
    last_name    varchar(50),
    city         varchar(40),
    street       varchar(50),
    house_number varchar(60),
    phone_number varchar(50),
    roles        varchar(255),
    facebookid   varchar(255)
);

alter table users
    owner to root;

create unique index users_email_uindex
    on users (email);

create unique index users_email_uindex_2
    on users (email);

create unique index users_id_uindex
    on users (id);




insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (6, 'recep@gmail.com', '$2a$10$ioH/Yiq4tDC2qNigVGeuOuqvyBoHC3UWbJ0VnXTt3ubdWcj0GVBH2', 'Paweł', 'Recepcjonista', 'Małogoszcz', 'Swietokrzyskaa', 11, '+48 123 123 125', 'ROLE_RECEPTIONIST,ROLE_USER', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (5, 'doktor@gmail.com', '$2a$10$4YaujkVETLGPwIEV0YZerutc6/DCDzlnebs1DVn/8BB7U7yd/u51K', 'Szymon', 'Doktor', 'Małogoszcz', 'Swietokrzyskaa', 1, '+48 123 123 122', 'ROLE_USER,ROLE_DOCTOR', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (1, 'admin@gmail.com', '$2a$10$JNB3xDzToT/XNH6LgJU.lOREp8pN5q4x07p66LrBXHoIt1GEgqW0S', 'Admin', 'Admin', 'Kielce', 'Wiejska', 12, '+48 123 222 999', 'ROLE_USER,ROLE_ADMIN', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (16, 'klient@gmail.com', '$2a$10$hYL6GjxrD4vecCy/SMpD8OOeOsJtMY6lh448SLiOBN1QILOH.olO2', 'Adam', 'Klient', 'Małogoszcz', 'Wrzosówka', 111, '+48 123 123 124', 'ROLE_USER,ROLE_CLIENT', null);

insert into clients (id, born_at, user_id) values (9, '2020-09-02 00:00:00.000000', 16);
insert into doctors (id, specialization, user_id) values (2, 'Konie', 5);
insert into treatments (id, cost, name) values (2, 70, 'Szczepienie');
insert into treatments (id, cost, name) values (3, 120, 'Wizyta kontrolna ');
insert into treatments (id, cost, name) values (5, 60, 'Zdjęcie opatrunków');

insert into visits (id, details, name, scheduled_for, doctor_id, client_id, price, is_payed, external_id) values (21, null, 'Psa boli łapa', '2021-06-02 02:00:00.000000', 2, 9, 120, 'false', null);
insert into visits (id, details, name, scheduled_for, doctor_id, client_id, price, is_payed, external_id) values (16, null, 'Szczepienie psa', '2021-06-18 01:00:00.000000', 2, 9, 10, 'true', '44L7VD8QHM210604GUEST000P01');
insert into visits (id, details, name, scheduled_for, doctor_id, client_id, price, is_payed, external_id) values (19, null, 'Psa boli ucho', '2021-06-09 01:00:00.000000', 2, 9, 100, 'false', '1NWQQ7797N210612GUEST000P01');








