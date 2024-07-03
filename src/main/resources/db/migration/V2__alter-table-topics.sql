alter table topics ADD CONSTRAINT title_unique UNIQUE(title);
alter table topics ADD CONSTRAINT message_unique UNIQUE(message);
