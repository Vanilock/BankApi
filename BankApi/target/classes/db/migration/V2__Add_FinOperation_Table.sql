create table finance_operations (id  bigserial not null, date date,
                            operation varchar(255) not null,
                            amount numeric(19, 2) not null,
                            user_id int8 not null,
                            primary key (id));

alter table finance_operations add constraint FK_finance_operations_to_users foreign key (user_id) references users;

