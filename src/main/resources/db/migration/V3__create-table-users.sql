create table users(
    id serial primary key,
    name varchar(64) unique not null,
    email varchar(32) unique not null,
    password varchar(255) not null
);