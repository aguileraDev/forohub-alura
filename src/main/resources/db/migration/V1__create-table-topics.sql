create table topics(
    id serial primary key,
    title varchar(64) not null,
    message varchar(1024) not null,
    status boolean default 't' not null,
    created_at timestamp not null

);