insert into users (id, first_name, last_name, balance) VALUES (1, 'Иван', 'Кулешевич', 10000);
insert into users (id, first_name, last_name, balance) VALUES (2, 'Егор', 'Турок', 300);
insert into users (id, first_name, last_name, balance) VALUES (3, 'Владислав', 'Кулик', 10000);

insert into fin_operations (id, date, operation, amount, user_id) values (1, '2023-10-18', 'withdraw', 1000, 1);
insert into fin_operations (id, date, operation, amount, user_id) values (2, '2023-10-18', 'deposit', 1000, 1);
insert into fin_operations (id, date, operation, amount, user_id, recipient_id) values (3, '2023-10-18', 'transfer', 1000, 2, 1);
