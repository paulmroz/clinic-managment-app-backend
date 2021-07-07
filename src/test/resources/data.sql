insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (6, 'recep@gmail.com', '$2a$10$ioH/Yiq4tDC2qNigVGeuOuqvyBoHC3UWbJ0VnXTt3ubdWcj0GVBH2', 'Paweł', 'Recepcjonista', 'Małogoszcz', 'Swietokrzyskaa', 11, '+48 123 123 125', 'ROLE_RECEPTIONIST,ROLE_USER', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (5, 'doktor@gmail.com', '$2a$10$4YaujkVETLGPwIEV0YZerutc6/DCDzlnebs1DVn/8BB7U7yd/u51K', 'Szymon', 'Doktor', 'Małogoszcz', 'Swietokrzyskaa', 1, '+48 123 123 122', 'ROLE_USER,ROLE_DOCTOR', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (1, 'admin@gmail.com', '$2a$10$JNB3xDzToT/XNH6LgJU.lOREp8pN5q4x07p66LrBXHoIt1GEgqW0S', 'Admin', 'Admin', 'Kielce', 'Wiejska', 12, '+48 123 222 999', 'ROLE_USER,ROLE_ADMIN', null);
insert into users (id, email, password, first_name, last_name, city, street, house_number, phone_number, roles, facebookid) values (16, 'klient@gmail.com', '$2a$10$hYL6GjxrD4vecCy/SMpD8OOeOsJtMY6lh448SLiOBN1QILOH.olO2', 'Adam', 'Klient', 'Małogoszcz', 'Wrzosówka', 111, '+48 123 123 124', 'ROLE_USER,ROLE_CLIENT', null);

insert into clients (id, born_at, user_id) values (9, '2020-09-02', 16);

insert into doctors (id, specialization, user_id) values (2, 'Konie', 5);

insert into treatments (id, cost, name) values (2, 70, 'Szczepienie');
insert into treatments (id, cost, name) values (3, 120, 'Wizyta kontrolna ');
insert into treatments (id, cost, name) values (5, 60, 'Zdjęcie opatrunków');




