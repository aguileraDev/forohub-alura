create table responses(
    id serial primary key,
    message varchar(1000) not null,
    solution bool default 'f' not null,
    created_at timestamp not null,
    topic_id int not null,
    author_id int not null,
    constraint topic_fk
    foreign key(topic_id)
    references topics(id)
    on delete cascade
    on update cascade,
    constraint author_fk
    foreign key(author_id)
    references users(id)
    on delete cascade
    on update cascade
);