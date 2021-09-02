CREATE TABLE users
(
    user_id   int auto_increment primary key,
    name      varchar(15)  not null,
    last_name varchar(30)  not null,
    email     varchar(30)  not null,
    password  varchar(500) not null
);

CREATE TABLE passports
(
    passport_id   int auto_increment primary key,
    seria_number  varchar(50) not null,
    date_of_birth date        not null,
    registration  text        not null,
    user_id       int         not null,
    foreign key (user_id) references users (user_id)
);

CREATE TABLE orders
(
    order_id     int auto_increment primary key,
    user_id      int         not null,
    foreign key (user_id) references users (user_id),
    commentary   text        not null,
    status       varchar(50) not null,
    date_of_rent date        not null
);

CREATE TABLE cars
(
    car_id       int auto_increment primary key,
    brand        varchar(30) not null,
    model        varchar(50) not null,
    color        varchar(20) not null,
    rental_price int         not null,
    available    boolean
);

CREATE TABLE roles
(
    role_id int auto- increment primary key,
    role    varchar(50)
);

CREATE TABLE users_roles
(
    user_role_id int auto_increment primary key,
    user_id      int not null,
    foreign key (user_id) references users (user_id),
    role_id      int not null,
    foreign key (role_id) references roles (role_id)
);