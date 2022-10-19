create table if not exists hotels
(
    id      bigserial
    primary key,
    address varchar(255) not null,
    city    varchar(255) not null,
    name    varchar(255) not null
    );

create table if not exists roles
(
    id   bigserial
    primary key,
    name varchar(255) not null
    constraint uk_ofx66keruapi6vyqpv6f2or37
    unique
    );

create table if not exists rooms
(
    id         bigserial
    primary key,
    bed_amount integer  not null,
    number     smallint not null,
    hotel_id   bigint
    constraint fkp5lufxy0ghq53ugm93hdc941k
    references hotels
);

create table if not exists users
(
    id         bigserial
    primary key,
    email      varchar(255) not null
    constraint uk_6dotkott2kjsp8vw4d0m25fb7
    unique,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    role_id    bigint
    constraint fkp56c1712k691lhsyewcssf40f
    references roles
    );

create table if not exists bookings
(
    id        bigserial
    primary key,
    check_in  date not null,
    check_out date not null,
    room_id   bigint
    constraint fkrgoycol97o21kpjodw1qox4nc
    references rooms,
    user_id   bigint
    constraint fkeyog2oic85xg7hsu2je2lx3s6
    references users
);

INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('MANAGER');
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Nick', 'Green', 'nick@mail.com', '$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC', 2);
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Nora', 'White', 'nora@mail.com', '$2a$10$yYQaJrHzjOgD5wWCyelp0e1Yv1KEKeqUlYfLZQ1OQvyUrnEcX/rOy', 2);
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);


INSERT INTO hotels (address, city, name) VALUES ('Незалежності, 40', 'Івано-Франківськ', 'Надія');
INSERT INTO hotels (address, city, name) VALUES ('Гетьмана Мазепи, 146', 'Івано-Франківськ', 'Reikartz Парк Готель');
INSERT INTO hotels (address, city, name) VALUES ('Ґрюнвальдська, 7', 'Івано-Франківськ', 'Grand Hotel Roxolana');
INSERT INTO hotels (address, city, name) VALUES ('В''ячеслава Чорновола, 7', 'Івано-Франківськ', 'Станіславів');
INSERT INTO hotels (address, city, name) VALUES ('Маршала Конєва, 6', 'Київ', 'Favor Park Hotel');
INSERT INTO hotels (address, city, name) VALUES ('Богдана Хмельницького, 31/27', 'Київ', 'Royal Grand Hotel');
INSERT INTO hotels (address, city, name) VALUES ('бульвар Тараса Шевченка, 30', 'Київ', 'Hilton Kyiv');
INSERT INTO hotels (address, city, name) VALUES ('Алли Тарасової, 5', 'Київ', 'Hyatt Regency Kyiv');
INSERT INTO hotels (address, city, name) VALUES ('Єврейська вулиця, 27', 'Одеса', ' Готель California Odesa');
INSERT INTO hotels (address, city, name) VALUES ('Соборна площа, 2', 'Одеса', 'ApartHotel Soborka Garden');
INSERT INTO hotels (address, city, name) VALUES ('Асташкіна, 29/2', 'Одеса', 'Vivmar');
INSERT INTO hotels (address, city, name) VALUES ('Генуезька вулиця, 24A', 'Одеса', 'Atlantic Garden Resort Hotel');
INSERT INTO hotels (address, city, name) VALUES ('Проспект Чорновола, 7', 'Львів', 'Готель Львів');
INSERT INTO hotels (address, city, name) VALUES ('Проспект Свободи, 45', 'Львів', 'Panorama Lviv');
INSERT INTO hotels (address, city, name) VALUES ('Городоцька, 15', 'Львів', 'Astoria');
INSERT INTO hotels (address, city, name) VALUES ('Грабовського, 11', 'Львів', 'Citadel Inn Hotel & Resort');
INSERT INTO hotels (address, city, name) VALUES ('Костромська, 59', 'Рівне', 'Melrose');
INSERT INTO hotels (address, city, name) VALUES ('Соборна, 112', 'Рівне', 'Україна');

INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (100, 1, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (101, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (102, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (103, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (104, 1, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (105, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (211, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (212, 1, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (213, 1, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (214, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (215, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (321, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (322, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (323, 3, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (324, 1, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (325, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (431, 2, 1);
INSERT INTO rooms (number, bed_amount, hotel_id) VALUES (432, 1, 1);