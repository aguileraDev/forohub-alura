create table courses(
    id serial primary key,
    name varchar(32) not null,
    category varchar(32) not null
);

alter table topics
add column course_id int not null,
add constraint course_fk
foreign key(course_id)
references courses(id)
on delete cascade
on update cascade;