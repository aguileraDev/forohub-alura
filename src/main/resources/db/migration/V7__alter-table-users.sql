alter table users add column is_active boolean default 't' not null;
alter table users add column last_Login timestamp not null;