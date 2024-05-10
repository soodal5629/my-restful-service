insert into users(id, join_date, name, password, ssn) values(90001, now(), 'user1', 'test1', '111111-1111111');
insert into users(id, join_date, name, password, ssn) values(90002, now(), 'user2', 'test2', '222222-1111111');
insert into users(id, join_date, name, password, ssn) values(90003, now(), 'user3', 'test3', '333333-1111111');

insert into post(description, user_id) values('my first post', 90001);
insert into post(description, user_id) values('my second post', 90002);