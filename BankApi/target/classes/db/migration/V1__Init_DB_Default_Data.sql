
create table users (id  bigserial not null,
                    balance numeric(19, 2),
                    first_name varchar(255),
                    last_name varchar(255),
                    primary key (id));

insert into users (first_name, last_name, balance) VALUES ('Иван', 'Кулешевич', 10000);
insert into users (first_name, last_name, balance) VALUES ('Егор', 'Турок', 100);
insert into users (first_name, last_name, balance) VALUES ('Сергей', 'Сидарович', 5000);
insert into users (first_name, last_name, balance) VALUES ('Владислав', 'Кулик', 7000);
