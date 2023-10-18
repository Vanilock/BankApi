alter table finance_operations add column recipient_id int8;

alter table finance_operations add constraint FK_finance_operations_recipient_id_to_users foreign key (user_id)
        references users;